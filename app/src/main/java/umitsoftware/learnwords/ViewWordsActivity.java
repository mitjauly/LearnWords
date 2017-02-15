package umitsoftware.learnwords;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ViewWordsActivity extends AppCompatActivity {
    private TextView textView3;
    private RecyclerView mRecyclerView;
    private WordListAdapter wordListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vew_words);

        // Action Bar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);//Back to main button

        // Creating list of Cards
        mRecyclerView = (RecyclerView) findViewById(R.id.rwWordsList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Context aa=getBaseContext();

        wordListAdapter = new WordListAdapter(getApplicationContext());

        mRecyclerView.setAdapter(wordListAdapter);



        textView3=(TextView ) findViewById(R.id.textView3);
        ArrayList<UserWord> list ;
        list=UserWordsDB.GetAll(getBaseContext());
     /*   for (UserWord word: list) {
           textView3.append(word.EnWord+" "+word.RuWord);
        }*/

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        return true;
    }

    public void purgeButtonClick(View view) {
        //wordListAdapter.clearSelected();
        new DeleteSelected().execute();
       // UserWordsDB.PurgeDB(getBaseContext());
    }

    private class DeleteSelected extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            wordListAdapter.clearSelected();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            finish();
            startActivity(getIntent());
            //wordListAdapter.notifyDataSetChanged();
            super.onPostExecute(o);
        }
    }
}
