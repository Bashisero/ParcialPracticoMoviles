package com.example.parciali;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    EditText edRespuestaHTTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edRespuestaHTTP = findViewById(R.id.edRespuestaHTTP);
    }

    public void Volley (View v){
        RequestQueue queue =  Volley.newRequestQueue(this);
        String url = "https://run.mocky.io/v3/75521479-578e-4d1f-a579-a7e2c624f66c";

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("", "Respuesta: " + response.toString());
                            JSONArray jsonArray = response.getJSONArray("usuario");
                            Log.d("", "Respuesta: " + jsonArray.toString());
                            String usuarios = "";

                            Log.d("", "Respuesta: " + response.toString());
                            JSONArray jsonArray2 = response.getJSONArray("contraseña");
                            Log.d("", "Respuesta: " + jsonArray2.toString());
                            String contraseñas = "";
                            for (int i = 0; i <2; i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                usuarios += jsonObject2.getString("nick") +"\n";
                            }
                            edRespuestaHTTP.setText(usuarios);
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