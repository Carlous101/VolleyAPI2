package com.example.volleyapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText txtCodigo;
    EditText multitxtResultado;
    Button btnBuscar;
    RequestQueue requestQueue;
    private static final String URL1 = "https://revistas.uteq.edu.ec/ws/issues.php?j_id=";
    private static final String URL2 = "https://revistas.uteq.edu.ec/ws/issues.php?j_id=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        initUI();

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multitxtResultado.setText("");
                jsonRequest(txtCodigo.getText().toString());
            }
        });

    }

    private void initUI(){
        txtCodigo = findViewById(R.id.txtCodigo);
        multitxtResultado = findViewById(R.id.mltxt_resultado);
        btnBuscar = findViewById(R.id.btnBuscar);
    }

    private void jsonRequest(String codigo){
        String s_issue = getString(R.string.issue_id);
        String s_volume = getString(R.string.volume);
        String s_number = getString(R.string.number);
        String s_year = getString(R.string.year);
        String s_date_published = getString(R.string.date_published);
        String s_title = getString(R.string.title);
        String s_doi = getString(R.string.doi);
        String s_cover = getString(R.string.cover);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL2+codigo,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int tamaño = response.length();
                        for(int i=0; i<tamaño; i++)
                        {
                            try {
                                JSONObject jsonObject = new JSONObject(response.get(i).toString());
                                String issue_id = jsonObject.getString("issue_id");
                                String volume = jsonObject.getString("volume");
                                String number = jsonObject.getString("number");
                                String year = jsonObject.getString("year");
                                String date_published = jsonObject.getString("date_published");
                                String title = jsonObject.getString("title");
                                String doi = jsonObject.getString("doi");
                                String cover = jsonObject.getString("cover");
                                multitxtResultado.append(s_issue + " " + issue_id + "\n" +
                                                         s_volume + " " + volume + "\n" +
                                                         s_number + " " + number + "\n" +
                                                         s_year + " " + year + "\n" +
                                                         s_date_published + " " + date_published + "\n" +
                                                         s_title + " " + title + "\n" +
                                                         s_doi + " " + doi + "\n" +
                                                         s_cover + " " + cover + "\n");
                            }catch(JSONException jex){
                                jex.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}