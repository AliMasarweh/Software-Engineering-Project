package com.alimasarweh.traingleuniversity.Users;

import com.alimasarweh.traingleuniversity.HelpingClasses.Course;

import java.util.ArrayList;
import java.util.List;

public class Lecturer implements UserWithCourses {
    private String name;
    private String email;
    private String pass;
    private List<Course> courses;


    public Lecturer(){
        this.name = "";
        this.email = "";
        this.courses = new ArrayList<>();
        this.pass = "";
    }

    public Lecturer(String name, String Id, List<Course> courses,String pass){
        this.name = name;
        this.email = Id;
        this.courses = new ArrayList<>(courses);
        this.pass = pass;
    }

    public List<Course> getCourses(){
        return courses;
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

    @Override
    public void setCourses(List<Course> listOfCoutrses) {
        courses = new ArrayList<>(listOfCoutrses);
    }

    public Course courseAtById(String courseId){
        for (int i = 0; i < courses.size(); i++){
            if(courses.get(i).getId().equals(courseId))
                return courses.get(i);
        }
        return null;
    }

    public void setCourseAtById(String courseId, String name, double points) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getId().equals(courseId)) {
                courses.get(i).setCourseName(name);
                courses.get(i).setPoints(points);
            }
        }
    }
}
