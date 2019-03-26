package buddy.bunk.sanjal.bunkbuddy;

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

import buddy.bunk.sanjal.bunkbuddy.Fragments.HomeFragment;
import buddy.bunk.sanjal.bunkbuddy.Functionality.Sessions;

public class activity_todo extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        navigation=(NavigationView)findViewById(R.id.navigation);
        navigation.setItemIconTintList(null);
        navigation.setNavigationItemSelectedListener(this);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer);





        View headerview = navigation.getHeaderView(0);

        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
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
        else if(id==R.id.Nlogout)
        {
            Sessions session = new Sessions(activity_todo.this);
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
