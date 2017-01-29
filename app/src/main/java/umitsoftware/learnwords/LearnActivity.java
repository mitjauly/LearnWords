package umitsoftware.learnwords;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class LearnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_menu, menu);
            return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.menuAddWord:
                intent = new Intent(LearnActivity.this, AddWordActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuList:
                intent = new Intent(LearnActivity.this, ViewWordsActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuHelp:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
