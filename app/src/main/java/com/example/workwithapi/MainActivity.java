
package com.example.workwithapi;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.bumptech.glide.Glide;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;

public class MainActivity extends AppCompatActivity {


    Button btn;
    ImageView image;
    String futureIconString = "";
    String icon_url = "https://assets.chucknorris.host/img/avatar/chuck-norris.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btnClick);
        image = findViewById(R.id.image);

        btn.setOnClickListener(view -> new IconLoader().execute());
    }

    protected void icon() {
        // Загружаем картинку
        Glide
                .with(this)
                .load(icon_url)
                .into(image);
    }

    private class IconLoader extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String jsonString = getJson("https://api.chucknorris.io/jokes/random");

            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                futureIconString += jsonObject.getString("icon_url");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            futureIconString = "";
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(!futureIconString.equals("")) {
                icon();
            }
        }
    }

    private String getJson(String link) {
        String data = "";
        try{
            URL url =new URL(link);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),
                        "utf-8"));
                data = r.readLine();
                urlConnection.disconnect();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }
}