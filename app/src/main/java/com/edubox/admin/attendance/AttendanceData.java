package com.edubox.admin.attendance;

import com.edubox.admin.db.DBConnection;
import com.edubox.admin.scl.schoolData;
import com.edubox.admin.scl.subAData;
import com.edubox.admin.section.Section;
import com.edubox.admin.section.SectionData;
import com.edubox.admin.std.student;
import com.edubox.admin.std.studentData;
import com.edubox.admin.subject.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class AttendanceData {

	private Connection con = null;
	private PreparedStatement pst = null, pstt=null, pstaY=null;
	private ResultSet rs = null, rsp=null;
	private String sql,sqlu,sqlaY;
	private String gdate;

	public AttendanceData(){
		con = DBConnection.ConnectionDB();
	}
	
	
	public ArrayList<Student> getAttendanceSheet(String sId, int subId, String date, int sec){
		ArrayList<Student> list = new ArrayList<Student>();
		
		int ri = checkAttendance(subId,date,sec);
		
		if(ri!=0) {
			sql = "SELECT * FROM attendanceSheet WHERE sId LIKE ? AND subId LIKE ? AND date LIKE ? order by id asc";
			
			try {
				pst = con.prepareStatement(sql);
				pst.setString(1, sId);
				pst.setInt(2, subId);
				pst.setString(3, date);
				rs = pst.executeQuery();
				
				while(rs.next()) {
					
					Student s = new Student();
					student std = new student();
					s.setstdId(rs.getString("stdId"));
					
					std= new studentData().getStudentByStdId(rs.getString("stdId"));
					
					s.setName(std.getStdName());
					s.setEmail(std.getstdEmail());
					s.setDate(rs.getString("date"));
					s.setTeacherId(rs.getString("teacherId"));
					s.setAttendanceStatus(rs.getString("attendance"));
					
					list.add(s);
					
				}
				
				pst.close();
				rs.close();
				
			} catch (SQLException e1) {


				e1.printStackTrace();
			}finally {
				try {
					pst.close();
					rs.close();
				}catch(Exception ef){
					
				}
			}
		}else {
			subAData sd = new subAData();
			ResultSet rem = sd.getStudentInfo(sId,subId,sec);
			
			if(rem!=null) {

				try {
					while(rem.next()) {
						
						Section se = new Section();
						se = new SectionData().getSection(rem.getInt(12),sId);

							Student s = new Student();
							s.setstdId(rem.getString(3));
							s.setName(rem.getString(4));
							s.setEmail(rem.getString(6));
							s.setDate(date);
							s.setAttendanceStatus("A");
							
							list.add(s);
						
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}finally {
					try {
						rem.close();
					}catch(Exception ef){
						
					}
				}
				

			}else {
				System.out.println("Subject not found");
			}
		}
		
		return list;
	}
	
	public ArrayList<Student> takeAttendance(ArrayList<Student> takeStd, Subject sub, String date, Section sec, String myId){
		ArrayList<Student> list = new ArrayList<Student>();
		
		int checkResult = checkAttendance(sub.getId(),date,sec.getid());
		int[] ri = null ;
		
		if(checkResult==0) {
			
			sqlu = "INSERT INTO attendanceSheet (sId,subId,date,stdId,secId,clsId,time,teacherId,attendance,status) VALUES(?,?,?,?,?,?,?,?,?,?)";
			
			try {
				pst = con.prepareStatement(sqlu);
				
				for(Student std : takeStd) {
					pst.setString(1, sub.getSId());
					pst.setInt(2, sub.getId());
					pst.setString(3, date);
					pst.setString(4, std.getstdId());
					pst.setInt(5, sec.getid());
					pst.setString(6, sec.getclsId());
					pst.setString(7, generateAdmissionDate());
					pst.setString(8, myId);
					pst.setString(9, std.getAttendanceStatus());
					pst.setInt(10, 1);
					
					pst.addBatch();
				}
				
//				System.out.println(""+s.getProfilePicInBytes()+" stdudentData "+s.getImgData());
				
//				pst.execute();
				ri = pst.executeBatch();
				pst.close();

				
			} catch (SQLException e1) {

				e1.printStackTrace();
			}finally {
				try {
					pst.close();
				}catch(Exception ef){
					
				}
			}
			
			
			
		}else {

				sqlu = "UPDATE attendanceSheet set time=?, attendance=?, status=?  WHERE  sId = ? AND  subId = ? AND secId = ? AND date = ? AND stdId = ?";
				try {
					pst = con.prepareStatement(sqlu);
					
					for(Student std : takeStd) {
						pst.setString(1, generateAdmissionDate());
						pst.setString(2, std.getAttendanceStatus());
						pst.setInt(3, 1);
						pst.setString(4, sub.getSId());
						pst.setInt(5, sub.getId());
						pst.setInt(6, sec.getid());
						pst.setString(7, date);
						pst.setString(8, std.getstdId());
						
						pst.addBatch();
					}
					
//					System.out.println(""+s.getProfilePicInBytes()+" stdudentData "+s.getImgData());
					
//					pst.execute();
					ri = pst.executeBatch();
					pst.close();

					
				} catch (SQLException e1) {
					

					e1.printStackTrace();
				}finally {
					try {
						pst.close();
					}catch(Exception ef){
						
					}
				}
		}
		
		int i = 0;
		
		for(Student stdd : takeStd) {
			String attend = "(P/A)";
			if(ri[i]==1) {
				attend = "[Saved!]";
			}else {
				attend = "{Try Again}";
			}
			Student student = new Student(stdd.getstdId(), stdd.getName()+"~"+attend, stdd.getEmail(), stdd.getAttendanceStatus());

			System.out.println(""+ri[i]);
			
			list.add(student);
			i++;
		}
		
		return list;
	}
	
	public String generateAdmissionDate()
	{
		return gdate;
	}
	
	public int checkAttendance(int sub, String date, int sec) {
		int result = 0;
		
		sqlaY = "SELECT * FROM attendanceSheet WHERE  subId LIKE ? AND date LIKE ? AND secId LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setInt(1, sub);
			pst.setString(2, date);
			pst.setInt(3, sec);
	
			rs = pst.executeQuery();
			
			if(rs.next()) {
				
				result = 1;
				return result;
			}else {
				result = 0;
				return result;
			}
			
			
		} catch (SQLException e1) {
			


			e1.printStackTrace();
		}finally {
			try {
				pst.close();
				rs.close();
			}catch(Exception ef){
				
			}
		}
		
		return result;
	}
	
	public int checkAttendance(int sub, String date, String std, int sec) {
		int result = 0;
		
		sqlaY = "SELECT * FROM attendanceSheet WHERE  subId LIKE ? AND date LIKE ? AND stdId LIKE ? AND secId LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setInt(1, sub);
			pst.setString(2, date);
			pst.setString(3, std);
			pst.setInt(4, sec);
	
			rs = pst.executeQuery();
			
			if(rs.next()) {
				
				result = 1;
				return result;
			}else {
				result = 0;
				return result;
			}
			
			
		} catch (SQLException e1) {
			


			e1.printStackTrace();
		}finally {
			try {
				pst.close();
				rs.close();
			}catch(Exception ef){
				
			}
		}
		
		return result;
	}
	
	
	public int checkAttendance(Subject sub, String date) {
		int result = 0;
		
		sqlaY = "SELECT * FROM attendanceSheet WHERE  subId LIKE ? AND date LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setInt(1, sub.getId());
			pst.setString(2, date);
	
			rs = pst.executeQuery();
			
			if(rs.next()) {
				
				result = 1;
				return result;
			}else {
				result = 0;
				return result;
			}
			
			
		} catch (SQLException e1) {
			


			e1.printStackTrace();
		}finally {
			try {
				pst.close();
				rs.close();
			}catch(Exception ef){
				
			}
		}
		
		return result;
	}
	
	public int checkAttendance(Subject sub, String date, Student std) {
		int result = 0;
		
		sqlaY = "SELECT * FROM attendanceSheet WHERE  subId LIKE ? AND date LIKE ? AND stdId LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setInt(1, sub.getId());
			pst.setString(2, date);
			pst.setString(3, std.getstdId());
	
			rs = pst.executeQuery();
			
			if(rs.next()) {
				
				result = 1;
				return result;
			}else {
				result = 0;
				return result;
			}
			
			
		} catch (SQLException e1) {
			


			e1.printStackTrace();
		}finally {
			try {
				pst.close();
				rs.close();
			}catch(Exception ef){
				
			}
		}
		
		return result;
	}
	
	public int addAttendance(student s) {
		int result=0;
		
		sqlu = "INSERT INTO students (sId,stdId,uId,stdName,nidBirth,stdPhone,stdEmail,homePhone,stdReligion,dob,address,country,UnionWord,aStatus,fatherName,motherName,fNid,mNid,gName,gAddress,gPhone,gEmail,stdImg,sMajor,stdPass,gender,proPic,addDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			pst = con.prepareStatement(sqlu);
			
			
//			System.out.println(""+s.getProfilePicInBytes()+" stdudentData "+s.getImgData());
			
			pst.execute();
			
			pst.close();
			int upS = new schoolData().updateSchoolNum("sStudent=sStudent+1", s.getSId());
			if(upS==1) {
				result = 1;
			}
			return result;
			
		} catch (SQLException e1) {

			e1.printStackTrace();
		}finally {
			try {
				pst.close();
			}catch(Exception ef){
				
			}
		}
		
		return result;
	}
	
	
	public int updateAttendance(String uId ,student s, int a) {
		
		int result=0;
		
		sqlu = "UPDATE students set stdId=?, stdName = ?, nidBirth=?, stdPhone = ?, stdEmail = ?, stdPass = ?,  address = ?, homePhone = ?, stdReligion=?, dob=?, country=?, UnionWord=?, fatherName=?, motherName=?, fNid=?, mNid=?, gName=?, gAddress=?, gPhone=?, gEmail=?, sMajor=?, aStatus=?, gender=?  WHERE  sId = ? AND  uId = ?";
		
		try {

			pst.execute();
			
			pst.close();
			
			result = 1;
			return 1;
			
		} catch (SQLException e1) {
			

			e1.printStackTrace();
		}finally {
			try {
				pst.close();
			}catch(Exception ef){
				
			}
		}
		
		return result;
		
	}
	
	
	public static void main(String[] args) {
		
		

	}

}
