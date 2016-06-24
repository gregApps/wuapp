package wuinc.wuapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
/**
 * Created by Paul on 10/06/2016.
 */
public class Challenge_1vEverybody extends ChallengeMainClass{

    private ArrayList<User> ChallengersList;

    private ArrayList<Publication> PublicationsList;

    public Challenge_1vEverybody(User publisher, String title, String description) {
        super(publisher, title, description);
        this.ChallengersList = null;
        this.PublicationsList = null;
    }

    public void setChallengersList(ArrayList<User> challengersList) {
        ChallengersList = challengersList;
    }

    public void setPublicationsList(ArrayList<Publication> publicationsList) {
        PublicationsList = publicationsList;
    }

    public User getPublisher() {
        return super.getPublisher();
    }
    public void setPublisher(User publisher) {
        super.setPublisher(publisher);
    }

    public String getTitle() {
        return super.getTitle();
    }
    public void setTitle(String title) {
        super.setTitle(title);
    }

    public String getDescription() {
        return super.getDescription();
    }
    public void setDescription(String description) {
        super.setDescription(description);
    }

    public ArrayList<User> getChallengers() {
        return this.ChallengersList;
    }
    public void addChallenger(User newChallenger) {
        this.ChallengersList.add(newChallenger);
    }
    public void removeChallenger(User Challenger) {
        this.ChallengersList.remove(Challenger);
    }

    public ArrayList<Publication> getPublicationsList() {
        return this.PublicationsList;
    }
    public void addPublication(Publication newPost) {
        this.PublicationsList.add(newPost);
    }
    public void removePublication(Publication Post) {
        this.PublicationsList.remove(Post);
    }




}
