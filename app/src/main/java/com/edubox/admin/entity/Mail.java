package com.edubox.admin.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mail")
public class Mail {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String u_mail,u_key,uMailPass;
    private String send_id,sendMail,recMail,rec_id,send_date,send_time,deli_date,delli_time,rec_date,rec_time;
    private String sub,msg,file;
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

    public Mail(){

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // Setter and getter for u_mail
    public void setU_mail(String u_mail) {
        this.u_mail = u_mail;
    }

    public String getU_mail() {
        return u_mail;
    }

    // Setter and getter for u_key
    public void setU_key(String u_key) {
        this.u_key = u_key;
    }

    public String getU_key() {
        return u_key;
    }

    // Setter and getter for uMailPass
    public void setUMailPass(String uMailPass) {
        this.uMailPass = uMailPass;
    }

    public String getUMailPass() {
        return uMailPass;
    }

    // Setter and getter for send_id
    public void setSend_id(String send_id) {
        this.send_id = send_id;
    }

    public String getSend_id() {
        return send_id;
    }

    // Setter and getter for sendMail
    public void setSendMail(String sendMail) {
        this.sendMail = sendMail;
    }

    public String getSendMail() {
        return sendMail;
    }

    // Setter and getter for recMail
    public void setRecMail(String recMail) {
        this.recMail = recMail;
    }

    public String getRecMail() {
        return recMail;
    }

    // Setter and getter for rec_id
    public void setRec_id(String rec_id) {
        this.rec_id = rec_id;
    }

    public String getRec_id() {
        return rec_id;
    }

    // Setter and getter for send_date
    public void setSend_date(String send_date) {
        this.send_date = send_date;
    }

    public String getSend_date() {
        return send_date;
    }

    // Setter and getter for send_time
    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getSend_time() {
        return send_time;
    }

    // Setter and getter for deli_date
    public void setDeli_date(String deli_date) {
        this.deli_date = deli_date;
    }

    public String getDeli_date() {
        return deli_date;
    }

    // Setter and getter for delli_time
    public void setDelli_time(String delli_time) {
        this.delli_time = delli_time;
    }

    public String getDelli_time() {
        return delli_time;
    }

    // Setter and getter for rec_date
    public void setRec_date(String rec_date) {
        this.rec_date = rec_date;
    }

    public String getRec_date() {
        return rec_date;
    }

    // Setter and getter for rec_time
    public void setRec_time(String rec_time) {
        this.rec_time = rec_time;
    }

    public String getRec_time() {
        return rec_time;
    }

    // Setter and getter for sub
    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getSub() {
        return sub;
    }

    // Setter and getter for msg
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    // Setter and getter for file
    public void setFile(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }


//	Properties props = new Properties();
//	props.put("mail.smtp.host", "smtp.gmail.com");
//	props.put("mail.smtp.port", "587");
//	props.put("mail.smtp.auth", "true");
//	props.put("mail.smtp.starttls.enable", "true");
//
//	Session session = Session.getInstance(props, new javax.mail.Authenticator() {
//	    protected PasswordAuthentication getPasswordAuthentication() {
//	        return new PasswordAuthentication("your.email@gmail.com", "your.password");
//	    }
//	});
//
//
//
//	MimeMessage message = new MimeMessage(session);
//	message.setFrom(new InternetAddress("your.email@gmail.com"));
//	message.addRecipient(Message.RecipientType.TO, new InternetAddress("recipient.email@example.com"));
//	message.setSubject("Test email from JavaMail");
//	message.setText("Hello, this is a test email sent from a Java Swing application using JavaMail!");
//
//
//
//	Transport.send(message);




    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
