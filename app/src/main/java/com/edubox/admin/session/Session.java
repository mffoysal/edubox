package com.edubox.admin.session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Session {

	private Connection con = null;
	private PreparedStatement pst = null, pstt=null, pstaY=null;
	private ResultSet rs = null, rsp=null;
	private String sql,sqlu,sqlaY;


	private String uId,sId,aYname,sYear,sMonth,eYear,eMonth,key;
	private int id=0,aStatus;
	private String sync_key;
	private int sync_status= 0;
	private String uniqueId;

	public Session(String selectASession) {
		this.aYname = selectASession;
	}

	public void setKey(String currentId) {
		this.key = currentId;
	}
	public String getKey() {
		return key;
	}
	public void setUId(String currentId) {
		this.uId = currentId;
	}
	public String getUId() {
		return uId;
	}
	public String getSId() {
		return sId;
	}
	public void setUniqueId(String uniqueId){
		this.uniqueId=uniqueId;
	}public String getUniqueId(){
		return uniqueId;
	}
	public int getSync_status() {
		return sync_status;
	}
	public void setSync_status(int id) {
		this.sync_status = id;
	}
	public String getSync_key() {
		return sync_key;
	}
	public void setSync_key(String sId) {
		this.sync_key = sId;
	}
	public Session() {
		
	}
	
	public void setId(int id) {
		this.id = id;
	}public void setSId(String sId) {
		this.sId = sId;
	}public void setaYname(String aYname) {
		this.aYname = aYname;
	}public void setsYear(String sYear) {
		this.sYear = sYear;
	}public void setsMonth(String sMonth) {
		this.sMonth = sMonth;
	}public void seteYear(String eYear) {
		this.eYear = eYear;
	}public void seteMonth(String eMonth) {
		this.eMonth = eMonth;
	}public void setaStatus(int aStatus) {
		this.aStatus = aStatus;
	}
	
	
	public int getId() {
		return id;
	}
	public int getaStatus() {
		return aStatus;
	}
	public String getaYname() {
		return aYname;
	}
	public String getsYear() {
		return sYear;
	}public String getsMonth() {
		return sMonth;
	}
	public String geteYear() {
		return eYear;
	}
	public String geteMonth() {
		return eMonth;
	}
	
	
	public String toString() {
		return aYname;
	}
	
	
	public static void main(String[] args) {


	}

}
