package com.alimasarweh.traingleuniversity.Users;

import com.alimasarweh.traingleuniversity.HelpingClasses.Course;

import java.util.List;

public interface UserWithCourses extends User{
    public void setCourses(List<Course> listOfCoutrses);
}
