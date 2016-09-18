package thinktechsol.msquare.Location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class GetLocation implements LocationListener {
    LocationManager locationManager;
    String provider;
    private Location location;
    double latitude;
    double longitude;
    // Current location address,city,country
    static String city, country, address;
    Context context;
    // Desired location address,city,country
    String DLAddress, DLCity, DLCountry;

    public GetLocation(Context context) {
        this.context = context;
    }

    public void getCurrentLocationOFMyPhone() {

        locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        // getting GPS status
        boolean isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        if (!isGPSEnabled && !isNetworkEnabled) {
            Toast.makeText(context, "Network not found", Toast.LENGTH_LONG).show();
        } else {

            try {
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER, 60000, 0, (android.location.LocationListener) GetLocation.this);
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            getCurrentLocationInformation(latitude, longitude);
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 0, (android.location.LocationListener) GetLocation.this);
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                Log.d("activity", "RLOC: loc by GPS");

                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                getCurrentLocationInformation(latitude, longitude);
                                ;
                            }
                        }
                    }
                }
            } catch (SecurityException e) {
                Log.d("GetLocation", "SecurityException=" + e);
            }

        }
    }

    public String getCurrentLatLng() {


        locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        // getting GPS status
        boolean isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        if (!isGPSEnabled && !isNetworkEnabled) {
            Toast.makeText(context, "Network not found", Toast.LENGTH_LONG).show();
        } else {
            try {
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER, 60000, 0, GetLocation.this);
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                        return latitude + "," + longitude;
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 0, GetLocation.this);
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                        return latitude + "," + longitude;
                    }
                }
            } catch (SecurityException e) {
                Log.d("GetLocation", "SecurityException=" + e);
            }
        }
        return null;
    }

    public String getCurrentLocationInformation(double latitude,
                                                double longitude) {
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 5);
            while (addresses.size() == 0) {
                addresses = geocoder.getFromLocation(latitude, longitude, 5);
                Log.e("lnglat", "lnglat=" + addresses);
            }
            if (addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0);
                city = addresses.get(0).getAddressLine(1);
                country = addresses.get(0).getAddressLine(2);
            } else {
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCityName() {
        return city;
    }

    public String getCountryName() {
        return country;
    }

    public String getAddress() {
        return address;
    }

    public void getDesiredLocationInformation(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses != null) {
            DLAddress = addresses.get(0).getAddressLine(0);
            DLCity = addresses.get(0).getAddressLine(1);
            DLCountry = addresses.get(0).getAddressLine(2);
        } else {
        }
    }

    //
    public String getDesiredLocationAddress() {
        // getDesiredLocationInformation(latlng.latitude, latlng.longitude);
        return DLAddress;
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
