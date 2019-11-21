package com.example.barcodescanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
//implementing the scannerView from ZXing **Check build gradle to see**
public class Main2Activity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Making scanner view be set to the content to present that instead of the actual page itself
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    //Whatever the camera scans will be put in the Tv1
    @Override
    public void handleResult(Result result) {
        //MainActivity.tv1.setText(result.getText());
        onBackPressed();

    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.startCamera();
    }
}
