package com.moviemania.akash.projectwork;

import android.support.v7.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


public class AnimationUtils {


    public static void animate(RecyclerView.ViewHolder holder, boolean goesdown) {


        YoYo.with(Techniques.SlideInDown).duration(1000).playOn(holder.itemView);


    }
}
