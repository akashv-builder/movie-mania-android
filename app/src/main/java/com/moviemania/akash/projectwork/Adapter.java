package com.moviemania.akash.projectwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static com.moviemania.akash.projectwork.Keys.EndPointBoxOffice.KEY_RESULTS;
import static com.moviemania.akash.projectwork.Keys.EndPointBoxOffice.KEY_You_Tube;

/**
 * Created by Akash on 14-Aug-16.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<Movie> listMovie = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton, volleySingleton1;
    private ImageLoader imageLoader, imageLoader1;
    private DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
    private int previousPosition = 0;
    Context context;
    private ProgressDialog progress;
    private RequestQueue requestQueue;
    private ArrayList<Movie> listMovies=new ArrayList<>();
    Adapter adapter;


    public Adapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        volleySingleton1 = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
        imageLoader1 = volleySingleton1.getImageLoader();
        requestQueue=volleySingleton.getRequestQueue();

    }


    public void setMovieList(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
        notifyItemRangeChanged(0, listMovie.size() - 1);
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.item_car_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Movie currentMovie = listMovie.get(position);
        holder.movieTitle.setText(currentMovie.getTitle());

        Date movieReleaseDate = currentMovie.getReleaseDateTheater();
        if (movieReleaseDate != null) {
            holder.movieReleaseDate.setText(dateFormatter.format(movieReleaseDate));
        } else {
            holder.movieReleaseDate.setText("NA");
        }
        int i;
        i = currentMovie.getAudienceScore();
        if (i == 0) {
            holder.movieAudienceScore.setRating(3);
        } else if (i == 1) {
            holder.movieAudienceScore.setRating(2);
        } else {
            holder.movieAudienceScore.setRating(currentMovie.getAudienceScore() - 2);
        }


        String urlThumbnail = currentMovie.getUrlThumnail();

        if (urlThumbnail != null) {


            Picasso.with(context)
                    .load(urlThumbnail)
                    .fit().centerCrop()
                    .noFade()
                    .into(holder.movieThumbnail, new Callback() {
                        @Override

                        public void onSuccess() {

                            holder.pb1.setVisibility(View.GONE);
                           // holder.movieThumbnail.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onError() {
                            holder.pb1.setVisibility(View.GONE);
                            //holder.movieThumbnail.setVisibility(View.VISIBLE);
                            Picasso.with(context)
                                    .load(R.drawable.nt)
                                    .error(R.drawable.nt)
                                    .fit().centerCrop()
                                    .noFade()
                                    .into(holder.movieThumbnail);
                        }
                    });


        } else {
            Picasso.with(context)
                    .load(R.drawable.nt)
                    .error(R.drawable.nt)
                    .fit().centerCrop()
                    .noFade()
                    .into(holder.movieThumbnail);


        }


        /*if (urlThumbnail != null) {


            Picasso.with(context)
                    .load(urlThumbnail)
                    .error(R.drawable.nt)
                    .fit().centerCrop()
                    .noFade()
                    .into(holder.movieThumbnail);
            staticdata.j=0;


        }
        else
        {
            Picasso.with(context)
                    .load(R.drawable.nt)
                    .error(R.drawable.nt)
                    .fit().centerCrop()
                    .noFade()
                    .into(holder.movieThumbnail);

            //   holder.movieThumbnail.setImageResource(R.drawable.nt);

        }


        android.os.Handler handler=new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (staticdata.j == 0) {
                    holder.movieThumbnail.setVisibility(View.VISIBLE);
                    holder.pb1.setVisibility(View.GONE);
                } else {
                    holder.pb1.setVisibility(View.GONE);
                    holder.movieThumbnail.setVisibility(View.VISIBLE);

                }


            }
        }, 1000);*/




        int[] allColors = context.getResources().getIntArray(R.array.colors);
        /*array[0] = context.getResources().getColor(R.color.LightSkyBlue);
        array[1] = context.getResources().getColor(R.color.Plum);
        array[2] = context.getResources().getColor(R.color.LightGrey);
        array[3] = context.getResources().getColor(R.color.LightCoral);*/
        int randomColor = allColors[new Random().nextInt(allColors.length)];
        holder.cardView.setCardBackgroundColor(randomColor);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                staticdata.key="";
                Handler handler = new Handler();
                Adapter.this.progress = new ProgressDialog(Adapter.this.context);
                Adapter.this.progress.setMessage("Loading....");
                Adapter.this.progress.setProgressStyle(0);
                Adapter.this.progress.setIndeterminate(true);
                Adapter.this.progress.setProgress(0);
                Adapter.this.progress.setCancelable(false);
                Adapter.this.progress.show();

                final Movie specificMovie = listMovie.get(position);
                staticdata.moviename = specificMovie.getTitle();
                staticdata.b = specificMovie.getUrlThumnail1();
                staticdata.id = specificMovie.getId();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Adapter.this.progress.hide();

                        sendJasonRequestforvideo();

                        Date movieReleaseDate = specificMovie.getReleaseDateTheater();
                        if (movieReleaseDate != null) {
                            staticdata.releasedate = Adapter.this.dateFormatter.format(movieReleaseDate);
                        } else {
                            staticdata.releasedate = "NA";
                        }
                        staticdata.audiencescore = specificMovie.getAudienceScore();
                        if (specificMovie.getOverview().equals(BuildConfig.FLAVOR)) {
                            staticdata.description = "No Overview Avialable";
                        } else {
                            staticdata.description = specificMovie.getOverview();
                        }


                        context.startActivity(new Intent(context, click_on_list.class));
                    }
                }, 3000);

            }

        });




        if (position > previousPosition) {
            AnimationUtils.animate(holder, true);

        } else {

            AnimationUtils.animate(holder, false);
        }

        previousPosition = position;
    }



    private void sendJasonRequestforvideo() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRequestUrlforvideo(10), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                listMovies = parseJSONResponse2(jsonObject);
               // adapter.setMovieList(listMovies);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {


                if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {

                    //   textvollyerror.setText("No Internet Connection");

                } else if (volleyError instanceof AuthFailureError) {
                    // textvollyerror.setText("Authentication Failure");
                } else if (volleyError instanceof ServerError) {
                    //  textvollyerror.setText("ServerError");
                } else if (volleyError instanceof NetworkError) {
                    //textvollyerror.setText("NetworkError");

                } else if (volleyError instanceof ParseError) {
                    // textvollyerror.setText("ParseError");
                }


            }
        });
        requestQueue.add(request);
    }



    private ArrayList<Movie> parseJSONResponse2(JSONObject response) {
        ArrayList<Movie> listMovies = new ArrayList<>();

        if (response != null && response.length() != 0) {


            try {

                JSONArray arrayResults = response.getJSONArray(KEY_RESULTS);
                if (arrayResults == null || arrayResults.length() == 0) {
                    staticdata.key="";

                }



                for (int i = 0; i < arrayResults.length(); i++) {

                    JSONObject currentMovie = arrayResults.getJSONObject(i);

                    String key = currentMovie.getString(KEY_You_Tube);


                    Movie movie = new Movie();
                    movie.setkey(key);
                    staticdata.key=key;
                   // L.t(context,key);



                }


            } catch (JSONException e) {


            }
        }
        return listMovies;
    }

    public String getRequestUrlforvideo(int limit) {
        return "http://api.themoviedb.org/3/movie/" + staticdata.id + "/videos?api_key=ebbbc775ac71b9b9bf5363c1d4072724";

    }




    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView movieThumbnail;
        private TextView movieTitle;
        private TextView movieReleaseDate;
        private RatingBar movieAudienceScore;
        private ProgressBar pb1;
        CardView cardView;

        public ViewHolder(View itemView) {

            super(itemView);
            movieThumbnail = (ImageView) itemView.findViewById(R.id.movieTumbnail);
            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);
            movieAudienceScore = (RatingBar) itemView.findViewById(R.id.movieAudienceScore);
            pb1 = (ProgressBar) itemView.findViewById(R.id.pb1);
            cardView = (CardView) itemView.findViewById(R.id.card);


        }
    }

}
