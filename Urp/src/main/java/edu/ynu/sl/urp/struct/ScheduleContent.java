package edu.ynu.sl.urp.struct;

import edu.ynu.sl.urp.util.Util;

import java.util.ArrayList;

/**
 * Created by ku on 2015/1/3.
 */
public class ScheduleContent {

    private String courseCode;
    private String courseName;
    private String courseCredit;

    private ArrayList<String> courseTime;
    private String courseSelected;
    private String courseCount;
    private String courseTeacher;
    private String courseRoom;
    private String courseExamTime;
    private String courseRemark;
    private String courseProperty;

    private int index = 0;
    private int week = 0;
    private int exam = 0;

    public ScheduleContent() {
        setCourseCode("");
        setCourseName("");
        setCourseTeacher("");
        setCourseCount("");
        setCourseSelected("");
        setCourseCredit("");
        setCourseRemark("");
        setCourseExamTime("");
        setCourseProperty("");
        setCourseRoom("");
        setCourseTime(new ArrayList<String>());
    }

    public void add(String c) {
        switch (index) {
            case 0:
                setCourseCode(c);
                break;
            case 1:
                setCourseName(c);
                break;
            case 2:
                if (checkNull(c))
                    setCourseCredit(c);
                break;
            case 3:
                getCourseTime().add(c);
                week++;
                if (week == 7) {
                    index++;
                }
                return;
            case 4:
                if (checkNull(c))
                    setCourseSelected(c);
                break;
            case 5:
                if (checkNull(c))
                    setCourseCount(c);
                break;
            case 6:
                if (checkNull(c))
                    setCourseTeacher(c);
                break;
            case 7:
                if (checkNull(c))
                    setCourseRoom(c);
                break;
            case 8:
                setCourseExamTime(getCourseExamTime() + c);
                exam++;
                if (exam == 2) {
                    index++;
                }

                return;
            case 9:
                if (checkNull(c))
                    setCourseRemark(c);
                break;
            case 10:
                if (checkNull(c))
                    setCourseProperty(c);
                break;
            case 11:
                if (checkNull(c))
                    setCourseProperty(c);
                break;
        }

        index++;
        Util.print(index + "");
    }

    private boolean checkNull(String c) {
        if (c.equals("null") || c == null)
            return false;
        return true;
    }

    public String toString() {

        String time = "";
        for (int i = 0; i < getCourseTime().size(); i++) {
            time = getCourseTime().get(i);
        }
        return "课程代码" + getCourseCode() + "\n"
                + "课程名称" + getCourseName() + "\n"
                + "课程学分" + getCourseCredit() + "\n"
                + "课程性质" + getCourseProperty() + "\n"
                + "已选" + getCourseSelected() + "\n"
                + "总数" + getCourseCount() + "\n"
                + "教师" + getCourseTeacher() + "\n"
                + "教室" + getCourseRoom() + "\n"
                + "考试时间" + getCourseExamTime() + "\n"
                + "上课时间" + time + "\n"
                + "备注" + getCourseRemark();
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(String courseCredit) {
        this.courseCredit = courseCredit;
    }

    public String getCourseProperty() {
        return courseProperty;
    }

    public void setCourseProperty(String courseProperty) {
        this.courseProperty = courseProperty;
    }


    public String getCourseSelected() {
        return courseSelected;
    }

    public void setCourseSelected(String courseSelected) {
        this.courseSelected = courseSelected;
    }

    public String getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(String courseCount) {
        this.courseCount = courseCount;
    }

    public String getCourseTeacher() {
        return courseTeacher;
    }

    public void setCourseTeacher(String courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    public String getCourseRoom() {
        return courseRoom;
    }

    public void setCourseRoom(String courseRoom) {
        this.courseRoom = courseRoom;
    }

    public String getCourseExamTime() {
        return courseExamTime;
    }

    public void setCourseExamTime(String courseExamTime) {
        this.courseExamTime = courseExamTime;
    }

    public String getCourseRemark() {
        return courseRemark;
    }

    public void setCourseRemark(String courseRemark) {
        this.courseRemark = courseRemark;
    }

    public ArrayList<String> getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(ArrayList<String> courseTime) {
        this.courseTime = courseTime;
    }
}
