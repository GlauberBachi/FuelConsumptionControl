package com.bachi.android.fuelconsumption;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper MyDb;
    private ListView mListView;
    Button btn_new_vehicle;
    private static final String TAG = "MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        mListView = (ListView)findViewById(R.id.listView);
         MyDb = new DatabaseHelper(this);


        btn_new_vehicle = (Button)findViewById(R.id.btn_new_vehicle);
        btn_new_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Vehicle_new.class);
                startActivity(i);
            }
        });

        populateListView();

    }




    public void populateListView(){
        //get the data and append to the list
        Cursor data = MyDb.getAllData();
        ArrayList<String> listData = new ArrayList<>();

        while (data.moveToNext()){
            listData.add(data.getString(1));
        }



        //create the list adapter and set the adapter
        ListAdapter adapter;

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);


        //set onItemClickListener to the listView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);

                Cursor data = MyDb.getItemID(name);//get the id associated with that name
                int itemID = -1;
                while (data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if (itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent screenVehicle = new Intent(MainActivity.this, Vehicle_painel.class);
                    screenVehicle.putExtra("ID", itemID);
                    screenVehicle.putExtra("name", name);
                    startActivity(screenVehicle);

                }else{
                    toastMessage("No ID associated with that name");
                }
            }
        });
      }




      private void toastMessage(String message){
          Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
      }









}
