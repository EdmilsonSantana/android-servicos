package br.edu.ifpe.tads.pdm.android_servicos;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.net.URL;

/**
 * Created by Clarice on 17/11/2016.
 */

public class CityAdapter extends ArrayAdapter<City> {

    private City[] cities;

    private RequestQueue queue;

    public CityAdapter(Context context, int resource, City[] cities, RequestQueue queue) {
        super(context, resource);
        this.cities = cities;
        this.queue = queue;
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View listItem = null;
        ViewHolder holder = null;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            listItem = inflater.inflate(R.layout.city_listview, null, true);

            holder = new ViewHolder();
            holder.cityName = (TextView) listItem.findViewById(R.id.city_name);
            holder.cityWeather = (TextView) listItem.findViewById(R.id.city_weather);

            listItem.setTag(holder);
        } else {
            listItem = view;
            holder = (ViewHolder) view.getTag();
        }

        holder.cityName.setText(cities[position].getName());

        if (cities[position].getWeather() != null) {
            holder.cityWeather.setText(cities[position].getWeather());
        } else {
            final City city = cities[position];
            final TextView weather = holder.cityWeather;
            weather.setText("Loading weather...");
            loadInBackground(weather, city);
        }
        return listItem;
    }

    private void loadInBackground( final TextView weatherView, final City city) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http");
        builder.authority("api.openweathermap.org");
        builder.appendPath("data/2.5/forecast/daily");
        builder.appendQueryParameter("q", city.getName());
        builder.appendQueryParameter("mode", "json");
        builder.appendQueryParameter("units", "metric");
        builder.appendQueryParameter("cnt", String.valueOf(1));
        builder.appendQueryParameter("APPID", "");
    }

    static class ViewHolder {
        TextView cityName;
        TextView cityWeather;
    }
}
