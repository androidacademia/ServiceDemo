package login.com.girish.servicedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class VolleyWeatherActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {
    private TextView textViewCity,textViewCon,textViewTemp,textViewWind;
    private RequestQueue requestQueue;
    private String url = "https://api.openweathermap.org/data/2.5/weather?mode=json&appid=e242949c082b0e914a2f46b05f41eacf&lon=-0.13&lat=51.51";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        textViewCity = findViewById(R.id.textViewCity);
        textViewCon = findViewById(R.id.textViewCountry);
        textViewTemp = findViewById(R.id.textViewTemp);
        textViewWind = findViewById(R.id.textViewWind);
        requestQueue = Volley.newRequestQueue(this);
    }



    public void weatherCondition(View view) {
        if(NetworkConnection.isConnected(this)){
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, this,this);
            requestQueue.add(jsonObjectRequest);


        }else {

        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {


    }

    @Override
    public void onResponse(JSONObject response) {

        try {
            JSONObject main = response.getJSONObject("main");
            JSONObject sys = response.getJSONObject("sys");
            //main.getString("temp");
            textViewTemp.setText(main.getString("temp"));
            textViewCon.setText(sys.getString("country"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}













