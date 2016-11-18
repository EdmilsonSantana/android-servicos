package br.edu.ifpe.tads.pdm.android_servicos;

/**
 * Created by Clarice on 17/11/2016.
 */

public class City {
    private String name;
    private String weather;

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
