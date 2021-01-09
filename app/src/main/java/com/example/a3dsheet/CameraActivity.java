package com.example.a3dsheet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class CameraActivity extends AppCompatActivity {

    // Views and Layouts
    private TextView connection_status;

        /** snip this */
    private TextView warning_text;
    private RelativeLayout map_layout;

    private RelativeLayout zoombar_layout;


    private View TranslationView;
        /** snip this */

    // Variables
    private String ip_address;





    // Variable for testing ( not needed )
    float x_0 = 0;          // initial values for x and y
    float y_0 = 0;
    float x = 0;            // Coordinates for touch listener
    float y = 0;
    float zoom_var = (float) 0.5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);

        
            /** snip this */
        Initialize_Views();

            /** snip this */
        Translation_Touch_Listener();

    }

    private void Initialize_Views(){           /** need this */


        warning_text = (TextView) findViewById(R.id.Warnings_text);

        map_layout = (RelativeLayout) findViewById(R.id.Map_Layout);
        zoombar_layout = (RelativeLayout) findViewById(R.id.ZoomBarLayout);

        TranslationView = (View) findViewById(R.id.TranslationView);


        // Set content of IP_Status (MAY NOT BE NEEDED)
        connection_status = (TextView) findViewById(R.id.IP_Status);
        ip_address = getIntent().getStringExtra("EXTRA_IP_ADDRESS");
        connection_status.setText("Connecting to " + ip_address);

    }




    public void Test_Zoombar(View view){

        zoom_var = zoom_var + (float) 0.1;
        if (zoom_var > 1.1) {zoom_var = 0;}


        /** snip */
        ZoomView.Update_Zoombar(zoom_var);
        /** snip */
    }



    public void Show_UI(View view){
        // show all UI elements
        map_layout.setVisibility(View.VISIBLE);
        warning_text.setVisibility(View.VISIBLE);
        zoombar_layout.setVisibility(View.VISIBLE);
    }

    public void Hide_UI(View view){
        // hide all UI elements
        map_layout.setVisibility(View.GONE);
        warning_text.setVisibility(View.GONE);
        zoombar_layout.setVisibility(View.GONE);
    }


    private void Translation_Touch_Listener(){
        TranslationView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View V, MotionEvent event) {
                x = event.getX();
                y = event.getY();
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    x_0 = x;
                    y_0 = y;
                }


                /** snip */
                MapView.Update_Map(x-x_0,y-y_0);
                /** snip */


                x_0 = x;
                y_0 = y;
                return true;
            }
        });
    }


    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {           /** need this */
        // To find boundary conditions
        return dp * context.getResources().getDisplayMetrics().density;
    }

}





