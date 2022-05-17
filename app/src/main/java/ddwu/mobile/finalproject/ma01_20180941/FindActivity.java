package ddwu.mobile.finalproject.ma01_20180941;

import android.graphics.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.*;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FindActivity extends AppCompatActivity implements OnMapReadyCallback {
    final static String TAG = "FindActivity";

    private static NaverMap naverMap;
    private EditText region;
    private String regionString;
    private Geocoder geocoder;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        geocoder = new Geocoder(this, Locale.getDefault());

        region = findViewById(R.id.etRegion);

        //        map 객체 준비(mapFragment)
        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.findMap);
        mapFragment.getMapAsync(this); //map 정보 가져오기(callback 호출)
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearch:
                Log.d(TAG, "검색 버튼 눌렸다");
                regionString = region.getText().toString();
                List<LatLng> latLng = getLatLng(regionString);
                double longitude = latLng.get(0).longitude;
                double latitude = latLng.get(0).latitude;
                LatLng latLng1 = new LatLng(latitude, longitude);

                Log.d(TAG, "latitude : " + latitude + " longitude : " + longitude);

                Marker marker = new Marker();
                marker.setPosition(latLng1);
                marker.setMap(naverMap);

                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(latLng1);
                naverMap.moveCamera(cameraUpdate);

                break;
        }
    }

    //    Reverse geocoding
    private List<LatLng> getLatLng(String targetLocation) {

        List<Address> addresses = null;
        ArrayList<LatLng> addressFragments = null;

//        주소에 해당하는 위도/경도 정보를 Geocoder 에게 요청
        try {
            addresses = geocoder.getFromLocationName(targetLocation, 1);
        } catch (IOException e) { // Catch network or other I/O problems.
            e.printStackTrace();
        } catch (IllegalArgumentException e) { // Catch invalid address values.
            e.printStackTrace();
        }

        if (addresses == null || addresses.size()  == 0) {
            return null;
        } else {
            Address addressList = addresses.get(0);
            addressFragments = new ArrayList<LatLng>();

            for(int i = 0; i <= addressList.getMaxAddressLineIndex(); i++) {
                LatLng latLng = new LatLng(addressList.getLatitude(), addressList.getLongitude());
                addressFragments.add(latLng);
            }
        }

        return addressFragments;
    }
}
