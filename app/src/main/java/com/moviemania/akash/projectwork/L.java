package com.moviemania.akash.projectwork;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Akash on 13-Aug-16.
 */
public class L {

public static void m(String message)

{

    Log.d("",""+message);
}
    public static void t(Context context,String message)
    {

        Toast.makeText(context,message+"",Toast.LENGTH_LONG).show();
    }

}
