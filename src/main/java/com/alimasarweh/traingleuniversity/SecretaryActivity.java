package com.alimasarweh.traingleuniversity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alimasarweh.traingleuniversity.HelpingClasses.Request;
import com.alimasarweh.traingleuniversity.Users.Secretary;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SecretaryActivity extends AppCompatActivity {
    private TextView welcome;
    private ListView requests;
    private Button accept;
    private Button reject;
    private EditText studentEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secretary);
        welcome = (TextView) findViewById(R.id.welcomeTextViewSecretaryActivity);
        requests = (ListView) findViewById(R.id.requestsSecretaryActivity);
        accept = (Button) findViewById(R.id.acceptButtonSecretaryActivity);
        reject = (Button) findViewById(R.id.rejectButtonSecretaryActivity);
        studentEmail = (EditText) findViewById(R.id.newUniversityEmailEditTextSecretaryActivity);
        final Secretary secrertary = (Secretary) UserDataHolder.instance.getAppUser();
        welcome.setText("Welcome "+secrertary.getName());

        final FirebaseAuth authenticate = FirebaseAuth.getInstance();
        final DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        final List<Request> listOfRequests = new ArrayList<>();
        final List<String> listOfSRequestsString = new ArrayList<>();


        db.child("Requests").getRef().child("Registration Requests")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds:dataSnapshot.getChildren()) {
                            Request req = new Request();
                            for (DataSnapshot requestInfo:ds.getChildren()){
                                if(requestInfo.getKey().equals("gpa"))
                                    req.setGPA(Double.parseDouble(
                                            requestInfo.getValue().toString()
                                    ));
                                else if(requestInfo.getKey().equals("fullName"))
                                    req.setFullName(requestInfo.getValue().toString());
                                else
                                    req.setPreviousEducation(requestInfo.getValue().toString());
                            }
                            listOfRequests.add(req);
                            listOfSRequestsString.add(req.toString());
                        }
                        secrertary.setRequests(listOfRequests);
                        final ArrayAdapter<String> adapterOfRequests = new ArrayAdapter<>(
                                requests.getContext()
                                ,R.layout.support_simple_spinner_dropdown_item
                                ,listOfSRequestsString);
                        accept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(adapterOfRequests.isEmpty()) {
                                    Toast.makeText(view.getContext(),"No requests"
                                            ,Toast.LENGTH_LONG).show();
                                    return;
                                }
                                String newEmail = studentEmail.getText().toString();
                                if(authenticate.isSignInWithEmailLink(newEmail)) {
                                    Toast.makeText(view.getContext(), "Such a mail exits"
                                            , Toast.LENGTH_LONG).show();
                                    return;
                                }
                                authenticate.createUserWithEmailAndPassword(newEmail,"123456");
                                String DeletedRequest = adapterOfRequests.getItem(0);
                                adapterOfRequests.remove(DeletedRequest);
                                requests.setAdapter(adapterOfRequests);
                                db.child("Students").
                                        child(newEmail.replaceAll("[.]","|"))
                                        .child("Name")
                                        .setValue(
                                                (DeletedRequest.split(",")[0])
                                                        .substring("Full Name: ".length())
                                        );
                                final String[] id = {"0"};
                                db.child("Requests").getRef().child("Registration Requests")
                                        .getRef().addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        id[0] = dataSnapshot.getChildren().iterator().next().getKey();
                                        db.child("Requests").getRef().child("Registration Requests")
                                                .getRef().child(id[0]).removeValue();
                                        //Update Student to Database
                                        Toast.makeText(accept.getContext(),"Approved",Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        });

                        reject.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(adapterOfRequests.isEmpty()) {
                                    Toast.makeText(view.getContext(),"No requests"
                                            ,Toast.LENGTH_LONG).show();
                                    return;
                                }
                                String SRequest = adapterOfRequests.getItem(0);
                                adapterOfRequests.remove(SRequest);
                                requests.setAdapter(adapterOfRequests);
                                final String[] id = {"0"};
                                db.child("Requests").getRef().child("Registration Requests")
                                        .getRef().addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        id[0] = dataSnapshot.getChildren().iterator().next().getKey();
                                        db.child("Requests").getRef().child("Registration Requests")
                                                .getRef().child(id[0]).removeValue();
                                        Toast.makeText(reject.getContext(),"Rejected",Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        requests.setAdapter(adapterOfRequests);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }
}
