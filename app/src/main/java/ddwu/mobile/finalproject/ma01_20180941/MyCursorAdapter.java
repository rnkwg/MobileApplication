package ddwu.mobile.finalproject.ma01_20180941;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCursorAdapter extends CursorAdapter {
    LayoutInflater inflater;
    int layout;

    public MyCursorAdapter(Context context, int layout, Cursor c) {
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = inflater.inflate(layout, viewGroup, false);
        ViewHolder holder = new ViewHolder();
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        if(holder.cafeName == null) {
            holder.cafeName = view.findViewById(R.id.cafeName);
            holder.themeName = view.findViewById(R.id.themeName);
            holder.grade = view.findViewById(R.id.grade);
            holder.picture = view.findViewById(R.id.picture);
        }
        holder.cafeName.setText(cursor.getString(cursor.getColumnIndexOrThrow(RoomEscapeCafeDBHelper.COL_CAFENAME)));
        holder.themeName.setText(cursor.getString(cursor.getColumnIndexOrThrow(RoomEscapeCafeDBHelper.COL_THEMENAME)));
        holder.grade.setText(cursor.getString(cursor.getColumnIndexOrThrow(RoomEscapeCafeDBHelper.COL_GRADE)));
        String picturePath = cursor.getString(cursor.getColumnIndexOrThrow(RoomEscapeCafeDBHelper.COL_PICTURE));
        Uri imageUri = null;
        if(picturePath != null) {
            imageUri = Uri.parse(picturePath);
        }
        holder.picture.setImageURI(imageUri);
    }

    static class ViewHolder {
        public ViewHolder() {
            cafeName = null;
            themeName = null;
            grade = null;
            picture = null;
        }

        TextView cafeName;
        TextView themeName;
        TextView grade;
        ImageView picture;
    }
}
