package login.com.girish.servicedemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<Bitmap> {

    private Intent intent;
    private ImageView imageView;
    private String urlImg = "https://i.ytimg.com/vi/uP4WxY7GGZk/hqdefault.jpg";
    private ProgressBar progressBar;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);
        requestQueue = Volley.newRequestQueue(this);
        intent = new Intent(this,MyService.class);
        intent.putExtra("my_value","Girish");
    }

    public void start(View view) {
        startService(intent);
    }

    public void stop(View view) {
        stopService(intent);
    }

    public void download(View view) {
        if(NetworkConnection.isConnected(this)){
            //performnetworkoperation
            //ImageTask imageTask = new ImageTask();
            //imageTask.execute(urlImg);
            ImageRequest imageRequest = new ImageRequest(urlImg,this,300,300, ImageView.ScaleType.CENTER,null,this);
            requestQueue.add(imageRequest);
        }else {
            Toast.makeText(this, "Not connected....", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(Bitmap response) {
        imageView.setImageBitmap(response);
    }


    /*
    public class ImageTask extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String str = strings[0];
            Bitmap bitmap = null;
            try {
                URL url = new URL(str);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                ////////////////////
                //conversion/////////
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            progressBar.setVisibility(View.GONE);
            if (bitmap !=null){
                imageView.setImageBitmap(bitmap);
                //done....
            }

        }
    }
    */

}





















