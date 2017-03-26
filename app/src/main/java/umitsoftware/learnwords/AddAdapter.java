package umitsoftware.learnwords;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by umitsoftware on 2/19/2017.
 */

public class AddAdapter extends RecyclerView.Adapter<AddAdapter.ViewHolder> {

    private Context context;
    private ArrayList<UserWord> list;
    private static iWordSuggested itemListener;

    public static interface iWordSuggested {
        public void suggestWord(UserWord userWord);
    }

    public AddAdapter(Context cntext,iWordSuggested itemListener){
        this.itemListener=itemListener;
        context=cntext;
    }

    @Override
    public AddAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        list=DictionaryWordsDB.GetSuggestion(context);
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sujest_card,parent,false));
    }

    @Override
    public void onBindViewHolder(AddAdapter.ViewHolder holder, int position) {
       if(position<list.size()){
           holder.id=list.get(position).Id;
           holder.pos=position;
           holder.engWord.setText(list.get(position).EnWord);
           holder.rusWord.setText(list.get(position).RuWord);
           if(list.get(position).EnCount == 1) {
               holder.rl.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
           }
           else
           {
               holder.rl.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
           }
       }
        else {
           holder.engWord.setText("");
           holder.rusWord.setText("");
       }
    }

    @Override
    public int getItemCount() {
        return DictionaryWordsDB.SHOW_NUMBER;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView engWord;
        TextView rusWord;
        int id;
        int pos;
        RelativeLayout rl;

        public ViewHolder(final View itemView ){
            super(itemView);
            engWord=(TextView)itemView.findViewById(R.id.engWordS);
            rusWord=(TextView)itemView.findViewById(R.id.rusWordS);
            rl=(RelativeLayout) itemView.findViewById(R.id.rl);
            itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                itemListener.suggestWord(list.get(pos));
            }
            });
        }
    }
}
