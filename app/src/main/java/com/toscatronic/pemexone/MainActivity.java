package com.toscatronic.pemexone;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Button btConnect;
    private Button updPrice;
    private EditText pemexPremium;
    private EditText pemexMagna;
    private EditText pemexDiesel;
    private BluetoothSocket mmSocket;
    private byte[] msgBytes;
    private BluetoothDevice btDevice;

    @Override
    protected void onResume() {
        super.onResume();
        btAdapter.enable();
        updPrice = (Button) findViewById(R.id.priceButton);
        btConnect = (Button) findViewById(R.id.btConnect);

        btConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btConnect.getText().toString().equals("Conectar")) {
                    if (!btAdapter.isEnabled()) {
                        toast("Conecta la aplicación");
                    }
                    if (btAdapter.isEnabled()) {
                        btDevice = btAdapter.getBondedDevices().iterator().next();
                        toast("Connectado a: " + btAdapter.getBondedDevices().iterator().next().getName());
                        ConnectThread connectThread = new ConnectThread(btDevice);
                        connectThread.run();
                        btConnect.setText("Desconectar");
                    }
                } else {
                    if (btConnect.getText().toString().equals("Desconectar")) {
                        ConnectThread connectThread = new ConnectThread(btDevice);
                        connectThread.cancel();
                        ConnectedThread connectedThread = new ConnectedThread(mmSocket);
                        connectedThread.cancel();
                        toast("Desconectado");
                        btConnect.setText("Conectar");
                        finish();
                    }
                }
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String info = prefs.getString("code", "");
            String mPremium = "";
            String mMagna = "";
            String mDiesel = "";


            @Override
            public void onClick(View v) {
                if (btConnect.getText().equals("Desconectar")) {
                    if (info.length() < 1) {
                        toast("Debes activar la aplicación");
                    } else {
                        if (pemexPremium.getText().length() < 1) {
                            pemexPremium.setError("Ingresa el precio");
                        } else {
                            if (pemexPremium.getText().length() < 5) {
                                pemexPremium.setError("Precio incompleto");
                            } else {
                                if (pemexPremium.getText().charAt(2) != '.') {
                                    pemexPremium.setError("Ingresa la cantidad con punto decimal");
                                } else {
                                    if (pemexPremium.getText().length() == 5 && pemexPremium.getText().charAt(2) == '.') {
                                        mPremium = pemexPremium.getText().toString();
                                        mPremium = mPremium.replaceAll("\\.", "");
                                    }
                                }
                            }
                        }

                        if (pemexMagna.getText().length() < 1) {
                            pemexMagna.setError("Ingresa el precio");
                        } else {
                            if (pemexMagna.getText().length() < 5) {
                                pemexMagna.setError("Precio incompleto");
                            } else {
                                if (pemexMagna.getText().charAt(2) != '.') {
                                    pemexMagna.setError("Ingresa la cantidad con punto decimal");
                                } else {
                                    if (pemexMagna.getText().length() == 5 && pemexMagna.getText().charAt(2) == '.') {
                                        mMagna = pemexMagna.getText().toString();
                                        mMagna = mMagna.replaceAll("\\.", "");
                                    }
                                }
                            }
                        }

                        if (pemexDiesel.getText().length() < 1) {
                            pemexDiesel.setError("Ingresa el precio");
                        } else {
                            if (pemexDiesel.getText().length() < 5) {
                                pemexDiesel.setError("Precio incompleto");
                            } else {
                                if (pemexDiesel.getText().charAt(2) != '.') {
                                    pemexDiesel.setError("Ingresa la cantidad con punto decimal");
                                } else {
                                    if (pemexDiesel.getText().length() == 5 && pemexDiesel.getText().charAt(2) == '.') {
                                        mDiesel = pemexDiesel.getText().toString();
                                        mDiesel = mDiesel.replaceAll("\\.", "");
                                        String msgString = info + mPremium + mMagna + mDiesel;
                                        msgBytes = msgString.getBytes();
                                        ConnectedThread connectedThread = new ConnectedThread(mmSocket);
                                        connectedThread.write(msgBytes);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    toast("Conecta la aplicación");
                }
            }
        };

        updPrice.setOnClickListener(onClickListener);
    }

    public BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pemexPremium = (EditText) findViewById(R.id.editText1);
        pemexMagna = (EditText) findViewById(R.id.editText2);
        pemexDiesel = (EditText) findViewById(R.id.editText3);
    }

    private void toast(String msj) {
        Toast toast = Toast.makeText(this, msj, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }

    private void preferences(TextView txt) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        txt.setText(prefs.getString("code", ""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, CodeActivity.class);
            startActivity(intent);
            EditText pemexPremium = (EditText) findViewById(R.id.editText1);
            EditText pemexMagna = (EditText) findViewById(R.id.editText2);
            EditText pemexDiesel = (EditText) findViewById(R.id.editText3);
            pemexPremium.setText("");
            pemexMagna.setText("");
            pemexDiesel.setText("");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        ConnectThread connectThread = new ConnectThread(btDevice);
        connectThread.cancel();
        btAdapter.disable();
        super.onDestroy();
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        /*public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    // Send the obtained bytes to the UI activity
                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }*/

        /* Call this from the main activity to send data to the remote device */
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }

    private class ConnectThread extends Thread {
        private final BluetoothDevice mmDevice;
        private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
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
                /*try {
                    mmSocket.close();
                } catch (IOException closeException) {
                }*/
                return;
            }

            // Do work to manage the connection (in a separate thread)
            if (mmSocket.isConnected()) {
            } else {
                toast("No se puede establecer una conexión");
            }
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
}
