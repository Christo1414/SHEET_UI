package com.example.a3dsheet;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class InfoView extends ConstraintLayout {

    static private TextView connection_status;
    static private String ip_address;




    public InfoView(@NonNull Context context) {
        super(context);
        initialize();
    }

    public InfoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public InfoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize(){
        inflate(getContext(), R.layout.info_view, this);

        connection_status = (TextView) findViewById(R.id.IP_Status);
        connection_status.setText("Connecting to camera");

    }

    public static void Update_IP(String IP){
        ip_address = IP;
        connection_status.setText("Connected to " + ip_address);
    }

}
