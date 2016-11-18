package br.edu.ifpe.tads.pdm.android_servicos;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Clarice on 16/11/2016.
 */

public class ForecastTask extends AsyncTask<String, Void, String[]> {

    private final String LOG_TAG = ForecastTask.class.getSimpleName();
    private String[] forecast = null;
    private ForecastListener listener = null;
    private final String APPID = "3129d6ce645ca3ce691f09c596e131c6";

    public ForecastTask(ForecastListener listener) {
        this.listener = listener;
    }

    @Override
    protected String[] doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        int noOfDays = 7;
        String locationString = params[0];
        String forecastJson = null;

        try {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http");
            builder.authority("api.openweathermap.org");
            builder.appendPath("data/2.5/forecast/daily");
            builder.appendQueryParameter("q", locationString);
            builder.appendQueryParameter("mode", "json");
            builder.appendQueryParameter("units", "metric");
            builder.appendQueryParameter("cnt", String.valueOf(noOfDays));
            builder.appendQueryParameter("APPID", APPID);
            URL url = new URL(builder.build().toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (buffer == null) {
                forecastJson = null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
                buffer.append("\n");
            }
            if (buffer.length() == 0) {
                forecastJson = null;
            } else {
                forecastJson = buffer.toString();
            }
            forecast = ForecastParser.getDataFromJson(forecastJson, noOfDays);
        } catch (MalformedURLException e) {
            Log.d(LOG_TAG, e.getMessage(), e);
        } catch (IOException e) {
            Log.d(LOG_TAG, e.getMessage(), e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                }
            }
        }
        return forecast;
    }

    @Override
    protected void onPostExecute(String[] result) {
        for (String s : result) {
            Log.v(LOG_TAG, "Forecast entry: " + s);
        }
        listener.showForecast(result);
    }
}
