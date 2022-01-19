package com.yjk.sample._2_header.datamodule;

public class Header_Data implements Comparable<Header_Data>{
    private String date;
    private String contents;

    public Header_Data(String str,String text){
        this.date = str;
        this.contents = text;
    }

    public String getDate() {
        return date;
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

    @Override
    public int compareTo(Header_Data header_data) {
        return this.date.compareTo(header_data.date);
    }
}
