package com.edubox.admin.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "section")
public class Section {

	@PrimaryKey(autoGenerate = true)
	private int id;
	private int aStatus=1,secNumStd=0;
	private String secName,sId,clsId,subId,sessionId,secCode,secFee,secTeaId;
	private String sync_key;
	private int sync_status= 0;
	private String uniqueId;
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

	public void setSubId(String subId){
		this.subId = subId;
	}public String getSubId(){
		return subId;
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
	public Section() {
		
	}
	
	public void setid(int id) {
		this.id= id;	
	}public void setaStatus(int aStatus) {
		this.aStatus = aStatus;
	}public void setsecNumStd(int secNumStd) {
		this.secNumStd = secNumStd;
	}public void setsecName(String secName) {
		this.secName = secName;
	}public void setsId(String sId) {
		this.sId = sId;
	}public void setclsId(String clsId) {
		this.clsId = clsId;
	}public void setsecCode(String secCode) {
		this.secCode = secCode;
	}public void setsecFee(String secFee) {
		this.secFee = secFee;
	}public void setsecTeaId(String secTeaId) {
		this.secTeaId = secTeaId;
	}

	public int getid() {
		return id;
	}public int getaStatus() {
		return aStatus;
	}public int getsecNumStd() {
		return secNumStd;
	}public String getsecName() {
		return secName;
	}public String getsId() {
		return sId;
	}public String getclsId() {
		return clsId;
	}public String getsecCode() {
		return secCode;
	}public String getsecFee() {
		return secFee;
	}public String getsecTeaId() {
		return secTeaId;
	}
	
	public String toString() {
		return secName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSecNumStd() {
		return secNumStd;
	}

	public void setSecNumStd(int secNumStd) {
		this.secNumStd = secNumStd;
	}

	public String getSecName() {
		return secName;
	}

	public void setSecName(String secName) {
		this.secName = secName;
	}

	public String getClsId() {
		return clsId;
	}

	public void setClsId(String clsId) {
		this.clsId = clsId;
	}

	public String getSecCode() {
		return secCode;
	}

	public void setSecCode(String secCode) {
		this.secCode = secCode;
	}

	public String getSecFee() {
		return secFee;
	}

	public void setSecFee(String secFee) {
		this.secFee = secFee;
	}

	public String getSecTeaId() {
		return secTeaId;
	}

	public void setSecTeaId(String secTeaId) {
		this.secTeaId = secTeaId;
	}

	public static void main(String[] args) {


	}

}
