package umitsoftware.learnwords;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by umitsoftware on 2/19/2017.
 */

public class AddWordActivity extends AppCompatActivity implements YaTrans.iYandxResponse, AddAdapter.iWordSuggested {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter addAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText editTextEW;
    private EditText editTextRW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        // Action Bar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true); //Back to main button
        editTextEW=(EditText ) findViewById(R.id.editTextEngWord);
        editTextRW=(EditText ) findViewById(R.id.editTextRusWord);

        // list of recently added words
        mRecyclerView = (RecyclerView) findViewById(R.id.rwResentWords);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        addAdapter = new AddAdapter(this.getApplicationContext(),this);
        mRecyclerView.setAdapter(addAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.menuList:
                intent = new Intent(AddWordActivity.this, ViewWordsActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuHelp:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goYandex(View view) {
        String url = "http://translate.yandex.com/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void AddWord(View view) {
        String engWord=editTextEW.getText().toString();
        String rusWord=editTextRW.getText().toString();
        Log.i(rusWord,engWord);
        if(engWord.length() > 0 && rusWord.length() > 0) {
            UserWordsDB.AddElem(this,engWord, rusWord);
            Toast toast = Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.Added) +
                            engWord + " = " +
                            rusWord,
                    Toast.LENGTH_SHORT);
            toast.show();
            new UpdateCards(engWord).execute();
            editTextEW.setText("");
            editTextRW.setText("");
        }
    }

    private class UpdateCards extends AsyncTask {
        String engWord;
        private UpdateCards(String engWord){
            this.engWord=engWord;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            DictionaryWordsDB.MarkAdded(getApplicationContext(),engWord);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRecyclerView.invalidate();
                    addAdapter.notifyDataSetChanged();
                }
            }, 300);
            super.onPostExecute(o);
        }
    }

    @Override
    public void showTranslResult(String translResult,UserWordsDB.TranslateDirection translateDirection) {
        if(translateDirection!=null){
            if(translateDirection== UserWordsDB.TranslateDirection.RUEN){
                editTextEW.setText(translResult);
            } else{
                editTextRW.setText(translResult);
            }
        } else{
            Toast toast = Toast.makeText(getApplicationContext(),
                                         getResources().getString(R.string.TraslationError)+
                                         "( "+translResult+" )"   ,
                                         Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void btnTranslate(View view) {
        YaTrans yaTrans=new YaTrans();
        yaTrans.delegate=this;

        if((editTextRW.length()!=0)&&(editTextEW.length()==0)){
            yaTrans.execute(editTextRW.getText().toString(),"ru-en");
        }
        else{
            yaTrans.execute(editTextEW.getText().toString(),"en-ru");
        }
    }

    @Override
    public void suggestWord(UserWord userWord) {
            editTextEW.setText(userWord.EnWord);
            editTextRW.setText(userWord.RuWord);
    }
}
