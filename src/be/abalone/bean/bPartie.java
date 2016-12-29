package be.abalone.bean;

import javax.websocket.Session;

public class bPartie {
	private int uid_partie = 0;
	private Session session_noir  = null;
	private Session session_blanc = null;
	
	public int getUid_partie() {
		return uid_partie;
	}
	public void setUid_partie(int uid_partie) {
		this.uid_partie = uid_partie;
	}
	public Session getSession_noir() {
		return session_noir;
	}
	public void setSession_noir(Session session_noir) {
		this.session_noir = session_noir;
	}
	public Session getSession_blanc() {
		return session_blanc;
	}
	public void setSession_blanc(Session session_blanc) {
		this.session_blanc = session_blanc;
	}
}
