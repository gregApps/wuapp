package wuinc.wuapp;

public class FriendRequest {
	
	private String sender;
	private String recipient;
	private String message;
	
	public FriendRequest(String sender, String recipient, String message){
		this.sender = sender;
		this.recipient = recipient;
		this.message = message;
	}

	public String getSender() {
		return sender; }

	public String getRecipient() {
		return recipient; }

	public String getMessage() {
		return message; }
	public void setMessage(String message) {
		this.message = message;
	}
		
	
}
