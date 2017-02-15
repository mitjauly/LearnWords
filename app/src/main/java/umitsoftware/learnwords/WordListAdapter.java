package umitsoftware.learnwords;

import android.app.Application;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {
    private  Context context;
    private ArrayList<UserWord> list;
    public Set<Integer> selectedItems;

    public  WordListAdapter(Context cntext){
 context=cntext;
    }

    public void clearSelected(){


        //new DeleteSelected().execute(context,selectedItems);
      UserWordsDB.DeletePositions(context, selectedItems);
     //   this.notifyDataSetChanged();

    }



    @Override
    public WordListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        list=UserWordsDB.GetAll(context);
        selectedItems=new HashSet<>();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listword_card,parent,false));


    }

    @Override
    public void onBindViewHolder(WordListAdapter.ViewHolder holder, int position) {
        holder.id=list.get(position).Id;
        holder.pos=position;
        holder.engWord.setText(list.get(position).EnWord);
        holder.rusWord.setText(list.get(position).RuWord);
        if(list.get(position).EnCount>2) holder.engStar3.setVisibility(View.VISIBLE);
        if(list.get(position).EnCount>1) holder.engStar2.setVisibility(View.VISIBLE);
        if(list.get(position).EnCount>0) holder.engStar1.setVisibility(View.VISIBLE);
        if(list.get(position).RuCount>2) holder.rusStar3.setVisibility(View.VISIBLE);
        if(list.get(position).RuCount>1) holder.rusStar2.setVisibility(View.VISIBLE);
        if(list.get(position).RuCount>0) holder.rusStar1.setVisibility(View.VISIBLE);
        if((list.get(position).EnCount>2)&&(list.get(position).RuCount>2)) {
            holder.ll.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        }
        /*if(holder.selected) holder.engStar1.setVisibility(View.VISIBLE);
        else holder.engStar1.setVisibility(View.INVISIBLE);*/
        //list[position].
    }

    @Override
    public int getItemCount() {

        return UserWordsDB.GetSize(context);

    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView engWord;
        TextView rusWord;
        ImageView engStar1;
        ImageView engStar2;
        ImageView engStar3;
        ImageView rusStar1;
        ImageView rusStar2;
        ImageView rusStar3;
        LinearLayout ll;
        int id;
        int pos;
        boolean selected=false;

    public ViewHolder(final View itemView ){

        super(itemView);

        engWord=(TextView)itemView.findViewById(R.id.engWordC);
        rusWord=(TextView)itemView.findViewById(R.id.rusWordC);
        engStar1=(ImageView)itemView.findViewById(R.id.ivStarEng1);
        engStar2=(ImageView)itemView.findViewById(R.id.ivStarEng2);
        engStar3=(ImageView)itemView.findViewById(R.id.ivStarEng3);
        rusStar1=(ImageView)itemView.findViewById(R.id.ivStarRus1);
        rusStar2=(ImageView)itemView.findViewById(R.id.ivStarRus2);
        rusStar3=(ImageView)itemView.findViewById(R.id.ivStarRus3);
        ll=(LinearLayout) itemView.findViewById(R.id.ll);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                // item clicked
                selected=!selected;
                if(selected) {
                    ll.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
                    selectedItems.add(id);
                }
                else
                {
                    ll.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                    selectedItems.remove(id);
                }

                //engWord.setText(Integer.toString(id));
                //ll.setBackgroundColor(Color.BLACK);
            }
        });

        }

    }
}
