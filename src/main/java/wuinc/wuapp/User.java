package wuinc.wuapp;

import java.sql.Date;
import java.util.Random;

public class User {
	private Date date;
	private String pseudo;
	private String firstName;
	private String LastName;
	private String birthDate;
	private Boolean gender; //true for man

	private String UserEmail;
	private String password;
	
	private String description;
	
	private FriendsList friendsList;
	private Mailbox mailbox; //intern mailbox for friend requests and challenges

	//empty constructor
	public User(){
	}

	//constructor
	public User(String pseudo, String email, String password){
		new java.util.Date();
		this.pseudo = pseudo;
		this.firstName = "";
		this.LastName  = "";
		this.gender = false;

		this.UserEmail = email;
		this.password = password;

		this.description = "";
		this.friendsList = new FriendsList();
		this.mailbox = new Mailbox();
	}

	public String getPassword() {
		return password;
	}



	public String getPseudo() {
		return this.pseudo;
	}

	public String getUserEmail() {
		return UserEmail;
	}
	public boolean setUserEmail(String newMail) {
		Random r = new Random();
		Integer activationCode = r.nextInt(1000000);
		//activation code sent to the new email adress
		//check activation code with user : "prompt"
		String prompt = "";
		if (prompt.equals(activationCode.toString())) {
			this.UserEmail = newMail;
			return true;
		}else {
			return false;
		}
	}

	public void resetCode(){
		Random r = new Random();
		Integer activationCode = r.nextInt(1000000);
		//send email to reset code
		//procedure to set a new Code
	}
	public boolean setCode(String oldPassword, String newPassword) {
		if (this.password.equals(oldPassword)) { //check old password
			this.password = newPassword;
			return true;
		}else{
			return false;
		}
	}

	public String getFirstName() {
		return this.firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.LastName; }
	public void setLastName(String LastName) {
		this.LastName = LastName;
	}

	public String getBirthDate() {
		return this.birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public Boolean getGender() {
		return this.gender; }
	public void setGender(Boolean gender) {
		this.gender = gender;
	}
	
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description; 
	}
	
	public FriendsList getFriendsList() {
		return this.friendsList;
	}
	public void setFriendsList(FriendsList friendsList) {
		this.friendsList = friendsList;
	}
	
	public Mailbox getMailBox() {
		return this.mailbox;
	}
	public void setMailBox(Mailbox mailBox) {
		this.mailbox = mailBox;
	}

	public Mailbox getMailbox() {
		return mailbox;
	}
	public void setMailbox(Mailbox mailbox) {
		this.mailbox = mailbox;
	}
	
	
}
