package com.toscatronic.pemexone;

import android.bluetooth.BluetoothAdapter;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Switch btConnect;
    private Switch btSwitch;
    private Button updPrice;
    private TextView txt;
    private EditText pemexPremium;
    private EditText pemexMagna;
    private EditText pemexDiesel;

    @Override
    protected void onResume() {
        super.onResume();

        updPrice = (Button) findViewById(R.id.priceButton);
        txt = (TextView) findViewById(R.id.textView);
        preferences(txt);
        btConnect = (Switch) findViewById(R.id.btConnect);
        btSwitch = (Switch) findViewById(R.id.btSwitch);

        btConnect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (btAdapter.isEnabled()) {
                        Intent intent = new Intent(MainActivity.this, DeviceActivity.class);
                        deviceActivity(intent);
                    } else {
                        btConnect.setChecked(false);
                        toast("Para conectarte enciende el bluetooth");
                    }
                }
                if (!isChecked) {
                    if (btAdapter.isEnabled()) {
                        btAdapter.disable();
                        btConnect.setChecked(false);
                    }
                }
            }
        });

        if (btAdapter.isEnabled()) {
            btSwitch.setChecked(true);
        }
        if (!btAdapter.isEnabled()) {
            btSwitch.setChecked(false);
        }


        btSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!btAdapter.isEnabled()) {
                        btAdapter.enable();
                    }
                }
                if (!isChecked) {
                    if (btAdapter.isEnabled()) {
                        btAdapter.disable();
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
                                    TextView mensaje = (TextView) findViewById(R.id.textView2);
                                    mensaje.setText(info + mPremium + mMagna + mDiesel);
                                }
                            }
                        }
                    }
                }
            }
        };

        updPrice.setOnClickListener(onClickListener);
    }

    public BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

    private void deviceActivity(Intent intent) {
        if (btAdapter.isEnabled()) {
            startActivity(intent);
        }
    }

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
        super.onDestroy();
    }
}
