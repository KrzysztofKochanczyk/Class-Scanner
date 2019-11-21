package com.example.barcodescanner;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //helps us read and write to the firebase
    public class FirebaseHelper {

        DatabaseReference dbRef;
        Boolean saved;
        ArrayList<student> students = new ArrayList<>();
        ListView mListView;
        Context c;

        //receives a reference to Firebase
        public FirebaseHelper(DatabaseReference dbRef, Context context, ListView mListView) {
            this.dbRef = dbRef;
            this.c = context;
            this.mListView = mListView;
            if (cardPos == 0)
            {
                //childString = "-Lu3n_fC2B4YekCOgrxP";
                this.retrieve();
            }
            else if (cardPos == 1)
            {
                //childString = "class1";
                this.retrieve1();
            }
            else if (cardPos == 2)
            {
                //childString = "class2";
            }
            else if (cardPos == 3)
            {
                //childString = "class3";
            }

        }

        //saving to database
        public Boolean save(student cc) {
            //check if they have passed us a valid teacher. If sp then return false
            if (cc == null) {
                saved = false;
            }
            else {
                //otherwise try to push data into firebase
                try {

                    dbRef.child("users").child("teacher1").child("-Lu3n_fC2B4YekCOgrxP").child("students").push().setValue(cc);
                    saved = true;

                } catch (DatabaseException e) {
                    e.printStackTrace();
                    saved = false;
                }
            }
            return saved;
        }

        public ArrayList<student> retrieve() {
            //if userID is equal to the userID then use classes for th
            //i believe here we can check to see if the teacher is signed in as a certain teacher or not to enter the proper data
            dbRef.child("users").child(userID).child("-Lu3n_fC2B4YekCOgrxP").child("students").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    students.clear();

                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                        //if the above statement is true we are going to look through the children we have in the database
                        System.out.println("Students---------" + dataSnapshot.getChildrenCount());
                        //for each item in
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            //now get course objects and populate our arraylist
                            student stud = ds.getValue(student.class);
                            students.add(stud);
                        }
                        adapter = new myAdapter(c, students);
                        mListView.setAdapter(adapter);

                        //enqueue messages so they actually work one after another
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                mListView.smoothScrollToPosition(students.size());
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
            return students;
        }

        public ArrayList<student> retrieve1() {
            //if userID is equal to the userID then use classes for th
            //i believe here we can check to see if the teacher is signed in as a certain teacher or not to enter the proper data
            dbRef.child("users").child(userID).child("class1").child("students").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    students.clear();

                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                        //if the above statement is true we are going to look through the children we have in the database
                        System.out.println("Students---------" + dataSnapshot.getChildrenCount());
                        //for each item in
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            //now get course objects and populate our arraylist
                            student stud = ds.getValue(student.class);
                            students.add(stud);
                        }
                        adapter = new myAdapter(c, students);
                        mListView.setAdapter(adapter);

                        //enqueue messages so they actually work one after another
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                mListView.smoothScrollToPosition(students.size());
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
            return students;
        }
    }

    FloatingActionButton fab, fab1, fab2, fab3;
    Animation fabOpen, fabClose, rotateF, rotateB;
    boolean isOpen = false;

    Toolbar toolbar;
    ListView listView;
    DatabaseReference dbREF;
    FirebaseHelper helper;
    FirebaseAuth mAuth;
    FirebaseDatabase mFireDB;
    FirebaseAuth.AuthStateListener mAuthListener;
    myAdapter adapter;
    EditText lastName, firstName, jnumber;
    String userID;
    String childString;
    int cardPos;


    //doing this will allow "tv1" to pass to other activities

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.studentTB);
        listView = (ListView)findViewById(R.id.studentLV);

        dbREF = FirebaseDatabase.getInstance().getReference();
        mFireDB = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        helper = new FirebaseHelper(dbREF, this, listView);

        cardPos = getIntent().getIntExtra("cardPosition", cardPos);
        System.out.println(cardPos);


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
                //add students to the class
                listView.smoothScrollToPosition(4);
                displayStudDialog();
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
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Class name");
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

    private void displayStudDialog() {
        Dialog d = new Dialog(this);
        d.setTitle("Save to Firebase");
        d.setContentView(R.layout.student_input_dialog);

        lastName = d.findViewById(R.id.lastNameET);
        firstName = d.findViewById(R.id.firstNameET);
        jnumber = d.findViewById(R.id.jNumET);
        Button button = d.findViewById(R.id.studSaveBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameFirst = firstName.getText().toString();
                String nameLast = lastName.getText().toString();
                String jnumm = jnumber.getText().toString();

                student studentt = new student();
                studentt.setFirstName(nameFirst);
                studentt.setLastName(nameLast);
                studentt.setjNum(jnumm);

                if (nameFirst != null && nameFirst.length() > 0) {
                    if (helper.save(studentt)) {
                        //clearing fields
                        firstName.setText("");
                        lastName.setText("");
                        jnumber.setText("");

                        //refresh listview
                        ArrayList<student> fetchedData = helper.retrieve();
                        adapter = new myAdapter(MainActivity.this, fetchedData);
                        listView.setAdapter(adapter);
                        listView.smoothScrollToPosition(fetchedData.size());
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Nameeee Must not be empty", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        d.show();
    }

    public class myAdapter extends BaseAdapter {
        Context c;
        ArrayList<student> students;

        public myAdapter(Context c, ArrayList<student> students) {
            this.c = c;
            this.students = students;
        }

        @Override
        public int getCount() {
            return students.size();
        }

        @Override
        public Object getItem(int position) {
            return students.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //inflating cardview here
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(c).inflate(R.layout.student_card, parent, false);
            }

            TextView fName = convertView.findViewById(R.id.studFName);
            TextView lName = convertView.findViewById(R.id.studLName);

            final student theFinalStudent = (student) this.getItem(position);

            fName.setText(theFinalStudent.getFirstName());
            lName.setText(theFinalStudent.getLastName());

            return convertView;
        }

    }
}