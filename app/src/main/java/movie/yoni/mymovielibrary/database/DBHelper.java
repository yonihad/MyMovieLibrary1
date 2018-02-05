package movie.yoni.mymovielibrary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import movie.yoni.mymovielibrary.objects.DBConstants;

/**
 * Created by User on 19/07/2017.
 */

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context, DBConstants.DATA_BASE_NAME, null, DBConstants.VER);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + DBConstants.TABLE_NAME_MOVIE + "(";
        sql+= DBConstants.COL_ID_MOVIE + " INTEGER PRIMARY KEY AUTOINCREMENT ,";
        sql+= DBConstants.COL_OVERVIEW_MOVIE + " TEXT ,";
        sql+= DBConstants.COL_TITLE_MOVIE + " TEXT ,";
        sql+= DBConstants.COL_URL_MOVIE+ " TEXT";

        sql+=")";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TABLE_NAME_MOVIE);



        onCreate(db);

    }
}
