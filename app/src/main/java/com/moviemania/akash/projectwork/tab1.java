package com.moviemania.akash.projectwork;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.Date;

import static com.moviemania.akash.projectwork.Keys.EndPointBoxOffice.*;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tab1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String URL_BOX_OFFICE="https://api.themoviedb.org/3/discover/movie";
    public static final String URL_BOX_OFFICE_HINDI = "https://api.themoviedb.org/3/discover/movie?primary_release_date.gte=2016-04-30&certification_country=IN&certification.lte=A&language=hi";
    private static final String STATE_MOVIES = "state_movies";
    private ProgressBar progressbarmain1;

    // TODO: Rename and change types of parameters

    private String mParam1;
    private String mParam2;
    int engpass,hindifail;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private TextView textvollyerror;
    private ArrayList<Movie> listMovies=new ArrayList<>();
    private DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
    private RecyclerView listMovieHits;
    private Adapter adapter;
    Button englishmovie,hindimovie;
    int i=0;
    private SwipeRefreshLayout refresh;




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

    public tab1() {
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




        sendJasonRequestforenglish();



    }

    private void sendJasonRequest()
    {

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, getRequestUrl(10), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                textvollyerror.setVisibility(View.GONE);
                progressbarmain1.setVisibility(View.GONE);
               listMovies=parseJSONResponse(jsonObject);
                adapter.setMovieList(listMovies);
                listMovieHits.setVisibility(View.VISIBLE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                hindifail=0;
                textvollyerror.setVisibility(View.VISIBLE);
                progressbarmain1.setVisibility(View.GONE);
                if(volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError)
                {

                    textvollyerror.setText("No Internet Connection");

                }

                else if(volleyError instanceof AuthFailureError)
                {
                    textvollyerror.setText("Authentication Failure");
                }

                else if(volleyError instanceof ServerError)
                {
                    textvollyerror.setText("Server Error");
                }

                else if(volleyError instanceof NetworkError)
                {
                    textvollyerror.setText("Network Error");

                }

                else if(volleyError instanceof ParseError)
                {
                    textvollyerror.setText("Parse Error");
                }


            }
        });
        requestQueue.add(request);
    }

    private void sendJasonRequestforenglish()
    {

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, getRequestUrlforenglish(10), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                textvollyerror.setVisibility(View.GONE);
                progressbarmain1.setVisibility(View.GONE);
                listMovies=parseJSONResponse(jsonObject);
                adapter.setMovieList(listMovies);
                listMovieHits.setVisibility(View.VISIBLE);
                engpass=0;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                progressbarmain1.setVisibility(View.GONE);
                textvollyerror.setVisibility(View.VISIBLE);
                if(volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError)
                {

                    textvollyerror.setText("No Internet Connection");

                }

                else if(volleyError instanceof AuthFailureError)
                {
                    textvollyerror.setText("Authentication Failure");
                }

                else if(volleyError instanceof ServerError)
                {
                    textvollyerror.setText("ServerError");
                }

                else if(volleyError instanceof NetworkError)
                {
                    textvollyerror.setText("NetworkError");

                }

                else if(volleyError instanceof ParseError)
                {
                    textvollyerror.setText("ParseError");
                }


            }
        });
        requestQueue.add(request);
    }







    private ArrayList<Movie> parseJSONResponse(JSONObject response) {
        ArrayList<Movie> listMovies = new ArrayList<>();

        if (response != null && response.length() != 0) {


            try {

                JSONArray arrayResults = response.getJSONArray(KEY_RESULTS);


                for (int i = 0; i < arrayResults.length(); i++) {

                    JSONObject currentMovie = arrayResults.getJSONObject(i);
                    Long idd = currentMovie.getLong(KEY_ID);
                  //  staticdata.id=idd;
                   // L.t(getActivity(),""+staticdata.id);
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
                    movie.setId(idd);
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
                   // L.t(getActivity(),""+thumbnail);
                    if (!releasedate.equals("NA")) {
                        listMovies.add(movie);
                    }


                }



            } catch (JSONException e) {


            }
        }
        return listMovies;
    }





    public static String getRequestUrl(int limit)
    {
        return "https://api.themoviedb.org/3/discover/movie?primary_release_date.gte=2016-04-30&certification_country=IN&certification.lte=A&language=hi?&api_key=ebbbc775ac71b9b9bf5363c1d4072724&page=2&limit=" + limit;

    }

    public static String getRequestUrlforenglish(int limit)
    {
        return "https://api.themoviedb.org/3/discover/movie?api_key=ebbbc775ac71b9b9bf5363c1d4072724&certification.Ite=A&page=2&limit=" + limit;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view= inflater.inflate(R.layout.tab1, container, false);

        textvollyerror= (TextView) view.findViewById(R.id.textVolleyError);
        progressbarmain1 = (ProgressBar) view.findViewById(R.id.progressBarmain1);
        listMovieHits= (RecyclerView) view.findViewById(R.id.listMovieHits);
        hindimovie= (Button) view.findViewById(R.id.hindimovie);
        englishmovie= (Button) view.findViewById(R.id.englishmovie);
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new Adapter(getActivity());
        listMovieHits.setAdapter(adapter);
        refresh= (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(this);


        sendJasonRequestforenglish();

        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            englishmovie.setBackgroundDrawable( getResources().getDrawable(R.drawable.button));
            hindimovie.setBackgroundDrawable(getResources().getDrawable(R.drawable.button2));
        } else {
            hindimovie.setBackground( getResources().getDrawable(R.drawable.button2));
            englishmovie.setBackground(getResources().getDrawable(R.drawable.button));
        }



        hindimovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressbarmain1.setVisibility(View.VISIBLE);
                sendJasonRequest();

              /*  englishmovie.setBackgroundColor(Color.WHITE);
                hindimovie.setBackgroundColor(getResources().getColor(R.color.DodgerBlue));*/

                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    englishmovie.setBackgroundDrawable( getResources().getDrawable(R.drawable.button3));
                    hindimovie.setBackgroundDrawable(getResources().getDrawable(R.drawable.button4));
                } else {
                    hindimovie.setBackground( getResources().getDrawable(R.drawable.button4));
                    englishmovie.setBackground( getResources().getDrawable(R.drawable.button3));
                }
                hindimovie.setTextColor(getActivity().getResources().getColor(R.color.white));
                englishmovie.setTextColor(getActivity().getResources().getColor(R.color.DodgerBlue));
                hindimovie.setClickable(false);
                englishmovie.setClickable(true);
                i++;

                if(engpass==0 && hindifail==0)
                {
                    listMovieHits.setVisibility(View.GONE);
                    textvollyerror.setVisibility(View.VISIBLE);

                }

            }
        });

        englishmovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                englishmovie.setClickable(false);
                hindimovie.setClickable(true);
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    englishmovie.setBackgroundDrawable( getResources().getDrawable(R.drawable.button));
                    hindimovie.setBackgroundDrawable(getResources().getDrawable(R.drawable.button2));
                } else {
                    hindimovie.setBackground( getResources().getDrawable(R.drawable.button2));
                    englishmovie.setBackground( getResources().getDrawable(R.drawable.button));
                }

                i++;
                sendJasonRequestforenglish();
                if (i == 0) {
                } else {
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        englishmovie.setBackgroundDrawable( getResources().getDrawable(R.drawable.button));
                        hindimovie.setBackgroundDrawable(getResources().getDrawable(R.drawable.button2));
                    } else {
                        hindimovie.setBackground( getResources().getDrawable(R.drawable.button2));
                        englishmovie.setBackground( getResources().getDrawable(R.drawable.button));
                    }
                  //  englishmovie.setBackgroundColor(getResources().getColor(R.color.DodgerBlue));
                    englishmovie.setTextColor(getActivity().getResources().getColor(R.color.white));
                   // hindimovie.setBackgroundColor(Color.WHITE);
                    hindimovie.setTextColor(getActivity().getResources().getColor(R.color.DodgerBlue));



                }

            }
        });

        return view;
    }


    @Override
    public void onRefresh() {
        L.t(getActivity(),"Refreshing...");

        android.os.Handler handler=new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(i%2==0) {
                    sendJasonRequestforenglish();
                }
                else
                sendJasonRequest();

                if(refresh.isRefreshing())
                {

                    refresh.setRefreshing(false);
                }

            }
        },3200);

    }


}
