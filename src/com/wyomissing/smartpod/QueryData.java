package com.wyomissing.smartpod;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.os.AsyncTask;
import android.util.Log;

public class QueryData extends AsyncTask<QueryType, Void, String> {

    @Override
    protected String doInBackground(QueryType... params) {
        return query(params[0]);
    }
    
    /**
     * Queries the Pi SmartPod server for a response to data over LAN
     * @param qt - the type of the query.
     * @return the result from the web server
     */
    public static String query(QueryType qt) {
        String ip = "http://" + MainActivity.getCH().getIp() + ":8000/";
        switch (qt) {
        case PING: 
            ip += "?ping=ping";
            break;
        case TEMP:
            ip += "?type=live&call=gettemp";
            break;
        case HUMIDITY: 
            ip += "?type=live&call=gethumidity";
            break;
        case LIGHT: 
            ip += "?type=live&call=getlight";
            break;
        case MOTION:
            ip += "?type=live&call=getmotion";
            break;
        case POWER:
            ip += "?type=live&call=dickpower";
            break;
        default:
            break;
        }
        try {
            Log.d("dev", ip);
            Document doc = Jsoup.connect(ip).get();
            return doc.text();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    
}

