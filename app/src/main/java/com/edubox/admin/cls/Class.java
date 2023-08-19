package com.edubox.admin.cls;

import java.io.Serializable;

public class Class implements Serializable {

	private int id, aStatus=1,aYearId,depId,program=1;
	private String clsId,sId,clsName,clsCode,aYear;

	private String sync_key;
	private int sync_status= 0;
	private String uniqueId;
	private String sessionId,departmentId,key;

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

	public void setDepartmentId(String subId){
		this.departmentId = subId;
	}public String getDepartmentId(){
		return departmentId;
	}


	private int maxSec=10;
	public void setMaxSec(int uniqueId){
		this.maxSec=uniqueId;
	}public int getMaxSec(){
		return maxSec;
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

	public String getKey() {
		return key;
	}
	public void setKey(String sId) {
		this.key = sId;
	}

	public Class(){

	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setaStatus(int aStatus) {
		this.aStatus = aStatus;
	}public void setaYearId(int aYearId) {
		this.aYearId = aYearId;
	}public void setclsId(String clsId) {
		this.clsId = clsId;
	}public void setclsName(String clsName) {
		this.clsName = clsName;
	}public void setclsCode(String clsCode) {
		this.clsCode = clsCode;
	}public void setaYear(String aYear) {
		this.aYear = aYear;
	}public void setSId(String sId) {
		this.sId = sId;
	}public void setdepId(int depId) {
		this.depId = depId;
	}
	
	public int getId() {
		return id;
	}public int getdepId() {
		return depId;
	}

	public int getProgram() {
		return program;
	}
	public void setProgram(int id) {
		this.program = id;
	}

	public int getaStatus() {
		return aStatus;
	}public int getaYearId() {
		return aYearId;
	}public String getclsName() {
		return clsName;
	}public String getclsCode() {
		return clsCode;
	}public String getaYear() {
		return aYear;
	}public String getclsId() {
		return clsId;
	}public String getSId() {
		return sId;
	}
	
	
	public String toString() {
		return clsName+"("+aYear+")";
	}
	
	public static void main(String[] args) {


	}

}
