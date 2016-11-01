package robertfera.mad.bu.edu.bumad_2016_robertfera;

/**
 * Created by rfera213 on 10/30/16.
 */

public class Course {
    private String title;
    private String college;
    private String department;
    private String course_num;
    private String together;
    private String description;

    public Course(String title, String college, String department, String course_num, String description) {
        this.title = title;
        this.college = college;
        this.department = department;
        this.course_num = course_num;
        this.description = description;
    }

    public String getTitle() { return this.title; }

    public String getCollege() { return this.college; }

    public String getDepartment() { return this.department; }

    public String getCourse_num() { return this.course_num; }

    public String getTogether() { return this.college + " " + this.department + " " + this.course_num; }

    public String getDescription() { return this.description; }
}
