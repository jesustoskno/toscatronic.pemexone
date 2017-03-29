package com.toscatronic.pemexone;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CodeActivity extends AppCompatActivity {
    String mCode = "";

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mCode = prefs.getString("code", "");

        final EditText editCode = (EditText) findViewById(R.id.editCode);
        final TextView textStatus = (TextView) findViewById(R.id.textStatus);
        final Button codeButton = (Button) findViewById(R.id.codeButton);

        if(!mCode.isEmpty()){
            textStatus.setText("Aplicación activada");
            codeButton.setText("Modificar");
        }

        codeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editCode.getText().length()>0){
                    if(mCode.isEmpty()){
                        toast("Aplicación activada");
                    } else {
                        toast("Código modificado");
                    }
                    editPreferences(editCode.getText().toString());
                    editCode.setText("");
                    finish();
                } else {
                    toast("Debes insertar un código");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        DrawerLayout drawerLayout = new DrawerLayout(this);

    }

    private void toast(String msj){
        Toast toast = Toast.makeText(this, msj, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void editPreferences (String code){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.putString("code", code);
        prefsEdit.apply();
    }
}
