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
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;


public class InsertActivity extends AppCompatActivity {
    EditText nameOfOperation;
    EditText dateOfOperation;
    EditText amount;
    EditText description;
    RadioGroup radioGroup;
    RadioButton expanse;
    RadioButton income;
    Button addButton;
    Button backButton;
    boolean inOrEX;
    private RadioButton radioButton;


    public void editTextClean(){
            nameOfOperation.setText("");
            dateOfOperation.setText("");
            amount.setText("");
            description.setText("");
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        IntegerWraper isIncome =  new IntegerWraper();
        isIncome.setValue(1);
        //isIncome.orElse(1);

        nameOfOperation = findViewById(R.id.nameOfOperation);
        dateOfOperation = findViewById(R.id.dateOfOperation);
        amount = findViewById(R.id.amount);
        description = findViewById(R.id.description);
        expanse = findViewById(R.id.expanseRadioButton);
        income = findViewById(R.id.incomeRadioButton);
        addButton = findViewById(R.id.AddButton);
        backButton = findViewById(R.id.BackButton);
        radioGroup = findViewById(R.id.radioGroup);




        boolean expenseChecked = expanse.isChecked();
        boolean incomeChecked = income.isChecked();

        int selectedId = radioGroup.getCheckedRadioButtonId();

        radioButton = (RadioButton) findViewById(selectedId);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(checkedId==R.id.expanseRadioButton){
                        isIncome.setValue(-1);
                    }else{
                        isIncome.setValue(1);
                    }
                }
            });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Operation operation = new Operation(-1,nameOfOperation.getText().toString(),amount.getText().toString(),description.getText().toString(),expanse.isEnabled(),dateOfOperation.toString());

                Intent goBack = new Intent(InsertActivity.this, MainActivity.class);
                startActivity(goBack);
            }
        });








        //date pickear
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

                dateOfOperation.setText(sdf.format(myCalendar.getTime()));
            }
        };

        dateOfOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(InsertActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });


        dateOfOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(InsertActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Operation operation;
                try {
                    operation = new Operation(-1, nameOfOperation.getText().toString(),Integer.parseInt(amount.getText().toString())*isIncome.getValue() , description.getText().toString(), dateOfOperation.getText().toString());

                    Toast.makeText(InsertActivity.this, "the operation is saved " + operation.toString(), Toast.LENGTH_LONG).show();
                    editTextClean();
                }catch (Exception e){
                    Toast.makeText(InsertActivity.this, "Error creating customer" , Toast.LENGTH_SHORT).show();
                    operation = new Operation(-1,"error", -1, "eror", "date error");


                }



                DataBaseHelper dataBaseHelper = new DataBaseHelper(InsertActivity.this);
                boolean success =dataBaseHelper.addOne(operation);
                Toast.makeText(InsertActivity.this, "Success= "+success, Toast.LENGTH_LONG).show();
            }
        });



    }
}