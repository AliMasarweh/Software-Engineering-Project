package com.alimasarweh.traingleuniversity.HelpingClasses;

public class Course {

    private String Id;
    private String CourseName;
    private double points;
    private double grade;

    public Course(String Id, String CourseName, double points){
        this.Id = Id;
        this.CourseName = CourseName;
        this.points = points;
        grade = -404.0;
    }

    public Course(String Id, double grade){
        this.Id = Id;
        this.CourseName = "";
        this.points = -1;
        this.grade = grade;
    }

    public Course(String Id, String CourseName, double points, double grade){
        this.Id = Id;
        this.CourseName = CourseName;
        this.points = points;
        this.grade = grade;
    }

    public String getId(){
        return  Id;
    }

    public String getCourseName(){
        return CourseName;
    }

    public String  toString(){
        String ans = "CourseId: "+Id+", Course Name: "+CourseName+", Points: "+points
            +", Grade: "+grade;
        return ans;
    }

    public  String toStringWithoutId(){
        String ans = "Course Name: "+CourseName+", Points: "+points +", Grade: "+grade;
        return ans;
    }

    public String toStringWithoutGrades() {
        return "CourseId: "+Id+", Course Name: "+CourseName+", Points: "+points;
    }

    public double getPoints() {
        return points;
    }

    public double getGrade() {
        return grade;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
