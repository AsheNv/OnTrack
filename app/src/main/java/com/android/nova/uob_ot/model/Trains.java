package com.android.nova.uob_ot.model;

import java.io.Serializable;

/**
 * Created by NovA on 2/17/2016.
 */
public class Trains implements Serializable {

    private static final long id = 1L;
    private String trainId;
    private String tripId;
    private String arrivalTime;
    private String departureTime;
    private String arrivalTimeEndSt;
    private String arrivalTImeStartSt;
    private String duration;
    private String freq;
    private String startStationName;
    private String endStationName;
    private String searchStationId;
    private String startStationId;
    private String endStationId;
    private String searchTimeStart;
    private String searchTimeEnd;
    private String searchDate;
    private String trainType;
    private String delayTime;
    private String fdDescription;
    private String tyDescription;
    private String searchStationName;

    public static long getId() {
        return id;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public String getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(String delayTime) {
        this.delayTime = delayTime;
    }

    public String getSearchStationName() {
        return searchStationName;
    }

    public void setSearchStationName(String searchStationName) {
        this.searchStationName = searchStationName;
    }

    public String getStartStationName() {
        return startStationName;
    }

    public void setStartStationName(String startStationName) {
        this.startStationName = startStationName;
    }

    public String getEndStationName() {
        return endStationName;
    }

    public void setEndStationName(String endStationName) {
        this.endStationName = endStationName;
    }

    public String getFdDescription() {
        return fdDescription;
    }

    public void setFdDescription(String fdDescription) {
        this.fdDescription = fdDescription;
    }

    public String getTyDescription() {
        return tyDescription;
    }

    public void setTyDescription(String tyDescription) {
        this.tyDescription = tyDescription;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getArrivalTimeEndSt() {
        return arrivalTimeEndSt;
    }

    public void setArrivalTimeEndSt(String arrivalTimeEndSt) {
        this.arrivalTimeEndSt = arrivalTimeEndSt;
    }

    public String getArrivalTImeStartSt() {
        return arrivalTImeStartSt;
    }

    public void setArrivalTImeStartSt(String arrivalTImeStartSt) {
        this.arrivalTImeStartSt = arrivalTImeStartSt;
    }

    public String getSearchStationId() {
        return searchStationId;
    }

    public void setSearchStationId(String searchStationId) {
        this.searchStationId = searchStationId;
    }

    public String getStartStationId() {
        return startStationId;
    }

    public void setStartStationId(String startStationId) {
        this.startStationId = startStationId;
    }

    public String getEndStationId() {
        return endStationId;
    }

    public void setEndStationId(String endStationId) {
        this.endStationId = endStationId;
    }

    public String getSearchTimeStart() {
        return searchTimeStart;
    }

    public void setSearchTimeStart(String searchTimeStart) {
        this.searchTimeStart = searchTimeStart;
    }

    public String getSearchTimeEnd() {
        return searchTimeEnd;
    }

    public void setSearchTimeEnd(String searchTimeEnd) {
        this.searchTimeEnd = searchTimeEnd;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }
}
