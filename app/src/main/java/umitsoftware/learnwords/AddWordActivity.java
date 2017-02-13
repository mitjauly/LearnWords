package umitsoftware.learnwords;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;
import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class AddWordActivity extends AppCompatActivity {
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
        ab.setDisplayHomeAsUpEnabled(true);//Back to main button


        editTextEW=(EditText ) findViewById(R.id.editTextEngWord);
        editTextRW=(EditText ) findViewById(R.id.editTextRusWord);

        // list of recently added words
        mRecyclerView = (RecyclerView) findViewById(R.id.rwResentWords);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        addAdapter = new AddAdapter();
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
        UserWordsDB.AddElem(this,editTextEW.getText().toString(),editTextRW.getText().toString());
        editTextEW.setText("");
        editTextRW.setText("");


      // text
    }
}
