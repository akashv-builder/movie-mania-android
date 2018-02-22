package com.moviemania.akash.projectwork;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
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

import static com.moviemania.akash.projectwork.Keys.EndPointBoxOffice.KEY_ID;
import static com.moviemania.akash.projectwork.Keys.EndPointBoxOffice.KEY_OVERVIEW;
import static com.moviemania.akash.projectwork.Keys.EndPointBoxOffice.KEY_RELEASE_DATES;
import static com.moviemania.akash.projectwork.Keys.EndPointBoxOffice.KEY_RESULTS;
import static com.moviemania.akash.projectwork.Keys.EndPointBoxOffice.KEY_TITLE;
import static com.moviemania.akash.projectwork.Keys.EndPointBoxOffice.KEY_TUMBNAIL;
import static com.moviemania.akash.projectwork.Keys.EndPointBoxOffice.KEY_TUMBNAIL1;
import static com.moviemania.akash.projectwork.Keys.EndPointBoxOffice.KEY_VOTE_AVERAGE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link tab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tab2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProgressBar progressBar;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    SearchView searchView1;
    private RequestQueue requestQueue;
    public static final String URL_BOX_OFFICE_SEARCH = "https://api.themoviedb.org/3/search/movie";
    private EditText moviesearch;
    private String moviename;
    private ArrayList<Movie> listMoviesforsearch = new ArrayList<>();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private RecyclerView listMovieHitsforsearch;
    private Adapter adapter;
    private Button go;
    LinearLayout fragment2;
    private TextView volleyerrorforsearch;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tab2.
     */
    // TODO: Rename and change types and number of parameters
    public static tab2 newInstance(String param1, String param2) {
        tab2 fragment = new tab2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public String getRequestUrlforserch(int limit) {
        return "https://api.themoviedb.org/3/search/movie?api_key=ebbbc775ac71b9b9bf5363c1d4072724&query=" + moviename + "&limit=" + limit;

    }

    public tab2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

    }

    private void sendJasonRequestforsearch() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRequestUrlforserch(10), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                volleyerrorforsearch.setVisibility(View.GONE);
                listMovieHitsforsearch.setVisibility(View.VISIBLE);
                listMoviesforsearch = parseJSONResponseforsearch(response);
                adapter.setMovieList(listMoviesforsearch);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                progressBar.setVisibility(View.GONE);
                volleyerrorforsearch.setVisibility(View.VISIBLE);
                if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {

                    volleyerrorforsearch.setText("No Internet Connection");

                } else if (volleyError instanceof AuthFailureError) {
                    volleyerrorforsearch.setText("Authentication Failure");
                } else if (volleyError instanceof ServerError) {
                    volleyerrorforsearch.setText("No Result Found");
                } else if (volleyError instanceof NetworkError) {
                    volleyerrorforsearch.setText("Network Error");

                } else if (volleyError instanceof ParseError) {
                    volleyerrorforsearch.setText("Parse Error");
                }


            }
        });
        requestQueue.add(request);
    }


    private ArrayList<Movie> parseJSONResponseforsearch(JSONObject response) {
        ArrayList<Movie> listMovies = new ArrayList<>();

        if (response != null && response.length() != 0) {


            try {

                JSONArray arrayResults = response.getJSONArray(KEY_RESULTS);

                if (arrayResults == null || arrayResults.length() == 0) {
                    volleyerrorforsearch.setText("No results found");
                    volleyerrorforsearch.setVisibility(View.VISIBLE);

                }


                for (int i = 0; i < arrayResults.length(); i++) {

                    JSONObject currentMovie = arrayResults.getJSONObject(i);
                    Long id = currentMovie.getLong(KEY_ID);
                    String title = currentMovie.getString(KEY_TITLE);
                    String releasedate = currentMovie.getString(KEY_RELEASE_DATES);
                    int voteaverage = currentMovie.getInt(KEY_VOTE_AVERAGE);
                    String overview = currentMovie.getString(KEY_OVERVIEW);
                    String s = "http://image.tmdb.org/t/p/w500";
                    String thumbnaildemo = currentMovie.getString(KEY_TUMBNAIL);
                    String thumbnail = s + thumbnaildemo;

                    String thumbnaildemo1 = currentMovie.getString(KEY_TUMBNAIL1);
                    String thumbnail1 = s + thumbnaildemo1;


                    Movie movie = new Movie();
                    movie.setId(id);
                    movie.setTitle(title);


                    Date date = null;
                    try {
                        date = dateFormat.parse(releasedate);

                    } catch (ParseException e) {
                    }
                    movie.setReleaseDateTheater(date);
                    movie.setAudienceScore(voteaverage);
                    movie.setOverview(overview);
                    movie.setUrlThumnail(thumbnail);
                    movie.setUrlThumnail1(thumbnail1);


                    listMovies.add(movie);


                }


            } catch (JSONException e) {


            }
        }
        return listMovies;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab2, container, false);

        listMovieHitsforsearch = (RecyclerView) view.findViewById(R.id.listMovieHitssearch);
        listMovieHitsforsearch.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new Adapter(getActivity());
        progressBar = (ProgressBar) view.findViewById(R.id.progressbarforsearch);
        listMovieHitsforsearch.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
        fragment2 = (LinearLayout) view.findViewById(R.id.fragment2);


        searchView1 = (SearchView) view.findViewById(R.id.searchView1);
        searchView1.setQueryHint("Enter A Movie To Search...");
        int searchIconId = searchView1.getContext().getResources().getIdentifier("android:id/search_mag_icon",null, null);
        ImageView searchIcon = (ImageView) searchView1.findViewById(searchIconId);
        searchIcon.setImageResource(R.drawable.search);
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk <= Build.VERSION_CODES.LOLLIPOP) {
            searchView1.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.hintSearchMess) + "</font>"));

        } else {

            searchView1.setQueryHint(Html.fromHtml("<font color = #D3D3D3>" + getResources().getString(R.string.hintSearchMess) + "</font>"));

        }

        searchView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView1.setIconified(false);
            }
        });

        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                String s, s2;
                s = query;
                s2 = s.replace(' ', '+');
                moviename = s2;
                volleyerrorforsearch.setVisibility(View.GONE);

                try {
                    ((InputMethodManager) tab2.this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(tab2.this.getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                }
                if (s.equals("")) {
                    try {
                        ((InputMethodManager) tab2.this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(tab2.this.getActivity().getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                    }
                    Snackbar snackbar = Snackbar.make(fragment2, "Enter A Movie To Search", Snackbar.LENGTH_SHORT);
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                    snackbar.show();
                    moviesearch.requestFocus();
                    volleyerrorforsearch.setVisibility(View.GONE);
                } else {

                    try {
                        progressBar.setVisibility(View.VISIBLE);
                        volleyerrorforsearch.setVisibility(View.GONE);
                        sendJasonRequestforsearch();
                        listMovieHitsforsearch.setVisibility(View.GONE);
                    } catch (Exception e) {
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(getActivity(), newText, Toast.LENGTH_LONG).show();
                return false;
            }
        });
        volleyerrorforsearch = (TextView) view.findViewById(R.id.textVolleyErrorsearch);


        return view;

    }


}
