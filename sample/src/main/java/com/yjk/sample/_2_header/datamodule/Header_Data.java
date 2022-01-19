package com.yjk.sample._2_header.datamodule;

public class Header_Data {
    private String day;
    private String contents;

    public Header_Data(String str, String text){
        this.day = str;
        this.contents = text;
    }

    public String getDate() {
        return day;
    }
    public void setDate(String str) {
        this.contents = str;
    }

    public String getContents(){
        return contents;
    }
    public void setContents(String text){
        this.contents = text;
    }

}
