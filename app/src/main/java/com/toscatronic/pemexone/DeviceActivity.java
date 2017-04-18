package com.toscatronic.pemexone;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeviceActivity extends AppCompatActivity {

    private ListView deviceListView;
    private List<String> deviceList = new ArrayList<String>();
    private List<BluetoothDevice> btDevice = new ArrayList<BluetoothDevice>();
    private BluetoothAdapter btAdapter;
    private UUID MY_UUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        deviceListView = (ListView) findViewById(R.id.deviceListView);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        btAdapter.startDiscovery();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(btReceiver, filter);
        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ConnectThread connectThread = new ConnectThread(btDevice.get(position));
                try {
                    connectThread.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // BluetoothConnector connector = new BluetoothConnector(btDevice.get(position), false, btAdapter, null);
                // try {
                //  connector.connect();
                //} catch (IOException e) {
                //   e.printStackTrace();
                // }
            }
        });
    }

    private final BroadcastReceiver btReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                deviceList.add(device.getName() + "\n" + device.getAddress());
                btDevice.add(device);
                Log.i("BT", device.getName() + "\n" + device.getAddress());
                deviceListView.setAdapter(new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, deviceList));
            }
        }
    };

    @Override
    protected void onDestroy() {
        unregisterReceiver(btReceiver);
        finish();
        super.onDestroy();
    }



    private void toast(String msj) {
        Toast toast = Toast.makeText(this, msj, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }


}
