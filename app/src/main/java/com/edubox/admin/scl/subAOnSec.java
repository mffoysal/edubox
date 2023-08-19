package com.edubox.admin.scl;

public class subAOnSec {

	private int id,aStatus=1,secId, subId;
	private String subAId, subFee,secFee, sId;
	private String subjectId;
	private String uniqueId;

	public void setUniqueId(String uniqueId){
		this.uniqueId=uniqueId;
	}public String getUniqueId(){
		return uniqueId;
	}
	public void setSubjectId(String subId){
		this.subjectId = subId;
	}public String getSubjectId(){
		return subjectId;
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
	}public void setaStatus(int aStatus) {
		this.aStatus = aStatus;
	}public void setsubAId(String subAId) {
		this.subAId = subAId;
	}public void setsecId(int secId) {
		this.secId = secId;
	}public void setsubId(int subId) {
		this.subId = subId;
	}public void setsubFee(String subFee) {
		this.subFee = subFee;
	}public void setsecFee(String secFee) {
		this.secFee = secFee;
	}public void setsId(String sId) {
		this.sId = sId;
	}
	
	public int getid() {
		return id;
	}public int getaStatus() {
		return aStatus;
	}public String getsubAId() {
		return subAId;
	}public int getsubId() {
		return subId;
	}public int getsecId() {
		return secId;
	}public String getsubFee() {
		return subFee;
	}public String getsecFee() {
		return secFee;
	}public String getsId() {
		return sId;
	}
	
	
	public String toString() {
		return subAId;
	}
	
	public static void main(String[] args) {

	}

}
