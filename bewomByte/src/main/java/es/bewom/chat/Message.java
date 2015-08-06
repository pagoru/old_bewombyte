package es.bewom.chat;

import java.util.UUID;

import com.google.gson.annotations.Expose;

public class Message {
	
	@Expose
	private String hour;
	@Expose
	private String user;
	@Expose
	private UUID uuid;
	@Expose
	private String message;
	
	public Message builder(){
		this.uuid = null;
		this.hour = null;
		this.message = null;
		return this;
	}
	public Message hour(String d){
		this.hour = d;
		return this;
	}
	public Message user(String u){
		this.user = u;
		return this;
	}
	public Message uuid(UUID u){
		this.uuid = u;
		return this;
	}
	public Message message(String m){
		this.message = m.toString();
		return this;
	}
	public void build(){}
	
	public String gethour() {
		return hour;
	}
	public String getMessage() {
		return message;
	}
	public UUID getUuid() {
		return uuid;
	}
	public String getUser() {
		return user;
	}
	
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	public void setHour(String date) {
		this.hour = date;
	}
	public void setMessage(String message) {
		this.message = message.toString();
	}
	public void setUser(String user) {
		this.user = user;
	}

}
