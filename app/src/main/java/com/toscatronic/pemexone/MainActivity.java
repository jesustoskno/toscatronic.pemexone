package com.toscatronic.pemexone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        EditText pemexPremium = (EditText) findViewById(R.id.editText1);
        EditText pemexMagna = (EditText) findViewById(R.id.editText2);
        EditText pemexDiesel = (EditText) findViewById(R.id.editText3);
        Button updPrice = (Button) findViewById(R.id.priceButton);
        TextView txt = (TextView) findViewById(R.id.textView);

        txt.setText(getString(R.string.activation_code_preference));
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
        }

        return super.onOptionsItemSelected(item);
    }
}
