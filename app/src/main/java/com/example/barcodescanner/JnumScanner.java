package com.example.barcodescanner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class JnumScanner extends AppCompatActivity {

    SurfaceView cameraView;
    TextView textView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_number_scanner);

        cameraView = (SurfaceView) findViewById(R.id.sv1);
        textView = (TextView) findViewById(R.id.jnum_tv);





    }
}
