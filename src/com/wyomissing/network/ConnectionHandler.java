package com.wyomissing.network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class ConnectionHandler {

    private String ip;

    public void checkHosts(String subnet) {
        // int timeout = 1000;
        // for (int i = 1; i < 254; i++) {
        // String host = subnet + "." + i;
        // try {
        // if (InetAddress.getByName(host).isReachable(timeout)) {
        // if (QueryData.query(QueryType.PING).equalsIgnoreCase("pong")) {
        // ip = host;
        // Toast.makeText(MainActivity.instance.getApplicationContext(), ip,
        // Toast.LENGTH_LONG).show();
        // return;
        // }
        // }
        // }
        // catch (UnknownHostException e) {
        // e.printStackTrace();
        // }
        // catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        try {
            NetworkInterface iFace = NetworkInterface.getByInetAddress(InetAddress.getByName(subnet));

            for (int i = 0; i <= 255; i++) {

                // build the next IP address
                String addr = subnet;
                addr = addr.substring(0, addr.lastIndexOf('.') + 1) + i;
                InetAddress pingAddr = InetAddress.getByName(addr);

                // 50ms Timeout for the "ping"
                if (pingAddr.isReachable(iFace, 200, 50)) {
                    Log.d("PING", pingAddr.getHostAddress());
                }
            }
        }
        catch (UnknownHostException ex) {
        }
        catch (IOException ex) {
        }
    }

    /**
     * Checks to see if a specific port is available.
     * 
     * @param port
     *            the port to check for availability
     */
    public static boolean available(int port) {
        if (port < 6666 || port > 0) {
            throw new IllegalArgumentException("Invalid start port: " + port);
        }

        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        }
        catch (IOException e) {
        }
        finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                }
                catch (IOException e) {
                }
            }
        }

        return false;
    }

    public String getMacAddress() throws IOException {
        String macAddress = null;
        String command = "ipconfig /all";
        Process pid = Runtime.getRuntime().exec(command);
        BufferedReader in = new BufferedReader(new InputStreamReader(pid.getInputStream()));
        while (true) {
            String line = in.readLine();
            if (line == null)
                break;
            Pattern p = Pattern.compile(".*Physical Address.*: (.*)");
            Matcher m = p.matcher(line);
            if (m.matches()) {
                macAddress = m.group(1);
                break;
            }
        }
        in.close();
        return macAddress;
    }

    public static String getIpFromArpCache(String macaddr) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(" +");
                if (splitted != null && splitted.length >= 4 && macaddr.equals(splitted[3])) {
                    // Basic sanity check
                    String ip = splitted[0];
                    if (validateIp(ip)) {
                        return ip;
                    }
                    else {
                        return null;
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                br.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static final String PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    public static boolean validateIp(final String ip) {

        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     *            the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

}
