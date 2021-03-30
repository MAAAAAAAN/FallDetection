package com.example.falldetection;

public class RecordData {
    private int state;
    private String time;
    private int toDetail;

    public RecordData(){}

    public RecordData(int state, String time, int toDetail){
        this.state = state;
        this.time = time;
        this.toDetail = toDetail;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getToDetail() {
        return toDetail;
    }

    public void setToDetail(int toDetail) {
        this.toDetail = toDetail;
    }

}
