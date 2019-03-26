package buddy.bunk.sanjal.bunkbuddy.Todo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

    public void setDetails(Context ctx, String Pushid, String Task, String Status) {

        TextView done = mView.findViewById(R.id.done);
        TextView undone = mView.findViewById(R.id.undone);
        TextView task = mView.findViewById(R.id.task);
        TextView remove = mView.findViewById(R.id.remove);

        TextView dummy = (TextView) mView.findViewById(R.id.dummy);

        task.setText(Task);
        dummy.setText(Pushid);

        done.setVisibility(View.GONE);
        undone.setVisibility(View.GONE);

        if(!TextUtils.isEmpty(Status)) {
            if (Status.equals("Undone")) {
                done.setVisibility(View.GONE);
                undone.setVisibility(View.VISIBLE);
            }

            if (Status.equals("Done")) {
                done.setVisibility(View.VISIBLE);
                undone.setVisibility(View.GONE);
            }
        }


    }

    public void setOnClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);

        void onItemLongClick(View v, int position);
    }
}