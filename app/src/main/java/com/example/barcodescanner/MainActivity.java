package com.example.barcodescanner;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.BaseAdapter;
import android.widget.AdapterView;




public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab, fab1, fab2, fab3;
    Animation fabOpen, fabClose, rotateF, rotateB;
    boolean isOpen = false;

    Toolbar toolbar;
    ListView listView;

    //doing this will allow "tv1" to pass to other activities
    public static TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tv1 = (TextView)findViewById(R.id.tv1);
        toolbar = (Toolbar)findViewById(R.id.studentTB);
        listView = (ListView)findViewById(R.id.studentLV);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab3= (FloatingActionButton)findViewById(R.id.fab3);

        fabOpen = AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this,R.anim.fab_close);

        rotateF = AnimationUtils.loadAnimation(this,R.anim.rotate_foward);
        rotateB = AnimationUtils.loadAnimation(this,R.anim.rotate_backward);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatedFab();
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //30:30 will show how to do the dialog box for this
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, JnumScanner.class));
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });


        initToolBar();
        setupListView();
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Class name");
    }

    private void setupListView() {
        String[] student = getResources().getStringArray(R.array.Students);

        studentAdap simpleAdap = new studentAdap(this, student);
        listView.setAdapter(simpleAdap);
    }

    public class studentAdap extends BaseAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private TextView student;
        private String[] studentArray;

        public studentAdap(Context context, String[] students) {
            context = context;
            studentArray = students;
            //layout Inflater allows you to load different layouts in the view
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return studentArray.length;
        }

        @Override
        public Object getItem(int position) {
            return studentArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //if current view is null it takes the present view that is available and inflates
            //our card view into list view
            if(convertView == null) {
                convertView = layoutInflater.inflate(R.layout.student_card, null);
            }

            student = (TextView)convertView.findViewById(R.id.studentName);
            student.setText(studentArray[position]);
            return convertView;
        }
    }

    private void animatedFab()
    {
        if(isOpen)
        {
            fab.startAnimation(rotateF);
            fab1.startAnimation(fabClose);
            fab2.startAnimation(fabClose);
            fab3.startAnimation(fabClose);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);

            isOpen = false;
        }
        else
        {
            fab.startAnimation(rotateB);
            fab1.startAnimation(fabOpen);
            fab2.startAnimation(fabOpen);
            fab3.startAnimation(fabOpen);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);

            isOpen = true;
        }
    }
}