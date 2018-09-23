package login.com.girish.servicedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONArrayActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONArray> {
    private TextView textViewName;
    private RequestQueue requestQueue;
    private String url = "https://api.androidhive.info/volley/person_array.json";
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonarray);
        textViewName = findViewById(R.id.textViewPName);
        progressBar = findViewById(R.id.progressBarJSON);
        requestQueue = Volley.newRequestQueue(this);

    }

    public void showNames(View view) {
        if (NetworkConnection.isConnected(this)){
            progressBar.setVisibility(View.VISIBLE);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,this,this);
            requestQueue.add(jsonArrayRequest);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onResponse(JSONArray response) {
        progressBar.setVisibility(View.GONE);
        for (int i=0;i<response.length();i++){
            try {
                JSONObject object = response.getJSONObject(i);
                String name = object.getString("name");
                textViewName.append(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
