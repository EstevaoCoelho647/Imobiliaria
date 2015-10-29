package com.project.imobiliaria.controller.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.project.imobiliaria.R;
import com.project.imobiliaria.fragment.MapFragment;
import com.project.imobiliaria.mapHelper.AtualizaPosicao;
import com.project.imobiliaria.model.entities.House;

import java.util.ArrayList;

public class ViewHouses extends FragmentActivity {
    AtualizaPosicao atualizaPosicao;
    House house;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);
        initHouse();

        if (house.getId() == null) {
            FragmentManager manager = getSupportFragmentManager();

            FragmentTransaction transaction = manager.beginTransaction();
            MapFragment mapFragment = new MapFragment();
            transaction.replace(R.id.mapa, new MapFragment());
            transaction.replace(R.id.mapa, mapFragment);
            transaction.commit();
            atualizaPosicao = new AtualizaPosicao(this, mapFragment);
        } else {
            ArrayList<House> houses = new ArrayList<>();
            houses.add(house);
            MapFragment mapFragment = new MapFragment();
            mapFragment.markerLocals(getApplicationContext(), houses);
        }
    }

    private void initHouse() {
        Bundle values = getIntent().getExtras();
        if (values != null) {
            this.house = values.getParcelable("SelectedHouse");
        } else
            this.house = new House();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        atualizaPosicao.cancelar();
    }
}

