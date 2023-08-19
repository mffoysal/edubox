package com.edubox.admin.subject;

public class allSub {

	private int id,status=1,typeId,depId,program=1;
	private String clsId,subName,sId,subId,subCode,subFee,subTeacherId,semester,key,departmentId;
	private String sync_key;
	private int sync_status= 0;
	private String uniqueId;
	public void setUniqueId(String uniqueId){
		this.uniqueId=uniqueId;
	}public String getUniqueId(){
		return uniqueId;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String sId) {
		this.key = sId;
	}
	public int getProgram() {
		return program;
	}
	public void setProgram(int id) {
		this.program = id;
	}
	public void setDepartmentId(String subId){
		this.departmentId = subId;
	}public String getDepartmentId(){
		return departmentId;
	}

	private String credit;
	public void setCredit(String uniqueId){
		this.credit=uniqueId;
	}public String getCredit(){
		return credit;
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
	public allSub(){

	}
	public void settypeId(int id) {
		this.typeId = id;
	}public void setdepId(int id) {
		this.depId = id;
	}
	public void setId(int id) {
		this.id = id;
	}public void setaStatus(int aStatus) {
		this.status = aStatus;
	}public void setsubId(String subId) {
		this.subId = subId;
	}public void setsubName(String subName) {
		this.subName = subName;
	}public void setsubCode(String subCode) {
		this.subCode = subCode;
	}public void setsubFee(String subFee) {
		this.subFee = subFee;
	}public void setsubTeacherId(String subTeacherId) {
		this.subTeacherId = subTeacherId;
	}public void setSId(String sId) {
		this.sId = sId;
	}public void setclsId(String clsId) {
		this.clsId = clsId;
	}public void setsemester(String sId) {
		this.semester = sId;
	}
	
	public int gettypeId() {
		return typeId;
	}
	public int getdepId() {
		return depId;
	}
	public int getId() {
		return id;
	}public int getaStatus() {
		return status;
	}public String getsubName() {
		return subName;
	}public String getsubCode() {
		return subCode;
	}public String getsubId() {
		return subId;
	}public String getsubFee() {
		return subFee;
	}public String getsubTeacherId() {
		return subTeacherId;
	}public String getSId() {
		return sId;
	}public String getclsId() {
		return clsId;
	}public String getsemester() {
		return semester;
	}
	
	
	public String toString() {
		return subName+"("+""+")";
	}
	
	public static void main(String[] args) {


	}

}
