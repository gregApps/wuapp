package wuinc.wuapp;

import java.sql.Date;
import java.util.ArrayList;

public class Mail {
	
	private User sender;
	private String senderS;
	private ArrayList<User> recipients;
	private ArrayList<String> recipientsS;
	private String subject; 
	private String message;
	private String dateEnvoi;
	private String dateReception;
	private Integer id;
	
	public Mail(User sender, ArrayList<User> recipients){
		this.sender = sender;
		this.recipients = recipients;
		this.subject = "";
		this.message = "";
	}

	public String getSenderS() {
		return senderS;
	}

	public void setSenderS(String senderS) {
		this.senderS = senderS;
	}

	public ArrayList<String> getRecipientsS() {
		return recipientsS;
	}

	public void setRecipientsS(ArrayList<String> recipientsS) {
		this.recipientsS = recipientsS;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getSender() {
		return sender; }

	public ArrayList<User> getRecipients() {
		return this.recipients; }
	
	public void addRecipient(User newRecipient) {
		this.recipients.add(newRecipient);
	}
	public void removeRecipient(User recipient) {this.recipients.remove(recipient);}

	public String getSubject() {
		return subject; }
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message; }
	public void setMessage(String message) {
		this.message = message;
	}

	public String getDateEnvoi() {
		return this.dateEnvoi; }
	public void setDateEnvoi(String dateEnvoi) {
		this.dateEnvoi = dateEnvoi;
	}

	public String getDateReception() {
		return dateReception;  }
	public void setDateReception(String dateReception) {
		this.dateReception = dateReception;
	}
}
