package movie.yoni.mymovielibrary.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import movie.yoni.mymovielibrary.MainActivity;
import movie.yoni.mymovielibrary.Movie;
import movie.yoni.mymovielibrary.R;
import movie.yoni.mymovielibrary.database.DBHelperMovie;
import movie.yoni.mymovielibrary.objects.DBConstants;

public class ScreenWeb extends SearchInternetActivity implements View.OnClickListener {

    private TextView tx_title;
    private TextView tx_overview;
    private Button btn_return;
    private Button btn_save;
    private String url;
    private boolean isMain;
    private ImageView img_movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_web);


        Intent intent = getIntent();


        tx_overview = (TextView) findViewById(R.id.tx_overview);
        tx_title = (TextView) findViewById(R.id.tx_title);


        btn_return = (Button) findViewById(R.id.btn_return);
        btn_save = (Button) findViewById(R.id.btn_save);
        img_movie = (ImageView) findViewById(R.id.img_movie);


        btn_return.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        url = intent.getStringExtra("Url");

        if(url.contains("http")|| url.contains("content") ){

            Picasso.with(this).load(url).into(img_movie);
        }else {
            Bitmap bitmap;
            bitmap = StringToBitMap(url);
            img_movie.setImageBitmap(bitmap);


        }

        isMain=intent.getBooleanExtra("isMain",false);
        if (isMain == true){
            btn_save.setVisibility(View.INVISIBLE);
        }

        tx_title.setText(intent.getStringExtra("Title"));
        tx_overview.setText(intent.getStringExtra("Overview"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_return :
               this.finish();
                 break;

            case R.id.btn_save :
                DBHelperMovie dbHelperMovie = new DBHelperMovie(this);
                Movie movie = new Movie(tx_title.getText().toString(), tx_overview.getText().toString(),url);
                dbHelperMovie.AddMovie(movie);

                Intent intent1 = new Intent(ScreenWeb.this, MainActivity.class);
                startActivity(intent1);



                break;
        }
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    public class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {



            return null;
        }

        @Override
        protected void onPostExecute(String s) {

        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }
}
