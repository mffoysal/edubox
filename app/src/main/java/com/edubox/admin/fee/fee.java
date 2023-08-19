package com.edubox.admin.fee;

import com.edubox.admin.std.student;

import java.util.HashMap;


public class fee extends student {

	private int id,fee_type,status=1,scholarship_status=0,pay_status=0,sessionId,depId,clsId,secId,subId,discountStatus=0,discount=0;
	private HashMap<Integer, String> feeArr = new HashMap<>();
	private String feeId,fee_details,fee="0",sId,stdId,date,time,trx_id,pay_method,disAmount="0";

    private String sync_key;
    private int sync_status= 0;
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

	public fee() {
		feeArr.put(1,"Cash");
		feeArr.put(2, "Bkash");
		feeArr.put(3, "Nagad");
		feeArr.put(4, "Bank");
	}
	
	public void setStdId(String stdId) {
		this.stdId = stdId;
	}
    public void setId(int id) {
        this.id = id;
    }

    public void setdiscountStatus(int ds) {
    	this.discountStatus =ds;
    }
    
    public void setdiscount(int ds) {
    	this.discount = ds;
    }
    
    public void setdisAmount(String am) {
    	this.disAmount = am;
    }
    
    public int getdiscountStatus() {
    	return discountStatus;
    }
    
    public int getdiscount() {
    	return discount;
    }
    
    public String getdisAmount() {
    	return disAmount;
    }
    
    public void setFeeType(int fee_type) {
        this.fee_type = fee_type;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setScholarshipStatus(int scholarship_status) {
        this.scholarship_status = scholarship_status;
    }

    public void setPayStatus(int pay_status) {
        this.pay_status = pay_status;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public void setDepId(int depId) {
        this.depId = depId;
    }

    public void setClsId(int clsId) {
        this.clsId = clsId;
    }

    public void setSecId(int secId) {
        this.secId = secId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public void setFeeArr(HashMap<Integer, String> feeArr) {
        this.feeArr = feeArr;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    public void setFeeDetails(String fee_details) {
        this.fee_details = fee_details;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public void setSId(String sId) {
        this.sId = sId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTrxId(String trx_id) {
        this.trx_id = trx_id;
    }

    public void setPayMethod(String pay_method) {
        this.pay_method = pay_method;
    }


    public int getId() {
        return id;
    }

    public int getFeeType() {
        return fee_type;
    }

    public int getStatus() {
        return status;
    }

    public int getScholarshipStatus() {
        return scholarship_status;
    }

    public int getPayStatus() {
        return pay_status;
    }

    public int getSessionId() {
        return sessionId;
    }

    public int getDepId() {
        return depId;
    }

    public int getClsId() {
        return clsId;
    }

    public int getSecId() {
        return secId;
    }

    public int getSubId() {
        return subId;
    }

    public HashMap<Integer, String> getFeeArr() {
        return feeArr;
    }

    public String getFeeId() {
        return feeId;
    }

    public String getFeeDetails() {
        return fee_details;
    }

    public String getFee() {
        return fee;
    }

    public String getSId() {
        return sId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getTrxId() {
        return trx_id;
    }

    public String getPayMethod() {
        return pay_method;
    }
	
    public String getStdId() {
        return stdId;
    }
    
	public static void main(String[] args) {
		

	}

}
