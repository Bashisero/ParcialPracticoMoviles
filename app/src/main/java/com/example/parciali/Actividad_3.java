package com.example.parciali;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.parciali.databinding.ActivityMainBinding;
import com.example.parciali.services.Servicio;

public class Actividad_3 extends AppCompatActivity {

    TextView integrantes;
    CheckBox intg1,intg2,intg3;
    String s1="",s2="",s3="";
    public static final String BroadcastStringForAction="checkinternet";

    private IntentFilter mIntentFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad3);
        integrantes = findViewById(R.id.integrantes);
        intg1 = findViewById(R.id.intg1);
        intg2 = findViewById(R.id.intg2);
        intg3 = findViewById(R.id.intg3);
        mIntentFilter=new IntentFilter();
        mIntentFilter.addAction(BroadcastStringForAction);
    }

    public void agregar(View view){
        Intent ir = new Intent(Actividad_3.this,Actividad_2.class);
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

    public BroadcastReceiver MyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(BroadcastStringForAction)){
                if(intent.getStringExtra("estatus online").equals("true")){
                    //Toast.makeText(getApplicationContext(),"hay conexion", Toast.LENGTH_SHORT).show();
                    //Set_Visibility_ON();
                }
                else{
                    Toast.makeText(getApplicationContext(),"no hay conexion", Toast.LENGTH_SHORT).show();
                    Intent ir = new Intent(Actividad_3.this,MainActivity.class);
                    startActivity(ir);
                }
            }
        }
    };
    @Override
    protected void onRestart(){
        super.onRestart();
        registerReceiver(MyReceiver,mIntentFilter);
    }

    @Override
    protected void onPause(){
        super.onPause();
        unregisterReceiver(MyReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(MyReceiver, mIntentFilter);
    }
}