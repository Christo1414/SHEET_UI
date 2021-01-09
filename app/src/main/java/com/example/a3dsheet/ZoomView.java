package com.example.a3dsheet;

import android.content.Context;
import android.graphics.Camera;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ZoomView extends ConstraintLayout {


    static private RelativeLayout zoombar_layout;
    static private View zoombar_fill;

    static private float zoombar_range;
    static float zoom_percentage = (float) 0.5;


    public ZoomView(@NonNull Context context) {
        super(context);
        initialize();
    }

    public ZoomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ZoomView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        inflate(getContext(), R.layout.zoom_view, this);

        zoombar_layout = (RelativeLayout) findViewById(R.id.ZoomBarLayout);
        zoombar_fill = (View) findViewById(R.id.ZoomBarView);
        zoombar_range = pxFromDp(this, 500);
        zoombar_fill.setY((zoombar_range)*zoom_percentage);

    }

    public static void Update_Zoombar(float percentage){
        // Call when zooming

        zoombar_layout.setVisibility(View.VISIBLE);

        /** Update ZOOM percentage HERE with global variable or parameter */
        zoom_percentage = percentage;

        zoombar_fill.setY((zoombar_range)*(1-zoom_percentage));

    }

    public static float pxFromDp(final ZoomView context, final float dp) {
        // To find boundary conditions
        return dp * context.getResources().getDisplayMetrics().density;
    }


}
