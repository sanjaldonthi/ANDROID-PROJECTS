package buddy.bunk.sanjal.bunkbuddy.Home;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import buddy.bunk.sanjal.bunkbuddy.R;

public  class ViewHolder extends RecyclerView.ViewHolder {
    View mView;

    private ClickListener mClickListener;

    public ViewHolder(View itemView) {

        super(itemView);
        mView = itemView;
        //Item Click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });

        //Item Long Click
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });
    }

    public void setDetails(Context ctx, String Name, String Bclasses, String MinPercentage, String Percentage, String SafeBunk, String Tclasses) {

        TextView name=mView.findViewById(R.id.name);
        TextView percentage=mView.findViewById(R.id.percentage);
        TextView dummy=mView.findViewById(R.id.dummy);
        dummy.setText(MinPercentage);
        name.setText(Name);
        percentage.setText(Percentage);

    }


    public void setOnClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);

        void onItemLongClick(View v, int position);
    }
}
