package movie.yoni.mymovielibrary.objects;

/**
 * Created by User on 19/07/2017.
 */

public class DBConstants {

    public static final String API_MOVIE = "http://api.themoviedb.org/3/search/movie?api_key=07c664e1eda6cd9d46f1da1dbaefc959&query=";
    public static final String API_PICTURE = "http://image.tmdb.org/t/p/w500/";

    public static final String DATA_BASE_NAME = "MyMovieLibrary.db";

    public static final String TABLE_NAME_MOVIE ="movies";
    public static final String COL_ID_MOVIE = "_id";
    public static final String COL_TITLE_MOVIE = "title";
    public static final String COL_OVERVIEW_MOVIE = "overview";
    public static final String COL_URL_MOVIE = "url";


    public static final int VER = 2;
}
