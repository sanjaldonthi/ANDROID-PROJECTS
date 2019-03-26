package buddy.bunk.sanjal.bunkbuddy.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import buddy.bunk.sanjal.bunkbuddy.Functionality.Sessions;
import buddy.bunk.sanjal.bunkbuddy.R;

public class Referandearn extends Fragment {

    public Referandearn() {
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
        View v=inflater.inflate(R.layout.fragment_referandearn, container, false);

        TextView refer=v.findViewById(R.id.textView4);
        final Sessions sessions=new Sessions(getContext());
        refer.setText(sessions.getphonenumber());
        ImageView whatsapp=v.findViewById(R.id.whatsapp);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "JUST ORDER");
                    String sAux = "Want to Bunk More \n Use My Referral Code:"+sessions.getphonenumber()+"\n & Claim your reward at the Store & Maintain your attendace perfectly\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id="+getContext().getPackageName();
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "Choose One"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });
        return v;
    }


}
