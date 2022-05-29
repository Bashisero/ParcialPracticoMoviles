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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    EditText edUsuario;
    EditText edContraseña;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleButton;
    Bundle datos = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

}