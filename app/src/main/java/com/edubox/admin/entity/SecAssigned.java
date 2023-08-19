package com.edubox.admin.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "secAssigned")
public class SecAssigned {

	@PrimaryKey(autoGenerate = true)
	private int id;
	private int aStatus=1,clsId,secId,aYearId;
	private String sId,stdId,aYear,secAId,feeTk;

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

	private String sectionId;
	public void setSectionId(String uniqueId){
		this.sectionId=uniqueId;
	}public String getSectionId(){
		return sectionId;
	}

	private String sessionId;
	public void setSessionId(String uniqueId){
		this.sessionId=uniqueId;
	}public String getSessionId(){
		return sessionId;
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
	}public void setclsId(int clsId){
		this.clsId = clsId;
	}public void setsecId(int secId){
		this.secId = secId;
	}public void setaYearId(int aYearId){
		this.aYearId = aYearId;
	}public void setsId(String sId){
		this.sId = sId;
	}public void setstdId(String stdId){
		this.stdId = stdId;
	}public void setaYear(String aYear){
		this.aYear = aYear;
	}public void setsecAId(String secAId){
		this.secAId = secAId;
	}public void setsecfeeTk(String feeTk) {
		this.feeTk = feeTk;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClsId() {
		return clsId;
	}

	public void setClsId(int clsId) {
		this.clsId = clsId;
	}

	public int getSecId() {
		return secId;
	}

	public void setSecId(int secId) {
		this.secId = secId;
	}

	public String getStdId() {
		return stdId;
	}

	public void setStdId(String stdId) {
		this.stdId = stdId;
	}

	public String getSecAId() {
		return secAId;
	}

	public void setSecAId(String secAId) {
		this.secAId = secAId;
	}

	public String getFeeTk() {
		return feeTk;
	}

	public void setFeeTk(String feeTk) {
		this.feeTk = feeTk;
	}

	public int getid() {
		return id;
	}public int getaStatus(){
		return aStatus;
	}public int getclsId(){
		return clsId;
	}public int getsecId(){
		return secId;
	}public int getaYearId(){
		return aYearId;
	}public String getsId(){
		return sId;
	}public String getstdId(){
		return stdId;
	}public String getaYear(){
		return aYear;
	}public String getsecAId(){
		return secAId;
	}public String getfeeTk() {
		return feeTk;
	}
	
	public String toString() {
		return secAId;
	}
	
	public static void main(String[] args) {


	}

}
