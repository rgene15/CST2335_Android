package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.xmlpull.v1.XmlPullParser.START_TAG;


public class WeatherForecast extends AppCompatActivity {

    ProgressBar progBar;
    ImageView weatherImage;
    TextView currTempT;
    TextView minTempT;
    TextView maxTempT;
    TextView Uv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        ForecastQuery tQuery = new ForecastQuery();
        tQuery.execute();

        weatherImage = findViewById(R.id.weatherImg);
        currTempT = findViewById(R.id.currentTemp);
        minTempT = findViewById(R.id.mimTemp);
        maxTempT = findViewById(R.id.maxTemp);
        Uv = findViewById(R.id.UV);
        progBar = findViewById(R.id.progBar);
        progBar.setVisibility(View.VISIBLE);
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {

        public static  final String ACTIVITY_NAME = "Weather Forecast";
        private String wind;
        private String UV;
        private String minTemp;
        private String maxTemp;
        private String currentTemp;
        private Bitmap image;

        String icon;
        @Override
        protected String doInBackground(String... string) {    // Type1 is for doInBackground parameter
            String ret = null;
            String forecastUrlStr = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=bc2d8682d08b58cb83309fa6ef1f89c1&mode=xml&units=metric";
            String UVUrl = "http://api.openweathermap.org/data/2.5/uvi?appid=bc2d8682d08b58cb83309fa6ef1f89c1&lat=45.348945&lon=-75.759389";


            try {
                //     // connect to the weather forecast server
                URL forecastUrl = new URL(forecastUrlStr);
                HttpURLConnection connection = (HttpURLConnection) forecastUrl.openConnection();
                InputStream inStreamForecast = connection.getInputStream();

                // create parser for the XML
                XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
                pullParserFactory.setNamespaceAware(false);
                XmlPullParser xpp = pullParserFactory.newPullParser();
                xpp.setInput(inStreamForecast, "UTF-8");

                URL uv = new URL(UVUrl);
                connection = (HttpURLConnection) uv.openConnection();
                InputStream inStreamUv = connection.getInputStream();

                BufferedReader jsonReader = new BufferedReader(new InputStreamReader(inStreamUv, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder(100);
                String line;
                while ((line = jsonReader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();
                JSONObject jObj = new JSONObject(result);
                UV = String.valueOf(jObj.getDouble("value"));

                int EVENT_TYPE;
                while ((EVENT_TYPE = xpp.getEventType()) != XmlPullParser.END_DOCUMENT) {
                    // check if the tag's name is temperature
                    if (EVENT_TYPE == START_TAG) {
                        String tagName = xpp.getName();
                        switch (tagName) {
                            case "temperature":
                                currentTemp = xpp.getAttributeValue(null, "value");
                                publishProgress(25);
                                minTemp = xpp.getAttributeValue(null, "min");
                                publishProgress(50);
                                maxTemp = xpp.getAttributeValue(null, "max");
                                publishProgress(75);
                                break;
                            case "speed":
                                wind = xpp.getAttributeValue(null, "value");
                                break;
                            case "weather":
                                icon = xpp.getAttributeValue(null, "icon");
                        }
                    }
                    // move cursor to next XML element
                    xpp.next();
                }
                String file = icon + ".png";
                Log.i("looking for file", file);
                if (fileExistence(file)) {
                    Log.i("File found", file);
                    FileInputStream fis = null;
                    try {
                        fis = openFileInput(file);
                        image = BitmapFactory.decodeStream(fis);
                    } catch (FileNotFoundException fne) {
                        fne.printStackTrace();
                    }
                } else {
                    // gets the outlook picture (cloudy, sunny, rainy, etc.) from the server
                    URL outlookPicUrl = new URL("http://openweathermap.org/img/w/" + file);
                    connection = (HttpURLConnection) outlookPicUrl.openConnection();
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        image = BitmapFactory.decodeStream(connection.getInputStream());
                    }
                    // save outlookPic image to local storage
                    Log.i("file Download", file);
                    FileOutputStream outputStream = openFileOutput(file,
                            Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                }
                publishProgress(100);
                // connects to the UV index server

            } catch(MalformedURLException mfe){ ret = "Malformed URL exception"; }
            catch(IOException ioe){ ret = "IO Exception. Is the Wifi connected?";}
            catch(XmlPullParserException pe){ ret = "XML Pull exception. The XML is not properly formed" ;}
            catch (JSONException json) {
                ret = "JSON file Have an error";
            }
            return ret;
        }

        @Override                   //Type 3
        protected void onPostExecute(String sentFromDoInBackground) {
            super.onPostExecute(sentFromDoInBackground);
            //update GUI Stuff:
            char Temp = 0x2103;
            weatherImage.setImageBitmap(image);
            currTempT.setText(String.format("Current Temperature: %s%c", currentTemp, Temp));
            maxTempT.setText(String.format("High: %s%c", maxTemp, Temp));
            minTempT.setText(String.format("Low: %s%c", minTemp, Temp));
            Uv.setText(String.format("UV Index: %s", UV));
            progBar.setVisibility(View.INVISIBLE);
        }

        @Override                       //Type 2
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //Update GUI stuff only (the ProgressBar):
            progBar.setVisibility(View.VISIBLE);
            progBar.setProgress(values[0]);
        }



        private boolean fileExistence(String fileName) {
            File file = getBaseContext().getFileStreamPath(fileName);
            return file.exists();
        }
    }
}