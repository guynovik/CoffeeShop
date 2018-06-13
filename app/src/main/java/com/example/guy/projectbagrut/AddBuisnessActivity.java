package com.example.guy.projectbagrut;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBuisnessActivity extends AppCompatActivity {

    private Spinner typeSpinner;
    private EditText nameText;
    private EditText addressText;
    private Button addButton;
    private FusedLocationProviderClient mFusedLocationProvider;
    private LatLng locationLatLng;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_buisness);

        mFusedLocationProvider = LocationServices.getFusedLocationProviderClient(this);
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
        mFusedLocationProvider.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task)
            {
                if (task.isSuccessful())
                {
                    Location location = task.getResult();
                    if (location!=null)
                    {
                        AddBuisnessActivity.this.locationLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Location Not Available. Please make sure your location services is on and try again", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Location Not Available. Please make sure your location services is on and try again", Toast.LENGTH_LONG).show();
                }
            }
        });

        nameText = (EditText) findViewById(R.id.nameEnter);
        addressText = (EditText) findViewById(R.id.addressText);
        addButton = (Button) findViewById(R.id.addButton);
        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = String.valueOf(typeSpinner.getSelectedItem());
                String name = String.valueOf(nameText.getText());
                String address = String.valueOf(addressText.getText());
                mDatabase = FirebaseDatabase.getInstance();
                DatabaseReference dbRef = mDatabase.getReference();
                DatabaseReference categoryRef = dbRef.child(category);
                DatabaseReference newBuisnessRef = categoryRef.child(name);
                newBuisnessRef.child("address").setValue(address);
                newBuisnessRef.child("latitude").setValue(locationLatLng.latitude);
                newBuisnessRef.child("longitude").setValue(locationLatLng.longitude);
                Toast.makeText(getApplicationContext(), "Buisness Added Successfully", Toast.LENGTH_SHORT).show();
                Intent back = new Intent(AddBuisnessActivity.this, MainActivity.class);
                startActivity(back);
            }
        });
    }
}
