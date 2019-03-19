package com.alimasarweh.traingleuniversity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alimasarweh.traingleuniversity.HelpingClasses.Course;
import com.alimasarweh.traingleuniversity.Users.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        // Views of Student Activity
        final TextView welcomeTextView = (TextView) findViewById(R.id.welcomeStudentActivityTextView);
        final ListView gradesList = (ListView) findViewById(R.id.showStudentGradesListView);
        final List<String> listOfStudentGrades = new ArrayList<>();
        final DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        final Student[] thisStudent = {(Student) UserDataHolder.instance.getAppUser()};
        welcomeTextView.setText("Welcome " + thisStudent[0].getName());
        final List<Course> courses = thisStudent[0].getCourses();
        db.child("Courses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot coursesIds:dataSnapshot.getChildren()){
                    if(thisStudent[0].courseAtById(coursesIds.getKey()) != null){
                        double points = Double.parseDouble(coursesIds.child("Points").getValue().toString());
                        String name = coursesIds.child("Name").getValue().toString();
                        thisStudent[0].setCourseAtById(coursesIds.getKey(),name,points);
                    }
                }

                db.child("Courses Participants")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot coursesIds:dataSnapshot.getChildren()){
                                    if(thisStudent[0].courseAtById(coursesIds.getKey())!=null){
                                        DataSnapshot students = coursesIds.getChildren()
                                                .iterator().next();
                                        for (DataSnapshot emails:
                                                students.getChildren()){
                                            if(thisStudent[0].getEmail().equals(
                                                    emails.getKey().replaceAll("[|]","."))){

                                                thisStudent[0].setGradeAtById(
                                                        coursesIds.getKey()
                                                        ,Double.parseDouble(emails.getValue().toString()
                                                        ));
                                            }
                                        }
                                    }
                                }
                                for(Course c:thisStudent[0].getCourses()) {
                                    listOfStudentGrades.add(c.toStringWithoutId());
                                }


                                ArrayAdapter<String> adapterOfStudentGradesList = new ArrayAdapter<>(gradesList.getContext()
                                        ,R.layout.support_simple_spinner_dropdown_item,
                                        listOfStudentGrades);
                                gradesList.setAdapter(adapterOfStudentGradesList);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
