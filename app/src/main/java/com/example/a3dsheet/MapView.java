package com.example.a3dsheet;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;


public class MapView extends ConstraintLayout{
    float xPercentage;
    float yPercentage;
    View map_cursor;


    public MapView(@NonNull Context context) {
        super(context);
        inflate(getContext(), R.layout.map_view, this);

    }

    public MapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.map_view, this);

    }

    public MapView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.map_view, this);

    }
}
