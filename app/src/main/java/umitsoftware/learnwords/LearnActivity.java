package umitsoftware.learnwords;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by umitsoftware on 2/19/2017.
 */

public class LearnActivity extends AppCompatActivity implements AnswerDialog.iAnswer {

    private static final String KEY_TRANSLATE_DIRECTION = "TRANSLATE_DIRECTION";
    private UserWordsDB.TranslateDirection translateDirection = UserWordsDB.TranslateDirection.ENRU;
    private Button btnRus,btnEng,btnAnswer;
    private TextView tvToLearn,tvLearnt;
    private UserWord userWord;
    DialogFragment answerDialog;
    DialogFragment helpDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        answerDialog = new AnswerDialog();
        helpDialog = new HelpDialog();
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
                Bundle args = new Bundle();
                args.putString("str", getResources().getString(R.string.HelpMain));
                helpDialog.setArguments(args);
                helpDialog.show(getFragmentManager(), "TAG");
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

    public void btnAnswer(View view) {
        if (btnAnswer.getText().length() > 0) {
            Bundle args = new Bundle();
            args.putString("str", userWord.EnWord + " = " + userWord.RuWord);
            answerDialog.setArguments(args);
            answerDialog.show(getFragmentManager(), "TAG");
        }
    }

    //shows Statistic of answered word
    private void showToastAfterAnswer(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.listword_card, (ViewGroup) findViewById(R.id.ll));
        layout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        TextView engWord=(TextView)layout.findViewById(R.id.engWord);
        TextView rusWord=(TextView)layout.findViewById(R.id.rusWord);
        ImageView engStar1=(ImageView)layout.findViewById(R.id.ivStarEng1);
        ImageView engStar2=(ImageView)layout.findViewById(R.id.ivStarEng2);
        ImageView engStar3=(ImageView)layout.findViewById(R.id.ivStarEng3);
        ImageView rusStar1=(ImageView)layout.findViewById(R.id.ivStarRus1);
        ImageView rusStar2=(ImageView)layout.findViewById(R.id.ivStarRus2);
        ImageView rusStar3=(ImageView)layout.findViewById(R.id.ivStarRus3);
        if(userWord.EnCount>2) engStar3.setVisibility(View.VISIBLE);
        if(userWord.EnCount>1) engStar2.setVisibility(View.VISIBLE);
        if(userWord.EnCount>0) engStar1.setVisibility(View.VISIBLE);
        if(userWord.RuCount>2) rusStar3.setVisibility(View.VISIBLE);
        if(userWord.RuCount>1) rusStar2.setVisibility(View.VISIBLE);
        if(userWord.RuCount>0) rusStar1.setVisibility(View.VISIBLE);
        layout.setPadding(30,10,30,10);
        engWord.setTextColor(Color.WHITE);engWord.setText(userWord.EnWord);
        rusWord.setTextColor(Color.WHITE);rusWord.setText(userWord.RuWord);
        Toast toast=new Toast(getApplicationContext());
        toast.setView(layout);
        toast.show();
    }

    // Right answer given
    @Override
    public void onRight() {
        if(translateDirection==UserWordsDB.TranslateDirection.ENRU) {
            userWord.EnCount++;
        } else if(translateDirection==UserWordsDB.TranslateDirection.RUEN){
            userWord.RuCount++;
        }
        UserWordsDB.RewriteElem(getApplicationContext(),userWord,translateDirection);
        showToastAfterAnswer();

        if(userWord.EnCount>2&&userWord.RuCount>2){
            Toast toast = Toast.makeText(getApplicationContext(),getResources().getString(R.string.Congratulations), Toast.LENGTH_LONG);
            toast.show();
        }
        else if (translateDirection==UserWordsDB.TranslateDirection.ENRU&&userWord.EnCount>2) {
            Toast toast = Toast.makeText(getApplicationContext(),getResources().getString(R.string.gratsEngLearnt), Toast.LENGTH_SHORT);
            toast.show();

        } else if(translateDirection==UserWordsDB.TranslateDirection.RUEN&&userWord.RuCount>2){
            Toast toast = Toast.makeText(getApplicationContext(),getResources().getString(R.string.gratsRusLearnt), Toast.LENGTH_SHORT);
            toast.show();
        }

        ShowUpdatedData();
    }

    @Override
    public void onWrong() {
        UserWordsDB.RewriteElem(getApplicationContext(),userWord,translateDirection);
        showToastAfterAnswer();
        ShowUpdatedData();
    }
}
