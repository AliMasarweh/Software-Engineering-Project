package com.alimasarweh.traingleuniversity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alimasarweh.traingleuniversity.HelpingClasses.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    private Button registerButton;
    private EditText FullNameET;
    private EditText GPAET;
    private EditText previousEducationET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        registerButton = (Button) findViewById(R.id.registerButtonRegisterActivity);
        FullNameET = (EditText) findViewById(R.id.fullNameEditTextRegisterActivity);
        GPAET = (EditText) findViewById(R.id.GPAEditTextRegisterActivity);
        previousEducationET = (EditText) findViewById(R.id.previousEducationEditTextRegisterActivity);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = FullNameET.getText().toString();
                double gpa;
                try {
                    gpa = Double.parseDouble(GPAET.getText().toString());
                }
                catch(NumberFormatException nfe)
                {
                    Toast.makeText(view.getContext(),"Grade empty or wrong format"
                            ,Toast.LENGTH_LONG);
                    return;
                }
                String previousEducation = previousEducationET.getText().toString();
                if(fullName.isEmpty() || previousEducation.isEmpty()){
                    Toast.makeText(view.getContext(),"Fill all the informations, please!"
                            ,Toast.LENGTH_LONG);
                    return;
                }
                final Request request = new Request(fullName,gpa,previousEducation);
                //Register Student
                DatabaseReference db = FirebaseDatabase.getInstance().getReference()
                        .child("Requests").child("Registration Requests");
                final int[] id = {0};
                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds:dataSnapshot.getChildren()){
                            if(Integer.parseInt(ds.getKey()) != id[0])
                                break;
                            id[0]++;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                db.child(id[0]+"").setValue(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                            Toast.makeText(registerButton.getContext(),"Succesful",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(registerButton.getContext(),"Failed",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Succesful", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
