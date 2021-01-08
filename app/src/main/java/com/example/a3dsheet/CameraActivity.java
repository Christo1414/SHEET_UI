package com.example.a3dsheet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

    /** Variables for touchlistener ( for updating UI )*/
    float x_0 = 0;          // initial values for x and y
    float y_0 = 0;
    float x = 0;            // Coordinates for touch listener
    float y = 0;
    float i = 0;            // for counting Motion Events
    float map_x_percentage = (float) 0.5;
    float map_y_percentage = (float) 0.5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);

        Initialize_Views();
        Initialize_UI();
        Initialize_WebView();

        Translation_Touch_Listener();
        Update_Zoombar();
    }


    private void Initialize_Views(){
        // initialize zooms and layouts here
        connection_status = (TextView) findViewById(R.id.IP_Status);
        warning_text = (TextView) findViewById(R.id.Warnings_text);
        map_layout = (RelativeLayout) findViewById(R.id.Map_Layout);
        map_cursor = (View) findViewById(R.id.Map_Cursor_View);
        zoombar_layout = (RelativeLayout) findViewById(R.id.ZoomBarLayout);
        zoombar_fill = (View) findViewById(R.id.ZoomBarView);
        TranslationView = (View) findViewById(R.id.TranslationView);
        ZoomView = (View) findViewById(R.id.ZoomBarLayout);
        Camera_WebView = (WebView) findViewById(R.id.Camera_WebView);

        // Set content of IP_Status
        ip_address = getIntent().getStringExtra("EXTRA_IP_ADDRESS");
        connection_status.setText("Connecting to " + ip_address);

    }
    private void Initialize_UI() {
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
        // zoombar height is static
        zoombar_y_max = pxFromDp(this, 500);
        zoombar_fill.setY(zoombar_y_max);
        zoombar_y_min = 0;

        // Hiding warning textview
        warning_text.setVisibility(View.GONE);

    }

    private void Initialize_WebView(){
        /** CAN DELETE WEBVIEW CODE */
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
        // Call when zooming
        zoombar_layout.setVisibility(View.VISIBLE);

        /** Update Zoom percentage HERE */
        float zoom_percentage = (float) 0.5;

        zoombar_fill.setY((zoombar_y_max - zoombar_y_min)*zoom_percentage);

    }

    private void Update_Map(){
        // Call when panning
        map_layout.setVisibility(View.VISIBLE);
        Hide_Warning();

        /** Update map percentages HERE */
        map_x_percentage = Check_Map_Boundary(map_x_percentage);
        map_y_percentage = Check_Map_Boundary(map_y_percentage);

        map_cursor.setX((cursor_x_max-cursor_x_min)*map_x_percentage);
        map_cursor.setY((cursor_y_max-cursor_y_min)*map_y_percentage);


    }

    private float Check_Map_Boundary(float percentage){
        // for Update_Map
        if (percentage >= 1){
            Show_Warning();
            percentage =  1;
        }
        else if (percentage <= 0){
            Show_Warning();
            percentage =  0;
        }
        return percentage;
    }


    public void Hide_Map(View view){
        /** should be called after camera is stationary for some time */
        map_layout.setVisibility(View.GONE);
    }

    public void Hide_Zoombar(View view){
        /** should be called sometime after zooming */
        zoombar_layout.setVisibility(View.GONE);
    }

    public void Hide_Warning(){
        /** should be called sometime after warning appears */
        warning_text.setVisibility(View.GONE);
    }


    private void Show_Warning(){
        // called when boundary condition met
        warning_text.setVisibility(View.VISIBLE);
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
        /** NOT FOR FINAL PROJECT. Instead just need to update map_percentage then call update_map()*/

        TranslationView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View V, MotionEvent event) {
                x = event.getX();
                y = event.getY();
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    x_0 = x;
                    y_0 = y;
                    i = 0;
                }
                //x//
                map_x_percentage = map_x_percentage + (x-x_0)/1000;
                map_y_percentage = map_y_percentage + (y-y_0)/1000;
                Update_Map();
                //x//
                x_0 = x;
                y_0 = y;
                i++;
                return true;
            }
        });
    }


    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        // To find boundary conditions
        return dp * context.getResources().getDisplayMetrics().density;
    }

}





