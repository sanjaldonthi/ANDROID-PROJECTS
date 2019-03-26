package buddy.bunk.sanjal.bunkbuddy.Functionality;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Sessions {

    private SharedPreferences prefs;

    public Sessions(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setphonenumber(String usename)
    {
        prefs.edit().putString("phonenumber", usename).commit();
    }

    public String getphonenumber() {
        String usename = prefs.getString("phonenumber","");
        return usename;
    }

    public void setemail(String email){
        prefs.edit().putString("email", email).commit();
    }

    public String getemail() {
        String usename = prefs.getString("email","");
        return usename;
    }

    public void setname(String email){
        prefs.edit().putString("name", email).commit();
    }

    public String getname() {
        String usename = prefs.getString("name","");
        return usename;
    }

    public void setpercentage(String email){
        prefs.edit().putString("percentage", email).commit();
    }

    public String getpercentage() {
        String usename = prefs.getString("percentage","");
        return usename;
    }

    public void setbunk(String email){
        prefs.edit().putString("bunk", email).commit();
    }

    public String getbunk() {
        String usename = prefs.getString("bunk","");
        return usename;
    }

    public void setcount(String email){
        prefs.edit().putString("count", email).commit();
    }

    public String getcount() {
        String usename = prefs.getString("count","");
        return usename;
    }

}
