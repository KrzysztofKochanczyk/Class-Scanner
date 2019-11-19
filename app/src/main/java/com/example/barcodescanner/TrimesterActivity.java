package com.example.barcodescanner;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class TrimesterActivity extends AppCompatActivity {
    //private to keep other classes from using them

    //helps us read and write to the firebase
    public class FirebaseHelper {

        DatabaseReference dbRef;
        Boolean saved;
        ArrayList<course> courses = new ArrayList<>();
        ListView mListView;
        Context c;

        //receives a reference to Firebase
        public FirebaseHelper(DatabaseReference dbRef, Context context, ListView mListView) {
            this.dbRef = dbRef;
            this.c = context;
            this.mListView = mListView;
            this.retrieve();
        }

        //saving to database
        public Boolean save(course cc) {
            //check if they have passed us a valid teacher. If sp then return false
            if (cc == null) {
                saved = false;
            }
            else {
                //otherwise try to push data into firebase
                try {
                    dbRef.child("Teacher").push().setValue(cc);
                    saved = true;

                } catch (DatabaseException e) {
                    e.printStackTrace();
                    saved = false;
                }
            }
            return saved;
        }

        public ArrayList<course> retrieve() {
            //i believe here we can check to see if the teacher is signed in as a certain teacher or not to enter the proper data
            dbRef.child("Teacher").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    courses.clear();

                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                        //if the above statement is true we are going to look through the children we have in the database
                        System.out.println("There is more that exist" + dataSnapshot.getChildrenCount());
                        //for each item in
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            //now get course objects and populate our arraylist
                            course course = ds.getValue(course.class);
                            courses.add(course);
                        }
                        adapter = new myAdapter(c, courses);
                        mListView.setAdapter(adapter);

                        //enqueue messages so they actually work one after another
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                mListView.smoothScrollToPosition(courses.size());
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("here", databaseError.getMessage());
                    Toast.makeText(c, "Error " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            return courses;
        }
    }

    Toolbar toolbar;
    ListView listView;
    DatabaseReference db;
    FirebaseHelper helper;
    myAdapter adapter;
    EditText classNameET, crnET, dayET, timeET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimester);

        toolbar = (Toolbar)findViewById(R.id.triTB);
        listView = (ListView)findViewById(R.id.lvTri);
        db = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(db, this, listView);

        FloatingActionButton fabb = (FloatingActionButton) findViewById(R.id.fabb);
        fabb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.smoothScrollToPosition(4);
                displayInputDialog();
            }
        });
        initToolBar();
        //setupListView();
    }

    private void displayInputDialog() {
        Dialog d = new Dialog(this);
        d.setTitle("Save to Firebase");
        d.setContentView(R.layout.input_dialog);

        classNameET = d.findViewById(R.id.nameDET);
        crnET = d.findViewById(R.id.crnDET);
        dayET = d.findViewById(R.id.dayDET);
        timeET = d.findViewById(R.id.timeEDT);
        Button button = d.findViewById(R.id.DsaveBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = classNameET.getText().toString();
                String crn = crnET.getText().toString();
                String day = dayET.getText().toString();
                String time = timeET.getText().toString();

                //set data to pojo
                course courcc = new course();
                courcc.setName(name);
                courcc.setCrn(crn);
                courcc.setDay(day);
                courcc.setTime(time);

                if (name != null && name.length() > 0) {
                    if (helper.save(courcc)) {
                        //clear edittexts
                        classNameET.setText("");
                        crnET.setText("");
                        dayET.setText("");
                        timeET.setText("");

                        //refreshing listview now
                        ArrayList<course> fetchedData = helper.retrieve();
                        adapter = new myAdapter(TrimesterActivity.this, fetchedData);
                        listView.setAdapter(adapter);
                        listView.smoothScrollToPosition(fetchedData.size());
                    }
                    else
                    {
                        Toast.makeText(TrimesterActivity.this, "Name Must not be empty", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        d.show();
    }

    //action bar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu
        getMenuInflater().inflate(R.menu.tri_menu, menu);
        return true;
    }
    //handles action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Logout)
        {
            //incorporate logout function here
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Winter");
    }
    /*setup listview function here*/
//    private void setupListView() {
//        String[] clas = getResources().getStringArray(R.array.Class);
//        String[] course = getResources().getStringArray(R.array.Course);
//
//        myAdapter simpleAdap = new myAdapter(this, clas, course);
//        listView.setAdapter(simpleAdap);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //we can do a switch case if that makes it easy to push all the information inside of the view in the mainactivity
//                //which is where the students would get populated
//                Intent intent = new Intent(TrimesterActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
//    }

    public class myAdapter extends BaseAdapter {
        Context c;
        ArrayList<course> courses;

        public myAdapter(Context c, ArrayList<course> courses) {
            this.c = c;
            this.courses = courses;
        }

        @Override
        public int getCount() {
            return courses.size();
        }

        @Override
        public Object getItem(int position) {
            return courses.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //inflating cardview here
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(c).inflate(R.layout.tri_card, parent, false);
            }

            TextView classNameTV = convertView.findViewById(R.id.className);
            TextView courseNameTV = convertView.findViewById(R.id.course);
            TextView dayTV = convertView.findViewById(R.id.day);
            TextView classTimeTV = convertView.findViewById(R.id.classTime);

            final course theFinalCourse = (course) this.getItem(position);

            classNameTV.setText(theFinalCourse.getName());
            courseNameTV.setText(theFinalCourse.getCrn());
            dayTV.setText(theFinalCourse.getDay());
            classTimeTV.setText(theFinalCourse.getTime());

            //handles on click for each card view here
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //we can do a switch case if that makes it easy to push all the information inside of the view in the mainactivity
                    //which is where the students would get populated
                    Intent intent = new Intent(TrimesterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            return convertView;
        }

/*original adapter here */
//        private Context context;
//        private LayoutInflater layoutInflater;
//        private TextView classname, course;
//        private String[] classnameArray;
//        private String[] courseArray;
//
//        public myAdapter(Context context, String[] classname, String[] course) {
//            context = context;
//            classnameArray = classname;
//            courseArray = course;
//            //layout Inflater allows you to load different layouts in the view
//            layoutInflater = LayoutInflater.from(context);
//        }
//
//        @Override
//        public int getCount() {
//            return classnameArray.length;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return classnameArray[position];
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        //this entire function basically gives tri_card control to replace the view for its own view
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            //if current view is null it takes the present view that is available and inflates
//            //our card view into list view
//            if(convertView == null) {
//                convertView = layoutInflater.inflate(R.layout.tri_card, null);
//            }
//
//            classname = (TextView)convertView.findViewById(R.id.className);
//            course = (TextView)convertView.findViewById(R.id.course);
//
//            classname.setText(classnameArray[position]);
//            course.setText(courseArray[position]);
//            return convertView;
//
//        }
    }

}
