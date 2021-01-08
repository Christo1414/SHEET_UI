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
    private View map_cursor;
    private RelativeLayout zoombar_layout;
    private View zoombar_fill;

    private View TranslationView;
        /** snip this */

    // Variables
    private String ip_address;

        /** snip this */
    private float cursor_x_range;
    private float cursor_y_range;
    private float zoombar_range;
    float map_x_percentage = (float) 0.5;
    float map_y_percentage = (float) 0.5;
    float zoom_percentage = (float) 0;
        /** snip this */



    // Variable for touch listener ( not needed )
    float x_0 = 0;          // initial values for x and y
    float y_0 = 0;
    float x = 0;            // Coordinates for touch listener
    float y = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);

            /** snip this */
        Initialize_Views();
        Initialize_UI();
            /** snip this */

        Translation_Touch_Listener();
        Update_Zoombar();
    }

    private void Initialize_Views(){           /** need this */


        warning_text = (TextView) findViewById(R.id.Warnings_text);
        map_layout = (RelativeLayout) findViewById(R.id.Map_Layout);
        map_cursor = (View) findViewById(R.id.Map_Cursor_View);

        zoombar_layout = (RelativeLayout) findViewById(R.id.ZoomBarLayout);
        zoombar_fill = (View) findViewById(R.id.ZoomBarView);

        TranslationView = (View) findViewById(R.id.TranslationView);


        // Set content of IP_Status (MAY NOT BE NEEDED)
        connection_status = (TextView) findViewById(R.id.IP_Status);
        ip_address = getIntent().getStringExtra("EXTRA_IP_ADDRESS");
        connection_status.setText("Connecting to " + ip_address);

    }

    private void Initialize_UI() {          /** need this */
     // Map and Zoom UI have static dimensions

        // Boundary Conditions
        cursor_x_range = pxFromDp(this, 80);
        cursor_y_range = pxFromDp(this, 60);

        map_cursor.setX((cursor_x_range)*map_x_percentage);
        map_cursor.setY((cursor_y_range)*map_y_percentage);


        // Initialize zoombar
        zoombar_range = pxFromDp(this, 500);
        zoombar_fill.setY((zoombar_range));


        // Hiding warning textview
        warning_text.setVisibility(View.GONE);
    }

    public void Update_Zoombar(){           /** need this */
        // Call when zooming
        zoombar_layout.setVisibility(View.VISIBLE);

        /** Update ZOOM percentage HERE with global variable or parameter */
        zoom_percentage = (float) 0.5;

        zoombar_fill.setY((zoombar_range)*(1-zoom_percentage));

    }

    private void Update_Map(){              /** need this */
        // Call when translating
        map_layout.setVisibility(View.VISIBLE);
        warning_text.setVisibility(View.GONE); //hide warning

        /** Update MAP percentage HERE with global variable or parameter */
        map_x_percentage = Check_Map_Boundary(map_x_percentage);
        map_y_percentage = Check_Map_Boundary(map_y_percentage);

        map_cursor.setX((cursor_x_range)*map_x_percentage);
        map_cursor.setY((cursor_y_range)*map_y_percentage);


    }

    private float Check_Map_Boundary(float percentage){             /** need this */
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
        /** NOT FOR FINAL PROJECT. Instead just need to update map_percentage then call update_map()
          same for zoom*/

        TranslationView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View V, MotionEvent event) {
                x = event.getX();
                y = event.getY();
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    x_0 = x;
                    y_0 = y;
                }
                //x//
                map_x_percentage = map_x_percentage + (x-x_0)/1000;
                map_y_percentage = map_y_percentage + (y-y_0)/1000;
                Update_Map();
                //x//
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





