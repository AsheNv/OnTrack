package com.android.nova.uob_ot.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nova.uob_ot.R;
import com.android.nova.uob_ot.adapter.AppController;
import com.android.nova.uob_ot.model.Trains;
import com.android.nova.uob_ot.services.JsonUrls;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pushbots.push.Pushbots;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

;

public class TrainDetailedActivity extends BaseActivity {



    private ArrayList<String> trainGeo = new ArrayList<>();


    JsonUrls jsonUrls = new JsonUrls();


    private Trains train;
    private TextView arrivalStation;
    private TextView from;
    private TextView to;
    private TextView duration;
    private TextView freq;
    private TextView prediction;
    private TextView averageTime;
    private TextView trainType;
    private TextView eta;
    private TextView delay;


    private String labelLocation;
    private String trainAv;
    private String trainMsg;
    private String trainLat;
    private String trainLon;
    private String trainDelay;
    private String trainEta;
    private int trainId;
    private String stationName;
    private String timeArrival;
    private String trainStatus;



    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ProgressDialog pDialog;
    private boolean exp = false;
    private String trainAlias;


    //private String trnId = train.getTrainId();

    public static final String mainUrl ="http://itstimetohike.com/app/PushTokens.php?Alias=";
    public static final String secondnUrl ="&TrainId=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_detailes);

       ;

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
        // titles
        // from
        // strings.xml




        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        // strings.xml

        set(navMenuTitles, navMenuIcons);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(TrainDetailedActivity.this, Third.class);
//                startActivity(intent);

                labelLocation = " ETA : " + timeArrival;


                if (exp == true) {


                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<" + Double.parseDouble(trainLat) + ">,<" + Double.parseDouble(trainLon) + ">?q=<" + Double.parseDouble(trainLat) + ">,<" + Double.parseDouble(trainLon) + ">(" + labelLocation + ")"));

                    startActivity(intent);


                } else {


                    Toast.makeText(TrainDetailedActivity.this,
                            "Location Not Found!", Toast.LENGTH_LONG).show();


                }


            }
        });

        final Switch mSwitch = (Switch)  findViewById(R.id.switch1);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                String pushTrainId = String.valueOf(trainId);
                String pushTrainOffId = "0";

                SearchActivity sA = new SearchActivity();
                String pushAppId = sA.APP_ID;
                Log.v("tata",pushAppId);


                String sUrl = mainUrl+pushAppId+secondnUrl+pushTrainId;
                String sOffUrl = mainUrl+pushAppId+secondnUrl+pushTrainOffId;

                if(isChecked){

                    sendData(sUrl);

                }else{

                    sendData(sOffUrl);

                }

            }
        });







        train = (Trains) getIntent().getSerializableExtra("trainObj");

        arrivalStation = (TextView) findViewById(R.id.dArrival);
        from = (TextView) findViewById(R.id.dFrom);
        to = (TextView) findViewById(R.id.dTo);
        freq = (TextView) findViewById(R.id.dFreq);
        trainType = (TextView) findViewById(R.id.dType);
        duration = (TextView) findViewById(R.id.dDuration);

        trainId = Integer.parseInt(train.getTrainId());
        stationName = train.getSearchStationName();



        arrivalStation.setText( train.getArrivalTime());
        from.setText(train.getStartStationName());
        to.setText( train.getEndStationName());
        freq.setText( train.getFreq());
        trainType.setText(train.getTrainType());
        duration.setText(train.getDuration());



      //  Log.v("Test",trainPrd.get(0));

      //  Log.v("Test", trainGeo.get(1));

        getLocation();
        getPrediction();
        getTimeArrival();


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//         Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_activity_train_details, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_gmapId)
        {




            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<" + Double.parseDouble(trainLat)  + ">,<" + Double.parseDouble(trainLon) + ">?q=<" + Double.parseDouble(trainLat)   + ">,<" + Double.parseDouble(trainLon) + ">(" + labelLocation+ ")"));

            startActivity(intent);


        }


        return super.onOptionsItemSelected(item);
    }


    private void sendData (String url){


        String sendUrl = url;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, sendUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(TrainDetailedActivity.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(TrainDetailedActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


    private void getLocation ()
    {
        trainGeo.clear();


        String finalUrl = jsonUrls.TRAIN_POS+trainId;

        Log.v("ADA",finalUrl );


        // json request to get location
        JsonObjectRequest trainGeoReq = new JsonObjectRequest(Request.Method.GET, finalUrl, (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                       // hidePDialog();


                        try {

                            JSONArray trainGeoListArray = response.getJSONArray("Position");

                            for (int i =0; i < trainGeoListArray.length(); i++)
                            {
                                JSONObject jsonObject = trainGeoListArray.getJSONObject(i);

                                 trainLat = jsonObject.getString("lat");
                                 trainLon = jsonObject.getString("long");

                                if(jsonObject.isNull("Position")){

                                    exp = true;
                                }
                                else{

                                    exp = false;
                                }

                            }




                        } catch (JSONException e) {
                            e.printStackTrace();



                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

              //  hidePDialog();

            }
        });

        AppController.getInstance().addToRequestQueue(trainGeoReq);


    }

    private void getPrediction ()
    {






        JsonUrls jsonUrls = new JsonUrls();



        String arrival = train.getArrivalTime();

        String finalUrl = jsonUrls.TRAIN_ID +trainId+jsonUrls.STATION_NAME+stationName+jsonUrls.ARRIVAL_TIME+arrival;
        //String finalurl = "http://ontrack.16mb.com/app/predictTrain.php?trainId=10&startStationName=Mirigama&ArrivaTime=13:00:00";

        Log.v("ADC", finalUrl);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest trainPrReq = new JsonObjectRequest(Request.Method.GET, finalUrl, (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                         hidePDialog();



                        try {

                            JSONObject prObject = response.getJSONObject("Result");


                                trainAv = prObject.getString("Average");
                                trainMsg = prObject.getString("Prediction");



                            prediction = (TextView) findViewById(R.id.dPred);
                         //  averageTime = (TextView) findViewById(R.id.d);

                             prediction.setText(trainMsg + "% chance of delay");
                           // averageTime.setText(trainAv);




//                            Log.v("Test", trainGeo.get(1));



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

        AppController.getInstance().addToRequestQueue(trainPrReq);


    }

    private void getTimeArrival ()
    {


        // int trid = Integer.parseInt(train.getTrainId());



        JsonUrls jsonUrls = new JsonUrls();



        String finalUrl = jsonUrls.ARRIVAL_TRAIN_ID +trainId+jsonUrls.USER_STATION+stationName;
        //String finalurl = "http://ontrack.16mb.com/app/predictTrain.php?trainId=10&startStationName=Mirigama&ArrivaTime=13:00:00";

        Log.v("ADC", finalUrl);
//        pDialog = new ProgressDialog(this);
//        pDialog.setMessage("Loading...");
//        pDialog.show();

        JsonObjectRequest trainPrReq = new JsonObjectRequest(Request.Method.GET, finalUrl, (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        hidePDialog();




                        try {

                            JSONObject etaObject = response.getJSONObject("Result");


                            trainDelay = etaObject.getString("Delay");
                            trainEta = etaObject.getString("Eta");
                            timeArrival = etaObject.getString("Time Arrival");
                            trainStatus = etaObject.getString("Train Status");



                            delay = (TextView) findViewById(R.id.dDelay);
                            eta = (TextView) findViewById(R.id.dEta);

                            if (trainStatus == "true"){

                                delay.setText("Train has already departured");
                                eta.setText("Train has already departured");
                            }
                            else{

                                delay.setText(trainDelay);
                                eta.setText(timeArrival);

                            }






//                            Log.v("Test", trainGeo.get(1));



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

        AppController.getInstance().addToRequestQueue(trainPrReq);


    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


}
