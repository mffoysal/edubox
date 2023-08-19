package com.edubox.admin.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.edubox.admin.std.student;

import java.util.HashMap;

@Entity(tableName = "fee")
public class fee extends student {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int fee_type,status=1,scholarship_status=0,pay_status=0,sessionId,depId,clsId,secId,subId,discountStatus=0,discount=0;
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

    public int getFee_type() {
        return fee_type;
    }

    public void setFee_type(int fee_type) {
        this.fee_type = fee_type;
    }

    public int getScholarship_status() {
        return scholarship_status;
    }

    public void setScholarship_status(int scholarship_status) {
        this.scholarship_status = scholarship_status;
    }

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public int getDiscountStatus() {
        return discountStatus;
    }

    public void setDiscountStatus(int discountStatus) {
        this.discountStatus = discountStatus;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getFee_details() {
        return fee_details;
    }

    public void setFee_details(String fee_details) {
        this.fee_details = fee_details;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getTrx_id() {
        return trx_id;
    }

    public void setTrx_id(String trx_id) {
        this.trx_id = trx_id;
    }

    public String getPay_method() {
        return pay_method;
    }

    public void setPay_method(String pay_method) {
        this.pay_method = pay_method;
    }

    public String getDisAmount() {
        return disAmount;
    }

    public void setDisAmount(String disAmount) {
        this.disAmount = disAmount;
    }

    public static void main(String[] args) {
		

	}

}
