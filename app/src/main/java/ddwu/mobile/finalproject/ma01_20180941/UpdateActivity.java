package ddwu.mobile.finalproject.ma01_20180941;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateActivity extends AppCompatActivity {
    private static final String TAG = "UpdateActivity";

    EditText cafeName;
    EditText cafeLocation;
    EditText themeName;
    CheckBox escaped;
    EditText difficulty;
    EditText genre;
    EditText grade;
    EditText review;
    ImageView picture;
    boolean checked;

    RoomEscapeCafeDBHelper helper;
    RoomEscapeCafe roomEscapeCafe;

    private String mCurrentPhotoPath;

    private static final int REQUEST_TAKE_PHOTO = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        roomEscapeCafe = (RoomEscapeCafe) getIntent().getSerializableExtra("roomEscapeCafe");

        cafeName = findViewById(R.id.updateCafeName);
        cafeLocation = findViewById(R.id.updateCafeLocation);
        themeName = findViewById(R.id.updateThemeName);
        escaped = findViewById(R.id.updateEscaped);
        difficulty = findViewById(R.id.updateDifficulty);
        genre = findViewById(R.id.updateGenre);
        grade = findViewById(R.id.updateGrade);
        review = findViewById(R.id.updateReview);
        picture = findViewById(R.id.updatePicture);

        cafeName.setText(roomEscapeCafe.getCafeName());
        cafeLocation.setText(roomEscapeCafe.getCafeLocation());
        themeName.setText(roomEscapeCafe.getThemeName());
        if(roomEscapeCafe.isEscaped() == true)
            escaped.setChecked(true);
        Log.d(TAG, "checkbox" + roomEscapeCafe.isEscaped());
        difficulty.setText(Integer.toString(roomEscapeCafe.getDifficulty()));
        genre.setText(roomEscapeCafe.getGenre());
        grade.setText(Integer.toString(roomEscapeCafe.getGrade()));
        review.setText(roomEscapeCafe.getReview());
        picture.setImageURI(Uri.parse(roomEscapeCafe.getPicture()));

        escaped.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(escaped.isChecked() == true) {
                    checked = true;
                }
                else {
                    checked = false;
                }
            }
        });

        helper = new RoomEscapeCafeDBHelper(this);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnUpdateContact:
//                DB ????????? ?????? ?????? ??????
                if(TextUtils.isEmpty(cafeName.getText().toString()) ||
                        TextUtils.isEmpty(cafeLocation.getText().toString()) ||
                        TextUtils.isEmpty(themeName.getText().toString()) ||
                        TextUtils.isEmpty(escaped.getText().toString()) ||
                        TextUtils.isEmpty(difficulty.getText().toString()) ||
                        TextUtils.isEmpty(genre.getText().toString()) ||
                        TextUtils.isEmpty(grade.getText().toString()) ||
                        TextUtils.isEmpty(review.getText().toString())
                ) {
                    Toast.makeText(this, "????????? ??? ???????????? ???????????????", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    value.put(helper.COL_CAFENAME, cafeName.getText().toString());
                    value.put(helper.COL_CAFELOCATION, cafeLocation.getText().toString());
                    value.put(helper.COL_THEMENAME, themeName.getText().toString());
                    value.put(helper.COL_ESCAPED, checked);
                    value.put(helper.COL_DIFFICULTY, difficulty.getText().toString());
                    value.put(helper.COL_GENRE, genre.getText().toString());
                    value.put(helper.COL_GRADE, grade.getText().toString());
                    value.put(helper.COL_REVIEW, review.getText().toString());
                    if(mCurrentPhotoPath == null)
                        value.put(helper.COL_PICTURE, roomEscapeCafe.getPicture());
                    else
                        value.put(helper.COL_PICTURE, mCurrentPhotoPath);

                    String whereClause = RoomEscapeCafeDBHelper.COL_ID + "=?";
                    String[] whereArgs = new String[] { String.valueOf(roomEscapeCafe.get_id()) };
                    db.update(RoomEscapeCafeDBHelper.TABLE_NAME, value, whereClause, whereArgs);
                }
                helper.close();
                finish();
                break;
            case R.id.btnUpdateContactClose:
//			DB ????????? ?????? ?????? ??????
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.btnPicture:
                dispatchTakePictureIntent();
                break;
            case R.id.btnLocation:
                Intent intent = null;
                intent = new Intent(UpdateActivity.this, LocationActivity.class);
                intent.putExtra("cafeLocation", roomEscapeCafe.getCafeLocation());
                if(intent != null) startActivity(intent);
                break;
        }
    }

    /*?????? ?????? ?????? ??????*/
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//        ????????? ????????? ??? ?????? ????????? ?????? ?????? ??????
        if(takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            ????????? ????????? ?????? ??????
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

//            ????????? ?????? ??????????????? ??????
            if(photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(this,
                        "ddwu.mobile.finalproject.ma01_20180941.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    /*?????? ?????? ????????? ???????????? ?????? ?????? ??????*/
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        ????????? ????????? ????????? ???????????? imageView ??? ????????????
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            setPic();
        }
    }

    /*????????? ????????? ImageView?????? ????????? ??? ?????? ????????? ??????*/
    private void setPic() {
        // Get the dimensions of the View
        int targetW = picture.getWidth();
        int targetH = picture.getHeight();

        // Get the dimensions of the bitmap / ????????? ????????? ???????????? / ??????????????? ????????? ????????????
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image / ????????????
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View / ????????? ????????? bitmap?????? decoding?????? / ????????? ??????
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
//        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        picture.setImageBitmap(bitmap);
    }
}
