package ddwu.mobile.finalproject.ma01_20180941;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btnOpenAll:
                Toast.makeText(this, "전체 방탈출 보기", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "전체 방탈출 보기");
                intent = new Intent(MainActivity.this, AllActivity.class);
                break;
            case R.id.btnAddNew:
                Toast.makeText(this, "방탈출 추가", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "방탈출 추가");
                intent = new Intent(MainActivity.this, InsertActivity.class);
                break;
            case R.id.btnFind:
                Toast.makeText(this, "방탈출 찾기", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "방탈출 찾기");
                intent = new Intent(MainActivity.this, FindActivity.class);
                break;
        }
        if(intent != null) startActivity(intent);
    }
}