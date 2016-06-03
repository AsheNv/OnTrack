package com.android.nova.uob_ot.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.nova.uob_ot.R;
import com.android.nova.uob_ot.activity.BaseActivity;
import com.android.nova.uob_ot.adapter.AppPrefs;
import com.pushbots.push.Pushbots;

public class SearchActivity extends BaseActivity {
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private Spinner spinnerPickDays;
    private Spinner spinnerSearchTimeFrom;
    private Spinner spinnerSearchTimeto;
    private String fromSelection;
    private String toSelection;
    private String daySelection;
    private String timeFromSelection;
    private String timeToSelection;
    private Button getSchduleBtn;
    private Button getSchduleAll;
    public static String APP_ID;
    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapterFrom;
    ArrayAdapter<CharSequence> adapterTo;
    ArrayAdapter<String> fromAdapter;
    AutoCompleteTextView textViewFrom;
    ArrayAdapter<String> toAdapter;
    AutoCompleteTextView textViewTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Pushbots.sharedInstance().init(this);
        APP_ID = Pushbots.sharedInstance().regID();
        Pushbots.sharedInstance().setAlias(APP_ID);

        Log.v("PSS", APP_ID);

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
        // titles
        // from
        // strings.xml


        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        // strings.xml

        set(navMenuTitles, navMenuIcons);


        spinnerPickDays = (Spinner) findViewById(R.id.spinnerDay);


        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_day_items, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerPickDays.setAdapter(adapter);


        spinnerSearchTimeFrom = (Spinner) findViewById(R.id.search_times_from);

        adapterFrom = ArrayAdapter.createFromResource(this,
                R.array.search_time, android.R.layout.simple_spinner_item);

        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSearchTimeFrom.setAdapter(adapterFrom);


        // get refs to spinner
        spinnerSearchTimeto = (Spinner) findViewById(R.id.search_times_to);

        adapterTo = ArrayAdapter.createFromResource(this,
                R.array.search_time, android.R.layout.simple_spinner_item);

        adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSearchTimeto.setAdapter(adapterTo);


        fromAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, STATIONS);
        textViewFrom = (AutoCompleteTextView)
                findViewById(R.id.search_stations_from);
        textViewFrom.setAdapter(fromAdapter);


        toAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, STATIONS);
        textViewTo = (AutoCompleteTextView)
                findViewById(R.id.search_stations_to);
        textViewTo.setAdapter(toAdapter);

        getSchduleBtn = (Button) findViewById(R.id.search_get_given);
        getSchduleAll = (Button) findViewById(R.id.search_get_all);



        // set onlick listener

        textViewFrom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                fromSelection = (String) parent.getItemAtPosition(position);
                Log.v("AB", fromSelection);
            }
        });

        // set onlick listener
        textViewTo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                toSelection = (String) parent.getItemAtPosition(position);
                Log.v("ABC", toSelection);

            }
        });


        //set onitem select listener
        spinnerSearchTimeFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                timeFromSelection = spinnerSearchTimeFrom.getSelectedItem().toString();

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        spinnerSearchTimeto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                timeToSelection = spinnerSearchTimeto.getSelectedItem().toString();

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        spinnerPickDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                daySelection = spinnerPickDays.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getSchduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AppPrefs stPreference = new AppPrefs(SearchActivity.this);
                stPreference.setStartSt(fromSelection);
                stPreference.setEndSt(toSelection);
                stPreference.setStartTime(timeFromSelection);
                stPreference.setEndTime(timeToSelection);
                stPreference.setDate(daySelection);

                String newStartSt = stPreference.getStartSt();
                String newEndSt = stPreference.getEndSt();
                String newstartTime = stPreference.getStartTime();
                String newEndTime = stPreference.getEndTime();
                String newDate = stPreference.getDate();


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Bundle extras = new Bundle();
                extras.putString("FROM_ST", newStartSt);
                extras.putString("TO_ST", newEndSt);
                extras.putString("FROM_TIME", newstartTime);
                extras.putString("TO_TIME", newEndTime);
                extras.putString("PICK_DAY", newDate);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        getSchduleAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fromTime = "00:00:00";
                String toTime = "23:59:59";
                String newDate = "All";


                AppPrefs stPreference = new AppPrefs(SearchActivity.this);
                stPreference.setStartSt(fromSelection);
                stPreference.setEndSt(toSelection);
                stPreference.setStartTime(timeFromSelection);
                stPreference.setEndTime(timeToSelection);
                stPreference.setDate(daySelection);

                String newStartSt = stPreference.getStartSt();
                String newEndSt = stPreference.getEndSt();


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Bundle extras = new Bundle();
                extras.putString("FROM_ST", newStartSt);
                extras.putString("TO_ST", newEndSt);
                extras.putString("FROM_TIME", fromTime);
                extras.putString("TO_TIME", toTime);
                extras.putString("PICK_DAY", newDate);
                intent.putExtras(extras);
                startActivity(intent);


            }
        });




    }




    private static final String[] STATIONS = new String[] {
            "Abanpola", "Ahangama", "Ahungalle", "Akbopura", "Akurala", "Alawatupitiya", "Alawwa", "Alutambalama", "Aluthgama", "Ambalangoda", "Ambewela", "Ambepussa",
    "Anawilundawa", "Andadola", "Angampitiya", "Angulana", "Anuradhapura", "Anuradhapura Town", "Arachchikattuwa", "Arapangama", "Arukkuwatte", "Aselapura", "Aukana", "Avissawella", "Badulla", "Balana", "Balapitiya",
            "Bambalapitiya", "Bandarawela", "Bangadeniya", "Baseline Road", "Battaluoya", "Batticaloa", "Batuwatte", "Bemmulla", "Bentota", "Beruwala", "Bolawatte", "Boossa", "Borelessa", "Botale", "Bulugahagoda", "Buthgamuwa", "Chavakachcheri", "Cheddiikulam", "Chilaw", "China Bey", "Colombo Fort",
    "Cotta Road", "Daraluwa", "Dehiwala", "Dekinda", "Dematagoda", "Demodara", "Dewapuram", "Diyatalawa", "Dodanduwa", "Egoda Uyana", "Eliphant Pass", "Elle",
    "Eluthumattuval", "Elwala", "Enderamulla", "Erattaperiyakulam", "Eravur", "Erukkalam Pendu", "Free Trade Zone", "Galaboda", "Galgamuwa", "Galle", "Gallella", "Galoya Junction", "Gammana", "Gampaha", "Gampola", "Ganegoda", "Ganemulla", "Ganewatte", "Gangoda", "Gelioya",
    "Ginthota", "Girambe", "Godagama", "Great Western", "Habaraduwa", "Habarana", "Haliela", "Haputale", "Hatamuna", "Hataras Kotuwa", "Hatton", "Heel Oya", "Heendeniya", "Hettimulla",
    "Hikkaduwa", "Hingurakgoda", "Hiriyala", "Homagama", "Homagama Hospital", "Horape", "Horiwiala", "Hunupitiya", "Hyinport", "Idalgasinna", "Ihalagama",
    "Ihalakotte", "Ihalawatawala", "Induruwa", "Inguruoya", "Ja-Ela", "Jaffna", "Jayanthipura", "Kachcheri Halt", "Kadadasi Nagar",
    "Kadigamuwa", "Kadugannawa", "Kadugoda", "Kahawa", "Kakkapalliya", "Kalawewa", "Kalkudah", "Kalutara North", "Kalutara South", "Kamburugamuwa",
    "Kandana", "Kandegoda", "Kandy", "Kantale", "Kapuwatte", "Karadipuwal", "Kathaluwa", "Kattuwa", "Katugastota", "Katugoda", "Katukurunda", "Katunayaka Airport", "Katunayake", "Keenawala", "Kekirawa", "Kelaniya", "Kilinochchi", "Kinigama", "Kirulapana",
            "Kital Elle", "Kochchikade", "Kodikamam", "Koggala", "Kollupitiya", "Kolonnawa", "Kompannavediya", "Konwewa", "Koralawella", "Kosgama", "Kosgoda", "Koshinna", "Kotagala", "Kottawa", "Kuda Wawa", "Kudahakapola", "Kumarakanda", "Kumbalgama", "Kurana", "Kurunegala", "Laksauyana", "Liyanagemulla", "Liyanwala", "Lunawa",
    "Lunuwila", "Madampagama", "Madampe", "Madhu Road", "Madurankuliya", "Magalegoda", "Maggona", "Mahaiyawa", "Maharagama", "Maho", "Malapalle", "Manampitiya", "Mangalaeliya",
    "Mankulam", "Manuwangama", "Maradana", "Matale", "Matara", "Medagama", "Medawachchiya", "Meegoda", "Meesalai", "Mha Induruwa", "Midigama", "Mihintale", "Mihintale Junction",
    "Minneriya", "Mirigama", "Mirissa", "Mirusuvil", "Mollipatana", "Moragollagama", "Morakele", "Moratuwa", "Mount Laviniya",
    "Mundal", "Murikandy", "Muththettugala", "Nagollagama", "Nailiya", "Nanuoya", "Narahenpita", "Nattandiya", "Navatkuli", "Nawalapitiya", "Nawinna", "Negama", "Negombo", "Nelumpokuna", "Neriyakulam", "Nooranagar", "Nugegoda", "Ohiya", "Omanthai",
    "Padukka", "Palavi", "Pallai", "Pallewala", "Palugaswewa", "Panadura", "Panagoda", "Panaleeya", "Pangiriwatta", "Pannipitiya",
    "Parakumpura", "Paranthan", "Parasangahawewa", "Patagamgoda", "Pathanpha", "Pattipola", "Payagala North", "Payagala South", "Pemrose", "Peradeniya",
    "Peralanda", "Periyanagavillu",
    "Piliduwa", "Pilimatalawa", "Pinnawala", "Pinwatte", "Piyadigama", "Piyagama", "Polgahawela", "Polonnaruwa", "Polwathumodara", "Poonewa", "Potuhera", "Pulachchikulam", "Puliyankulam",
    "Punani", "Punkankulam", "Puttalam", "Puwakpitiya", "Radella", "Ragama", "Rambukkana", "Ranamuggamuwa", "Randenigama", "Rathgama", "Ratmalana", "Redeethenna", "Richmond Hill", "Rosella", "Saliyapura", "Sankathanai", "Sarasaviuyana", "Sawarana",
    "Secretartat Halt", "Seeduwa", "Seenigama", "Senarathgama", "Sevanapitiya", "Siyabalagawewa", "Siyalangamuwa", "Srawasthipura", "Talawa", "Talawakele", "Talawattegedara",
    "Tampalapopla", "Telwatte", "Tembligala", "Thachanthoppu", "Thalpe", "Thambuttegama", "Thandikulam", "Thilladiya",
    "Thiranagama", "Timbiriyagedara", "Tismalpola", "Train Halt 01", "Trincomalle", "Tudella", "Tummodara", "Udatalawinna", "Udaththawala",
    "Udhamulla", "Uduwara", "Uggalla", "Ukuwela", "Ulapane", "Unawatuna", "Valachchenei", "Vandaramullai", "Vavuniya", "Veyangoda", "Viralmurippuwa", "Wadduwa", "Waga",
    "Waikkala", "Walahapitiya", "Walgama", "Walpola", "Wanawasala", "Wandurawa", "Watagoda", "Wataraka", "Watawala", "Wattegama", "Weligama", "Welikanda", "Wellawa", "Wellawatta", "Wijayarajadahana", "Wilwatte", "Wlakubura", "Yagoda", "Yapahuwa", "Yatagama",
    "Yatirawana", "Yattalgoda",
    };


    private void sendInput(){


        AppPrefs stPreference = new AppPrefs(SearchActivity.this);
        stPreference.setStartSt(fromSelection);
        stPreference.setEndSt(toSelection);
        stPreference.setStartTime(timeFromSelection);
        stPreference.setEndTime(timeToSelection);
        stPreference.setDate(daySelection);

        String newStartSt = stPreference.getStartSt();
        String newEndSt = stPreference.getEndSt();
        String newstartTime = stPreference.getStartTime();
        String newEndTime = stPreference.getEndTime();
        String newDate = stPreference.getDate();

    }

    
}