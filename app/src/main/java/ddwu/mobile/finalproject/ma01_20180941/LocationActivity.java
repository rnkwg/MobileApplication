package ddwu.mobile.finalproject.ma01_20180941;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity {
    final static String TAG = "LocationActivity";
    final static int PERMISSION_REQ_CODE = 100;

    private GoogleMap mGoogleMap;
    private Geocoder geocoder;
    private double longitude;
    private double latitude;
    private String location;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        geocoder = new Geocoder(this, Locale.getDefault());

        location = (String) getIntent().getSerializableExtra("cafeLocation");
        List<LatLng> latLng = getLatLng(location);
        longitude = latLng.get(0).longitude;
        latitude = latLng.get(0).latitude;

        Log.d(TAG, "longitude: " + longitude + " latitude: " + latitude);

        //        map 객체 준비(mapFragment)
        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(mapReadyCallBack); //map 정보 가져오기(callback 호출)
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOk:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }

    OnMapReadyCallback mapReadyCallBack = new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            mGoogleMap = googleMap; //map 정보 가져오기 멤버에 저장한다

//            위도 경도를 저장할 수 있는 객체에 위치 지정
            LatLng currentLoc = new LatLng(latitude, longitude);
//            지정한 위치로 이동 후 17의 배율로 확대
//            mgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17));
//            지정한 위치로 애니메이션 이동 부드럽게 이동한다
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17));

//            마커 정보 지정
            MarkerOptions options = new MarkerOptions();
            options.position(currentLoc);
            options.title(location);
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            Marker centerMarker = mGoogleMap.addMarker(options);
            centerMarker.showInfoWindow();
        }

    };

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

    /* 필요 permission 요청 */
    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 퍼미션을 획득하였을 경우 맵 로딩 실행
            } else {
                // 퍼미션 미획득 시 액티비티 종료
                Toast.makeText(this, "앱 실행을 위해 권한 허용이 필요함", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
