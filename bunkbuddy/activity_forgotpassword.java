package buddy.bunk.sanjal.bunkbuddy;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import buddy.bunk.sanjal.bunkbuddy.Functionality.Encryption;

public class activity_forgotpassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        final EditText number=findViewById(R.id.number);
        final EditText password=findViewById(R.id.password);
        final EditText cpassword=findViewById(R.id.cpassword);
        Button submit=findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(number.getText().toString())){
                    Toast.makeText(activity_forgotpassword.this,"Enter Number",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(activity_forgotpassword.this,"Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(cpassword.getText().toString())){
                    Toast.makeText(activity_forgotpassword.this,"Enter Confirm Password",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.getText().toString().equals(cpassword.getText().toString())){
                    Toast.makeText(activity_forgotpassword.this,"Passwords Doesnt Match",Toast.LENGTH_SHORT).show();
                    return;
                }


                DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").child("+91"+number.getText().toString());
                try {
                    ref.child("Password").setValue(Encryption.encrypt(password.getText().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                number.setText("");
                password.setText("");
                cpassword.setText("");
                Toast.makeText(activity_forgotpassword.this,"Password Changed Successfully",Toast.LENGTH_SHORT).show();

            }
        });



    }
}
