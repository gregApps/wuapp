package wuinc.wuapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Paul on 10/06/2016.
 */
public class Publication {
    private User user;
    private String userS;
    private String challenge;
    private String comment;

    private String pathVideoClip;
    private Integer Score;

    private String dateCreation;
    private ArrayList<String> userLike;
    private Integer id;

    public Publication(User user, String challenge, String comment, String pathVideoClip) {
        this.user = user;
        this.challenge = challenge;
        this.comment = comment;
        this.pathVideoClip = pathVideoClip;
        this.Score = 0;
        this.userLike = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        this.id = 0;

    }

    public String getUserS() {
        return userS;
    }

    public void setUserS(String userS) {
        this.userS = userS;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPathVideoClip() {
        return pathVideoClip;
    }
    public void setPathVideoClip(String pathVideoClip) {
        this.pathVideoClip = pathVideoClip;
    }

    public Integer getScore() {
        return Score;
    }
    public void upScore() {
        this.Score += 1;
    }
    public void downScore() {
        this.Score += -1;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public ArrayList<String> getUserLike() {
        return userLike;
    }

    public void setUserLike(ArrayList<String> userLike) {
        this.userLike = userLike;
    }

    public void setScore(Integer score) {
        Score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }
}
