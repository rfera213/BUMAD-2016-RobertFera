package robertfera.mad.bu.edu.bumad_2016_robertfera;

/**
 * Created by rfera213 on 11/9/16.
 */

public class CalendarEvent {
    private String id;
    private String summary;
    private String time;
    private String displayDate;
    private String displayTime;
    private String description;
    private String openTo;
    private String phone;
    private String email;
    private String location;
    private String registration_url;
    private String url; // more info

    public CalendarEvent(String id, String summary, String time) {
        this.id = id;
        this.summary = summary;
        this.time = time;
    }

    public CalendarEvent(String id, String summary, String displayDate, String displayTime, String description,
                         String openTo, String phone, String email, String location, String registration_url, String url) {
        this.id = id;
        this.summary = summary;
        this.displayDate = displayDate;
        this.displayTime = displayTime;
        this.description = description;
        this.openTo = openTo;
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.registration_url = registration_url;
        this.url = url;
    }

    public String getId() { return this.id; }

    public String getSummary() { return this.summary; }

    public String getTime() { return this.time; }

    public String getDisplayDate() { return this.displayDate; }

    public String getDisplayTime() { return this.displayTime; }

    public String getDescription() { return this.description; }

    public String getOpenTo() { return this.openTo; }

    public String getPhone() { return this.phone; }

    public String getEmail() { return this.email; }

    public String getLocation() { return this.location; }

    public String getRegistration_url() { return this.registration_url; }

    public String getUrl() { return this.url; }
}
