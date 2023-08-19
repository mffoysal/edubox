package com.edubox.admin.scl;

import com.edubox.admin.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class schoolDep {

	private Connection con = null;
	private PreparedStatement pst = null, pstt=null, pstaY=null;
	private ResultSet rs = null, rsp=null;
	private String sql,sqlu,sqlaY;
	
	private int id,mStatus=1;
	private String sId,mName,startId, key, endId,currentId,location, deanId, phone;
	private String sync_key;
	private int sync_status= 0;
	private String uniqueId;
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
	public schoolDep() {
		con = DBConnection.ConnectionDB();
	}

	
	public void setid(int id) {
		this.id = id;
	}public void setsId(String sId) {
		this.sId = sId;
	}public void setmStatus(int mStatus) {
		this.mStatus = mStatus;
	}public void setmName(String mName) {
		this.mName = mName;
	}public void setstartId(String startId) {
		this.startId = startId;
	}public void setendId(String endId) {
		this.endId = endId;
	}public void setcurrentId(String currentId) {
		this.currentId = currentId;
	}
	public void setLocation(String currentId) {
		this.location = currentId;
	}
	public String getLocation() {
		return location;
	}

	public void setKey(String currentId) {
		this.key = currentId;
	}
	public String getKey() {
		return key;
	}

	public void setDean(String currentId) {
		this.deanId = currentId;
	}
	public String getDean() {
		return deanId;
	}

	public void setPhone(String currentId) {
		this.phone = currentId;
	}
	public String getPhone() {
		return phone;
	}
	
	public int getid() {
		return id;
	}public int getmStatus() {
		return mStatus;
	}public String getmName() {
		return mName;
	}public String getstartId() {
		return startId;
	}public String getendId() {
		return endId;
	}public String getcurrentId() {
		return currentId;
	}public String getsId() {
		return sId;
	}
	
	public String toString() {
		return mName;
	}
	

	
	public static void main(String[] args) {


	}

}
