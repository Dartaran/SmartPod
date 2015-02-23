package com.wyomissing.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.os.AsyncTask;

import com.wyomissing.smartpod.QueryData;
import com.wyomissing.smartpod.QueryType;

public class CheckHosts extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        // try {
        // NetworkInterface iFace =
        // NetworkInterface.getByInetAddress(InetAddress.getByName(params[0]));
        //
        // for (int i = 0; i <= 255; i++) {
        //
        // // build the next IP address
        // String addr = params[0];
        // addr = addr.substring(0, addr.lastIndexOf('.') + 1) + i;
        // InetAddress pingAddr = InetAddress.getByName(addr);
        //
        // // 50ms Timeout for the "ping"
        // if (pingAddr.isReachable(iFace, 200, 50)) {
        // Log.d("PING", pingAddr.getHostAddress());
        // }
        // }
        // }
        // catch (UnknownHostException ex) {
        // }
        // catch (IOException ex) {
        // }
        // return null;
        int timeout = 1000;
        for (int i = 1; i < 254; i++) {
            String host = params[0] + "." + i;
            try {
                if (InetAddress.getByName(host).isReachable(timeout)) {
                    if (QueryData.syncQuery(QueryType.PING).equalsIgnoreCase("pong")) {
                        return host;
                    }
                }
            }
            catch (UnknownHostException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

}