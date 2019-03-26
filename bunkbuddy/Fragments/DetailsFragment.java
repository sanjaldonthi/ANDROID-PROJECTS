package buddy.bunk.sanjal.bunkbuddy.Fragments;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.zip.DeflaterOutputStream;

import buddy.bunk.sanjal.bunkbuddy.Functionality.Sessions;
import buddy.bunk.sanjal.bunkbuddy.Home.Home;
import buddy.bunk.sanjal.bunkbuddy.R;


public class DetailsFragment extends Fragment {


    public DetailsFragment() {
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
        View v=inflater.inflate(R.layout.fragment_details, container, false);

        final String name=getArguments().getString("name");
        final String minpercentage=getArguments().getString("min");


        TextView name1=(TextView)v.findViewById(R.id.name);
        final TextView safebunk=(TextView)v.findViewById(R.id.safebunk);
        final TextView bnumber=(TextView)v.findViewById(R.id.bnumber);
        final TextView tnumber=(TextView)v.findViewById(R.id.tnumber);
        final TextView minimum=(TextView)v.findViewById(R.id.minimum);
        LinearLayout bminus=v.findViewById(R.id.bminus);
        LinearLayout tminus=v.findViewById(R.id.tminus);
        LinearLayout bplus=v.findViewById(R.id.bplus);
        LinearLayout tplus=v.findViewById(R.id.tplus);
        final LinearLayout progressbar=v.findViewById(R.id.progressbar);
        final TextView percentage=v.findViewById(R.id.percentage);

        Button delete=v.findViewById(R.id.delete);




        name1.setText(name);

        final Sessions sessions=new Sessions(getContext());



        bminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref1=FirebaseDatabase.getInstance().getReference().child("Users").child("+91"+sessions.getphonenumber()).child("Subject").child("Subjects").child(name);
                ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Home home = dataSnapshot.getValue(Home.class);
                        int b=Integer.parseInt(home.Bclasses);
                        if(b<=0){
                            Toast.makeText(getContext(),"Bunk Cannot be Negative",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        b--;
                        int t=Integer.parseInt(home.Tclasses);


                        Sessions sessions=new Sessions(getContext());



                        double z=(100-Double.parseDouble(minpercentage))/100;
                        int safe=(int)(t*z);
                        safe=safe-b;



                        bnumber.setText(Integer.toString(b));
                        tnumber.setText(Integer.toString(t));


                        int tclass=Integer.parseInt(tnumber.getText().toString());
                        double per= ((Double.parseDouble(tnumber.getText().toString())-Double.parseDouble(bnumber.getText().toString()))/tclass)*100;
                        per=Double.parseDouble(new DecimalFormat("##.##").format(per));


                        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child("+91"+sessions.getphonenumber()).child("Subject").child("Subjects").child(name);
                        ref.child("Bclasses").setValue(Integer.toString(b));
                        ref.child("Tclasses").setValue(Integer.toString(t));
                        ref.child("Percentage").setValue(Double.toString(per));
                        ref.child("SafeBunk").setValue(Integer.toString(safe));




                        percentage.setText(Double.toString(per)+"%");
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                                progressbar.getLayoutParams();
                        params.weight = (float)Float.parseFloat(Double.toString(per))/100;
                        progressbar.setLayoutParams(params);
                        int tot=Integer.parseInt(sessions.getbunk());
                        tot--;
                        sessions.setbunk(Integer.toString(tot));

                        DatabaseReference ref2=FirebaseDatabase.getInstance().getReference().child("Users").child("+91"+sessions.getphonenumber()).child("Subject");
                        ref2.child("TotalBunk").setValue(Integer.toString(tot));

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


        bplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference ref1=FirebaseDatabase.getInstance().getReference().child("Users").child("+91"+sessions.getphonenumber()).child("Subject").child("Subjects").child(name);
                ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Home home = dataSnapshot.getValue(Home.class);
                        int b=Integer.parseInt(home.Bclasses);
                        b++;
                        int t=Integer.parseInt(home.Tclasses);


                        if(b>t)
                        {
                                Toast.makeText(getContext(),"Bunked Classes More than Total Classes",Toast.LENGTH_SHORT).show();
                                return;
                        }


                        bnumber.setText(Integer.toString(b));
                        tnumber.setText(Integer.toString(t));


                        double z=(100-Double.parseDouble(minpercentage))/100;
                        int safe=(int)(t*z);
                        safe=safe-b;


                        int tclass=Integer.parseInt(tnumber.getText().toString());
                        double per= ((Double.parseDouble(tnumber.getText().toString())-Double.parseDouble(bnumber.getText().toString()))/tclass)*100;
                        per=Double.parseDouble(new DecimalFormat("##.##").format(per));


                        Sessions sessions=new Sessions(getContext());
                        int b1=Integer.parseInt(sessions.getbunk());
                        b1++;
                        sessions.setbunk(Integer.toString(b1));


                        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child("+91"+sessions.getphonenumber()).child("Subject").child("Subjects").child(name);
                        ref.child("Bclasses").setValue(Integer.toString(b));
                        ref.child("Tclasses").setValue(Integer.toString(t));
                        ref.child("Percentage").setValue(Double.toString(per));
                        ref.child("SafeBunk").setValue(Integer.toString(safe));


                        int tot=Integer.parseInt(sessions.getbunk());
                        tot++;
                        sessions.setbunk(Integer.toString(tot));
                        DatabaseReference ref2=FirebaseDatabase.getInstance().getReference().child("Users").child("+91"+sessions.getphonenumber()).child("Subject");
                        ref2.child("TotalBunk").setValue(Integer.toString(tot));


                        percentage.setText(home.Percentage+"%");
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                                progressbar.getLayoutParams();
                        params.weight = (float)Float.parseFloat(home.Percentage)/100;
                        progressbar.setLayoutParams(params);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


        tminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference ref1=FirebaseDatabase.getInstance().getReference().child("Users").child("+91"+sessions.getphonenumber()).child("Subject").child("Subjects").child(name);
                ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Home home = dataSnapshot.getValue(Home.class);

                       int t=Integer.parseInt(home.Tclasses);
                       int b=Integer.parseInt(home.Bclasses);
                        if(t<=0||t<=b){
                            Toast.makeText(getContext(),"Bunk Cannot be Negative",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        t--;

                        tnumber.setText(Integer.toString(t));


                        int tclass=Integer.parseInt(tnumber.getText().toString());
                        double per= ((Double.parseDouble(tnumber.getText().toString())-Double.parseDouble(bnumber.getText().toString()))/tclass)*100;
                        per=Double.parseDouble(new DecimalFormat("##.##").format(per));





                        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child("+91"+sessions.getphonenumber()).child("Subject").child("Subjects").child(name);
                        ref.child("Tclasses").setValue(Integer.toString(t));
                        ref.child("Percentage").setValue(Double.toString(per));


                        percentage.setText(home.Percentage+"%");
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                                progressbar.getLayoutParams();
                        params.weight = (float)Float.parseFloat(home.Percentage)/100;
                        progressbar.setLayoutParams(params);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


        tplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference ref1=FirebaseDatabase.getInstance().getReference().child("Users").child("+91"+sessions.getphonenumber()).child("Subject").child("Subjects").child(name);
                ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Home home = dataSnapshot.getValue(Home.class);
                        int t=Integer.parseInt(home.Tclasses);
                        t++;


                        tnumber.setText(Integer.toString(t));


                        int tclass=Integer.parseInt(tnumber.getText().toString());
                        double per= ((Double.parseDouble(tnumber.getText().toString())-Double.parseDouble(bnumber.getText().toString()))/tclass)*100;
                        per=Double.parseDouble(new DecimalFormat("##.##").format(per));



                        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child("+91"+sessions.getphonenumber()).child("Subject").child("Subjects").child(name);
                        ref.child("Tclasses").setValue(Integer.toString(t));
                        ref.child("Percentage").setValue(Double.toString(per));





                        percentage.setText(home.Percentage+"%");
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                                progressbar.getLayoutParams();
                        params.weight = (float)Float.parseFloat(home.Percentage)/100;
                        progressbar.setLayoutParams(params);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int b1=Integer.parseInt(sessions.getbunk());
                b1=b1-Integer.parseInt(bnumber.getText().toString());;
                sessions.setbunk(Integer.toString(b1));


                DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users").child("+91"+sessions.getphonenumber()).child("Subject").child("Subjects").child(name);
                ref.removeValue();


                Fragment fragment=new HomeFragment();
                FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.frame_container, fragment).commit();

                int c=Integer.parseInt(sessions.getcount());
                c--;
                sessions.setcount(Integer.toString(c));
            }
        });



        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users").child("+91"+sessions.getphonenumber()).child("Subject").child("Subjects").child(name);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Home home = dataSnapshot.getValue(Home.class);

                if(Integer.parseInt(home.SafeBunk)>0){
                    safebunk.setText("You Have "+home.SafeBunk+" Safe Bunks");
                }else {
                    safebunk.setText("Their Are No Safe Bunks Available");
                }

                tnumber.setText(home.Tclasses);
                bnumber.setText(home.Bclasses);
                minimum.setText("Minimum Percentage Required:"+home.MinPercentage);

                percentage.setText(home.Percentage+"%");
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                        progressbar.getLayoutParams();
                params.weight = (float)Float.parseFloat(home.Percentage)*2/100;
                progressbar.setLayoutParams(params);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return v;
    }


}
