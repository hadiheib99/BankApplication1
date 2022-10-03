package com.itworks.bankapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String OPERATION_TABLE = "OPERATION_TABLE";
    public static final String COLUMN_OPERATION_NAME = "OPERATION_NAME";
    public static final String COLUMN_OPERATION_AMOUNT = "OPERATION_AMOUNT";
    public static final String COLUMN_OPERATION_DESCRIPTION = "OPERATION_DSECRIPTION";
    public static final String COLUMN_OPERATION_IS_INCOME = "OPERATION_ISINCOME";
    public static final String COLUMN_DATE = "DATE";
    private static final String COLUMN_ID = "ID";
    Context context;
    public DataBaseHelper(@Nullable Context context) {
        super(context,"operation.db ", null, 1);
        this.context = context;
    }




    //this is called the first time a database is accessed. There should be code in here to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + OPERATION_TABLE + " ("+" ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_OPERATION_NAME + " TEXT, " + COLUMN_OPERATION_AMOUNT + " INTEGER, " + COLUMN_OPERATION_DESCRIPTION + " TEXT, " + COLUMN_OPERATION_IS_INCOME + " BOOL, " + COLUMN_DATE + " TEXT )";

        db.execSQL(createTableStatement);
    }

    //this is called if the database version number changes. It prevents previous users apps from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+ OPERATION_TABLE);
        onCreate(db);
    }


    public boolean addOne(Operation operation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv =new ContentValues();

        cv.put(COLUMN_OPERATION_NAME,operation.getName());
        cv.put(COLUMN_DATE,operation.getDate());
        cv.put(COLUMN_OPERATION_AMOUNT,operation.getAmount());
        cv.put(COLUMN_OPERATION_DESCRIPTION,operation.getDescription());
        cv.put(COLUMN_OPERATION_IS_INCOME,operation.isIncome());

        long insert = db.insert(OPERATION_TABLE, null, cv);

        if(insert ==-1){
            return false;
        }else {
            return true;
        }
    }


    public List<Operation> getAllOperation(){
        List<Operation> returnList =new ArrayList<>();

        String queryString ="SELECT * FROM " +OPERATION_TABLE;
        SQLiteDatabase db =this.getReadableDatabase();
       Cursor cursor= db.rawQuery(queryString,null);
            if(cursor.moveToFirst()){
                do{
                    int operationID = cursor.getInt(0);
                    String operationName = cursor.getString(1);
                    int operationAmount = cursor.getInt(2);
                    String operationDsec = cursor.getString(3);
                    boolean operationISINCOME = cursor.getInt(4) == 1;
                    String operationDate = cursor.getString(5);

                    Operation newOperation = new Operation(operationID,operationName,operationAmount,operationDsec,operationDate);
                    returnList.add(newOperation);


                }while (cursor.moveToNext());
            }else{
                //failure, do not add anything to the list
            }
            cursor.close();
            db.close();
       return returnList;
    }


    public ArrayList<Operation> getAllData(){
        ArrayList<Operation> arrayList = new ArrayList<>();

        String queryString ="SELECT * FROM " +OPERATION_TABLE;
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor= db.rawQuery(queryString,null);






        while (cursor.moveToNext())
        {
            String oper_name = cursor.getString(1);
            String oper_date = cursor.getString(5);
            int oper_amount = cursor.getInt(2);
           // boolean operisIncome = cursor.getInt(4) ==1 ? true:false;
            Operation newOperation = new Operation(oper_name,oper_amount,oper_date);

            arrayList.add(newOperation);

        }

        cursor.close();
        db.close();
        return arrayList;
    }

    void updateOperation(int id,String name,String desc,int amount,String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ID,id);
        values.put(COLUMN_OPERATION_NAME,name);
        values.put(COLUMN_OPERATION_AMOUNT,amount);
        values.put(COLUMN_OPERATION_DESCRIPTION,desc);
        values.put(COLUMN_DATE,date);

       long result= db.update(OPERATION_TABLE, values, "ID=?", new String[] {String.valueOf(id)});
        if (result == -1){
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }



    }







    public void deleteItem(Operation item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(OPERATION_TABLE, COLUMN_ID + " = ?",
                new String[]{String.valueOf(item.getId())});
        db.close();
    }


    public void deleteDataNew(int open_id) {
        SQLiteDatabase db = this.getWritableDatabase();
       // String[] whereArgs = {""+ open_id};
        //int i= db.delete(DataBaseHelper.OPERATION_TABLE, DataBaseHelper.COLUMN_ID + " = ?", open_id);
        int i = db.delete(OPERATION_TABLE, DataBaseHelper.COLUMN_ID  + "=?", new String[]{String.valueOf((open_id))});
        db.close();

    }
}
