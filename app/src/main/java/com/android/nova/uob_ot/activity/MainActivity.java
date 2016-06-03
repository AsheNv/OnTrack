package com.android.nova.uob_ot.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nova.uob_ot.R;
import com.android.nova.uob_ot.adapter.AppController;
import com.android.nova.uob_ot.model.Trains;
import com.android.nova.uob_ot.services.JsonUrls;
import com.android.nova.uob_ot.adapter.CustomListviewAdapter;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class MainActivity extends BaseActivity {

    private CustomListviewAdapter adapter;
    private ArrayList<Trains> train = new ArrayList<>();
    private TextView selectedStartSt;
    private TextView selectedEndSt;
    private TextView selectedStartTime;
    private TextView selectedEndTime;
    private TextView selectedDate;
    private ListView listView;
    JsonUrls jsonUrls = new JsonUrls();

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private String from_st;
    private String to_st;
    private String from_time;
    private String to_time;
    private String pick_day;

    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
        // titles
        // from
        // strings.xml

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        // strings.xml

        set(navMenuTitles, navMenuIcons);

        //getting refs
        listView = (ListView) findViewById(R.id.list);
        listView.setEmptyView(findViewById(R.id.empty_list_item));

            adapter = new CustomListviewAdapter(MainActivity.this, R.layout.list_row, train);
            // listView.setAdapter(adapter);


            listView.setAdapter(adapter);





        // send info to the next activity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        from_st = extras.getString("FROM_ST");
        to_st = extras.getString("TO_ST");
        from_time = extras.getString("FROM_TIME");
        to_time = extras.getString("TO_TIME");
        pick_day = extras.getString("PICK_DAY");


        Log.v("from", from_st);





        showEvents(from_st,to_st,pick_day,from_time,to_time);


    }

    private void getTrains (String startSt, String endSt, String date, String startTime, String endTime )
    {
        train.clear();


        //initialize
        String fromS = startSt;
        String toS = endSt;
        String fromT = startTime;
        String toT = endTime;
        String opDayT = date;



        String finalUrl = jsonUrls.TRAIN_FROM_ST +fromS+jsonUrls.TRAIN_TO_ST +toS
                +jsonUrls.TRAIN_FROM_TIME +fromT+jsonUrls.TRAIN_TO_TIME +toT+jsonUrls.TRAIN_OP_DAY+opDayT;


        Log.v("GGG", finalUrl);

        // call the loading dialog
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        // get the data using json request
        JsonObjectRequest trainReq = new JsonObjectRequest(Request.Method.GET, finalUrl, (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        hidePDialog();




                        try {

                            JSONArray trainListArray = response.getJSONArray("Result");





                            for (int i =0; i < trainListArray.length(); i++)
                            {
                                JSONObject jsonObject = trainListArray.getJSONObject(i);

                                String trainIdText = jsonObject.getString("TripID");
                                String tripIdText = jsonObject.getString("TrainID");
                                String trainFreqText = jsonObject.getString("OpDay");
                                String startStNameText = jsonObject.getString("StartSt");
                                String endStNameText = jsonObject.getString("EndSt");
                                String searchStId = jsonObject.getString("SID");
                                String arrivalTimeEndStText = jsonObject.getString("EndStTime");
                                String arrivalTimeStartStText = jsonObject.getString("StartStTime");
                                String arrivalTimeStText = jsonObject.getString("ArrivalTime");
                                String depTimeText = jsonObject.getString("DepTime");
                                String trainTypeText = jsonObject.getString("TrainType");

                                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                                Date date1 = null;
                                try {
                                    date1 = format.parse(arrivalTimeStartStText);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Date date2 = null;
                                try {
                                    date2 = format.parse(arrivalTimeEndStText);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                long difference = date2.getTime() - date1.getTime();


                                String durationText = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(difference),
                                        TimeUnit.MILLISECONDS.toMinutes(difference) % TimeUnit.HOURS.toMinutes(1),
                                        TimeUnit.MILLISECONDS.toSeconds(difference) % TimeUnit.MINUTES.toSeconds(1));




                                // set the data
                                Trains trainEvents = new Trains();
                                trainEvents.setTrainId(trainIdText);
                                trainEvents.setTripId(tripIdText);
                                trainEvents.setFreq(trainFreqText);
                                trainEvents.setStartStationName(startStNameText);
                                trainEvents.setEndStationName(endStNameText);
                                trainEvents.setSearchStationId(searchStId);
                                trainEvents.setArrivalTimeEndSt(arrivalTimeEndStText);
                                trainEvents.setArrivalTImeStartSt(arrivalTimeStartStText);
                                trainEvents.setArrivalTime(arrivalTimeStText);
                                trainEvents.setDepartureTime(depTimeText);
                                trainEvents.setTrainType(trainTypeText);
                                trainEvents.setSearchStationName(from_st);
                                trainEvents.setDuration(durationText);



                                train.add(trainEvents);


                                Log.v("ENDID", arrivalTimeStText);


                            }



                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                hidePDialog();

            }
        });

        AppController.getInstance().addToRequestQueue(trainReq);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Toast.makeText(MainActivity.this,
                    "No Item", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }


    private void showEvents(String newStartSt, String newEndSt, String newDate, String newstartTime, String newEndTime) {
        getTrains(newStartSt,newEndSt,newDate,newstartTime,newEndTime);
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}

