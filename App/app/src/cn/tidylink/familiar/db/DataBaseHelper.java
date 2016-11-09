package cn.tidylink.familiar.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import cn.tidylink.familiar.App;

/**
 * Created by LT on 2015/10/26 0026.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_TABLE = "create table LOCATE_POINT(" +
            "id integer primary key autoincrement," +
            "time integet," +
            "points text)";

    public DataBaseHelper(String name) {
        super(App.getContext(),name,null,1);
    }

    public DataBaseHelper(String name,int version) {
        super(App.getContext(),name,null,version);
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
