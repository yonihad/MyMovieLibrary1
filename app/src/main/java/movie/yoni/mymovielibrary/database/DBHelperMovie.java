package movie.yoni.mymovielibrary.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;

import movie.yoni.mymovielibrary.Movie;
import movie.yoni.mymovielibrary.objects.DBConstants;

/**
 * Created by User on 19/07/2017.
 */

public class DBHelperMovie {

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;


    public DBHelperMovie(Context context) {
        dbHelper = new DBHelper(context);
        this.context = context;
    }

    public boolean AddMovie (Movie movie)
    {
        sqLiteDatabase=dbHelper.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(DBConstants.COL_OVERVIEW_MOVIE, movie.getOverview());
        values.put(DBConstants.COL_TITLE_MOVIE, movie.getTitle());
        values.put(DBConstants.COL_URL_MOVIE, movie.getUrl());



        long result = sqLiteDatabase.insert(DBConstants.TABLE_NAME_MOVIE, null, values);

        dbHelper.close();

        if (result == -1){
            return false;
        } else {
            return true;
        }

    }

    public ArrayList<Movie> getAllMovie (){

        sqLiteDatabase=dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(DBConstants.TABLE_NAME_MOVIE, null,null,null,null,null,null);

        ArrayList<Movie> movies = new ArrayList<>();

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DBConstants.COL_ID_MOVIE));
            String title = cursor.getString(cursor.getColumnIndex(DBConstants.COL_TITLE_MOVIE));
            String url = cursor.getString(cursor.getColumnIndex(DBConstants.COL_URL_MOVIE));
            String overview = cursor.getString(cursor.getColumnIndex(DBConstants.COL_OVERVIEW_MOVIE));
            Movie movie = new Movie(id, title, overview, url);
            movies.add(movie);
        }


        return movies;

 }
    public ArrayList<Movie> getAllMovieByTitle (String tit){

        sqLiteDatabase=dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(DBConstants.TABLE_NAME_MOVIE, null,DBConstants.COL_TITLE_MOVIE +" LIKE ?",new String[]{tit+"%"},null,null,null);

        ArrayList<Movie> movies = new ArrayList<>();

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DBConstants.COL_ID_MOVIE));
            String title = cursor.getString(cursor.getColumnIndex(DBConstants.COL_TITLE_MOVIE));
            String url = cursor.getString(cursor.getColumnIndex(DBConstants.COL_URL_MOVIE));
            String overview = cursor.getString(cursor.getColumnIndex(DBConstants.COL_OVERVIEW_MOVIE));


            Movie movie = new Movie(id, title, overview, url);
            movies.add(movie);
        }


        return movies;

    }
    public void DelMovie (int id){

        SQLiteDatabase db =dbHelper.getWritableDatabase();
        try {
            db.delete(DBConstants.TABLE_NAME_MOVIE, DBConstants.COL_ID_MOVIE +"=?",new String[]{String.valueOf(id)} );
        }catch (SQLiteException e){
            e.getCause();
        }finally {
            if(db.isOpen())
                db.close();;
        }


    }

}
