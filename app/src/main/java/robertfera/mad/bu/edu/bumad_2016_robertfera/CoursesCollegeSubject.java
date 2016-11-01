package robertfera.mad.bu.edu.bumad_2016_robertfera;

/**
 * Created by rfera213 on 10/28/16.
 */

public class CoursesCollegeSubject {
    private String subject_name;
    private String prefixes;

    public CoursesCollegeSubject(String subject_name, String prefixes) {
        this.subject_name = subject_name;
        this.prefixes = prefixes;
    }

    public String getSubject_name() { return this.subject_name; }

    public String getPrefixes() { return this.prefixes; }
}
