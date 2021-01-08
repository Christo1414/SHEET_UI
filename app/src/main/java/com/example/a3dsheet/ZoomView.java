package com.example.a3dsheet;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ZoomView extends ConstraintLayout {
    public ZoomView(@NonNull Context context) {
        super(context);
        inflate(getContext(), R.layout.zoom_view, this);

    }

    public ZoomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.zoom_view, this);

    }

    public ZoomView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.zoom_view, this);

    }
}
