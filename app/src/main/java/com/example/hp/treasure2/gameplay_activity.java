package com.example.hp.treasure2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class gameplay_activity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,View.OnClickListener {

    private GoogleMap mMap;
    private String data;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastlocation;
    private Marker currentLocationmMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 10000;
    double latitude,longitude;
    private Button search;
    private Firebase f;
    ArrayList<String> coupons= new ArrayList<>();
    String size;
    private ProgressDialog progressdialog ; //to show progress when user register as this app uses internet
    private  Button show;
    TextView textView3;
    LatLng l1,l2;
    Button button2,back;
    List<Address> addressList;
    double getLat[];
    double getLon[];
    String get[]=new String[100];
    String location,d1,name;
    boolean b=false;
    double d2,value;
    private ArrayList<String>id=new ArrayList<>();
    Location location1=new Location("Point A");
    Location location2=new Location("Point B");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay_activity);
        search = (Button)findViewById(R.id.search);
        show = (Button)findViewById(R.id.show);
        Firebase.setAndroidContext(this);
        progressdialog = new ProgressDialog(this);
        search.setOnClickListener(this);
        show.setOnClickListener(this);
        textView3=(TextView)findViewById(R.id.textView3);
        button2=(Button)findViewById(R.id.button2);
        back=(Button)findViewById(R.id.back);
        Firebase.setAndroidContext(this);
        f=new Firebase("https://treasure2-b836a.firebaseio.com/Coupons name");
        f.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String value=dataSnapshot.getValue(String.class);
                id.add(value);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        button2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       for(int i=0;i<coupons.size();i++)
                        {
                            value=distance(getLat[i],getLon[i],latitude,longitude);
                            if((value*1000)<200)
                            {
                                data=Integer.toString(i+1);
                                b=true;
                                name=get[i];
                                break ;
                            }
                            else
                            {
                                b=false;
                            }
                        }
                        if(b==true)
                        {
                            String s1=Double.toString(value);
                            Toast.makeText(gameplay_activity.this, "Coupon grabbed", Toast.LENGTH_LONG).show();
                            textView3.setText(s1);
                            finish();
                            Intent i = new Intent(getApplicationContext(),details_activity.class);
                            i.putExtra("data",data);
                            i.putExtra("name",name);
                            startActivity(i);
                        }
                        else
                        {
                            {
                                String s1=Double.toString(value);
                                Toast.makeText(gameplay_activity.this, "Coupon cannot be grabbed", Toast.LENGTH_LONG).show();
                                textView3.setText(s1);
                            }
                        }
                    }

                }
        );

        back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i=new Intent(getApplicationContext(),profile_activity.class);
                        startActivity(i);
                    }
                }
        );
    }
    @Override
    public void onClick(final View v)
    {
        if(v== search)
        {
            progressdialog.setMessage("Searching places where coupons are....");  //set message for progress dialog
            progressdialog.show(); // to show it make visible
            Firebase fdash = new Firebase("https://treasure2-b836a.firebaseio.com/Coupons name");
            fdash.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    String value = dataSnapshot.getValue(String.class);
                    System.out.println(value);
                    coupons.add(value);

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            progressdialog.dismiss(); //if let say user type email pppp then progress wont stop and error so to stop it
            Toast.makeText(gameplay_activity.this,"search complete click on show button",Toast.LENGTH_SHORT).show();









        }

        if(v==show)
        {  Toast.makeText(gameplay_activity.this,"showing places",Toast.LENGTH_SHORT).show();
            getLat=new double[coupons.size()];
             getLon=new double[coupons.size()];

            for(int j=0 ;j<coupons.size();j++)
            {
                location=(String)coupons.get(j);

                if(!location.equals(""))
                {
                    Geocoder geocoder = new Geocoder(this);

                    try {
                        addressList  = 0;i<addressList.size();i++)
                        {
                            LatLng latLng = new LatLng(addressList.get(i).getLatitude() , addressList.get(i).getLongitude());
                            getLat[j]=addressList.get(i).getLatitude();
                            getL= geocoder.getFromLocationName(location, 5);

                        if(addressList != null)
                        {
                            for(int ion[j]=addressList.get(i).getLongitude();
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(latLng);
                                markerOptions.title(location);
                                get[j]=markerOptions.getTitle();
                                mMap.addMarker(markerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(0));
                            }

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }



        }





    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            bulidGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


    }

    protected synchronized void bulidGoogleApiClient() {
        client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        client.connect();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            bulidGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                }
        }
    }



    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED )
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            return false;

        }
        else
            return true;
    }

    @Override
    public void onConnected(Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastlocation = location;
        if(currentLocationmMarker != null)
        {
            currentLocationmMarker.remove();

        }
        Log.d("lat = ", "" + latitude);
        LatLng latLng = new LatLng(location.getLatitude() , location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentLocationmMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(4));

        if(client != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }

        l1=latLng;

    }

/*    private double distance(double v, double v1, double latitude, double longitude) {

        double d2r=Math.PI/180;
        double dlong=(v1-longitude)*d2r;
        double dlat=(v-latitude)*d2r;
        double a=Math.pow(Math.sin(dlat/2.0),2)+Math.cos(latitude*d2r)*Math.cos(v*d2r)*Math.pow(Math.sin(dlong/2.0),2);
        double c=2*Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
        double d=6367*c;
        d2=d;
        return d2;
    }*/

    private  double distance(double lat1, double lon1, double lat2, double lon2)
    {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);

    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}


