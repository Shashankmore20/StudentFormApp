package com.example.formusingsqlite2.data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHandler extends SQLiteOpenHelper {

    private static final String Database_Name = "student_details";
    private static final String Table_Name = "sdetailstable";
    private static final int DATABASE_VERSION = 1;
    private static final String Column_id = "id";
    private static final String Col_1 = "Roll_No";
    private static final String Col_2 = "Name";
    private static final String Col_3 = "Class";

    public MyDbHandler(Context context)
    {
        super(context, Database_Name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + Table_Name + " (\n " +
                " " + Column_id + " INTEGER NOT NULL CONSTRAINT sdetailstable_PK PRIMARY KEY AUTOINCREMENT, \n" +
                " " + Col_1 + " VARCHAR(100) NOT NULL, \n" +
                " " + Col_2 + " VARCHAR(100) NOT NULL, \n" +
                " " + Col_3 + " VARCHAR(100) NOT NULL \n" + ");";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

   public boolean addDetails(String roll, String name, String clas){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Col_1, roll);
        values.put(Col_2, name);
        values.put(Col_3, clas);

        return db.insert(Table_Name, null, values) != -1 ;
    }

    public Cursor ViewDetails(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + Table_Name, null);
    }

   public boolean updateDetails(int id, String roll, String name, String Class){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Col_1, roll);
        values.put(Col_2, name);
        values.put(Col_3, Class);
        return db.update(Table_Name, values, Column_id + "=?", new String[]{String.valueOf(id)}) > 0;
    }

   public  boolean deleteDetails(int id){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(Table_Name, Column_id + "=?", new String[]{String.valueOf(id)}) > 0;
    }
}