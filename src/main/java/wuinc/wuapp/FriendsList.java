package wuinc.wuapp;

import java.util.ArrayList;

public class FriendsList {
		
	private String who;
	private ArrayList<String> friends;
	private Integer Nbfriends;
	
	public String getWho() {
		return who; }
	public void setWho(String who) {
		this.who = who;
	}
	
	public ArrayList<String> getFriends() {
		return this.friends; }
	public void setFriends(ArrayList<String> friends) {
		this.friends = friends;
	}
	
	public Integer getNbFriends() {
		return this.Nbfriends; }
	public void setNbFriends(Integer Nbfriends) {
		this.Nbfriends = Nbfriends;
	}
	
	public void addFriend(String friend){
		FriendsList.addInAlphOrder(friend, this.friends);
	}
	
	public void removeFriend(String friend){
		this.friends.remove(friend);
	}
	
	private static void addInAlphOrder(String elem, ArrayList<String> list){
		list.add(elem);
	}
	
	
	
}
