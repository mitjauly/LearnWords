package umitsoftware.learnwords;

import android.content.ClipData;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by User on 1/30/2017.
 */
public class AddAdapter extends RecyclerView.Adapter<AddAdapter.ViewHolder> {
    @Override
    public AddAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recentadded_card,parent,false));
    }

    @Override
    public void onBindViewHolder(AddAdapter.ViewHolder holder, int position) {
        Random rn = new Random();
holder.engWord.setText(Integer.toString(position));
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView engWord;
        RelativeLayout rl;
    public ViewHolder(final View itemView ){

        super(itemView);
        engWord=(TextView)itemView.findViewById(R.id.rusWordC);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                // item clicked
                rl=(RelativeLayout) itemView.findViewById(R.id.rl);
                rl.setBackgroundColor(Color.BLACK);
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, shadowBuilder, v, 0);
                //v.setVisibility(View.INVISIBLE);
                return true;
            }
        });

        itemView.setOnDragListener(new View.OnDragListener(){

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
        //rusWordC
    }

    }
}
