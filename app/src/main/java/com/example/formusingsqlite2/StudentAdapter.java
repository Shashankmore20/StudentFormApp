package com.example.formusingsqlite2;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.formusingsqlite2.data.MyDbHandler;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<student> {
    Context mctx;
    int layoutRes;
    List<student> studentList;
    MyDbHandler mdb;
    public StudentAdapter(Context mctx, int layoutRes, List<student> studentList, MyDbHandler mdb){
        super(mctx, layoutRes, studentList);

        this.mctx = mctx;
        this.layoutRes = layoutRes;
        this.studentList = studentList;
        this.mdb = mdb;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mctx);

        View view = inflater.inflate(layoutRes, null);

        TextView textViewRoll = view.findViewById(R.id.RollNo);
        TextView textViewName = view.findViewById(R.id.Name);
        TextView textViewClass = view.findViewById(R.id.Class);

        student student = studentList.get(position);

        textViewRoll.setText(student.getRoll());
        textViewName.setText(student.getName());
        textViewClass.setText(student.getClas());

        view.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStudent(student);
            }
        });

        view.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDetails(student);
            }
        });

        return view;
    }
    private void updateDetails(student student) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mctx);

        LayoutInflater inflater = LayoutInflater.from(mctx);

        View view = inflater.inflate(R.layout.updatedetails, null);

        builder.setView(view);

       final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final EditText editRoll = view.findViewById(R.id.e_RollNo);
        final EditText editName = view.findViewById(R.id.e_Name);
        final EditText editClass = view.findViewById(R.id.e_Class);

        editRoll.setText(student.getRoll());
        editName.setText(student.getName());
        editClass.setText(student.getClas());


        view.findViewById(R.id.b_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String roll = editRoll.getText().toString().trim();
                String name = editName.getText().toString().trim();
                String Class = editClass.getText().toString().trim();

                if (editRoll.getText().toString().length() != 12) {
                    editRoll.setError("Roll number must contain 12 digits");
                    editRoll.requestFocus();
                    return;
                }
                if (editName.getText().toString().length() <= 2) {
                    editName.setError("Name cannot be less than 2 characters");
                    editName.requestFocus();
                    return;
                }
                if (editClass.getText().toString().length() <= 2) {
                    editClass.setError("Class cannot be less than 2 characters");
                    editClass.requestFocus();
                    return;
                }

                if (mdb.updateDetails(student.getId(), roll, name, Class)) {
                    Toast.makeText(mctx, "Student Details have been updated", Toast.LENGTH_SHORT).show();
                    loadStudentDetailsAgain();
                }
                else {
                    Toast.makeText(mctx, "Student Details are not updated", Toast.LENGTH_SHORT).show();
                }
                alertDialog.dismiss();
            }
        });
    }

    private void deleteStudent(student student){
        AlertDialog.Builder builder = new AlertDialog.Builder(mctx);

        builder.setTitle("Are you sure");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mdb.deleteDetails(student.getId()))
                    loadStudentDetailsAgain();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void loadStudentDetailsAgain() {

        Cursor cursor = mdb.ViewDetails();

        if(cursor.moveToFirst()) {
            studentList.clear();
            do {
                studentList.add(new student(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                ));
            } while (cursor.moveToNext());

            notifyDataSetChanged();
        }
    }
}
