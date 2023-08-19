package com.edubox.admin.attendance;

class Student {
    private int id;
    private String name;
    private String email;
    private String attendanceStatus;
    private String date, teacherId, stdId;

    private String uniqueId;
    private String sectionId;
    private String subjectId;

    public void setUniqueId(String uniqueId){
        this.uniqueId=uniqueId;
    }public String getUniqueId(){
        return uniqueId;
    }

    public void setSectionId(String subId){
        this.sectionId = subId;
    }public String getSectionId(){
        return sectionId;
    }

    public void setSubjectId(String subId){
        this.subjectId = subId;
    }public String getSubId(){
        return subjectId;
    }
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


    public Student(String id, String name, String email, String attendanceStatus) {
        this.stdId = id;
        this.name = name;
        this.email = email;
        this.attendanceStatus = attendanceStatus;
    }
    public Student(int id, String name, String email, String attendanceStatus) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.attendanceStatus = attendanceStatus;
    }
    
    public Student() {
    	
    }
    
    public void setstdId(String id) {
    	this.stdId = id;
    }
    public void setId(int id) {
    	this.id = id;
    }public void setName(String name) {
    	this.name = name;
    }public void setEmail(String email) {
    	this.email = email;
    }public void setAttendanceStatus(String a) {
    	this.attendanceStatus = a;
    }public void setDate(String date) {
    	this.date = date;
    }public void setTeacherId(String id) {
    	this.teacherId = id;
    }
    
    public int getId() {
        return id;
    }

    public String getstdId() {
    	return stdId;
    }
    
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }
}