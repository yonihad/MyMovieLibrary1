package movie.yoni.mymovielibrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import movie.yoni.mymovielibrary.database.DBHelperMovie;
import movie.yoni.mymovielibrary.ui.ScreenManual;
import movie.yoni.mymovielibrary.ui.ScreenWeb;
import movie.yoni.mymovielibrary.ui.SearchInternetActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener  {


    private ListView lv;
    private Button btnAdd;
    private SearchView Sview;
    private DBHelperMovie dbHelperMovie;
    private ArrayList<Movie> movieArrayList;
    private ArrayAdapter<Movie> adapter;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        lv = (ListView) findViewById(R.id.listView);


        btnAdd = (Button) findViewById(R.id.btnadd);
        dbHelperMovie = new DBHelperMovie(this);

        movieArrayList = new ArrayList<Movie>();
        movieArrayList = dbHelperMovie.getAllMovie();

        adapter = new ArrayAdapter<Movie>(this, android.R.layout.simple_list_item_1, movieArrayList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Movie movie = movieArrayList.get(i);

                Intent intent = new Intent(MainActivity.this , ScreenWeb.class);
                intent.putExtra("Title" , movie.getTitle());
                intent.putExtra("Overview" , movie.getOverview());
                intent.putExtra("Url" , movie.getUrl());
                intent.putExtra("isMain" ,true);
                startActivity(intent);
            }
        });

        lv.setOnItemLongClickListener(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Choose what you want").setMessage("Choose how you want to search your movie : ");
                builder.setPositiveButton("MANUAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this, ScreenManual.class);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("WEB", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this , SearchInternetActivity.class);
                        startActivity(intent);
                    }
                });

                builder.show();

            }
        });

        searchView = (SearchView) findViewById(R.id.Sview);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.clear();
                movieArrayList = dbHelperMovie.getAllMovieByTitle(query);
                adapter = new ArrayAdapter<Movie>(getBaseContext(), android.R.layout.simple_list_item_1, movieArrayList);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.contentEquals("")){

                    adapter.clear();
                    movieArrayList = dbHelperMovie.getAllMovie();
                    adapter = new ArrayAdapter<Movie>(getBaseContext(), android.R.layout.simple_list_item_1, movieArrayList);
                    lv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    return true;
                }
                adapter.clear();
                movieArrayList = dbHelperMovie.getAllMovieByTitle(newText);
                adapter = new ArrayAdapter<Movie>(getBaseContext(), android.R.layout.simple_list_item_1, movieArrayList);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_share :


                break;

            case R.id.action_whatsapp:
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "HEY ! I have awesome app to show you!");
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MainActivity.this, "WhatsApp not installed", Toast.LENGTH_LONG).show();
                }


                break;



        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete").setMessage("Do you want to delete this movie ?");
        final Movie movie = movieArrayList.get(i);
        movieArrayList.remove(i);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {



                dbHelperMovie.DelMovie(movie.getId());

                adapter.notifyDataSetChanged();

            }

        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();

        return true;
    }
}

