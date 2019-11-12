package com.example.barcodescanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button scanBarcodeBtn;
    Button scanJnumBtn;
    Button manEnterBtn;
    //doing this will allow "tv1" to pass to other activities
    public static TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanBarcodeBtn = (Button)findViewById(R.id.scanBarcodeBtn);
        tv1 = (TextView)findViewById(R.id.tv1);

        scanBarcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });
    }
}