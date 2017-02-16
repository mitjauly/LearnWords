package umitsoftware.learnwords;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by User on 1/31/2017.
 */

public class UserWordsDB extends SQLiteOpenHelper {

    public enum TranslateDirection {
        ENRU,
        RUEN
    }

    private static final String DATABASE_NAME = "UserWordsDB.db";
    private static final String TABLE_NAME = "UsersWords";


  /*  public static final class WordColumns implements BaseColumns {
        public final static String TABLE_NAME = "UsersWords";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMNRUWORD = "RU";
        public final static String COLUMNENWORD = "name";
        public final static String COLUMNENCOUNT = "name";
        public final static String COLUMNRUCOUNT = "name";
        public final static String COLUMNRUTIME = "name";
        public final static String COLUMNENTIME = "name";

        public static final int GENDER_FEMALE = 0;
        public static final int GENDER_FEMALE = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_UNKNOWN = 2;
    }*/

    public UserWordsDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_USERWORDDB_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + "_ID" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "ENWORD" + " TEXT NOT NULL, "
                + "ENTIME" + " INTEGER NOT NULL, "
                + "ENCOUNT" + " INTEGER NOT NULL DEFAULT 0, "
                + "RUWORD" + " TEXT NOT NULL, "
                + "RUTIME" + " INTEGER NOT NULL, "
                + "RUCOUNT" + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_USERWORDDB_TABLE);
    }

    public static void AddElem(Context context, String EnWord, String RuWord) {
        UserWordsDB dbOpenHelper = new UserWordsDB(context);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        /*SimpleDateFormat frmt = new SimpleDateFormat("yyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());*/
        cv.put("ENWORD",EnWord);
        cv.put("RUWORD",RuWord);
        cv.put("RUTIME",System.currentTimeMillis()/1000);
        cv.put("ENTIME",System.currentTimeMillis()/1000);
        db.insert("UsersWords",null,cv);
        db.close();
    }

    public static int GetSize(Context context) {
       UserWordsDB dbOpenHelper = new UserWordsDB(context);
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT  * FROM " + TABLE_NAME, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        return  cnt;
    }

    public static Stats GetStats(Context context) {
        UserWordsDB dbOpenHelper = new UserWordsDB(context);
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursorAll = db.rawQuery( "SELECT  * FROM " + TABLE_NAME , null);
        Cursor cursorLeant = db.rawQuery( "SELECT  * FROM " + TABLE_NAME + " WHERE RUCOUNT > 2 AND ENCOUNT > 2", null);
        Stats result=new Stats(cursorLeant.getCount(),cursorAll.getCount()-cursorLeant.getCount());
        cursorAll.close();
        cursorLeant.close();
        db.close();
        return  result;
    }

    // Delets all DB(for testing purposes)
    public static void PurgeDB(Context context) {

        UserWordsDB dbOpenHelper = new UserWordsDB(context);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    // Delets selected positions
    public static void DeletePositions(Context context, Set<Integer> IDsList) {
        UserWordsDB dbOpenHelper = new UserWordsDB(context);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        for (Integer id:IDsList) {
            String stringID = String.valueOf(id);
            db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE _id = '" + stringID + "'");
        }
        db.close();
    }

    public static ArrayList<UserWord> GetAll(Context context) {
        ArrayList<UserWord> list= new ArrayList<UserWord>();
        UserWordsDB dbOpenHelper = new UserWordsDB(context);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String[] projection = {
                "_ID",
                "ENWORD",
                "ENCOUNT",
                "RUWORD",
                "RUCOUNT"};

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        try{
        while (cursor.moveToNext()) {
            list.add(new UserWord(
                    cursor.getInt(cursor.getColumnIndex("_ID")),
                    cursor.getString(cursor.getColumnIndex("ENWORD")),
                    cursor.getString(cursor.getColumnIndex("RUWORD")),
                    cursor.getInt(cursor.getColumnIndex("ENCOUNT")),
                    cursor.getInt(cursor.getColumnIndex("RUCOUNT"))
            ));
        }
        } finally {
            cursor.close();
        }
        return list;
    }



    public static UserWord GetNext(Context context,TranslateDirection translateDirection) {
       // UserWord Wordt= new UserWord();
        UserWordsDB dbOpenHelper = new UserWordsDB(context);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        UserWord word=null;
        String[] projection = {
                "_ID",
                "ENWORD",
                "ENCOUNT",
                "RUWORD",
                "RUCOUNT"};

        String selection="";
        String selectionArg="3";
        String sortOrder="";
        if(translateDirection==TranslateDirection.ENRU) {
            selection = "ENCOUNT < ?";
            sortOrder = "ENTIME ";
        }
        else if(translateDirection==TranslateDirection.RUEN){
            selection = "RUCOUNT < ?";
            sortOrder = "RUTIME";
        }else{
            selection = "ENCOUNT < ?";
            sortOrder = "ENTIME";
        }

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                new String[]{selectionArg},
                null,
                null,
                sortOrder);

        try {
            if(cursor.getCount()!=0){
            cursor.moveToFirst();

            word = new UserWord(
                    cursor.getInt(cursor.getColumnIndex("_ID")),
                    cursor.getString(cursor.getColumnIndex("ENWORD")),
                    cursor.getString(cursor.getColumnIndex("RUWORD")),
                    cursor.getInt(cursor.getColumnIndex("ENCOUNT")),
                    cursor.getInt(cursor.getColumnIndex("RUCOUNT"))
            );
            }

        } finally {
            cursor.close();
        }
        return word;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
