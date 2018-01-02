package com.bachi.android.fuelconsumption;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class Vehicle_new extends AppCompatActivity {
    DatabaseHelper MyDb;
    EditText et_name;
    Button btn_save;
    Button btn_ver;
    Button btn_verCarros;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_new);

        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        MyDb = new DatabaseHelper(this);

        et_name = (EditText)findViewById(R.id.et_name);
        btn_save = (Button)findViewById(R.id.btn_save_vehicle);
        //btn_ver = (Button)findViewById(R.id.btn_ver);
        //btn_verCarros = (Button)findViewById(R.id.btn_verCarros);
        AddData();
        //ver();
        //verCarros();
    }

    public void AddData(){
        btn_save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Cursor checkName = MyDb.checkName(et_name.getText().toString());



                        if(et_name.getText().toString().equals("")){
                            showMessage("Please", "Insert a name");

                        }else if(checkName.getCount() == 1 ) {
                            showMessage("Please", "Name alredy exists");
                        }else{
                       boolean isInserted =  MyDb.insertData(et_name.getText().toString());

                        if(isInserted){

                        Intent i = new Intent(Vehicle_new.this, MainActivity.class);
                        startActivity(i);}
                        else {
                            Toast.makeText(Vehicle_new.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                        }}

                    }
                }
        );
    }

    public void ver(){
        btn_ver.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor result = MyDb.getAll();//select * from table FILLUP
                        if (result.getCount() == 0){
                            showMessage("Error", "Nothing found");
                            return;
                        }
                            StringBuffer buffer = new StringBuffer();
                            while (result.moveToNext()){
                                buffer.append("fillup ID: " + result.getString(0)+ "\n");
                                buffer.append("vehicle ID: " + result.getString(1)+ "\n");
                                buffer.append("Date: " + result.getString(2)+ "\n");
                                buffer.append("ODOMETER: " + result.getString(3)+ "\n");
                                buffer.append("KM: " + result.getString(4)+ "\n");
                                buffer.append("liter: " + result.getString(5)+ "\n");
                                buffer.append("consumption: " + result.getString(6)+ "\n\n");
                            }
                            showMessage("Data",buffer.toString());


                         /*FILLUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                         + FILLUP_VEHICLE_ID + " INT,"
                                                         + FILLUP_DATE + " DATE,"
                                                         + FILLUP_ODOMETER + " INT,"
                                                         + FILLUP_KM + " REAL,"
                                                         + FILLUP_LITER + " REAL,"
                                                         + FILLUP_CONSUMPTION + " REAL)");
                        */


                    }
                }
        );
    }

    public void verCarros(){
        btn_verCarros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = MyDb.getAllData();//select * from table VEHICLE_TABLE
                if(result.getCount() == 0){
                    showMessage("error", "Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (result.moveToNext()){
                    buffer.append("ID: " + result.getString(0) + "\n");
                    buffer.append("Name: " + result.getString(1) + "\n\n");
                }
                showMessage("Data",buffer.toString());
            }
        });}


    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }
}
