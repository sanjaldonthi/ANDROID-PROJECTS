package buddy.bunk.sanjal.bunkbuddy.Fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import buddy.bunk.sanjal.bunkbuddy.Functionality.Sessions;
import buddy.bunk.sanjal.bunkbuddy.Home.Home;
import buddy.bunk.sanjal.bunkbuddy.Home.ViewHolder;
import buddy.bunk.sanjal.bunkbuddy.R;


public class HomeFragment extends Fragment {

    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mref;
    Fragment fragment=null;
    Sessions sessions;
    BarChart chart;


    public HomeFragment() {
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
        View v=inflater.inflate(R.layout.fragment_home, container, false);
        final TextView percentage=v.findViewById(R.id.percentage);
        final TextView totalbunk=v.findViewById(R.id.totalbunk);
        chart=v.findViewById(R.id.barchart);


        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    getActivity().finish();
                    System.exit(0);
                    return true;
                }
                return false;
            }
        } );



        sessions=new Sessions(getContext());
//        percentage.setText(sessions.getpercentage());
//        totalbunk.setText(sessions.getbunk());

        final float tot[]=new float[Integer.parseInt(sessions.getcount())];


        mRecyclerView = v.findViewById(R.id.homeview);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mref = mFirebaseDatabase.getReference("Users").child("+91"+sessions.getphonenumber()).child("Subject").child("Subjects");


        chart.getDescription().setEnabled(false);
        setData(Integer.parseInt(sessions.getcount()));
        chart.setFitBars(true);


        DatabaseReference ref3=FirebaseDatabase.getInstance().getReference().child("Users").child("+91"+sessions.getphonenumber()).child("Subject");
        ref3.child("Subjects").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                float sum=0;
                for(DataSnapshot temp:dataSnapshot.getChildren())
                {
                        tot[i++]=Float.parseFloat(temp.child("Percentage").getValue().toString());
                }

                for(i=0;i<tot.length;i++){
                    sum=sum+tot[i];
                }

                percentage.setText(Float.toString(sum/Integer.parseInt(sessions.getcount())));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference ref4=FirebaseDatabase.getInstance().getReference().child("Users").child("+91"+sessions.getphonenumber()).child("Subject");
        ref4.child("Subjects").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                float sum=0;
                for(DataSnapshot temp:dataSnapshot.getChildren())
                {
                    tot[i++]=Float.parseFloat(temp.child("Bclasses").getValue().toString());
                }

                for(i=0;i<tot.length;i++){
                    sum=sum+tot[i];
                }
                totalbunk.setText(Integer.toString((int)sum));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







        return v;
    }


    public  void  setData(long count){
        final ArrayList<BarEntry> yVals = new ArrayList<>();
        final String[] Months=new String[10];

        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child("+91"+sessions.getphonenumber()).child("Subject");
        ref.child("Subjects").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0,j=0;

                for(DataSnapshot temp:dataSnapshot.getChildren())
                {
                        Months[i++]=temp.child("Name").getValue().toString();
                        yVals.add(new BarEntry(j++,Float.parseFloat(temp.child("Percentage").getValue().toString())));


                }



                BarDataSet set=new BarDataSet(yVals,"Data Set");
                set.setColors(ColorTemplate.MATERIAL_COLORS);
                set.setDrawValues(true);
                BarData data=new BarData(set);
                chart.setData(data);
                chart.invalidate();
                chart.animateY(500);


                XAxis xAxis=chart.getXAxis();
                xAxis.setValueFormatter(new MyXAxisValueFormatter(Months));
                xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
                xAxis.setGranularity(1);
//                xAxis.setCenterAxisLabels(true);
//                xAxis.setAxisMinimum(1);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    public class MyXAxisValueFormatter implements IAxisValueFormatter{

        private  String[] mValues;

        public  MyXAxisValueFormatter(String[] Values){
            this.mValues=Values;
        }


        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int)value];
        }


    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Home, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Home, ViewHolder>(
                        Home.class,
                        R.layout.homerow,
                        ViewHolder.class,
                        mref
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Home orders, int position) {
                        viewHolder.setDetails(getContext(),orders.Name,orders.Bclasses,orders.MinPercentage,orders.Percentage,orders.SafeBunk,orders.Tclasses);

                    }

                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(final View v, int position) {

                                TextView name=v.findViewById(R.id.name);
                                TextView min=v.findViewById(R.id.dummy);


                                Fragment fragment=new DetailsFragment();
                                Bundle bundle=new Bundle();
                                bundle.putString("name",name.getText().toString());
                                bundle.putString("min",min.getText().toString());

                                fragment.setArguments(bundle);
                                FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction()
                                        .addToBackStack(null)
                                        .replace(R.id.frame_container, fragment).commit();






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
