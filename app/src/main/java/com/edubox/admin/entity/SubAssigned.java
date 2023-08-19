package com.edubox.admin.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subAssigned")
public class SubAssigned {

	@PrimaryKey(autoGenerate = true)
	private int id;
	private int aStatus=1,clsId,subId,secId;
	private String subAId,sId,subFee,stdId;

	private String sync_key;
	private int sync_status= 0;
	private String uniqueId;
	private String sessionId;
	private String subjectId;

	public void setUniqueId(String uniqueId){
		this.uniqueId=uniqueId;
	}public String getUniqueId(){
		return uniqueId;
	}

	public void setSessionId(String subId){
		this.sessionId = subId;
	}public String getSessionId(){
		return sessionId;
	}


	public void setSubjectId(String subId){
		this.subjectId = subId;
	}public String getSubjectId(){
		return subjectId;
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

	private String sectionId;
	public void setSectionId(String uniqueId){
		this.sectionId=uniqueId;
	}public String getSectionId(){
		return sectionId;
	}

	private String classId;
	public void setClassId(String uniqueId){
		this.classId=uniqueId;
	}public String getClassId(){
		return classId;
	}

	public void setid(int id) {
		this.id = id;
	}public void setaStatus(int aStatus){
		this.aStatus = aStatus;
	}public void setsubAId(String subAId){
		this.subAId = subAId;
	}public void setclsId(int clsId){
		this.clsId = clsId;
	}public void setsId(String sId){
		this.sId = sId;
	}public void setsubFee(String subFee){
		this.subFee = subFee;
	}public void setsubId(int subId){
		this.subId = subId;
	}public void setsecId(int secId){
		this.secId = secId;
	}public void setstdId(String stdId){
		this.stdId = stdId;
	}
	
	public int getid() {
		return id;
	}public int getaStatus(){
		return aStatus;
	}public String getsubAId(){
		return subAId;
	}public int getclsId(){
		return clsId;
	}public String getsId(){
		return sId;
	}public String getsubFee(){
		return subFee;
	}public int getsubId(){
		return subId;
	}public int getsecId(){
		return secId;
	}public String getstdId(){
		return stdId;
	}
	
	public String toString() {
		return subAId;
	}
	
	public static void main(String[] args) {


	}

}
