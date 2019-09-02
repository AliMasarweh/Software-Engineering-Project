package com.alimasarweh.traingleuniversity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alimasarweh.traingleuniversity.HelpingClasses.Course;
import com.alimasarweh.traingleuniversity.HelpingClasses.Request;
import com.alimasarweh.traingleuniversity.MyListeners.*;
import com.alimasarweh.traingleuniversity.Users.Lecturer;
import com.alimasarweh.traingleuniversity.Users.Secretary;
import com.alimasarweh.traingleuniversity.Users.Student;
import com.alimasarweh.traingleuniversity.Users.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText emailET;
    private EditText passwordET;
    private Spinner userType;
    private Button loginButton;
    private TextView register;
    private FirebaseAuth authenticated;
    private DatabaseReference db;
    final private List<String> items = Arrays.asList("Student","Lecturer","Secretary");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Initializing
        emailET = (EditText) findViewById(R.id.emailEditTextLoginActivity);
        passwordET = (EditText) findViewById(R.id.passwordEditTextLoginActivity);
        userType = (Spinner) findViewById(R.id.userTypeSpinnerLoginActivity);
        loginButton = (Button) findViewById(R.id.loginButtonLoginActivity);
        register = (TextView) findViewById(R.id.registerTextViewLoginActivity);
        authenticated = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();;
        //Building Spinner
        ArrayAdapter<String> userTypeAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,items);
        userType.setAdapter(userTypeAdapter);


        //Listeners
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRegisterIntent = new Intent(view.getContext(),RegisterActivity.class);
                startActivity(toRegisterIntent);
            }
        });
        final SaveItemOnItemSelectedListener userT = new SaveItemOnItemSelectedListener();
        userType.setOnItemSelectedListener(userT);

        /*loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String userName = "";
                final String email = emailET.getText().toString();
                final String password = passwordET.getText().toString();
                final User user;
                if (email.equals("") || password.equals("")) {
                    Toast.makeText(view.getContext(), "Fill all the inputs", Toast.LENGTH_LONG);
                    return;
                }
                final ProgressDialog progess = new ProgressDialog(view.getContext());
                progess.setMessage("Loggin in, please wait...");
                progess.show();
                if (userT.getSelectedItem().equals("Student")) {
                    if (email.contains("@stu")) {
                        authenticated.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            final Intent userIntent = new Intent(view.getContext(), StudentActivity.class);
                                            db.child("Students").child(email.replace(".", "|"))
                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            progess.dismiss();
                                                            User user = dataSnapshot.getValue(Student.class);
                                                            for(DataSnapshot dsp : dataSnapshot.getChildren()) {
                                                                if(dsp.getKey().equals("Name"))
                                                                    ((Student) user).setName(dsp.getValue().toString());
                                                            }
                                                            startActivity(userIntent);
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });
                                        }
                                        else {
                                            Toast.makeText(view.getContext(),"Unsuccessful",Toast.LENGTH_LONG);
                                        }
                                    }
                                });
                    }
                } else if (userT.getSelectedItem().equals("Lecturer")) {
                    Intent userIntent = new Intent(view.getContext(), LecturerActivity.class);
                    if (password.equals("123456")
                            && email.equals("niggaLec@tu.ac.il")) {
                        user = new Lecturer(userName
                                , email
                                , Arrays.asList(
                                new Course("1", "Software Engineering", 3.5)
                                , new Course("2", "Deep Learning", 3)));
                        UserDataHolder.instance.setAppUser(user);
                        view.getContext().startActivity(userIntent);
                    }
                } else {
                    Intent userIntent = new Intent(view.getContext(), SecretaryActivity.class);
                    if (password.equals("123456")
                            && email.equals("niggaSec@tu.ac.il")) {
                        user = new Secretary(userName
                                , email
                                , Arrays.asList(new Request("nigga"
                                        , 99
                                        , "High School")
                                , new Request("Omeveveve Onentenyn vovwa ovevemev osass"
                                        , 85
                                        , "BSC")));
                        UserDataHolder.instance.setAppUser(user);
                        view.getContext().startActivity(userIntent);
                    }
                }
                Snackbar.make(view, "Unauthorized user", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        loginButton.setOnClickListener(new LoginOnClickListener(this, passwordET,emailET, userT));
    }
}
