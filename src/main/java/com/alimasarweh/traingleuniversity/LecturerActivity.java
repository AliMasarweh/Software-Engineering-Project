package com.alimasarweh.traingleuniversity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alimasarweh.traingleuniversity.MyListeners.SaveItemOnItemSelectedListener;
import com.alimasarweh.traingleuniversity.MyListeners.SubmitGradesForStudent;
import com.alimasarweh.traingleuniversity.Users.Lecturer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LecturerActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer);

        final Lecturer[] thisLecturer = {(Lecturer) UserDataHolder.instance.getAppUser()};
        final TextView welcomeLecturer = (TextView) findViewById(R.id.welcomeLecturerTextView);
        final Spinner coursesIdSpinner = (Spinner)
                findViewById(R.id.lecturerActivityCoursesIdSpinner);
        final EditText StudentEmailET = (EditText) findViewById(R.id.lecturerActivitStudentEmailEditText);
        final EditText gradeET = (EditText) findViewById(R.id.lecturerActivityGradeEditText);
        final Button SubmitGradeB = (Button) findViewById(R.id.lecturerActivitySubmitGradeButton);
        //
        welcomeLecturer.setText("Welcome "+thisLecturer[0].getName());
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        final SaveItemOnItemSelectedListener CourseInfo = new SaveItemOnItemSelectedListener();
        coursesIdSpinner.setOnItemSelectedListener(CourseInfo);
        db.child("Courses").
                addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       for (DataSnapshot ds:dataSnapshot.getChildren()){
                           if(thisLecturer[0].courseAtById(ds.getKey()) != null){
                               double points = Double.parseDouble(ds.child("Points").getValue().toString());
                               String name = ds.child("Name").getValue().toString();
                               thisLecturer[0].setCourseAtById(ds.getKey(),name,points);
                           }
                       }

                       List<String> Courses = new ArrayList<>();
                       if(thisLecturer[0].getCourses() == null)
                           Courses.add("No Courses Available");
                       else for(int i = 0; i < thisLecturer[0].getCourses().size(); i++)
                           Courses.add(thisLecturer[0].getCourses().get(i).toStringWithoutGrades());
                       ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                               coursesIdSpinner.getContext()
                               ,R.layout.support_simple_spinner_dropdown_item, Courses);
                       coursesIdSpinner.setAdapter(spinnerAdapter);
                       //List of item selection
                       final SaveItemOnItemSelectedListener courseInfo = new SaveItemOnItemSelectedListener();
                       coursesIdSpinner.setOnItemSelectedListener(courseInfo);
                       courseInfo.setToFirst(spinnerAdapter);
                       SubmitGradeB.setOnClickListener(new SubmitGradesForStudent(StudentEmailET,gradeET,courseInfo));
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
    }
}
