package umitsoftware.learnwords;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LearnActivity extends AppCompatActivity {

    private static final String KEY_TRANSLATE_DIRECTION = "TRANSLATE_DIRECTION";
    private UserWordsDB.TranslateDirection translateDirection = UserWordsDB.TranslateDirection.ENRU;
    private Button btnRus,btnEng,btnAnswer;
    private TextView tvToLearn,tvLearnt;
    private UserWord userWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        btnEng=(Button )findViewById(R.id.btnEnglish);
        btnRus=(Button )findViewById(R.id.btnRussian);
        btnAnswer=(Button )findViewById(R.id.btnAnswer);
        tvToLearn=(TextView) findViewById(R.id.tvToLearn);
        tvLearnt=(TextView) findViewById(R.id.tvLearnt);
        if (savedInstanceState != null) {
            translateDirection = savedInstanceState.getInt(KEY_TRANSLATE_DIRECTION, 0)==0?
                    UserWordsDB.TranslateDirection.ENRU: UserWordsDB.TranslateDirection.RUEN;
        }
        ShowUpdatedData();
    }

    private void ShowUpdatedData(){
        if(translateDirection==UserWordsDB.TranslateDirection.ENRU){
            btnEng.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
            btnEng.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            btnRus.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            btnRus.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
            userWord=UserWordsDB.GetNext(getApplicationContext(), UserWordsDB.TranslateDirection.ENRU);
            if(userWord!=null){
                btnAnswer.setText(userWord.EnWord);
            } else{
                btnAnswer.setText("");
            }
        }
        else if(translateDirection==UserWordsDB.TranslateDirection.RUEN){
            btnRus.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
            btnRus.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            btnEng.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            btnEng.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
            userWord=UserWordsDB.GetNext(getApplicationContext(), UserWordsDB.TranslateDirection.RUEN);
            if(userWord!=null){
                btnAnswer.setText(userWord.RuWord);
            } else{
                btnAnswer.setText("");
            }
        }
        Stats stats= UserWordsDB.GetStats(getApplicationContext());
        tvLearnt.setText(Integer.toString(stats.learnt));
        tvToLearn.setText(Integer.toString(stats.toLearn));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_TRANSLATE_DIRECTION, (translateDirection== UserWordsDB.TranslateDirection.ENRU) ? 0 :1 );
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

    public void btnSwRussian(View view) {
        translateDirection = UserWordsDB.TranslateDirection.RUEN;
        ShowUpdatedData();
    }

    public void btnSwEnglish(View view) {
        translateDirection = UserWordsDB.TranslateDirection.ENRU;
        ShowUpdatedData();
    }
}
