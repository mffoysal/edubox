package com.edubox.admin.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subAonSec")
public class SubAOnSec {

	@PrimaryKey(autoGenerate = true)
	private int id;
	private int aStatus=1,secId, subId;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSecId() {
		return secId;
	}

	public void setSecId(int secId) {
		this.secId = secId;
	}

	public int getSubId() {
		return subId;
	}

	public void setSubId(int subId) {
		this.subId = subId;
	}

	public String getSubAId() {
		return subAId;
	}

	public void setSubAId(String subAId) {
		this.subAId = subAId;
	}

	public String getSubFee() {
		return subFee;
	}

	public void setSubFee(String subFee) {
		this.subFee = subFee;
	}

	public String getSecFee() {
		return secFee;
	}

	public void setSecFee(String secFee) {
		this.secFee = secFee;
	}

	public String toString() {
		return subAId;
	}
	
	public static void main(String[] args) {

	}

}
