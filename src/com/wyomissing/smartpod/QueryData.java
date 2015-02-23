package com.wyomissing.smartpod;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.os.AsyncTask;

public class QueryData extends AsyncTask<QueryType, Void, String> {

    @Override
    protected String doInBackground(QueryType... params) {
        String ip = "http://" + MainActivity.getCH().getIp() + ":8000/";
        URL url = null;
        switch (params[0]) {
        case PING: 
            ip += "?ping=ping";
            break;
        case TEMP:
            break;
        case LIGHT: 
            ip += "?type=live&call=getlight";
            break;
        case POWER:
            break;
        default:
            break;
        }
        try {
            url = new URL(ip);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            Document doc = Jsoup.parse(url, 3*1000);
            return doc.text();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public static String syncQuery(QueryType qt) {
        String ip = "http://" + MainActivity.getCH().getIp() + ":8000/";
        URL url = null;
        switch (qt) {
        case PING: 
            ip += "?ping=ping";
            break;
        case TEMP:
            break;
        case LIGHT: 
            ip += "?type=live&call=getlight";
            break;
        case POWER:
            break;
        default:
            break;
        }
        try {
            url = new URL(ip);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            Document doc = Jsoup.parse(url, 3*1000);
            return doc.text();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    
}

