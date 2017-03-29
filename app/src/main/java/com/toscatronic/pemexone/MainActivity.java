package com.toscatronic.pemexone;

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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();

        Button updPrice = (Button) findViewById(R.id.priceButton);
        TextView txt = (TextView) findViewById(R.id.textView);
        preferences(txt);

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
                    EditText pemexPremium = (EditText) findViewById(R.id.editText1);
                    EditText pemexMagna = (EditText) findViewById(R.id.editText2);
                    EditText pemexDiesel = (EditText) findViewById(R.id.editText3);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void toast(String msj){
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
}
