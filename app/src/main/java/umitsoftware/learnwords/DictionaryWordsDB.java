package umitsoftware.learnwords;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by User on 2/20/2017.
 */

public class DictionaryWordsDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FrUsedWordsDB.sqlite";
    private static final String TABLE_NAME = "FrUsedWords";
    public static final int SHOW_NUMBER = 478;


    public DictionaryWordsDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
        if(!checkDB(context)){
            try {
                copyDB(context);

            } catch (IOException e) {
                Log.i("DB","Erron on coppy DB");
                throw new Error("Error copying database ");

            }

        }

    }

    private boolean checkDB(Context context){
       /* SQLiteDatabase chkDB=null;
        try {
            chkDB = SQLiteDatabase.openDatabase(GetPath(context)+DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
            //chkDBdisk = SQLiteDatabase.openDatabase(mContext.getAssets().open(DB_NAME));
        } catch (SQLiteException e) {
            Log.i("DB", " doesn't exist");//база еще не существует
        }
        if (chkDB != null) {
            chkDB.close();
            return true;
        }
        return false;*/
        File dbFile = new File(getPath(context) + DATABASE_NAME);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        Log.i("DB exists?",Boolean.toString(dbFile.exists()));
        Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    private String getPath(Context context){
        String DB_PATH;
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        return DB_PATH;
    }


    private void copyDB(Context context) throws IOException {
        //Открываем локальную БД как входящий поток
        //String DB_PATH=GetPath(context);

        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        Log.v("myInput", myInput + "   ");
        OutputStream myOutput = new FileOutputStream(getPath(context)+DATABASE_NAME);
        Log.i("DB","we here");
        Log.v("myOutput", myOutput + "   ");


        //перемещаем байты из входящего файла в исходящий
        byte[] buffer = new byte[4096];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //закрываем потоки
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }




    public static ArrayList<UserWord> GetSuggestion(Context context) {
        ArrayList<UserWord> list = new ArrayList<UserWord>();
        DictionaryWordsDB dbOpenHelper = new DictionaryWordsDB(context);
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        String[] projection = {
                "_ID",
                "ENWORD",
                "RUWORD",
                "ST"
        };
        //String orderBy="";//"RANDOM() LIMIT "+Integer.toString(SHOW_NUMBER);
        String selection="ST IS NULL OR ST = ?";
        String selectionArg="0";

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                null,
                null,
                //selection,
                //new String[]{selectionArg},
                null,
                null,
                null);

        int nmb=0;
        try {
            while (cursor.moveToNext()&&nmb<SHOW_NUMBER+1){
                list.add(new UserWord(
                        cursor.getInt(cursor.getColumnIndex("_ID")),
                        cursor.getString(cursor.getColumnIndex("ENWORD")),
                        cursor.getString(cursor.getColumnIndex("RUWORD")),
                        cursor.getInt(cursor.getColumnIndex("ST")),
                        0));
                        nmb++;
            }
        } finally {
            cursor.close();
        }
        Log.i("list size",list.toString());
        Log.i("list size",list.get(0).EnWord+list.get(0).RuWord);
        return list;
    }

    public static int GetSize(Context context) {
        DictionaryWordsDB dbOpenHelper = new DictionaryWordsDB(context);
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null);
        int cnt = cursor.getCount();
        cursor.close();
        db.close();
        return  cnt;
    }

    public static void MarkAdded(Context context, String engWord) {
        DictionaryWordsDB dbOpenHelper = new DictionaryWordsDB(context);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ST",1);

        db.update(TABLE_NAME, cv, "ENWORD = ?" ,new String[] { engWord });

        db.close();

    }

    public static void PurgeData(Context context) {
        DictionaryWordsDB dbOpenHelper = new DictionaryWordsDB(context);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ST",0);

        db.update(TABLE_NAME, cv, null, null );

        db.close();

    }

    public static void UnmarkDeleted(Context context, String engWord) {
        Log.i("lissssst index",engWord);

        DictionaryWordsDB dbOpenHelper = new DictionaryWordsDB(context);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ST", 0);

        db.update(TABLE_NAME, cv, "ENWORD = ?" ,new String[] { engWord });

        db.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

     //   SQLiteDatabase chkDB = null;


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public synchronized void close()
    {
        super.close();
    }
}
