package com.alimasarweh.traingleuniversity.MyListeners;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class SubmitGradesForStudent implements View.OnClickListener {

    private EditText StudentEmailET;
    private EditText gradeET;
    private SaveItemOnItemSelectedListener courseInfo;

    public SubmitGradesForStudent(EditText studentIdET, EditText gradeET, SaveItemOnItemSelectedListener courseInfo) {
        this.StudentEmailET = studentIdET;
        this.gradeET = gradeET;
        this.courseInfo = courseInfo;
    }

    @Override
    public void onClick(View view) {
        DatabaseReference db =FirebaseDatabase.getInstance().getReference();
        String studentIEmail = StudentEmailET.getText().toString();
        if(studentIEmail.isEmpty()||!studentIEmail.contains("@stu")) {
            Toast.makeText(view.getContext(),"Email empty or wrong format"
                    ,Toast.LENGTH_LONG);
            return;
        }
        double grade;
        try
        {
            grade = Double.parseDouble(gradeET.getText().toString());
        }
        catch(NumberFormatException nfe)
        {
            Toast.makeText(view.getContext(),"Grade empty or wrong format"
                    ,Toast.LENGTH_LONG);
            return;
        }
        grade = Math.min(100,grade);
        grade = Math.max(-1,grade);
        System.out.println(courseInfo.getSelectedItem());
        db.child("Courses Participants").child((courseInfo.getSelectedItem().split(",")[0])
                .substring(
                        "CourseId: ".length()))
                .child("Students")
                .child(
                        studentIEmail.replaceAll("[.]","|")
                ).setValue(grade);
        Toast.makeText(view.getContext(),"Successful"
                ,Toast.LENGTH_LONG);
    }
}
