package buddy.bunk.sanjal.bunkbuddy.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import buddy.bunk.sanjal.bunkbuddy.Functionality.Sessions;
import buddy.bunk.sanjal.bunkbuddy.R;

public class AddSubjectFragment extends Fragment {



    public AddSubjectFragment() {
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
        final View v=inflater.inflate(R.layout.fragment_add_subject, container, false);
        final TextView name=v.findViewById(R.id.name);
        final TextView percentage=v.findViewById(R.id.percentage);
        final TextView tclasses=v.findViewById(R.id.tclasses);
        final TextView bclasses=v.findViewById(R.id.bclasses);
        Button submit=v.findViewById(R.id.submit);
        Button back=v.findViewById(R.id.back);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(name.getText().toString())){
                    Toast.makeText(getContext(),"Enter Name",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(percentage.getText().toString())){
                    Toast.makeText(getContext(),"Enter Percentage",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(tclasses.getText().toString())){
                    Toast.makeText(getContext(),"Enter Total Classes",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(bclasses.getText().toString())){
                    Toast.makeText(getContext(),"Enter Bunked Classes",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(bclasses.getText().toString())>Integer.parseInt(tclasses.getText().toString()))
                {
                    Toast.makeText(getContext(),"Bunked Classes More than Total Classes",Toast.LENGTH_SHORT).show();
                    return;
                }


                Sessions sessions=new Sessions(getContext());

                DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").child("+91"+sessions.getphonenumber()).child("Subject");
                ref=ref.child("Subjects");

                int tclass=Integer.parseInt(tclasses.getText().toString());
                int bclass=Integer.parseInt(bclasses.getText().toString());

                double per= ((Double.parseDouble(tclasses.getText().toString())-Double.parseDouble(bclasses.getText().toString()))/tclass)*100;
                per=Double.parseDouble(new DecimalFormat("##.##").format(per));

                double z=(100-Double.parseDouble(percentage.getText().toString()))/100;
                int safe=(int)(tclass*z);
                safe=safe-bclass;



                ref.child(name.getText().toString()).child("Name").setValue(name.getText().toString());
                ref.child(name.getText().toString()).child("MinPercentage").setValue(percentage.getText().toString());
                ref.child(name.getText().toString()).child("Tclasses").setValue(tclasses.getText().toString());
                ref.child(name.getText().toString()).child("Bclasses").setValue(bclasses.getText().toString());
                ref.child(name.getText().toString()).child("Percentage").setValue(Double.toString(per));
                ref.child(name.getText().toString()).child("SafeBunk").setValue(Integer.toString(safe));

                int c=Integer.parseInt(sessions.getcount());
                c++;
                sessions.setcount(Integer.toString(c));

                Toast.makeText(getContext(),"Successful",Toast.LENGTH_SHORT).show();
                name.setText("");
                percentage.setText("");
                tclasses.setText("");
                bclasses.setText("");
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new HomeFragment();
                FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });






        return v;
    }


    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
