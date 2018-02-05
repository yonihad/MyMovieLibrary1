package movie.yoni.mymovielibrary.ui;

import android.app.AlertDialog;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import movie.yoni.mymovielibrary.MainActivity;
import movie.yoni.mymovielibrary.Movie;
import movie.yoni.mymovielibrary.R;
import movie.yoni.mymovielibrary.database.DBHelperMovie;

public class ScreenManual extends AppCompatActivity implements View.OnClickListener {

    private EditText et_title;
    private String img=null;
    private EditText et_overview;
    private ImageView img_movie_manual;
    private Button btn_return_manual;
    private Button btn_save_manual;
    private static final int ACTION_CAMERA = 2000;
    private static final int ACTION_GALLERY = 1002;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_manual);

        et_title = (EditText) findViewById(R.id.et_title);
        et_overview= (EditText) findViewById(R.id.et_overview);
        img_movie_manual = (ImageView) findViewById(R.id.img_movie_manual);
        btn_return_manual = (Button) findViewById(R.id.btn_return_manual);
        btn_save_manual = (Button) findViewById(R.id.btn_save_manual);

        btn_return_manual.setOnClickListener(this);
        btn_save_manual.setOnClickListener(this);
        img_movie_manual.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_return_manual :
                final Intent intent = new Intent(ScreenManual.this , MainActivity.class);
                startActivity(intent);
                 break;

            case R.id.btn_save_manual:
                if (!et_title.getText().toString().isEmpty()){

                    DBHelperMovie dbHelperMovie = new DBHelperMovie(this);
                    Movie movie = new Movie(et_title.getText().toString(), et_overview.getText().toString(),img);
                    dbHelperMovie.AddMovie(movie);

                    Intent intent1 = new Intent(ScreenManual.this , MainActivity.class);
                    startActivity(intent1);


                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ScreenManual.this);
                    builder.setMessage("OOPS :/ You did not enter a title !");
                    builder.show();
                }

                break;

            case R.id.img_movie_manual:
                AlertDialog.Builder builder = new AlertDialog.Builder(ScreenManual.this);
                builder.setTitle("Choose your photo from : ");
                builder.setPositiveButton("CAMERA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent1,ACTION_CAMERA);




                    }
                });

                builder.setNegativeButton("GALLERY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent1 = new Intent(Intent.ACTION_PICK);
                        intent1.setType("image/*");
                        startActivityForResult(intent1,ACTION_GALLERY);

                        }




                });

                builder.show();





                break;
        }
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream stream=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);
        byte [] b = stream.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri imageUri;
        Bitmap bitmap;


        switch (requestCode){
            case ACTION_CAMERA :

                if (requestCode == ACTION_CAMERA && resultCode == Activity.RESULT_OK){
                    Bundle extras = data.getExtras();
                    bitmap = (Bitmap) extras.get("data");
                    img_movie_manual.setImageBitmap(bitmap);
                    img=BitMapToString(bitmap);

                }

                break;


            case ACTION_GALLERY :

                if(resultCode == Activity.RESULT_OK){
                    imageUri=data.getData();
                    img_movie_manual.setImageURI(imageUri);
                    img=imageUri.toString();
                }

                break;
        }
    }
}
