package umitsoftware.learnwords;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by User on 1/30/2017.
 */
public class AddAdapter extends RecyclerView.Adapter<AddAdapter.ViewHolder> {

   // public Set<Integer> selectedItems;
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
     //   selectedItems=new HashSet<>();

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sujest_card,parent,false));
    }

    @Override
    public void onBindViewHolder(AddAdapter.ViewHolder holder, int position) {
       if(position<list.size()){
           holder.id=list.get(position).Id;
           holder.pos=position;
           holder.engWord.setText(list.get(position).EnWord);
           holder.rusWord.setText(list.get(position).RuWord);
           if(list.get(position).EnCount == 1) holder.rl.setBackgroundColor(ContextCompat.getColor(context, R.color.gray)); else  holder.rl.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
       }
        else {
           holder.engWord.setText("");
           holder.rusWord.setText("");
       }


    }

    @Override
    public int getItemCount() {
        return DictionaryWordsDB.SHOW_NUMBER;
        //return DictionaryWordsDB.GetSize(context);
        //DictionaryWordsDB.SHOW_NUMBER;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView engWord;
        TextView rusWord;
        int id;
        int pos;
        View view;
        //boolean selected=false;
        RelativeLayout rl;


    public ViewHolder(final View itemView ){
        super(itemView);
       // view =itemView;
        engWord=(TextView)itemView.findViewById(R.id.engWordS);
        rusWord=(TextView)itemView.findViewById(R.id.rusWordS);
        rl=(RelativeLayout) itemView.findViewById(R.id.rl);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                itemListener.suggestWord(list.get(pos));
            }
        });

        //itemView.setOn
       /* itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, shadowBuilder, v, 0);
                //v.setVisibility(View.INVISIBLE);
                return true;
            }
        });*/

       /* itemView.setOnDragListener(new View.OnDragListener(){

            @Override
            public boolean onDrag(View v, DragEvent event) {


                final int action = event.getAction();

                // Handles each of the expected events
                switch(action) {

                    case DragEvent.ACTION_DRAG_STARTED:


                        return false;

                    case DragEvent.ACTION_DRAG_ENTERED:



                        return true;

                    case DragEvent.ACTION_DRAG_LOCATION:

                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:



                        return true;

                    case DragEvent.ACTION_DROP:


                        // Returns true. DragEvent.getResult() will return true.
                        return true;

                    case DragEvent.ACTION_DRAG_ENDED:



                        return false;

                }




            return false;
            }

            });
        //rusWordC*/
    }

    }
}
