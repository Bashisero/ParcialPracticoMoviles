package com.example.parciali;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.example.parciali.services.Servicio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.parciali.databinding.ActivityMainBinding;

import java.util.Observable;
import java.util.ArrayList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    EditText edUsuario;
    EditText edContraseña;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleButton;
    Bundle datos = new Bundle();
    ActivityMainBinding binding;
    public static final String BroadcastStringForAction="checkinternet";

    private IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        edUsuario = findViewById(R.id.edUsuario);
        edContraseña = findViewById(R.id.edContraseña);
        googleButton = findViewById(R.id.googleButton);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                signIn();
            }
        });
        mIntentFilter=new IntentFilter();
        mIntentFilter.addAction(BroadcastStringForAction);
        Intent irservice = new Intent(this,Servicio.class);
        startService(irservice);

        /*
        //binding.tvNotconnected.setVisibility(View.GONE);
        if(!linea(getApplicationContext())){
            Toast.makeText(getApplicationContext(),"no hay conexion", Toast.LENGTH_SHORT).show();
        }
         */
    }
    public void signIn()
    {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try
            {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            }
            catch (ApiException e)
            {
                Toast.makeText(getApplicationContext(),"Te quiero mucho", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void navigateToSecondActivity()
    {
        finish();
        Intent intent = new Intent(MainActivity.this,Actividad_2.class);
        Log.d("","respuesta:"+datos);
        intent.putExtras(datos);
        startActivity(intent);
    }

    public void Volley (View v){
        RequestQueue queue =  Volley.newRequestQueue(this);
        String url = "https://run.mocky.io/v3/a0e0d862-5f8d-41c3-a4e6-dda569173481";

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("", "Respuesta: " + response.toString());
                            JSONArray jsonArray = response.getJSONArray("usuarios");
                            Log.d("", "Respuesta: " + jsonArray.toString());

                            String usuarios, contraseñas = "";
                            int flag = 0;

                            for (int i = 0; i < response.getInt("count"); i++)
                            {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                usuarios=(jsonObject2.getString("usuario"));
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                contraseñas=(jsonObject.getString("contraseña"));

                                if(usuarios.equals(edUsuario.getText().toString()) && contraseñas.equals(edContraseña.getText().toString()))
                                {
                                    datos.putString("n",edUsuario.getText().toString());
                                    navigateToSecondActivity();
                                    flag = 1;
                                    break;
                                }
                            }
                            if(flag==0)
                            {
                                Toast.makeText(getApplicationContext(),"Te odio",
                                Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                VolleyLog.e("error: " ,error.getMessage());
            }
        }
        )
        {
            @Override
            public String getBodyContentType(){
                return "application/json;charset=utf-8";
            }
        }
        ;
        queue.add(req);
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
                }
            }
        }
    };

    public boolean linea(Context c){
        ConnectivityManager cm=(ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni=cm.getActiveNetworkInfo();

        if(ni!=null && ni.isConnectedOrConnecting())
            return true;
        else
            return false;
    }
    /*
    public void Set_Visibility_ON(){
        binding.tvNotconnected.setVisibility(View.GONE);
        binding.btnSubmit.setVisibility(View.VISIBLE);
        binding.parent.setBackgroundColor(Color.WHITE);
    }
    public void Set_Visibility_OFF(){
        binding.tvNotconnected.setVisibility(View.VISIBLE);
        binding.btnSubmit.setVisibility(View.GONE);
        binding.parent.setBackgroundColor(Color.RED);
    }
    */
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
    protected void onResume(){
        super.onResume();
        registerReceiver(MyReceiver,mIntentFilter);
    }
}