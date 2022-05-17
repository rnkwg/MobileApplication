package ddwu.mobile.finalproject.ma01_20180941;

import static org.xmlpull.v1.XmlPullParser.TEXT;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class RoomEscapeCafeDBHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "room_escape_cafe_db";
    public final static String TABLE_NAME = "room_escape_cafe_table";
    public final static String COL_ID = "_id";
    public final static String COL_CAFENAME = "cafeName";
    public final static String COL_CAFELOCATION = "cafeLocation";
    public final static String COL_THEMENAME = "themeName";
    public final static String COL_ESCAPED = "escaped";
    public final static String COL_DIFFICULTY = "difficulty";
    public final static String COL_GENRE = "genre";
    public final static String COL_GRADE = "grade";
    public final static String COL_REVIEW = "review";
    public final static String COL_PICTURE = "picture";

    public RoomEscapeCafeDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ( " + COL_ID + " integer primary key autoincrement,"
                + COL_CAFENAME + " TEXT, " + COL_CAFELOCATION + " TEXT, " + COL_THEMENAME + " TEXT, "
                + COL_ESCAPED + " TEXT, " + COL_DIFFICULTY + " TEXT, " + COL_GENRE + " TEXT, "
                + COL_GRADE + " TEXT, " + COL_REVIEW + " TEXT, " + COL_PICTURE + " TEXT);");

//		샘플 데이터
//        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (null, '홍대 비트포비아 던전', '홍대', '꿈의공장', '1', '3', '감성적', '4', '취준생의 감성 자극 스토리');");
//        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (null, '홍대 비트포비아 던전', '홍대', '사라진 보물', '1', '4', '모험/어드벤처', '5', '인테리어가 멋지고 재밌는 탐험을 할 수 있다');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE_NAME);
        onCreate(db);
    }
}
