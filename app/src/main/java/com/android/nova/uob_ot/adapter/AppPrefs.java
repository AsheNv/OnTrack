package com.android.nova.uob_ot.adapter;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by NovA on 2/19/2016.
 */
public class AppPrefs {


    SharedPreferences preferences;

    public AppPrefs(Activity activity) {
        preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }


    public void setStartSt(String startSt) {
        preferences.edit().putString("startSt", startSt).commit();
    }

    public void setEndSt(String endSt) {
        preferences.edit().putString("endSt", endSt).commit();
    }

    public void setStartTime(String startTime) {
        preferences.edit().putString("startTime", startTime).commit();
    }

    public void setEndTime(String endTime) {
        preferences.edit().putString("endTime", endTime).commit();
    }

    public void setDate(String date) {
        preferences.edit().putString("date", date).commit();
    }



    //If user has n ot chose , return default!
    public String getStartSt() {
        return preferences.getString("startSt", "171");
    }

    public String getEndSt() {
        return preferences.getString("endSt", "61");
    }

    public String getStartTime() {
        return preferences.getString("startTime", "11:00:00");
    }

    public String getEndTime() {
        return preferences.getString("endTime", "12:59:59");
    }

    public String getDate() {
        return preferences.getString("date", "2016-02-21");
    }

}
