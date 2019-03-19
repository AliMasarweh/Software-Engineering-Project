package com.alimasarweh.traingleuniversity.Users;

import com.alimasarweh.traingleuniversity.HelpingClasses.Course;

import java.util.ArrayList;
import java.util.List;

public class Student implements UserWithCourses {
    private List<Course> Courses;
    private String Name;
    private String pass;
    private String email;

    public Student(List<Course> courses,String name, String pass,String email) {
        this.Name = name;
        this.email = email;
        this.Courses = courses;
        this.pass = pass;
    }

    public Student(String name, String pass,String email) {
        this.Name = name;
        this.email = email;
        this.Courses = new ArrayList<>();
    }

    public Student(){
        Name = "";
        email = "";
        Courses = new ArrayList<>();
    }

    public void addCourse(Course course){
        Courses.add(course);
    }

    public List<Course> getCourses(){
        return  Courses;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCourses(List<Course> courses) {
        this.Courses = courses;
    }

    public Course courseAtById(String courseId){
        for (int i = 0; i < Courses.size(); i++){
            if(Courses.get(i).getId().equals(courseId))
                return Courses.get(i);
        }
        return null;
    }

    public void setCourseAtById(String courseId, String name, double points) {
        for (int i = 0; i < Courses.size(); i++) {
            if (Courses.get(i).getId().equals(courseId)) {
                Courses.get(i).setCourseName(name);
                Courses.get(i).setPoints(points);
            }
        }
    }

    public void setGradeAtById(String courseId, double grade){
        for (int i = 0; i < Courses.size(); i++) {
            if (Courses.get(i).getId().equals(courseId)) {
                Courses.get(i).setGrade(grade);
            }
        }
    }
}
