package com.toscatronic.pemexone;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);


        final EditText editCode = (EditText) findViewById(R.id.editCode);
        final TextView textStatus = (TextView) findViewById(R.id.textStatus);
        final Button codeButton = (Button) findViewById(R.id.codeButton);

        codeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editCode.getText().length()>0){
                    editPreferences(editCode.getText().toString());
                    textStatus.setText("Aplicación activada");
                    codeButton.setText("Modificar");
                    editCode.clearComposingText();
                    toast("Código modificado");
                } else {
                    toast("Debes insertar un código");
                }
            }
        });
    }

    private void toast(String msj){
        Toast toast = Toast.makeText(this, msj, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void editPreferences (String code){
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.putString(getString(R.string.activation_code_preference), code);
        prefsEdit.apply();
    }
}
