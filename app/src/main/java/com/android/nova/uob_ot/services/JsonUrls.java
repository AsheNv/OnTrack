package com.android.nova.uob_ot.services;

/**
 * Created by NovA on 1/27/2016.
 */
public class JsonUrls {

    public static final String LOGIN_URL = "http://www.globalxphoto.com/cangaroo/checkpassD.php";
    public static final String GET_ST_URL = "http://localhost/ontrack/GetStation.php";
    public static final String LOAD_CLIENT_URL = "http://www.globalxphoto.com/cangaroo/getClientList.php";
    public static final String UPDATE_HIS_URL = "http://localhost/ontrack/UpdateHistory.php";
    public static final String TEST_URL = "http://m.icta.lk/services/railwayservicev2/train/searchTrain?startStationID=171" +
            "&endSatationID=61&searchDate=2016-02-21&startTime=11:00:00&endTime=12:59:59&lang=en";


    public static final String TRAIN_FROM_ST ="http://itstimetohike.com/app/Search.php?startSt=";
    public static final String TRAIN_TO_ST = "&endSt=";
    public static final String TRAIN_OP_DAY = "&OpDay=";
    public static final String TRAIN_FROM_TIME = "&StartTime=";
    public static final String TRAIN_TO_TIME = "&EndTime=";

    public static final String TRAIN_ID = "http://itstimetohike.com/app/PredictTrain.php?trainId=";
    public static final String STATION_NAME = "&startStationName=";
    public static final String ARRIVAL_TIME = "&ArrivalTime=";

    public static final String ARRIVAL_TRAIN_ID = "http://itstimetohike.com/app/ArrivalTime.php?trainId=";
    public static final String USER_STATION = "&userSt=";

    public static final String TRAIN_POS = "http://itstimetohike.com/app/TrainPos.php?trainId=";

    //public static final String finalUrl = mainUrl+startSt+endUrl+endSt+searchDateUrl+date+startTimeUrl+startTime+endTimeUrl+endTime+lastUrl;

}
