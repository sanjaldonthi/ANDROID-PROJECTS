package buddy.bunk.sanjal.bunkbuddy;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import buddy.bunk.sanjal.bunkbuddy.Functionality.Encryption;
import buddy.bunk.sanjal.bunkbuddy.Functionality.Sessions;

public class Login extends AppCompatActivity {

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private Sessions session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        TextView forgotpassword = (TextView) findViewById(R.id.forgotpassword);
        Button login = (Button) findViewById(R.id.login);
        Button register = (Button) findViewById(R.id.registernext);

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Login.this, activity_forgotpassword.class);
                Login.this.startActivity(myIntent);
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Login.this, activity_otp.class);
                Login.this.startActivity(myIntent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(username.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Enter Mobile Number", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_LONG).show();
                    return;
                }

                database.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // TODO: handle the post here
                        if (dataSnapshot.hasChild("+91" + username.getText().toString())) {
                            DatabaseReference ref = database.child("Users").child("+91" + username.getText().toString());
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    // TODO: handle the post here
                                    User user = dataSnapshot.getValue(User.class);
                                    String hash = null;
                                    try {
                                        hash = Encryption.decrypt(user.Password);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (password.getText().toString().equals(hash)) {
                                        session = new Sessions(Login.this);
                                        session.setphonenumber(username.getText().toString());
                                        session.setemail(user.Email);
                                        session.setname(user.Usn);


                                        DatabaseReference ref1=FirebaseDatabase.getInstance().getReference().child("Users").child("+91"+username.getText().toString()).child("Subject").child("Subjects");
                                        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                session.setcount(Long.toString(dataSnapshot.getChildrenCount()));


                                                Intent intent = new Intent(Login.this, HomeActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });





                                    }
                                    else
                                        {
                                        Toast.makeText(getApplicationContext(), "Password Doesn't Match", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "User not Registered", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });


            }
        });
    }



    @IgnoreExtraProperties
    public  static class User {

        public String Password;
        public String Userid;
        public String Usn;
        public String Email;



        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String Password, String Userid,String Usn,String Email) {
            this.Password = Password;
            this.Userid = Userid;
            this.Email = Email;
            this.Usn=Usn;
        }


    }
}