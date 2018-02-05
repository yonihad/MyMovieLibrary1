package movie.yoni.mymovielibrary.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import movie.yoni.mymovielibrary.MainActivity;
import movie.yoni.mymovielibrary.Movie;
import movie.yoni.mymovielibrary.R;
import movie.yoni.mymovielibrary.objects.DBConstants;

public class SearchInternetActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ET_search;
    private Button btn_search;
    private ListView listView;
    private Button btn_cancel;
    private ArrayList<Movie> moviesList = new ArrayList<>();
    private ArrayAdapter<Movie> adapter;
    //private ArrayList <String> titles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_internet);

        btn_search = (Button)findViewById(R.id.btn_search);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        listView = (ListView) findViewById(R.id.listView);
        ET_search = (EditText)findViewById(R.id.ET_search);

        //titles = new ArrayList<String>();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1 , moviesList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Movie movie = moviesList.get(i);

                Intent intent = new Intent(SearchInternetActivity.this , ScreenWeb.class);
                intent.putExtra("Title" , movie.getTitle());
                intent.putExtra("Overview" , movie.getOverview());
                intent.putExtra("Url" , movie.getUrl());
                startActivity(intent);
            }
        });

        btn_cancel.setOnClickListener(this);
        btn_search.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_search :

                if (ET_search.getText().toString()!= ""&&ET_search.getText().toString()!=null){
                    String address = DBConstants.API_MOVIE + ET_search.getText().toString();
                    MyTask myTask = new MyTask();
                    myTask.execute(address);
                } else {
                    Toast.makeText(this,"You did not enter movie name",Toast.LENGTH_LONG).show();
                }



                break;

            case R.id.btn_cancel :
                Intent intent = new Intent(SearchInternetActivity.this, MainActivity.class);
                startActivity(intent);



                break;
        }

    }

    public class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {

        }



        @Override
        protected String doInBackground(String... strings) {
            URL url;
            BufferedReader bufferedReader=null;
            HttpURLConnection connection = null;
            StringBuilder builder = new StringBuilder();

            try {
                url =new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();

                if (connection.getResponseCode()==HttpURLConnection.HTTP_OK)
                {

                    bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line="";
                    while ((line=bufferedReader.readLine())!=null)
                    {
                        builder.append(line +"\n");
                    }

                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (bufferedReader != null){
                        bufferedReader.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (connection != null){
                    connection.disconnect();
                }
            }


            return builder.toString();
        }

        @Override
        protected void onPostExecute(String res) {
            if (res.length() == 0 || res == null){
                //no answer
            }else
            {
                //we have answer
                //parse json
                try {
                    JSONObject  object = new JSONObject(res);
                    JSONArray array = object.getJSONArray("results");
                    //ArrayList <Movie> movies = new ArrayList<>();
                    moviesList.clear();
                    for (int i = 0; i<array.length();i++){

                        object = array.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setId(object.getInt("id"));
                        movie.setOverview(object.getString("overview"));
                        movie.setTitle(object.getString("title"));
                        movie.setUrl(DBConstants.API_PICTURE+""+object.getString("poster_path"));
                        moviesList.add(movie);


                    }
                    //fillData(movies);
                    adapter.notifyDataSetChanged();



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
