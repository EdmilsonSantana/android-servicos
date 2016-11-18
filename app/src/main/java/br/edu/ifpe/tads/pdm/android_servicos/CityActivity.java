package br.edu.ifpe.tads.pdm.android_servicos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.toolbox.Volley;

public class CityActivity extends AppCompatActivity {

    public static final City[] cities = {new City("Recife"), new City("João Pessoa"),
            new City("Natal"), new City("Fortaleza"), new City("Rio de Janeiro"),
            new City("São Paulo"), new City("Salvador"), new City("Vitória"),
            new City("Florianópolis"), new City("Porto Alegre"), new City("São Luiz"),
            new City("Teresina"), new City("Belém"), new City("Manaus")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        Volley.newRequestQueue(this);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new CityAdapter(this, R.layout.city_listview, cities));
    }
}
