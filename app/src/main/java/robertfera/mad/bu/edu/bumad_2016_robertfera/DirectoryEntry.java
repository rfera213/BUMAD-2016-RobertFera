package robertfera.mad.bu.edu.bumad_2016_robertfera;

/**
 * Created by rfera213 on 11/3/16.
 */

public class DirectoryEntry {
    private String fname;
    private String lname;
    private String fullname;
    private String title;
    private String department;
    private String phone;
    private String address;
    private String email;

    public DirectoryEntry(String fname, String lname, String title, String department, String phone, String address, String email) {
        this.fname = fname;
        this.lname = lname;
        this.title = title;
        this.department = department;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public String getFname() { return this.fname; }

    public String getLname() { return this.lname; }

    public String getFullname() { return this.fname + " " + this.lname; }

    public String getTitle() { return this.title; }

    public String getDepartment() { return this.department; }

    public String getPhone() { return this.phone; }

    public String getAddress() { return this.address; }

    public String getEmail() { return this.email; }
}
