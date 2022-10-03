package com.itworks.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Description extends AppCompatActivity {

     EditText operName;
     EditText operDesc;
     EditText operDate;
     EditText operAmount;
     RadioGroup radioGroup;
     RadioButton expanse;
     RadioButton income;
     Button saveButton;
     Button deleteButton;


    String name,description,date1;
    int id,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);


        operName = findViewById(R.id.Name);
        operDesc = findViewById(R.id.description);
        operDate = findViewById(R.id.dateOfOperation);
        operAmount = findViewById(R.id.amount);
        radioGroup  = findViewById(R.id.radioGroup);
        expanse = findViewById(R.id.expanseRadioButton);
        income = findViewById(R.id.incomeRadioButton);
        saveButton = findViewById(R.id.SaveButton);
        deleteButton=findViewById(R.id.deleteButton);

      //  dataBaseHelper = new DataBaseHelper(Description.this);
       // Operation clickedOperation = (Operation) getIntent().getSerializableExtra("KEY_NAME");



        operName.setText(name);
        operAmount.setText(String.valueOf((amount)));
        operDate.setText(date1);
        operDesc.setText(description);



//        Operation clickedOperation = (Operation) getIntent().getSerializableExtra("KEY_NAME");


        Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

            private void updateDate() {
                String myFormat = "dd/MM/yy"; //put your date format in which you need to display
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

                operDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        operDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Description.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });


        operDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Description.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        getIntentData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(Description.this);

                dataBaseHelper.updateOperation(id,name,description,amount,date1);

              //
                //  Operation operation = new Operation(id,operName.getText().toString(),Integer.parseInt(operAmount.getText().toString()),operDesc.getText().toString(),operDate.getText().toString());

                //dataBaseHelper.updateOperation(id,operName.getText().toString(),operDesc.getText().toString(),Integer.parseInt(operAmount.getText().toString()),operDate.getText().toString());


               /* Intent i = new Intent(Description.this,MainActivity.class);
                startActivity(i);
                finish();*/
            }
        });




    }
    void getIntentData(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name")&& getIntent().hasExtra("amount")&& getIntent().hasExtra("description")&& getIntent().hasExtra("date1")){
            id = getIntent().getIntExtra("id",0);
            name = getIntent().getStringExtra("name");
            amount = getIntent().getIntExtra("amount",0);
            description = getIntent().getStringExtra("description");
            date1 = getIntent().getStringExtra("date1");

            operName.setText(name);
            operAmount.setText(String.valueOf((amount)));
            operDate.setText(date1);
            operDesc.setText(description);



        }else{
            Toast.makeText(this, "NO DATA", Toast.LENGTH_SHORT).show();
        }
    }





}