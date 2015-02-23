package com.wyomissing.smartpod;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
//        
//        try {
//            Toast.makeText(getApplicationContext(), new CheckHosts().execute("192.168.1.1").get(), Toast.LENGTH_LONG).show();
//        }
//        catch (InterruptedException e1) {
//            e1.printStackTrace();
//        }
//        catch (ExecutionException e1) {
//            e1.printStackTrace();
//        }
        ch.setIp(ConnectionHandler.getIpFromArpCache("b8:27:eb:17:a6:53"));
        Toast.makeText(getApplicationContext(), ConnectionHandler.getIpFromArpCache("b8:27:eb:17:a6:53"), Toast.LENGTH_LONG).show();
        
        final ImageButton refreshButton = (ImageButton) findViewById(R.id.btnRefresh);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                TextView txtLight = ((TextView) MainActivity.instance.findViewById(R.id.txtLight));
                try {
                    txtLight.setText(new QueryData().execute(QueryType.LIGHT).get());
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            
        });
    }
    
    public static ConnectionHandler getCH() {
        return ch;
    }
    
}
