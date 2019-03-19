package com.alimasarweh.traingleuniversity.HelpingClasses;

public class Request {
    private String FullName;
    private double GPA;
    private String PreviousEducation;

    public Request(String fullName, double GPA, String previousEducation){
        this.FullName = fullName;
        this.GPA = GPA;
        this.PreviousEducation = previousEducation;
    }

    public Request() {
        FullName = "";
        GPA = -1;
        PreviousEducation = "";
    }

    public String getPreviousEducation() {
        return PreviousEducation;
    }

    public double getGPA() {
        return GPA;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        this.FullName = fullName;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    public void setPreviousEducation(String previousEducation) {
        this.PreviousEducation = previousEducation;
    }

    public String toString(){
        String ans = "Full Name: "+FullName+", GPA: "+GPA+", Previous Education: "+PreviousEducation;
        return ans;
    }
}
