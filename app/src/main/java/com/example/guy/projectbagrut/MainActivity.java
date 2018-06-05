package com.example.guy.projectbagrut;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
//    DatabaseHelper myDb;


    private Spinner spinner1;
    private Button btnSubmit;
    private ArrayList<String> list1;
    private ArrayList<Business> list2;
    private ArrayAdapter<String> adapter;
    private ListView lv;
    private DataSnapshot dataSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        myDb = new DatabaseHelper(this);
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();


        /*
       list2.add(new Business("aharony", " dizengof 9 ", "Restaurants", 4.5, 1, 1));
        list2.add(new Business("cinema city", " glilot 18 ", "Movie Theatres", 4.3,28.1, 32.1));
        list2.add(new Business("landwer", " sokolov 20 ", "Restaurants", 5.3, 28.2, 32.2));
        list2.add(new Business("mama rasko", "yehuda halevi 34", "Pizza Places", 4.2, 28.3, 32.3));
        list2.add(new Business("dominos", " sokolov 102 ", "Pizza Places", 3.9, 28.4, 32.4));
        */



        FirebaseDatabase mDatabase;
        mDatabase = FirebaseDatabase.getInstance();
        mDatabase.goOnline();
        DatabaseReference dbRef = mDatabase.getReference();
        DatabaseReference justR = dbRef.child("restaurants");
        Log.i("Shawarma", "nuggets");

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Business b;
                Log.i("Started Data Upload", "Shawarma");
                MainActivity.this.dataSnapshot = dataSnapshot;
                for (DataSnapshot snap : dataSnapshot.getChildren())
                {
                    try {


                        for (DataSnapshot shot : snap.getChildren()) {
                            String name, address, category;
                            double rating, lng, lat;
                            name = shot.getKey();
                            address = shot.child("address").getValue(String.class);
                            //category = shot.child("category").getValue(String.class);
                            rating = shot.child("rating").getValue(Double.class);
                            lng = shot.child("longitude").getValue(Double.class);
                            lat = shot.child("latitude").getValue(Double.class);
                            b = new Business(name, address, "", rating, lng, lat);
                            MainActivity.this.list2.add(b);
                        }
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                        // expected output: SyntaxError: unterminated string literal
                        // Note - error messages will vary depending on browser
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Error", databaseError.toString());
            }
        });





        for (Business b : list2)
        {
            list1.add(b.toString());
        }

        lv = (ListView) findViewById(R.id.businessList);
       adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list1);




       // lv.setAdapter(adapter);


        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
    }

    // add items into spinner dynamically


    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);

    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this,
                        "Choice successful  " +
                                "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
                screen(String.valueOf((spinner1.getSelectedItem())));

/*

                FirebaseDatabase mDatabase;
                mDatabase = FirebaseDatabase.getInstance();

                DatabaseReference dbRef = mDatabase.getReference();
                DatabaseReference justR = dbRef.child("coffee places");
                Log.i("Shawarma", "nuggets");
                mDatabase.goOnline();
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Business b;
                        Log.i("Started Data Upload", "Shawarma");
                        MainActivity.this.dataSnapshot = dataSnapshot;
                        for (DataSnapshot snap : dataSnapshot.getChildren())
                        {
                            try {


                                for (DataSnapshot shot : snap.getChildren()) {
                                    String name, address, category;
                                    double rating, lng, lat;
                                    name = shot.getKey();
                                    address = shot.child("address").getValue(String.class);
                                    //category = shot.child("category").getValue(String.class);
                                    rating = shot.child("rating").getValue(Double.class);
                                    lng = shot.child("longitude").getValue(Double.class);
                                    lat = shot.child("latitude").getValue(Double.class);
                                    b = new Business(name, address, "", rating, lng, lat);
                                    MainActivity.this.list2.add(b);
                                }
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                                // expected output: SyntaxError: unterminated string literal
                                // Note - error messages will vary depending on browser
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i("Error", databaseError.toString());
                    }
                });
*/


/*
                FirebaseDatabase mDatabase;
                mDatabase = FirebaseDatabase.getInstance();

                mDatabase.goOnline();

                DatabaseReference dbRef = mDatabase.getReference();
                DatabaseReference justR = dbRef.child("restaurants");
                Log.i("Shawarma", "nuggets");
                mDatabase.goOnline();

                        Business b;
                        Log.i("Started Data Upload", "Shawarma");
                        MainActivity.this.dataSnapshot = dataSnapshot;
                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                            try {


                                for (DataSnapshot shot : snap.getChildren()) {
                                    String name, address, category;
                                    double rating, lng, lat;
                                    name = shot.getKey();
                                    address = shot.child("address").getValue(String.class);
                                    //category = shot.child("category").getValue(String.class);
                                    rating = shot.child("rating").getValue(Double.class);
                                    lng = shot.child("longitude").getValue(Double.class);
                                    lat = shot.child("latitude").getValue(Double.class);
                                    b = new Business(name, address, "", rating, lng, lat);
                                    MainActivity.this.list2.add(b);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                // expected output: SyntaxError: unterminated string literal
                                // Note - error messages will vary depending on browser
                            }
                        }



                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i("Error", databaseError.toString());
                    }
                });
*/
                if (list2.size()>0)
                {
                     Business b= list2.get(0);//Replace with Distance Buisness
                    Intent next = new Intent(MainActivity.this, MapsActivity.class);
                    next.putExtra("lng", b.getLng());
                    next.putExtra("lat", b.getLat());
                    next.putExtra("name", b.getName());
                    startActivity(next);
                }



            }

        });


    }

    public void screen (String filter)
    {
        ArrayList<String> filtered = new ArrayList();
        for(Business b : list2)
        {
            if(b.category.equals(filter))
            {
                filtered.add(b.toString());
                Log.i("Added", b.getName());
                //HeAra
            }
        }
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,filtered);

        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}

/*
class GetPlacesTask extends AsyncTask <Object, Integer, ArrayList<>>
{

}*/
