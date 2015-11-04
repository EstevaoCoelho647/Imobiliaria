package com.project.imobiliaria.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.imobiliaria.mapHelper.Localizador;
import com.project.imobiliaria.model.entities.House;
import com.project.imobiliaria.model.persistence.HouseRepository;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends SupportMapFragment {

    private ArrayList<House> housesOne;
    private List<House> houses;

    public MapFragment() {
    }

    @SuppressLint("ValidFragment")
    public MapFragment(ArrayList<House> house) {
        this.housesOne = house;
    }

    @Override
    public void onResume() {
        FragmentActivity context = getActivity();
        if (housesOne == null) {
            houses = HouseRepository.getAll();
            markerLocals(context, houses);
        }
        else
        markerLocals(context, housesOne);
        super.onResume();
    }

    public void markerLocals(Context context, List<House> houses) {
        for (House house : houses) {
            GoogleMap map = getMap();
            LatLng localHouse = new Localizador(context).getCoordenada(house.getEndereco());
            MarkerOptions options = new MarkerOptions().title(house.getId().toString()).position(localHouse);
            map.addMarker(options);
        }
    }

    public void centralizaLocal(LatLng local) {
        GoogleMap map = getMap();
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(local, 15);
        map.animateCamera(update);
    }
}
