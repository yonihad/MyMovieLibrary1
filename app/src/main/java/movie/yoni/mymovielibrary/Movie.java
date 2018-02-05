package movie.yoni.mymovielibrary;

/**
 * Created by User on 19/07/2017.
 */

public class Movie {

    private int id;
    private String title;
    private String overview;
    private String url;



    public Movie(int id, String title, String overview, String url) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.url = url;
    }



    public Movie(String s, String toString, String img) {
        this.id = 0;
        this.title = title;
        this.overview = overview;
        this.url = url;
    }

    public Movie() {
        this.id = 0;
        this.title = this.title;
        this.overview = this.overview;
        this.url = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    @Override
    public String toString() {
        return title;
    }
}
