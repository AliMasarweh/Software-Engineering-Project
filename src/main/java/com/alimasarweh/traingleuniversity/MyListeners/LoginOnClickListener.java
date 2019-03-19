package com.alimasarweh.traingleuniversity.MyListeners;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alimasarweh.traingleuniversity.HelpingClasses.Course;
import com.alimasarweh.traingleuniversity.HelpingClasses.Request;
import com.alimasarweh.traingleuniversity.LecturerActivity;
import com.alimasarweh.traingleuniversity.LoginActivity;
import com.alimasarweh.traingleuniversity.SecretaryActivity;
import com.alimasarweh.traingleuniversity.StudentActivity;
import com.alimasarweh.traingleuniversity.UserDataHolder;
import com.alimasarweh.traingleuniversity.Users.Lecturer;
import com.alimasarweh.traingleuniversity.Users.Secretary;
import com.alimasarweh.traingleuniversity.Users.Student;
import com.alimasarweh.traingleuniversity.Users.User;
import com.alimasarweh.traingleuniversity.Users.UserWithCourses;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class LoginOnClickListener implements View.OnClickListener, OnCompleteListener<AuthResult> {


    private EditText emailET;
    private EditText passwordET;
    private SaveItemOnItemSelectedListener userT;
    private User user;
    private Activity activity;
    private boolean taskIsSuccess;
    private ProgressDialog progess;
    private FirebaseAuth authenticated;
    private DatabaseReference db;
    private String email ;
    private String password;
    private Intent userIntent;

    public LoginOnClickListener(Activity act, EditText pass, EditText Id,
                                SaveItemOnItemSelectedListener selectedItemListener) {
        passwordET = pass;
        emailET =Id;
        userT = selectedItemListener;
        activity = act;
        email = "";
        password ="";
        authenticated = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        userIntent = null;
        taskIsSuccess = false;
        user = null;
    }

    @Override
    public void onClick(View view) {
        String userName = "";
        email = emailET.getText().toString();
        password = passwordET.getText().toString();
        if(email.equals("") || password.equals("")){
            Toast.makeText(activity,"Fill all the inputs",Toast.LENGTH_LONG);
            return ;
        }
        progess = new ProgressDialog(activity);
        progess.setMessage("Loggin in, please wait...");
        progess.show();
        if (userT.getSelectedItem().equals("Student")) {
            if (email.contains("@stu")) {
                authenticated.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(this);
            }
        } else if (userT.getSelectedItem().equals("Lecturer")) {
            userIntent = new Intent(view.getContext(), LecturerActivity.class);
            if (email.contains("@ltu")) {
                authenticated.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(this);
            }
        } else {
            System.out.println("Secretary");
            userIntent = new Intent(view.getContext(), SecretaryActivity.class);
            if(email.contains("@sectu")) {
                authenticated.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this);
            }
        }
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()) {
            taskIsSuccess = true;
            if(userT.getSelectedItem().equals("Student") || userT.getSelectedItem().equals("Lecturer")) {
                final int type = userT.getSelectedItem().equals("Student")?1:0;
                final UserWithCourses userWithCourses = type==1?new Student():new Lecturer();
                DatabaseReference databaseRef = null;
                if(type==1){
                    userIntent = new Intent(activity,StudentActivity.class);
                    databaseRef = db.child("Students");
                }else{
                    userIntent = new Intent(activity,LecturerActivity.class);
                    databaseRef = db.child("Lecturers");
                }
                user = userWithCourses;
                (userWithCourses).setEmail(email);
                (userWithCourses).setPass(password);
                databaseRef.child(email.replace(".", "|"))
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<String> courseId = new ArrayList<>();
                                List<String> grades = new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    if (ds.getKey().equals("Courses")) {
                                        for (DataSnapshot coursesGrades : ds.getChildren()) {
                                            courseId.add(coursesGrades.getValue().toString());
                                            grades.add("-1");
                                        }
                                    } else if (ds.getKey().equals("Name")) {
                                        (userWithCourses).setName(ds.getValue().toString());
                                    }
                                }
                                List<Course> coursesList = new ArrayList<>();
                                for (int i = 0; i < courseId.size(); i++) {
                                    coursesList.add(new Course(courseId.get(i),
                                            Integer.parseInt(grades.get(i))));
                                }
                                (userWithCourses).setCourses(coursesList);
                                progess.dismiss();
                                UserDataHolder.instance.setAppUser(user);
                                activity.startActivity(userIntent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                //activity.startActivity(userIntent);
            }
            else {
                System.out.println("In Progeress");
                final User user = new Secretary();
                DatabaseReference databaseRef = null;
                userIntent = new Intent(activity,SecretaryActivity.class);
                databaseRef = db.child("Secretaries");
                this.user = user;
                (this.user).setEmail(email);
                (this.user).setPass(password);
                databaseRef.child(email.replace(".", "|"))
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot ds:dataSnapshot.getChildren()){
                                    if(ds.getKey().equals("Name"))
                                        user.setName(ds.getValue().toString());
                                }
                                progess.dismiss();
                                UserDataHolder.instance.setAppUser(user);
                                activity.startActivity(userIntent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
            }
        }
        else{
            progess.dismiss();
            Toast.makeText(activity,"Failed",Toast.LENGTH_LONG).show();
        }
    }


}
