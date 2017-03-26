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
 * Created by umitsoftware on 2/19/2017.
 */

public class HelpDialog extends DialogFragment implements View.OnClickListener {

    private Button btnClose;
    private TextView HelpText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Help!");

        View v = inflater.inflate(R.layout.help_dialog, null);

        btnClose=(Button) v.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(this);

        HelpText=(TextView) v.findViewById(R.id.HelpText);
        HelpText.setOnClickListener(this);

        Bundle mArgs = getArguments();
        HelpText.setText(mArgs.getString("str"));
        return v;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onClick(View v) {
            this.dismiss();
    }
}
