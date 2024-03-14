package com.example.formusingsqlite2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.formusingsqlite2.data.MyDbHandler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editName, editRoll, editClass;

    MyDbHandler mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mdb = new MyDbHandler(this);

        editRoll= (EditText) findViewById(R.id.e_RollNo);
        editName = (EditText)  findViewById(R.id.e_Name);
        editClass = (EditText) findViewById(R.id.e_Class);

        findViewById(R.id.b_submit).setOnClickListener(this);
        findViewById(R.id.b_View).setOnClickListener(this);
    }


    public void AddData() {
        String Rollno = editRoll.getText().toString().trim();
        String Name = editName.getText().toString().trim();
        String Class = editClass.getText().toString().trim();

        if(editRoll.getText().toString().length() != 12){
            editRoll.setError("Roll number must contain 12 digits");
            editRoll.requestFocus();
            return;
        }
        if(editName.getText().toString().length() <= 2){
            editName.setError("Name cannot be less than 2 characters");
            editName.requestFocus();
            return;
        }
        if(editClass.getText().toString().length() <= 2){
            editClass.setError("Class cannot be less than 2 characters");
            editClass.requestFocus();
            return;
        }

        if (mdb.addDetails(Rollno, Name, Class))
            Toast.makeText(this, "Student data has been successfully added", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Student data has not been added", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.b_submit:
                AddData();

                break;
            case R.id.b_View:
                startActivity(new Intent(this, ViewDetails.class));
                break;
        }

    }
}