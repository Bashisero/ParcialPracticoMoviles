package com.example.parciali;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.CheckBox;

public class Actividad_3 extends AppCompatActivity {

    TextView integrantes;
    CheckBox intg1,intg2,intg3;
    String s1="",s2="",s3="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad3);
        integrantes = findViewById(R.id.integrantes);
        intg1 = findViewById(R.id.intg1);
        intg2 = findViewById(R.id.intg2);
        intg3 = findViewById(R.id.intg3);
    }

    public void agregar(View view){
        Intent ir = new Intent(this,Actividad_2.class);
        startActivity(ir);
    }

    public void opcion1(View v){
        if(intg1.isChecked()==true){
            s1="Cristhian ";
        }else{
            s1="";
        }
        integrantes.setText("Los participantes serán: " +s1+s2+s3);
    }

    public void opcion2(View v){
        if(intg2.isChecked()==true){
            s2="Camilo ";
        }else{
            s2="";
        }
        integrantes.setText("Los participantes serán: " +s1+s2+s3);
    }

    public void opcion3(View v){
        if(intg3.isChecked()==true){
            s3="Santiago ";
        }else{
            s3="";
        }
        integrantes.setText("Los participantes serán: " +s1+s2+s3);
    }
}