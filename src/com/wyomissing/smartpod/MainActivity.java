package com.wyomissing.smartpod;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wyomissing.network.CheckHosts;
import com.wyomissing.network.ConnectionHandler;

public class MainActivity extends Activity {

    private static ConnectionHandler ch;
    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;
        ch = new ConnectionHandler();

        try {
            ch.setIp(new CheckHosts().execute("172.20.10").get());
        }
        catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        catch (ExecutionException e1) {
            e1.printStackTrace();
        }
        
        if (ch.getIp() == null) {
            ch.setIp("172.20.10.12");
        }
        
        // Toast.makeText(getApplicationContext(), new
        // CheckHosts().execute("172.20.10").get(), Toast.LENGTH_LONG).show();
        // ch.setIp(ConnectionHandler.getIpFromArpCache("b8:27:eb:17:a6:53"));
        // Toast.makeText(getApplicationContext(),
        // ConnectionHandler.getIpFromArpCache("b8:27:eb:17:a6:53"),
        // Toast.LENGTH_LONG).show();

        final ImageButton refreshButton = (ImageButton) findViewById(R.id.btnRefresh);
        refreshButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ImageButton btnLight = ((ImageButton) MainActivity.instance.findViewById(R.id.btnLight));
                
                // LIGHT
                try {
                    if (Integer.valueOf((new QueryData().execute(QueryType.LIGHT).get())) <= 75) {
                        btnLight.setImageResource(R.drawable.lighton_applayout);
                    }
                    else {
                        btnLight.setImageResource(R.drawable.lightoff_applayout);
                    }
                }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catch (ExecutionException e) {
                    e.printStackTrace();
                }
                
                // TEMPERATURE
                TextView txtTemp = ((TextView) findViewById(R.id.txtTemp));
                
                try {
                    txtTemp.setText(new QueryData().execute(QueryType.TEMP).get());
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catch (ExecutionException e) {
                    e.printStackTrace();
                }
                
                // HUMIDITY
                TextView txtHumidity = ((TextView) findViewById(R.id.txtHumidity));
                
                try {
                    txtHumidity.setText(new QueryData().execute(QueryType.HUMIDITY).get());
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catch (ExecutionException e) {
                    e.printStackTrace();
                }
                
            }

        });

        final ImageButton powerButton = (ImageButton) findViewById(R.id.btnPower);
        powerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    new QueryData().execute(QueryType.POWER);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public static ConnectionHandler getCH() {
        return ch;
    }

}
