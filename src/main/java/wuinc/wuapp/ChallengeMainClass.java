package wuinc.wuapp;

import java.util.Date;

/**
 * Created by Paul on 10/06/2016.
 */
public class ChallengeMainClass {
    private String date;
    private User publisher;
    private String title;
    private String description;

    public ChallengeMainClass(User publisher, String title, String description) {
        this.publisher = publisher;
        this.title = title;
        this.description = description;
    }

    public User getPublisher() {
        return publisher;
    }
    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
