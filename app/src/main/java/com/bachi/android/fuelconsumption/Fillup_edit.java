package com.bachi.android.fuelconsumption;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Fillup_edit extends AppCompatActivity {
    Button  btn_delete;
    EditText et_date, et_odometer, et_liters, et_kmDriven, et_consumption, et_fillupID;
    int vehicleID;
    DatabaseHelper db;
    private TextView tv_vehicle_name;
    private int selectedFillupID;
    private String selectedName, fillupDate; //, receivedDescription;
    private long receivedOdometer, receivedLiters, receivedKmDriven, receivedConsumption;
    //Vehicle_painel vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fillup_edit);

        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        db = new DatabaseHelper(this);


        tv_vehicle_name = (TextView)findViewById(R.id.tv_vehicle_name_fillup);
        btn_delete = (Button)findViewById(R.id.btn_delete);
        et_fillupID = (EditText)findViewById(R.id.et_fillupID);
        et_date = (EditText)findViewById(R.id.et_date);
        et_odometer = (EditText)findViewById(R.id.et_odometer);
        et_kmDriven = (EditText)findViewById(R.id.et_kms);
        et_liters = (EditText)findViewById(R.id.et_liters);
        et_consumption = (EditText)findViewById(R.id.et_consumption);





        Intent receivedIntent = getIntent();
        selectedFillupID = receivedIntent.getIntExtra("fillupID", -1);
        vehicleID = receivedIntent.getIntExtra("vehicleID", -1);
        selectedName = receivedIntent.getStringExtra("vehicleName");
        fillupDate = receivedIntent.getStringExtra("date");
        receivedOdometer = receivedIntent.getLongExtra("odometer", -1);
        receivedKmDriven = receivedIntent.getLongExtra("kmDriven", -1);
        receivedLiters = receivedIntent.getLongExtra("liters", -1);
        receivedConsumption = receivedIntent.getLongExtra("consumption", -1);
        //receivedDescription = receivedIntent.getStringExtra("description");

        //toastMessage(selectedName);


        tv_vehicle_name.setText(selectedName);
        et_fillupID.setText(String.valueOf(selectedFillupID));
        et_date.setText(fillupDate);
        et_odometer.setText(String.valueOf(receivedOdometer));
        et_kmDriven.setText(String.valueOf(receivedKmDriven));
        et_liters.setText(String.valueOf(receivedLiters));
        et_consumption.setText(String.valueOf(receivedConsumption));


        //showMessage("Remeber", "For the sequence number 1, the fields 'kms driven' and 'km/Liter' will be always blank");

        tv_vehicle_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent screenVehicle = new Intent(Fillup_edit.this, Vehicle_edit.class);
                screenVehicle.putExtra("ID", vehicleID);
                screenVehicle.putExtra("name", selectedName);
                startActivity(screenVehicle);
            }
        });


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteFillup(selectedFillupID);
                //vp.onResume();
                //db.deleteAllFillup(selectedFillupID);
                Intent i = new Intent(Fillup_edit.this, Vehicle_painel.class);
                i.putExtra("name", selectedName);
                i.putExtra("ID",vehicleID);
                startActivity(i);
            }
        });

    }



    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }







    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
