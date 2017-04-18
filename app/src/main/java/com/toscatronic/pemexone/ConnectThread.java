package com.toscatronic.pemexone;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by jesus on 10/04/2017.
 */

public class ConnectThread extends Thread {
    private BluetoothSocket mmSocket = null;
    private final BluetoothDevice mmDevice;
    private BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

    public ConnectThread(BluetoothDevice device) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        mmDevice = device;

        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            tmp = device.createInsecureRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            Log.e("error de socket", e.getMessage());
        }
        mmSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it will slow down the connection

        btAdapter.cancelDiscovery();

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mmSocket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            Log.e("error", connectException.getMessage());
            try {
                mmSocket.getInputStream().close();
                mmSocket.getOutputStream().close();
                mmSocket.close();
            } catch (IOException closeException) {
                Log.e("error", closeException.getMessage());
            }
            return;
        }
        Log.e("breakpoint", null);
        // Do work to manage the connection (in a separate thread)

    }

    /**
     * Will cancel an in-progress connection, and close the socket
     */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        }
    }
}
