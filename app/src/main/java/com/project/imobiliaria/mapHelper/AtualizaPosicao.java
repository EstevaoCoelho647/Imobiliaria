package com.project.imobiliaria.mapHelper;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.project.imobiliaria.fragment.MapFragment;

public class AtualizaPosicao implements LocationListener {
    LocationManager locationManager;
    private MapFragment mapa;

    public AtualizaPosicao(Activity activity, MapFragment mapa) {
        this.mapa = mapa;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        String provider = LocationManager.GPS_PROVIDER;
        long tempoMin = 20000;
        float distanciaMin = 20;
        locationManager.requestLocationUpdates(provider, tempoMin, distanciaMin, this);
    }

    public void cancelar() {
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng local = new LatLng(latitude, longitude);
        mapa.centralizaLocal(local);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }
}
