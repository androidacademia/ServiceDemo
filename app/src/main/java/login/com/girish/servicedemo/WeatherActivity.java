package login.com.girish.servicedemo;

import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherActivity extends AppCompatActivity {
    private TextView textViewCity,textViewCon,textViewTemp,textViewWind;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        textViewCity = findViewById(R.id.textViewCity);
        textViewCon = findViewById(R.id.textViewCountry);
        textViewTemp = findViewById(R.id.textViewTemp);
        textViewWind = findViewById(R.id.textViewWind);
        constraintLayout = findViewById(R.id.constraintLayout);

    }

    public void weatherCondition(View view) {
        if (NetworkConnection.isConnected(this)){
            //execute task
            XMLTask xmlTask = new XMLTask();
            xmlTask.execute("https://api.openweathermap.org/data/2.5/weather?mode=xml&appid=e242949c082b0e914a2f46b05f41eacf&lon=-0.13&lat=51.51");
        }else {
            Snackbar.make(constraintLayout,"No internet connectivity",Snackbar.LENGTH_LONG).show();
        }
    }

    public class XMLTask extends AsyncTask<String,Void,Weather>{

        @Override
        protected Weather doInBackground(String... strings) {
            Weather weather = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                ////Data conversion....
                XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
                xmlPullParserFactory.setNamespaceAware(true);
                xmlPullParser.setInput(inputStream,null);
                ////////////////////////////////////////////////
                int eventType = xmlPullParser.getEventType();// START_DOCUMENT....
                while (eventType != XmlPullParser.END_DOCUMENT){
                    //infinite loop/////
                    switch (eventType){
                        case XmlPullParser.START_DOCUMENT:
                            weather = new Weather();
                            break;
                        case XmlPullParser.START_TAG:
                            if(xmlPullParser.getName().equals("city")){
                                weather.setCityName(xmlPullParser.getAttributeValue(1));
                            }else if (xmlPullParser.getName().equals("country")){
                                weather.setCountryName(xmlPullParser.nextText());
                            }else if (xmlPullParser.getName().equals("temperature")){
                                weather.setTemp(xmlPullParser.getAttributeValue(0)+":"+xmlPullParser.getAttributeValue(3));
                            }else if (xmlPullParser.getName().equals("speed")){
                                weather.setWind(xmlPullParser.getAttributeValue(1));
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            break;

                    }
                    eventType = xmlPullParser.next();
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            if (weather!=null){
                textViewCity.setText(weather.getCityName());
                textViewCon.setText(weather.getCountryName());
                textViewTemp.setText(weather.getTemp());
                textViewWind.setText(weather.getWind());
            }
        }
    }


}














