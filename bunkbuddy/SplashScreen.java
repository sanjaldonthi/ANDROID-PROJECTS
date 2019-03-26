package buddy.bunk.sanjal.bunkbuddy;

import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import buddy.bunk.sanjal.bunkbuddy.Functionality.Sessions;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Sessions sessions=new Sessions(SplashScreen.this);

        if(!TextUtils.isEmpty(sessions.getphonenumber())){
            Intent myIntent = new Intent(SplashScreen.this,HomeActivity.class);
            SplashScreen.this.startActivity(myIntent);
        }

        Button arrow=(Button)findViewById(R.id.arrow);

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SplashScreen.this,Login.class);
                SplashScreen.this.startActivity(myIntent);
            }
        });
    }
}
