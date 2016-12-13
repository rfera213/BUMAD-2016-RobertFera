package robertfera.mad.bu.edu.bumad_2016_robertfera;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.os.Handler;
import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class BusMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private static String urlBuses = "http://www.bu.edu/bumobile/rpc/bus/livebus.json.php";
    private static String urlBusStops = "http://www.bu.edu/bumobile/rpc/bus/stops.json.php";

    private String busesData;
    private String busStopData;
    private ArrayList<Marker> markers = new ArrayList<Marker>();

    // JSON Node names
    private static final String TAG_RESULTSET = "ResultSet";
    private static final String TAG_RESULT = "Result";

    // how often to update buses
    private final int delay = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // centers map on campus
        mMap = googleMap;
        LatLng campus = new LatLng(42.347, -71.095);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(campus, 13.25f));

        // polyline for route
        mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(42.352514, -71.118344), new LatLng(42.353600, -71.118092), new LatLng(42.353687, -71.117968), new LatLng(42.353683, -71.117775), new LatLng(42.353190, -71.116724), new LatLng(42.352641, -71.115673), new LatLng(42.352482, -71.115479),
                        new LatLng(42.351745, -71.115656), new LatLng(42.351007, -71.115801), new LatLng(42.350774, -71.113768), new LatLng(42.350520, -71.111773), new LatLng(42.349834, -71.106006), new LatLng(42.348882, -71.098013), new LatLng(42.348764, -71.096972),
                        new LatLng(42.348851, -71.092836), new LatLng(42.348545, -71.092772), new LatLng(42.347915, -71.092455), new LatLng(42.347808, -71.092359), new LatLng(42.347245, -71.092407), new LatLng(42.346948, -71.092380), new LatLng(42.346813, -71.092284),
                        new LatLng(42.346757, -71.092166), new LatLng(42.346444, -71.091141), new LatLng(42.346301, -71.090884), new LatLng(42.346083, -71.090739), new LatLng(42.345865, -71.090669), new LatLng(42.345493, -71.090594), new LatLng(42.344831, -71.090744),
                        new LatLng(42.344595, -71.090776), new LatLng(42.344359, -71.090744), new LatLng(42.344244, -71.090661), new LatLng(42.344109, -71.090444), new LatLng(42.343984, -71.089990), new LatLng(42.343831, -71.089124), new LatLng(42.343324, -71.085798),
                        new LatLng(42.342773, -71.084913), new LatLng(42.342265, -71.084189), new LatLng(42.342150, -71.084124), new LatLng(42.341337, -71.082923), new LatLng(42.340572, -71.081802), new LatLng(42.340255, -71.081367), new LatLng(42.339801, -71.080916),
                        new LatLng(42.339208, -71.080348), new LatLng(42.338391, -71.079409), new LatLng(42.337325, -71.078068), new LatLng(42.336448, -71.077027), new LatLng(42.333478, -71.073444), new LatLng(42.333569, -71.073304), new LatLng(42.333676, -71.073278),
                        new LatLng(42.334406, -71.072205), new LatLng(42.334937, -71.071443), new LatLng(42.334985, -71.071432), new LatLng(42.335469, -71.070719), new LatLng(42.335933, -71.070080), new LatLng(42.336016, -71.070182), new LatLng(42.337495, -71.071958),
                        new LatLng(42.338871, -71.073589), new LatLng(42.338013, -71.074844), new LatLng(42.336520, -71.076915), new LatLng(42.337892, -71.078572), new LatLng(42.338605, -71.079468), new LatLng(42.339367, -71.080353), new LatLng(42.340015, -71.080955),
                        new LatLng(42.340564, -71.081574), new LatLng(42.340640, -71.081705), new LatLng(42.341825, -71.083459), new LatLng(42.342220, -71.084028), new LatLng(42.342265, -71.084167), new LatLng(42.342997, -71.085262), new LatLng(42.343332, -71.085787),
                        new LatLng(42.345187, -71.086694), new LatLng(42.347975, -71.088083), new LatLng(42.349763, -71.088952), new LatLng(42.350849, -71.089489), new LatLng(42.350359, -71.091259), new LatLng(42.350115, -71.092214), new LatLng(42.349945, -71.092874),
                        new LatLng(42.349878, -71.093308), new LatLng(42.349755, -71.093571), new LatLng(42.349204, -71.095459), new LatLng(42.349073, -71.096033), new LatLng(42.348982, -71.096522), new LatLng(42.349009, -71.097149), new LatLng(42.348997, -71.097358),
                        new LatLng(42.349973, -71.105319), new LatLng(42.350472, -71.109428), new LatLng(42.350472, -71.109729), new LatLng(42.351543, -71.118548), new LatLng(42.352514, -71.118344))
                .width(5)
                .color(Color.RED));

        // fetches bus stops
        new GetBusStops().execute();

        // sets up timer to refresh bus markers
        final Handler h = new Handler();
        h.postDelayed(new Runnable(){
            public void run(){
                h.postDelayed(this, delay);
                new GetBuses().execute();
            }
        }, delay);

    }

    public void refreshMarkers(String json) {

        try {
            JSONObject jsonObj = new JSONObject(json);

            // Getting JSON Array node
            JSONObject resultSet = jsonObj.getJSONObject(TAG_RESULTSET);
            JSONArray results = resultSet.getJSONArray(TAG_RESULT);

            // looping through data
            for (int i = 0; i < results.length(); i++) {
                JSONObject c = results.getJSONObject(i);

                LatLng bus = new LatLng(c.getDouble("lat"), c.getDouble("lng"));
                Marker markerToUpdate = markers.get(i);
                markerToUpdate.setPosition(bus);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetBuses extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            WebRequest webreq = new WebRequest();

            // Making a request to url and getting response
            String jsonStr = webreq.makeWebServiceCall(urlBuses, WebRequest.GET);
            busesData = jsonStr;

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (markers.size() == 0) {
                try {
                    JSONObject jsonObj = new JSONObject(busesData);

                    // Getting JSON Array node
                    JSONObject resultSet = jsonObj.getJSONObject(TAG_RESULTSET);
                    JSONArray results = resultSet.getJSONArray(TAG_RESULT);

                    // looping through data
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject c = results.getJSONObject(i);

                        LatLng bus = new LatLng(c.getDouble("lat"), c.getDouble("lng"));
                        Marker marker = mMap.addMarker(new MarkerOptions().position(bus).icon(BitmapDescriptorFactory.fromResource(R.drawable.livebus)).title("Bus"));
                        markers.add(marker);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                refreshMarkers(busesData);
            }

        }
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetBusStops extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            WebRequest webreq = new WebRequest();

            // Making a request to url and getting response
            String jsonStr = webreq.makeWebServiceCall(urlBusStops, WebRequest.GET);
            busStopData = jsonStr;

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            addBusStops(busStopData);

        }

        public void addBusStops(String json) {

            try {
                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                JSONObject resultSet = jsonObj.getJSONObject(TAG_RESULTSET);
                JSONArray results = resultSet.getJSONArray(TAG_RESULT);

                // looping through data
                for (int i = 0; i < results.length(); i++) {
                    JSONObject c = results.getJSONObject(i);

                    LatLng bus = new LatLng(c.getDouble("stop_lat"), c.getDouble("stop_lon"));
                    Marker marker = mMap.addMarker(new MarkerOptions().position(bus).icon(BitmapDescriptorFactory.fromResource(R.drawable.businbound)).title("Bus Stop"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
