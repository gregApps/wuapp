package wuinc.wuapp;

import java.util.ArrayList;

public class Mailbox {
	
	private ArrayList<FriendRequest> requests;
	private ArrayList<Mail> inbox;
	private ArrayList<Mail> trash;
	
	public Mailbox(){
		this.inbox = null;
		this.trash = null;
		this.requests = null;
	}
	
	public ArrayList<Mail> getInbox() {
		return this.inbox; }
	public void setInbox(ArrayList<Mail> inbox) {
		this.inbox = inbox;
	}
	
	public ArrayList<Mail> getTrash() {
		return this.trash; }
	public void setTrash(ArrayList<Mail> trash) {
		this.trash = trash;
	}

	public ArrayList<FriendRequest> getRequests() {
		return this.requests;}
	public void setRequests(ArrayList<FriendRequest> requests) {
		this.requests = requests;
	}
	
	public void Update(){
	}
	
	public void SendMessage(){
	}
}
