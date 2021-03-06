package com.example.guy.projectbagrut;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
//    DatabaseHelper myDb;


    private Spinner spinner1;
    private Button btnSubmit, btnDistance;
    private ArrayList<String> list1;
    private ArrayList<Business> list2;
    private ArrayAdapter<String> adapter;
    private ListView lv;
    private EditText editTextDistance;
    private DataSnapshot dataSnapshot;
    private Location currentLocation;
    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        myDb = new DatabaseHelper(this);
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();

        Button buisButton = (Button) findViewById(R.id.updateButton);
        editTextDistance = (EditText) findViewById(R.id.editTextDistance);
        buisButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBuisness();
            }
        });

        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task)
            {
                if (task.isSuccessful())
                {
                    Location location = task.getResult();
                    if (location!=null)
                    {
                        MainActivity.this.currentLocation = location;
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Problem with the location retrieving", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Problem with the location retrieving", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button addBuisnessButton = (Button) findViewById(R.id.insertButton);
        addBuisnessButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(MainActivity.this, AddBuisnessActivity.class);
                startActivity(addIntent);
            }
        });

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
                Log.i("Started Data Upload", "");
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
                            if (shot.child("rating").getValue(Double.class)!=null)
                            {
                                rating = shot.child("rating").getValue(Double.class);
                            }
                            else
                            {
                                rating = 0;
                            }
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

      //  lv = (ListView) findViewById(R.id.businessList);
      // adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list1);




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

        btnDistance = (Button) findViewById(R.id.btnSubmit2);



        btnDistance.setOnClickListener(new OnClickListener() {

                                                   @Override
                                                   public void onClick (View v){

                                                       showMaps(Double.parseDouble(editTextDistance.getText().toString()));
                                                   }

                                               });



        btnSubmit.setOnClickListener(new OnClickListener() {

                                         @Override
                                         public void onClick(View v) {

                                             showMaps(-1);

                                             /*

                                             Toast.makeText(MainActivity.this,
                                                     "Choice successful  " +
                                                             "\nSpinner 1 : " + String.valueOf(spinner1.getSelectedItem()),
                                                     Toast.LENGTH_SHORT).show();
                                             screen(String.valueOf((spinner1.getSelectedItem())));






                if (list2.size()>0)
                {
                    ArrayList <String> names = new ArrayList<>();
                    ArrayList <Double> longs = new ArrayList<>();
                    ArrayList <Double> lats = new ArrayList<>();
                    String category = String.valueOf(spinner1.getSelectedItem());
                    DataSnapshot buisnessUno = dataSnapshot.child(category);
                    for (DataSnapshot b: buisnessUno.getChildren())
                    {
                        names.add(b.getKey());
                        longs.add(b.child("longitude").getValue(Double.class));
                        lats.add(b.child("latitude").getValue(Double.class));
                    }
                    Intent next = new Intent(MainActivity.this, MapsActivity.class);
                    next.putStringArrayListExtra("names", names);
                    Double [] longis = new Double[longs.size()];
                    longis = longs.toArray(longis);
                    double [] exLong = convertDouble(longis);
                    Double [] latis = new Double[lats.size()];
                    latis = lats.toArray(latis);
                    double [] exLat = convertDouble(latis);
                    if (latis.length==longis.length)
                    {
                        next.putExtra("lng", exLong);
                        next.putExtra("lat", exLat);
                        startActivity(next);
                    }

                }
                */
            }


        });


    }

    public void showMaps(double dis) {

        Toast.makeText(MainActivity.this,
                "Choice successful  " +
                        "\nSpinner 1 : " + String.valueOf(spinner1.getSelectedItem()),
                Toast.LENGTH_SHORT).show();
        screen(String.valueOf((spinner1.getSelectedItem())));
        if (list2.size()>0)
        {
            ArrayList <String> names = new ArrayList<>();
            ArrayList <Double> longs = new ArrayList<>();
            ArrayList <Double> lats = new ArrayList<>();
            String category = String.valueOf(spinner1.getSelectedItem());
            DataSnapshot buisnessUno = dataSnapshot.child(category);
            for (DataSnapshot b: buisnessUno.getChildren())
            {
                names.add(b.getKey());
                Location buisLocation = new Location("App");
                buisLocation.setLongitude(b.child("longitude").getValue(Double.class));
                buisLocation.setLatitude(b.child("latitude").getValue(Double.class));
                double distance = buisLocation.distanceTo(currentLocation);
                Log.i("Distance", distance+"");
                if (distance<dis||dis==-1)
                {
                    longs.add(b.child("longitude").getValue(Double.class));
                    lats.add(b.child("latitude").getValue(Double.class));
                }
            }
            Intent next = new Intent(MainActivity.this, MapsActivity.class);
            next.putExtra("dis",dis);
            next.putStringArrayListExtra("names", names);
            Double [] longis = new Double[longs.size()];
            longis = longs.toArray(longis);
            double [] exLong = convertDouble(longis);
            Double [] latis = new Double[lats.size()];
            latis = lats.toArray(latis);
            double [] exLat = convertDouble(latis);
            if (latis.length==longis.length)
            {
                next.putExtra("lng", exLong);
                next.putExtra("lat", exLat);
                startActivity(next);
            }
                    /*next.putExtra("lng", lng);
                    next.putExtra("lat", lat);
                    next.putExtra("name", name);
                    startActivity(next);*/
        }

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

        /*
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,filtered);

        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        */
    }

    private void updateBuisness ()
    {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading Buisnesses");
        dialog.show();
        FirebaseDatabase mDatabase;
        mDatabase = FirebaseDatabase.getInstance();
        mDatabase.goOnline();
        DatabaseReference dbRef = mDatabase.getReference();
        DatabaseReference justR = dbRef.child("restaurants");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Business b;
                Log.i("Started Data Upload", "");
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
                            if (shot.child("rating").getValue(Double.class)!=null)
                            {
                                rating = shot.child("rating").getValue(Double.class);
                            }
                            else
                            {
                                rating = 0;
                            }
                            lng = shot.child("longitude").getValue(Double.class);
                            lat = shot.child("latitude").getValue(Double.class);
                            b = new Business(name, address, "", rating, lng, lat);
                            MainActivity.this.list2.add(b);
                        }
                        dialog.dismiss();
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                        // expected output: SyntaxError: unterminated string literal
                        // Note - error messages will vary depending on browser
                    }
                }
                finishHim();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Error", databaseError.toString());
                dialog.dismiss();
            }
        });


    }

    public void finishHim ()
    {
        for (Business b : list2)
        {
            list1.add(b.toString());
        }

      //  lv = (ListView) findViewById(R.id.businessList);
       // adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list1);
    }

    private double [] convertDouble (Double [] arr)
    {
        double [] ret = new double[arr.length];
        for (int i = 0;i<arr.length;i++)
        {
            ret[i] = arr[i].doubleValue();
        }
        return ret;
    }

}
