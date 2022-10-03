package com.itworks.bankapplication;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.ipsec.ike.exceptions.IkeNetworkLostException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button addButton;
    Button calendar ;
    TextView balance ;
    ListView operationList;
    ArrayAdapter operationArrayAdapter;
    ArrayList<Operation> arrayList;
    CustomAdapter myAdapter;

    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d(TAG,"onActivityResult ");
                    recreate();

                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         addButton = findViewById(R.id.addButton);
         calendar = findViewById(R.id.calendarButton);
         balance = findViewById(R.id.balance);
         operationList = findViewById(R.id.operationList);
        calendar = findViewById(R.id.calendarButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InsertActivity.class);
                startActivity(intent);

            }
        });

        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

       // final SimpleCursorAdapter simpleCursorAdapter = dataBaseHelper.getAllData();
        extracted(dataBaseHelper);
calendar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(MainActivity.this,Description.class);
        activityLauncher.launch(i);

    }
});
        operationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Operation clickedOperation = (Operation) parent.getItemAtPosition(position);
              int open_id = Operation.getId();
               /* dataBaseHelper.deleteDataNew(open_id);
                myAdapter.deleteItem(position);
                arrayList =dataBaseHelper.getAllData();
                myAdapter = new CustomAdapter(MainActivity.this,arrayList);
                operationList.setAdapter(myAdapter);*/
                //myAdapter.deleteItem(clickedOperation);

                Intent i = new Intent(MainActivity.this,Description.class);
                i.putExtra("id",open_id);
                i.putExtra("name",clickedOperation.getName());
                i.putExtra("amount",clickedOperation.getAmount());
                i.putExtra("description",clickedOperation.getDescription());
                i.putExtra("date1",clickedOperation.getDate());


                activityLauncher.launch(i);





            }
        });
        extracted(dataBaseHelper);


    }

    private void extracted(DataBaseHelper dataBaseHelper) {
        arrayList = dataBaseHelper.getAllData();
        myAdapter = new CustomAdapter(MainActivity.this,this,MainActivity.this,arrayList);
        operationList.setAdapter(myAdapter);
    }
}



/*
    private void openDetails(String type){
        Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
        intent.putExtra(getString(R.string.friend_name_key),nameEditText.getText().toString());

        intent.putExtra(getString(R.string.type_key),type);
        startActivity(intent);
    }*/