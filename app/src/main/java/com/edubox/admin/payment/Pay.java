package com.edubox.admin.payment;

public class Pay implements Cash,Bank,Bkash,Nagad,Rocket{

	private int id,paymentId,sessionId,fId,payMethodType,feeType;
	private String sId,stdId,feeId,date,time,trxId,payAmount,payMethod,register,phone;
	private String onlinePhone,onlineTrxId,bankA,bankName,depositer;
	
	
    public void setId(int id) {
        this.id = id;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public void setFId(int fId) {
        this.fId = fId;
    }

    public void setPayMethodType(int payMethodType) {
        this.payMethodType = payMethodType;
    }

    public void setFeeType(int feeType) {
        this.feeType = feeType;
    }

    public void setSId(String sId) {
        this.sId = sId;
    }

    public void setStdId(String stdId) {
        this.stdId = stdId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOnlinePhone(String onlinePhone) {
        this.onlinePhone = onlinePhone;
    }

    public void setOnlineTrxId(String onlineTrxId) {
        this.onlineTrxId = onlineTrxId;
    }

    public void setBankA(String bankA) {
        this.bankA = bankA;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setDepositer(String depositer) {
        this.depositer = depositer;
    }

   
    public int getId() {
        return id;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public int getFId() {
        return fId;
    }

    public int getPayMethodType() {
        return payMethodType;
    }

    public int getFeeType() {
        return feeType;
    }

    public String getSId() {
        return sId;
    }

    public String getStdId() {
        return stdId;
    }

    public String getFeeId() {
        return feeId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getTrxId() {
        return trxId;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public String getRegister() {
        return register;
    }

    public String getPhone() {
        return phone;
    }

    public String getOnlinePhone() {
        return onlinePhone;
    }

    public String getOnlineTrxId() {
        return onlineTrxId;
    }

    public String getBankA() {
        return bankA;
    }

    public String getBankName() {
        return bankName;
    }

    public String getDepositer() {
        return depositer;
    }
	
	public static void main(String[] args) {


	}

	@Override
	public void processRocketPayment() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processNagadPayment() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processBkashPayment() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processBankPayment() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processCashPayment() {
		// TODO Auto-generated method stub
		
	}

}
