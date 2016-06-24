package wuinc.wuapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Paul on 10/06/2016.
 */
public class Challenge_1v1 extends ChallengeMainClass{
    private Publication Proposal;

    private User Challenger;
    private Publication Answer;

    public Challenge_1v1(User publisher, String title, String description) {
        super(publisher, title, description);
        this.Proposal = null;
        this.Challenger = null;
        this.Answer = null;
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

    public Publication getProposal() {
        return this.Proposal;
    }
    public void setProposal(Publication newProposal) {
        this.Proposal = newProposal;
    }

    public User getChallenger() {
        return Challenger;
    }
    public void setChallenger(User newChallenger) {
        this.Challenger = newChallenger;
    }

    public Publication getAnswer() {
        return this.Answer;
    }
    public void setAnswer(Publication newAnswer) {
        this.Answer = newAnswer;
    }

}

