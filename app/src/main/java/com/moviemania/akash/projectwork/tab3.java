package com.moviemania.akash.projectwork;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.moviemania.akash.projectwork.Keys.EndPointBoxOffice.*;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tab3 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String STATE_MOVIES = "state_movies";

    // TODO: Rename and change types of parameters

    private String mParam1;
    private String mParam2;
    private VolleySingleton volleySingleton;
    private ProgressBar progressbarmain2;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private TextView textvollyerror2;
    private ArrayList<Movie> listMovies=new ArrayList<>();
    private DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
    private RecyclerView listMovieHits2;
    private Adapter adapter;

    private SwipeRefreshLayout refresh1;
    int month;
    public String currentdate;
    int year,day;
    public String dateafteramonth;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static tab1 newInstance(String param1, String param2) {
        tab1 fragment = new tab1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public tab3() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        volleySingleton=VolleySingleton.getInstance();
        requestQueue=volleySingleton.getRequestQueue();


        sendJasonRequest();


    }

    private void sendJasonRequest()
    {

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, getRequestUrl(10), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                textvollyerror2.setVisibility(View.GONE);
                progressbarmain2.setVisibility(View.GONE);
                listMovies=parseJSONResponse(jsonObject);
                adapter.setMovieList(listMovies);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                progressbarmain2.setVisibility(View.GONE);
                textvollyerror2.setVisibility(View.VISIBLE);
                if(volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError)
                {

                    textvollyerror2.setText("No Internet Connection");

                }

                else if(volleyError instanceof AuthFailureError)
                {
                    textvollyerror2.setText("Authentication Failure");
                }

                else if(volleyError instanceof ServerError)
                {
                    textvollyerror2.setText("Server Error");
                }

                else if(volleyError instanceof NetworkError)
                {
                    textvollyerror2.setText("Network Error");

                }

                else if(volleyError instanceof ParseError)
                {
                    textvollyerror2.setText("Parse Error");
                }


            }
        });
        requestQueue.add(request);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view= inflater.inflate(R.layout.tab3, container, false);

        textvollyerror2= (TextView) view.findViewById(R.id.textVolleyError2);
        listMovieHits2= (RecyclerView) view.findViewById(R.id.listMovieHits2);

        listMovieHits2.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new Adapter(getActivity());
        listMovieHits2.setAdapter(adapter);
        progressbarmain2 = (ProgressBar) view.findViewById(R.id.progressBarmain2);
        refresh1= (SwipeRefreshLayout) view.findViewById(R.id.refresh1);
        refresh1.setOnRefreshListener(this);

        sendJasonRequest();





        return view;
    }




    private ArrayList<Movie> parseJSONResponse(JSONObject response) {
        ArrayList<Movie> listMovies = new ArrayList<>();

        if (response != null && response.length() != 0) {


            try {

                JSONArray arrayResults = response.getJSONArray(KEY_RESULTS);


                for (int i = 0; i < arrayResults.length(); i++) {

                    JSONObject currentMovie = arrayResults.getJSONObject(i);
                    Long id = currentMovie.getLong(KEY_ID);
                    String title = currentMovie.getString(KEY_TITLE);
                    String releasedate = currentMovie.getString(KEY_RELEASE_DATES);
                    int voteaverage = currentMovie.getInt(KEY_VOTE_AVERAGE);
                    String overview = currentMovie.getString(KEY_OVERVIEW);
                    String s = "http://image.tmdb.org/t/p/w500";
                    String thumbnaildemo = currentMovie.getString(KEY_TUMBNAIL);
                    String thumbnail = s+thumbnaildemo;

                    String thumbnaildemo1 = currentMovie.getString(KEY_TUMBNAIL1);
                    String thumbnail1 = s+thumbnaildemo1;



                    Movie movie = new Movie();
                    movie.setId(id);
                    movie.setTitle(title);


                    Date date = null;

                    try {
                        date = this.dateFormat.parse(releasedate);
                    } catch (ParseException e) {
                    }
                    movie.setReleaseDateTheater(date);
                    movie.setAudienceScore(voteaverage);
                    movie.setOverview(overview);
                    movie.setUrlThumnail(thumbnail);
                    movie.setUrlThumnail1(thumbnail1);
                    if (!releasedate.equals("NA")) {
                        listMovies.add(movie);
                    }


                }



            } catch (JSONException e) {


            }
        }
        return listMovies;
    }





    public String getRequestUrl(int limit)
    { Calendar c = Calendar.getInstance();
        month = c.get(Calendar.MONTH);
        year= c.get(Calendar.YEAR);
        day=c.get(Calendar.DAY_OF_MONTH);



        if(month==11)
        {
            currentdate = year + "-" + "12" + "-" + "1";
            dateafteramonth = year+1 + "-" + "1" + "-" + "1";
            staticdata.dateafteramonth = dateafteramonth;



        }

       else if(month==0)
        {  currentdate = year + "-" + "1" + "-" + "1";
            dateafteramonth = year + "-" + "2" + "-" + "1";
            staticdata.dateafteramonth = dateafteramonth;

        }
        else  {
            currentdate = year + "-" + (month+1) + "-" + 1;
            dateafteramonth = year + "-" + (month + 2) + "-" +"1";
            staticdata.dateafteramonth = dateafteramonth;

        }

        return "https://api.themoviedb.org/3/discover/movie?primary_release_date.gte=" +currentdate + "&primary_release_date.lte=" +dateafteramonth + "h&certification_country=IN&certification.lte=A&language=hi" + "?&api_key=" + MyApplication.API_KEY + "&limit=" + limit;

    }




    @Override
    public void onRefresh() {
        L.t(getActivity(),"Refreshing...");

        android.os.Handler handler=new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                sendJasonRequest();

                if(refresh1.isRefreshing())
                {

                    refresh1.setRefreshing(false);
                }

            }
        },3200);

    }


}
