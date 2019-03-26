package buddy.bunk.sanjal.bunkbuddy.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import buddy.bunk.sanjal.bunkbuddy.Functionality.Sessions;
import buddy.bunk.sanjal.bunkbuddy.R;
import buddy.bunk.sanjal.bunkbuddy.Todo.ViewHolder;
import buddy.bunk.sanjal.bunkbuddy.Todo.todo;

public class TodoFragment extends Fragment {

    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mref;
    Fragment fragment=null;
    Sessions sessions;
    public TodoFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_todo, container, false);
        mRecyclerView = v.findViewById(R.id.todoview);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        sessions = new Sessions(getContext());
        mref = mFirebaseDatabase.getReference("Users").child("+91"+sessions.getphonenumber()).child("Todo");


        final EditText task=v.findViewById(R.id.task);
        Button add=v.findViewById(R.id.add);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(task.getText().toString())){
                    Toast.makeText(getContext(),"Enter The Task",Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child("+91"+sessions.getphonenumber()).child("Todo").push();
                ref.child("Pushid").setValue(ref.getKey().toString());
                ref.child("Task").setValue(task.getText().toString());
                ref.child("Status").setValue("Undone");
                task.setText("");
            }
        });




        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<todo, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<todo, ViewHolder>(
                        todo.class,
                        R.layout.todorow,
                        ViewHolder.class,
                        mref
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, todo orders, int position) {
                        viewHolder.setDetails(getContext(),orders.Pushid,orders.Task,orders.Status);

                    }

                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(final View v, int position) {

                                final TextView done=v.findViewById(R.id.done);
                                final TextView undone=v.findViewById(R.id.undone);
                                TextView remove=v.findViewById(R.id.remove);
                                final TextView dummy=v.findViewById(R.id.dummy);

                                undone.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        done.setVisibility(View.VISIBLE);
                                        undone.setVisibility(View.GONE);
                                        DatabaseReference ref1=FirebaseDatabase.getInstance().getReference().child("Users").child("+91"+sessions.getphonenumber()).child("Todo").child(dummy.getText().toString());
                                        ref1.child("Status").setValue("Done");
                                    }
                                });

                                remove.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        DatabaseReference ref1=FirebaseDatabase.getInstance().getReference().child("Users").child("+91"+sessions.getphonenumber()).child("Todo");
                                        ref1.child(dummy.getText().toString()).removeValue();
                                    }
                                });



                            }

                            @Override
                            public void onItemLongClick(View v, int position) {

                            }
                        });
                        return viewHolder;
                    }
                };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

}
