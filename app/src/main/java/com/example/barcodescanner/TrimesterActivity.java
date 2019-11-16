package com.example.barcodescanner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class TrimesterActivity extends AppCompatActivity {
    //private to keep other classes from using them
    Toolbar toolbar;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimester);

        toolbar = (Toolbar)findViewById(R.id.triTB);
        listView = (ListView)findViewById(R.id.lvTri);

        initToolBar();
        setupListView();
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Winter");
    }

    private void setupListView() {
        String[] clas = getResources().getStringArray(R.array.Class);
        String[] course = getResources().getStringArray(R.array.Course);

        myAdapter simpleAdap = new myAdapter(this, clas, course);
        listView.setAdapter(simpleAdap);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //we can do a switch case if that makes it easy to push all the information inside of the view in the mainactivity
                //which is where the students would get populated
                Intent intent = new Intent(TrimesterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public class myAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private TextView classname, course;
        private String[] classnameArray;
        private String[] courseArray;

        public myAdapter(Context context, String[] classname, String[] course) {
            context = context;
            classnameArray = classname;
            courseArray = course;
            //layout Inflater allows you to load different layouts in the view
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return classnameArray.length;
        }

        @Override
        public Object getItem(int position) {
            return classnameArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //this entire function basically gives tri_card control to replace the view for its own view
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //if current view is null it takes the present view that is available and inflates
            //our card view into list view
            if(convertView == null) {
                convertView = layoutInflater.inflate(R.layout.tri_card, null);
            }

            classname = (TextView)convertView.findViewById(R.id.className);
            course = (TextView)convertView.findViewById(R.id.course);

            classname.setText(classnameArray[position]);
            course.setText(courseArray[position]);
            return convertView;

        }
    }
}
