package cs.dal.safewalk;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);

        EditText dest = (EditText)findViewById(R.id.editText5);
        RadioButton morn = (RadioButton)findViewById(R.id.radioButton2);
        RadioButton after = (RadioButton)findViewById(R.id.radioButton3);
        RadioButton even = (RadioButton)findViewById(R.id.radioButton4);
        RadioButton night = (RadioButton)findViewById(R.id.radioButton5);
        Button protect = (Button)findViewById(R.id.button5);

        protect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EditText source = (EditText)findViewById(R.id.editText4);
                String strs = source.toString();
                getLatLongFromPlace(strs);
            }


        });

    }



    public void getLatLongFromPlace(String place)
    {
        try
        {
            Geocoder selected_place_geocoder = new Geocoder(getApplicationContext());
            List<Address> address;

            address = selected_place_geocoder.getFromLocationName(place, 5);

            if (address == null)
            {
                //d.dismiss();
            } else
            {
                Address location = address.get(0);
                double lat = location.getLatitude();
                double lng = location.getLongitude();

                System.out.println("lat " +lat);

            }

        } catch (Exception e)
        {
            e.printStackTrace();
            fetchLatLongFromService fetch_latlng_from_service_abc = new fetchLatLongFromService(
                    place.replaceAll("\\s+", ""));
            fetch_latlng_from_service_abc.execute();

        }
    }
//Sometimes happens that device gives location = null



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
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }




}


