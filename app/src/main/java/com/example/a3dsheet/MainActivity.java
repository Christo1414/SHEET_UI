package com.example.a3dsheet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ConnectToCamera(View view) {

        // Create intent for CameraActivity
        Intent intent = new Intent(this, CameraActivity.class);

        // Send entered IP address as extra
        EditText editText = (EditText) findViewById(R.id.IP_ADDRESS_ID);
        String ip_address = editText.getText().toString();
        intent.putExtra("EXTRA_IP_ADDRESS", ip_address);
        startActivity(intent);
    }
}