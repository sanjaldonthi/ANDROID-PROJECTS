package buddy.bunk.sanjal.bunkbuddy;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import buddy.bunk.sanjal.bunkbuddy.Functionality.Encryption;
import buddy.bunk.sanjal.bunkbuddy.Functionality.Sessions;

public class activity_register extends AppCompatActivity {

    private Sessions session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        final EditText usn=(EditText)findViewById(R.id.usnregister);
        final EditText password=(EditText)findViewById(R.id.passwordregister);
        final EditText repassword=(EditText)findViewById(R.id.repasswordregister);
        final EditText email=(EditText)findViewById(R.id.emailregister);
        final EditText referal=(EditText)findViewById(R.id.referral);
        Button registernext=(Button)findViewById(R.id.registernext);
            session=new Sessions(activity_register.this);
        registernext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(TextUtils.isEmpty(usn.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"Enter Mobile Number",Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(password.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"Enter Mobile Number",Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(repassword.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"Enter Mobile Number",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(email.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"Enter Mobile Number",Toast.LENGTH_LONG).show();
                    return;
                }
                DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child("+91"+session.getphonenumber());
                String hash= null;
                try {
                    hash = Encryption.encrypt(password.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String device_unique_id = Settings.Secure.getString(activity_register.this.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                ref.child("IMEI").setValue(device_unique_id);
                ref.child("Password").setValue(hash);
                ref.child("Usn").setValue(usn.getText().toString());
                ref.child("Email").setValue(email.getText().toString());
                ref.child("Role").setValue("user");
                ref.child("Rewards").setValue("0");
                ref.child("Phone").setValue(session.getphonenumber());
                String refer=referal.getText().toString();
                Sessions sessions=new Sessions(activity_register.this);
                sessions.setname(usn.getText().toString());
                sessions.setemail(email.getText().toString());
                sessions.setpercentage("0");
                sessions.setbunk("0");
                sessions.setcount("0");
                if(refer.equals(""))
                    refer="empty";
                ref.child("Referral").setValue(refer);
                Intent intent=new Intent(activity_register.this,HomeActivity.class);
                startActivity(intent);
                finish();

                Intent myIntent = new Intent(activity_register.this,activity_otp.class);
                activity_register.this.startActivity(myIntent);
                }
        });

    }
}
