package ddwu.mobile.finalproject.ma01_20180941;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AllActivity extends AppCompatActivity {
    private static final String TAG = "AllActivity";

    ListView lvRoomEscapeCafe = null;
    RoomEscapeCafeDBHelper helper;
    Cursor cursor;
    MyCursorAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

        lvRoomEscapeCafe = (ListView) findViewById(R.id.lvRoomEscapeCafe);

        helper = new RoomEscapeCafeDBHelper(this);

        adapter = new MyCursorAdapter(this, R.layout.listview_layout, null);

        lvRoomEscapeCafe.setAdapter(adapter);

//        리스트 뷰 클릭 처리
        lvRoomEscapeCafe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //"delete from contact_table where _id ="  + id
                cursor.moveToPosition(position);

                RoomEscapeCafe roomEscapeCafe = new RoomEscapeCafe();
                roomEscapeCafe.set_id(cursor.getInt(cursor.getColumnIndexOrThrow(RoomEscapeCafeDBHelper.COL_ID)));
                roomEscapeCafe.setCafeName(cursor.getString(cursor.getColumnIndexOrThrow(RoomEscapeCafeDBHelper.COL_CAFENAME)));
                roomEscapeCafe.setCafeLocation(cursor.getString(cursor.getColumnIndexOrThrow(RoomEscapeCafeDBHelper.COL_CAFELOCATION)));
                roomEscapeCafe.setThemeName(cursor.getString(cursor.getColumnIndexOrThrow(RoomEscapeCafeDBHelper.COL_THEMENAME)));
                int check = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(RoomEscapeCafeDBHelper.COL_ESCAPED)));
                if(check == 1)
                    roomEscapeCafe.setEscaped(true);
                else
                    roomEscapeCafe.setEscaped(false);
                roomEscapeCafe.setDifficulty(cursor.getInt(cursor.getColumnIndexOrThrow(RoomEscapeCafeDBHelper.COL_DIFFICULTY)));
                roomEscapeCafe.setGenre(cursor.getString(cursor.getColumnIndexOrThrow(RoomEscapeCafeDBHelper.COL_GENRE)));
                roomEscapeCafe.setGrade(cursor.getInt(cursor.getColumnIndexOrThrow(RoomEscapeCafeDBHelper.COL_GRADE)));
                roomEscapeCafe.setReview(cursor.getString(cursor.getColumnIndexOrThrow(RoomEscapeCafeDBHelper.COL_REVIEW)));
                roomEscapeCafe.setPicture(cursor.getString(cursor.getColumnIndexOrThrow(RoomEscapeCafeDBHelper.COL_PICTURE)));

                Intent intent = new Intent(AllActivity.this, UpdateActivity.class);
                intent.putExtra("roomEscapeCafe", roomEscapeCafe);
                startActivity(intent);

                onResume();
            }
        });

//		리스트 뷰 롱클릭 처리
        lvRoomEscapeCafe.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                final int pos = position;
                String message = "정보를 삭제하시겠습니까?";
                AlertDialog.Builder builder = new AlertDialog.Builder(AllActivity.this);
                builder.setTitle("삭제 확인")
                        .setMessage(message)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
                                String whereClause = RoomEscapeCafeDBHelper.COL_ID + "=?";
                                String[] whereArgs = new String[] { String.valueOf(id) };
                                sqLiteDatabase.delete(RoomEscapeCafeDBHelper.TABLE_NAME, whereClause, whereArgs);
                                helper.close();

                                onResume();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .setCancelable(false)
                        .show();
                return true;
            }
        });
    }

    protected void onResume() {
        super.onResume();
//        DB에서 데이터를 읽어와 Adapter에 설정
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("select * from " + RoomEscapeCafeDBHelper.TABLE_NAME, null);

        adapter.changeCursor(cursor);
        helper.close();
    }

    protected void onDestroy() {
        super.onDestroy();
//        cursor 사용 종료
        if (cursor != null) cursor.close();
    }
}
