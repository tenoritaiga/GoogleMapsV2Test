package com.smartcity.redux;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.smartcity.redux.adapters.SensorDataAdapter;
import com.smartcity.redux.route.Routing;
import com.smartcity.redux.route.Routing.TravelMode;
import com.smartcity.redux.route.RoutingListener;

public class DirectionsActivity
{
    
}
