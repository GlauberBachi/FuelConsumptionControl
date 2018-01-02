package com.bachi.android.fuelconsumption;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.os.Build.ID;

public class Fillup_new extends AppCompatActivity implements DatePickerFragment.OnFragmentInteractionListener {
    DatabaseHelper db;
    EditText et_odometer, et_date, et_liters;
    TextView tv_vehicle_name_fup;
    Button btn_save2;
    CheckBox checkBox_newSequence;
    private int selectedID;
    private String selectedName;
    String label;
    int lastFillupID, sequence, lastSequence;
    float lastOdometer, kmDriven, consumption;





    private int selectedID2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fillup_new);

        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        db = new DatabaseHelper(this);
        et_odometer = (EditText)findViewById(R.id.et_odometer);
        et_date = (EditText)findViewById(R.id.et_date);
        et_liters = (EditText)findViewById(R.id.et_liters);
        tv_vehicle_name_fup = (TextView) findViewById(R.id.tv_vehicle_name_fillup);
        btn_save2 = (Button)findViewById(R.id.btn_save2);
        checkBox_newSequence = (CheckBox)findViewById(R.id.checkBox_newSequence);


 





        //get the intent extra from the MainActivity
        Intent receivedIntent = getIntent();

        //get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("ID", -1); //-1 is just the default value


        //get the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("name");


        //set the text to show the current selected name
        tv_vehicle_name_fup.setText(selectedName);




        addData();

    }

    public void addData(){


        btn_save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_date.getText().length()==0) {
                    showMessage("Please", "Fill the date");
                }else if(et_odometer.getText().length() == 0){
                    showMessage("Please", "Fill the odometer");
                }else if(et_liters.getText().length() == 0){
                    showMessage("Please", "Fill the liters");
                }
            else{

                    //get the last fillupID
                    Cursor data = db.getLastFillupID(selectedID);
                    while (data.moveToNext()) {
                        lastFillupID = data.getInt(0);}

                    //get the last odometer value
                    Cursor data2 = db.getLastOdometer(lastFillupID);
                    while (data2.moveToNext()){
                        lastOdometer = data2.getFloat(0);
                        lastSequence = data2.getInt(1);
                    }

                    if(lastOdometer == 0 || checkBox_newSequence.isChecked()){
                        kmDriven = 0;
                        consumption = 0;
                        label = " -  New Sequence                         ";
                        sequence = 1;

                    }else {
                        kmDriven = Float.parseFloat(et_odometer.getText().toString()) - lastOdometer;

                        consumption = kmDriven / Float.parseFloat(et_liters.getText().toString());
                        label = "                                         ";
                        sequence = lastSequence + 1;
                        //sequence = 2;
                    }
                    //toastMessage("checkBox " + checkBox_newSequence.isChecked());




                    boolean isInserted = db.insertDataFillup(selectedID,
                                                             et_date.getText().toString(),
                                                             Float.parseFloat(et_odometer.getText().toString()),
                                                             kmDriven,
                                                             Float.parseFloat(et_liters.getText().toString()),
                                                             consumption, label, sequence);


                    if (isInserted){
                        Intent i = new Intent(Fillup_new.this, Vehicle_painel.class);
                        i.putExtra("ID",selectedID);
                        i.putExtra("name", selectedName);
                        startActivity(i);
                    }else{
                        Toast.makeText(Fillup_new.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                    }
                }




            }


        });

    }





    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }



    public void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}
