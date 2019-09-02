package com.alimasarweh.traingleuniversity.Users;

import com.alimasarweh.traingleuniversity.HelpingClasses.Request;

import java.util.ArrayList;
import java.util.List;

public class Secretary implements User {
    private String name;
    private String email;
    private String pass;
    private List<Request> requests;

    public Secretary(String name,String Id, List<Request> requests,String pass){
        this.name = name;
        this.email = Id;
        this.requests = requests;
        this.pass = pass;
    }

    public Secretary() {
        this.name = "";
        this.email = "";
        this.requests = new ArrayList<>();
        this.pass = "";
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPass() {
        return pass;
    }

    @Override
    public void setName(String st) {
        name = st;
    }

    @Override
    public void setEmail(String mail) {
        this.email = mail;
    }

    @Override
    public void setPass(String pass) {
        this.pass = pass;
    }

}
