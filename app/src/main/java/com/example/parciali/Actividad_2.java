package com.example.parciali;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Actividad_2 extends AppCompatActivity
{
    TextView nombre;
    Button adios,integrantes;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    public static final String BroadcastStringForAction="checkinternet";

    private IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad2);
        mIntentFilter=new IntentFilter();
        mIntentFilter.addAction(BroadcastStringForAction);
        Bundle recibo = getIntent().getExtras();
        String n = recibo.getString("n");
        nombre = findViewById(R.id.nombre);
        adios = findViewById(R.id.adios);
        integrantes = findViewById(R.id.integrantes);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
        if(acc != null)
        {
            String name = acc.getDisplayName();
            nombre.setText(name);
        }else{
                nombre.setText(n);
        }
        adios.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                signOut();
            }
        });
    }
    public void signOut()
    {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(Task<Void> task)
            {
                finish();
                startActivity(new Intent(Actividad_2.this, MainActivity.class));
            }
        });
    }

    public void integrantes(View l){
        Intent ir = new Intent(this,Actividad_3.class);
        startActivity(ir);
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
                    Intent ir = new Intent(Actividad_2.this,MainActivity.class);
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