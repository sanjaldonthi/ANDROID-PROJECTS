package buddy.bunk.sanjal.bunkbuddy;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import buddy.bunk.sanjal.bunkbuddy.Fragments.AddSubjectFragment;
import buddy.bunk.sanjal.bunkbuddy.Fragments.CalenderFragment;
import buddy.bunk.sanjal.bunkbuddy.Fragments.HomeFragment;
import buddy.bunk.sanjal.bunkbuddy.Fragments.Referandearn;
import buddy.bunk.sanjal.bunkbuddy.Fragments.RewardsFragment;
import buddy.bunk.sanjal.bunkbuddy.Fragments.TodoFragment;
import buddy.bunk.sanjal.bunkbuddy.Functionality.Sessions;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigation=(NavigationView)findViewById(R.id.navigation);
        navigation.setItemIconTintList(null);
        navigation.setNavigationItemSelectedListener(this);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        View headerview = navigation.getHeaderView(0);
        final TextView Hname=(TextView)headerview.findViewById(R.id.Hname);
        final TextView Hemail=(TextView)headerview.findViewById(R.id.Hemail);
        final ImageView Hpp=(ImageView)headerview.findViewById(R.id.Hpp);


        Sessions sessions=new Sessions(HomeActivity.this);
        Hname.setText(sessions.getname());
        Hemail.setText(sessions.getemail());


        Fragment fragment=new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.frame_container, fragment).commit();


        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem plus = menu.findItem(R.id.plus);

        plus.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Fragment fragment=null;
                fragment=new AddSubjectFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.frame_container, fragment).commit();

                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        if (mToggle.onOptionsItemSelected(item)) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view =getCurrentFocus();
            if (view == null) {
                view = new View(HomeActivity.this);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return  true;
        }

        return  super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
       Fragment fragment=null;
       if(id==R.id.Nhome){
           fragment=new HomeFragment();

       }
       else if(id==R.id.Ntodo){
           fragment=new TodoFragment();
       }
       else if(id==R.id.Ncalendar){
//           fragment=new CalenderFragment();
           Toast.makeText(HomeActivity.this,"Coming Soon",Toast.LENGTH_SHORT).show();
       }
       else if(id==R.id.Nrefer){
           fragment=new Referandearn();
       }else if(id==R.id.Nrewards){
           fragment=new RewardsFragment();
       }
        else if(id==R.id.Nlogout)
        {
            Sessions session = new Sessions(HomeActivity.this);
            session.setphonenumber("");
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.frame_container, fragment).commit();

        }


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerLayout.closeDrawers();
        return true;
    }
}
