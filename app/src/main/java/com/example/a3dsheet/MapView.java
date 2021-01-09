package com.example.a3dsheet;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;


public class MapView extends ConstraintLayout{
     static RelativeLayout map_layout;
     static View map_cursor;
     static TextView warning_text;

     static float cursor_x_range;
     static float cursor_y_range;
     static float map_x_percentage = (float) 0.5;
     static float map_y_percentage = (float) 0.5;


    public MapView(@NonNull Context context) {
        super(context);
        initialize();
    }

    public MapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public MapView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        inflate(getContext(), R.layout.map_view, this);
        map_layout = (RelativeLayout) findViewById(R.id.Map_Layout);
        map_cursor = (View) findViewById(R.id.Map_Cursor_View);
        warning_text = (TextView) findViewById(R.id.Warnings_text);


        cursor_x_range = pxFromDp(this, 80);
        cursor_y_range = pxFromDp(this, 60);

        map_cursor.setX((cursor_x_range)*map_x_percentage);
        map_cursor.setY((cursor_y_range)*map_y_percentage);

        warning_text.setVisibility(View.GONE);
    }

    public static void Update_Map(float X_VECTOR, float Y_VECTOR){
        // Call after sending vectors to Pi

        map_layout.setVisibility(View.VISIBLE); // hide warning
        warning_text.setVisibility(View.GONE);

        map_x_percentage = map_x_percentage + (X_VECTOR)/1000;  // these need to scale same amount as Pi
        map_y_percentage = map_y_percentage + (Y_VECTOR)/1000;  //

        map_x_percentage = Check_Map_Boundary(map_x_percentage);
        map_y_percentage = Check_Map_Boundary(map_y_percentage);

        map_cursor.setX((cursor_x_range)*map_x_percentage);
        map_cursor.setY((cursor_y_range)*map_y_percentage);
    }

    private static float Check_Map_Boundary(float percentage){
        // for Update_Map
        if (percentage >= 1){
            warning_text.setVisibility(View.VISIBLE); //show warning
            percentage =  1;
        }
        else if (percentage <= 0){
            warning_text.setVisibility(View.VISIBLE); //show warning
            percentage =  0;
        }
        return percentage;
    }

    public static float pxFromDp(final MapView context, final float dp) {
        // To find boundary conditions
        return dp * context.getResources().getDisplayMetrics().density;
    }

}
