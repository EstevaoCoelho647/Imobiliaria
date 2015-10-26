package com.project.imobiliaria.fragment;

import android.content.Context;
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
import java.util.List;


public class MapFragment extends SupportMapFragment {

    public MapFragment(){
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity context = getActivity();
        List<House> houses = HouseRepository.getAll();

        markerLocals(context, houses);
    }

    public void markerLocals(Context context, List<House> houses) {

        for(House house : houses){
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
