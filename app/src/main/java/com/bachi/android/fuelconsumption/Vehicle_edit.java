package com.bachi.android.fuelconsumption;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Vehicle_edit extends Activity {

    private static final String TAG = "Vehicle_edit";

    DatabaseHelper myDb;
    MainActivity mainActivity;

    private Button btn_save, btn_delete;
    private EditText et_vehicle_name;
    private String selectedName;
    private int selectedID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_edit);

        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        btn_save = (Button) findViewById(R.id.btn_save);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        et_vehicle_name = (EditText) findViewById(R.id.et_vehicle_name);

        myDb = new DatabaseHelper(this);

        //get the intent extra from the MainActivity
        Intent receivedIntent = getIntent();

        //get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("ID", -1); //-1 is just the default value

        //get the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("name");
        //set the text to show the current selected name
        et_vehicle_name.setText(selectedName);






        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_vehicle_name.getText().toString();
                if (!name.equals("")){
                    myDb.updateName(name, selectedID);
                    //Intent i = new Intent(Vehicle_edit.this, Vehicle_painel.class);
                    Intent i = new Intent(Vehicle_edit.this, MainActivity.class);
                    i.putExtra("ID",selectedID);
                    i.putExtra("name", name);
                    startActivity(i);

                }else {
                    toastMessage("You must enter a name");
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.deleteName(selectedID, selectedName);
                myDb.deleteFillupByVehicle(selectedID);
                Intent MainActivity = new Intent(Vehicle_edit.this, MainActivity.class);

                startActivity(MainActivity);
            }
        });



    }




    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}


