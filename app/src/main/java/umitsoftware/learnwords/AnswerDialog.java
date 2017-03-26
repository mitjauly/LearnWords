package umitsoftware.learnwords;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by User on 2/19/2017.
 */

public class AnswerDialog extends DialogFragment implements View.OnClickListener {

    private String rightAnswer;
    private Button btnWrongAnswer,btnRightAnswer;
    private TextView tvRightAnswer;
    private iAnswer mListener;



    public static interface iAnswer {
        public void onRight();
        public void onWrong();
    }

    /*@Override
    public void onAttach(Activity activity) {
        mListener = (iAnswer) activity;
        super.onAttach(activity);
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Title!");

        View v = inflater.inflate(R.layout.answer_dialog, null);
        btnWrongAnswer=(Button)v.findViewById(R.id.btnWrongAnswer);
        btnWrongAnswer.setOnClickListener(this);
        btnRightAnswer=(Button)v.findViewById(R.id.btnRightAnswer);
        btnRightAnswer.setOnClickListener(this);

        v.findViewById(R.id.btnRightAnswer).setOnClickListener(this);
        tvRightAnswer=(TextView)v.findViewById(R.id.tvRightAnswer);

        Bundle mArgs = getArguments();
        tvRightAnswer.setText(mArgs.getString("str"));
       // mListener = (iAnswer) Activity;

        //String rightAnswer = mArgs.getString("str");

       // getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return v;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

    }

    @Override
    public void onDismiss(DialogInterface dialog) {

        super.onDismiss(dialog);


    }



    @Override
    public void onClick(View v) {
        if(((Button )v).getId()==btnRightAnswer.getId()){
            Activity activity = getActivity();
            if(activity instanceof iAnswer)
                ((iAnswer)activity).onRight();
            this.dismiss();

        }

        if(((Button )v).getId()==btnWrongAnswer.getId()){
            Activity activity = getActivity();
            if(activity instanceof iAnswer)
                ((iAnswer)activity).onWrong();
            this.dismiss();
        }

    }
}
