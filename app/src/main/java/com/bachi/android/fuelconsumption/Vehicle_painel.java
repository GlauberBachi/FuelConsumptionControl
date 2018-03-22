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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Vehicle_painel extends AppCompatActivity {
    private static final String TAG = "Vehicle_painel";
    int vehicleID;
    String label, description;
    int sequence;
    DatabaseHelper db;
    private TextView tv_vehicle_name;
    private Button btn_new_fillup;
    private ListView list_fillup;
    private int selectedID;
    private long odometer, liters, kmDriven, consumption;
    private String selectedName, fillupDate;




   /* @Override
    public void onResume(){
        super.onResume();
        this.onCreate(null);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_painel);

        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        list_fillup = (ListView)findViewById(R.id.list_fillup);
        db = new DatabaseHelper(this);

        tv_vehicle_name = (TextView)findViewById(R.id.tv_vehicle_name);
        btn_new_fillup = (Button)findViewById(R.id.btn_new_fillup);






        //get the intent extra from the MainActivity
        Intent receivedIntent = getIntent();

        //get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("ID", -1); //-1 is just the default value


        //get the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("name");


        //set the text to show the current selected name
        tv_vehicle_name.setText(selectedName);



        tv_vehicle_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent screenVehicle = new Intent(Vehicle_painel.this, Vehicle_edit.class);
                screenVehicle.putExtra("ID", selectedID);
                screenVehicle.putExtra("name", selectedName);
                startActivity(screenVehicle);
            }
        });





        btn_new_fillup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Vehicle_painel.this, Fillup_new.class);
                i.putExtra("ID", selectedID);
                i.putExtra("name", selectedName);
                startActivity(i);
            }
        });






    populateListView();

    }




    public void populateListView() {
        //get the data and append to the list
        Cursor cursor = db.getAllDataFillup(selectedID);
        ArrayList<String> arrayList = new ArrayList<>();


        while (cursor.moveToNext()) {
            arrayList.add(cursor.getString(0) + "          Fillup " + cursor.getString(8) + " " + cursor.getString(7));

        }


        //create the list adapter and set the adapter
        ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        list_fillup.setAdapter(listAdapter);


        //set onItemClickListener to the listView
        list_fillup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //int fillupID = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                int fillupID = Integer.parseInt(adapterView.getItemAtPosition(i).toString().substring(0,4).trim());
                //toastMessage("fillupID:  " + fillupID);


                //long fillupPosition = adapterView.getItemIdAtPosition(i);
                //toastMessage("fillupPosition:  " + fillupPosition);


                Log.d(TAG, "onItemClick: You Clicked on " + fillupID);


                Cursor data = db.getDataTableFillup(fillupID);//get the data associated with that fillupID

                fillupID = -1;
                while (data.moveToNext()) {
                    fillupID = data.getInt(0);
                    vehicleID = data.getInt(1);
                    fillupDate = data.getString(2);
                    odometer = data.getLong(3);
                    kmDriven = data.getLong(4);
                    liters = data.getLong(5);
                    consumption = data.getLong(6);
                    label = data.getString(7);
                    sequence = data.getInt(8);
                    //description = data.getString(0) + " - Fillup " + data.getString(8) + " " + data.getString(7);
                }
                if (fillupID > -1) {
                    Log.d(TAG, "onItemClick: The ID is: " + fillupID);
                    Intent screenVehicle = new Intent(Vehicle_painel.this, Fillup_edit.class);
                    screenVehicle.putExtra("fillupID", fillupID);
                    screenVehicle.putExtra("vehicleID", vehicleID);
                    screenVehicle.putExtra("vehicleName", selectedName);
                    screenVehicle.putExtra("date", fillupDate);
                    screenVehicle.putExtra("odometer", odometer);
                    screenVehicle.putExtra("kmDriven", kmDriven);
                    screenVehicle.putExtra("liters", liters);
                    screenVehicle.putExtra("consumption", consumption);
                    screenVehicle.putExtra("label", label);
                    screenVehicle.putExtra("sequence", sequence);
                    //screenVehicle.putExtra("description", description);

                    //toastMessage("fillupPosition:  " + fillupPosition);
                    startActivity(screenVehicle);

                } else {
                    toastMessage("fillupID = " + fillupID);
                    //db.deleteAllFillup(selectedID);
                    //toastMessage("No ID associated with that name hahaha");
                }
            }
        });



}




    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
