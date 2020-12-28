package com.example.a3dsheet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class CameraActivity extends AppCompatActivity {

    /** Views and Layouts */
    private TextView connection_status;
    private TextView warning_text;
    private RelativeLayout map_layout;
    private View map_cursor;
    private RelativeLayout zoombar_layout;
    private View zoombar_fill;
    private View TranslationView;
    private View ZoomView;
    private WebView Camera_WebView;




    /** Variables */
    private String ip_address;
    private float px;
    private float cursor_x_max;
    private float cursor_x_min;
    private float cursor_y_max;
    private float cursor_y_min;
    private float zoombar_y_max;
    private float zoombar_y_min;




    /** Variables for touchlistener */
    float x_0 = 0;          // initial values for x and y
    float y_0 = 0;
    float x = 0;            // Coordinates for touch listener
    float y = 0;
    float i = 0;            // for counting Motion Events
    float x_vector = 0;     // Vectors for movement
    float y_vector = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera); // changes the activity

        Initialize_Views();
        Initialize_WebView();

        /** call function to connect to IP address? */


        Translation_Touch_Listener();       // touch listeners might need to be asynch/thread
        Zoom_Touch_Listener();

    }


    private void Initialize_Views(){
        /** Perform any initialization of views and layouts here */

        connection_status = (TextView) findViewById(R.id.IP_Status);
        warning_text = (TextView) findViewById(R.id.Warnings_text);
        map_layout = (RelativeLayout) findViewById(R.id.Map_Layout);
        map_cursor = (View) findViewById(R.id.Map_Cursor_View);
        zoombar_layout = (RelativeLayout) findViewById(R.id.ZoomBarLayout);
        zoombar_fill = (View) findViewById(R.id.ZoomBarView);
        TranslationView = (View) findViewById(R.id.TranslationView);
        ZoomView = (View) findViewById(R.id.ZoomBarLayout);
        Camera_WebView = (WebView) findViewById(R.id.Camera_WebView);




        // Initialize map cursor (values depend on height and width of map and map cursor)
            // map size and values are static
        px = pxFromDp(this, 40);
        map_cursor.setX(px);
        map_cursor.setY(px*(float)0.8);
        // Boundary Conditions
        cursor_x_max = pxFromDp(this, 80);
        cursor_x_min = 0;
        cursor_y_max = pxFromDp(this, 60);
        cursor_y_min = 0;


        // Initialize zoombar
            //zoombar height is static
        zoombar_y_max = pxFromDp(this, 500);
        zoombar_fill.setY(zoombar_y_max);
        zoombar_y_min = 0;

        // Hiding warning textview
        warning_text.setVisibility(View.GONE);

    }

    private void Initialize_WebView(){
        // Set content of IP_Status
        ip_address = getIntent().getStringExtra("EXTRA_IP_ADDRESS");
        connection_status.setText("Connecting to " + ip_address);

        // Initialize webview
        Camera_WebView.setWebViewClient(new WebViewClient());
        Camera_WebView.getSettings().setJavaScriptEnabled(true);
        Camera_WebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        Camera_WebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        Camera_WebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        Camera_WebView.setWebChromeClient(new WebChromeClient());

        Camera_WebView.loadUrl("https://www.youtube.com/watch?v=8ayK3odtP8M");


    }

    public void Update_Zoombar(){

        float sensitivity =(float) 1;
        zoombar_fill.setY(zoombar_fill.getY() + sensitivity*y_vector);

        // check boundary conditions
        if (zoombar_fill.getY() > zoombar_y_max) {
            zoombar_fill.setY(zoombar_y_max);
        }

        if (zoombar_fill.getY() < zoombar_y_min) {
            zoombar_fill.setY(zoombar_y_min);
        }

    }


    private void Update_Map(){

        float sensitivity =(float) 1;
        map_cursor.setX(map_cursor.getX() + sensitivity*x_vector);
        map_cursor.setY(map_cursor.getY() + sensitivity*y_vector);


        // check boundary conditions
        if (map_cursor.getX() > cursor_x_max){
            map_cursor.setX(cursor_x_max);
            Show_Warning();
        }
        if (map_cursor.getY() > cursor_y_max){
            map_cursor.setY(cursor_y_max);
            Show_Warning();

        }
        if (map_cursor.getX() < cursor_x_min) {
            map_cursor.setX(cursor_x_min);
            Show_Warning();

        }
        if (map_cursor.getY() < cursor_y_min){
            map_cursor.setY(cursor_y_min);
            Show_Warning();

        }
    }


    public void Hide_Map(View view){
        /** should be called after camera is stationary for some time */
            /** map should be shown automatically during camera translation */

        map_layout.setVisibility(View.GONE);

    }

    public void Hide_Zoombar(View view){
        /** should be called after camera is stationary for some time */
            /** zoombar should be shown automatically when zooming */

        zoombar_layout.setVisibility(View.GONE);

    }


    public void Hide_Warning(View view){
        /** should be called sometime after show warning */

        warning_text.setVisibility(View.GONE);

    }


    private void Show_Warning(){

        warning_text.setVisibility(View.VISIBLE);

    }


    public void Show_All(View view){
        /** Show map and warning */

        map_layout.setVisibility(View.VISIBLE);
        warning_text.setVisibility(View.VISIBLE);
        zoombar_layout.setVisibility(View.VISIBLE);


    }

    public void Hide_All(View view){
        /** Show map and warning */

        map_layout.setVisibility(View.GONE);
        warning_text.setVisibility(View.GONE);
        zoombar_layout.setVisibility(View.GONE);


    }


    private void Translation_Touch_Listener(){
        /** used to demonstrate map icon functionality */

        TranslationView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View V, MotionEvent event) {
                x = event.getX();
                y = event.getY();

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    x_0 = x;
                    y_0 = y;
                    x_vector = 0;
                    y_vector = 0;
                    i = 0;
                    map_layout.setVisibility(View.VISIBLE);
                }

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    i = 0;
                }


                x_vector = x - x_0; // Vector is calculated every other motion event
                y_vector = y - y_0;
                Update_Map();    //should be asynch

                x_0 = x;
                y_0 = y;
                i++;
                return true;
            }
        });
    }


    private void Zoom_Touch_Listener(){
        /** used to demonstrate zoom functionality */


        ZoomView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View V, MotionEvent event) {

                y = event.getY();

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    y_0 = y;
                    y_vector = 0;
                    zoombar_layout.setVisibility(View.VISIBLE);
                }

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    i = 0;
                }

                y_vector = y - y_0;
                Update_Zoombar();

                y_0 = y;
                return true;
            }
        });
    }




    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

}





