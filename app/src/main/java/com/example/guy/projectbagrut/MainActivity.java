package com.example.guy.projectbagrut;

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

public class MainActivity extends AppCompatActivity {


    private Spinner spinner1;
    private Button btnSubmit;
    private ArrayList<String> list1;
    private ArrayList<Business> list2;
    private ArrayAdapter<String> adapter;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        list1 = new ArrayList<>();
        list2 = new ArrayList<>();

        list2.add(new Business("aharony", " dizengof 9 ", "Restaurants", 4.5));
        list2.add(new Business("cinema city", " glilot 18 ", "Movie Theatres", 4.3));
        list2.add(new Business("landwer", " sokolov 20 ", "Restaurants", 5.3));
        list2.add(new Business("mama rasko", "yehuda halevi 34", "Pizza Places", 4.2));
        list2.add(new Business("dominos", " sokolov 102 ", "Pizza Places", 3.9));

        for (Business b : list2)
        {
            list1.add(b.toString());
        }

        lv = (ListView) findViewById(R.id.businessList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list1);








        lv.setAdapter(adapter);


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
                        "Choice succesful  " +
                                "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
                screen(String.valueOf((spinner1.getSelectedItem())));

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
