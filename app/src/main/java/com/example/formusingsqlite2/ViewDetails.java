package com.example.formusingsqlite2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import com.example.formusingsqlite2.data.MyDbHandler;

import java.util.ArrayList;
import java.util.List;

public class ViewDetails extends AppCompatActivity {

    MyDbHandler mdb;

    List<student> studentList;
    ListView listView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        mdb = new MyDbHandler(this);

        studentList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listview);

        loadStudentDetails();

    }

    private void loadStudentDetails(){

        Cursor cursor = mdb.ViewDetails();

        if(cursor.moveToFirst()){
            do {
                studentList.add(new student(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                ));
            }while(cursor.moveToNext());

            StudentAdapter adapter = new StudentAdapter(this, R.layout.card_view,studentList, mdb);
            listView.setAdapter(adapter);
        }
    }
}
