package com.edubox.admin.std;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.edubox.admin.db.DBConnection;
import com.edubox.admin.scl.schoolData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class studentData {

	private Connection con = null;
	private PreparedStatement pst = null, pstt=null, pstaY=null;
	private ResultSet rs = null, rsp=null;
	private String sql,sqlu,sqlaY;
	
	public studentData(){
		con = DBConnection.ConnectionDB();
	}
	
	
	public int addStudent(student s) {
		int result=0;
		
		sqlu = "INSERT INTO students (sId,stdId,uId,stdName,nidBirth,stdPhone,stdEmail,homePhone,stdReligion,dob,address,country,UnionWord,aStatus,fatherName,motherName,fNid,mNid,gName,gAddress,gPhone,gEmail,stdImg,sMajor,stdPass,gender,proPic,addDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			pst = con.prepareStatement(sqlu);
			
			pst.setString(1, s.getSId());
			pst.setString(2, s.getStdId());
			pst.setString(3, s.getUId());
			pst.setString(4, s.getStdName());
			pst.setString(5, s.getnidBirth());
			pst.setString(6, s.getstdPhone());
			pst.setString(7, s.getstdEmail());
			pst.setString(8, s.gethomePhone());
			pst.setString(9, s.getstdReligion());
			pst.setString(10, s.getdob());
			pst.setString(11, s.getAddress());
			pst.setString(12, s.getcountry());
			pst.setString(13, s.getUnionWord());
			pst.setInt(14, s.getaStatus());
			pst.setString(15, s.getfatherName());
			pst.setString(16, s.getmotherName());
			pst.setString(17, s.getfNid());
			pst.setString(18, s.getmNid());
			pst.setString(19, s.getgName());
			pst.setString(20, s.getgAddress());
			pst.setString(21, s.getgPhone());
			pst.setString(22, s.getgEmail());
			pst.setString(23, s.getstdImg());
			pst.setString(24, s.getsMajor());
			pst.setString(25, s.getstdPass());
			pst.setString(26, s.getgender());
			pst.setString(28, s.getAdmissionDate());
			
			
//			System.out.println(""+s.getProfilePicInBytes()+" stdudentData "+s.getImgData());
			
			pst.execute();
			
			pst.close();
			int upS = new schoolData().updateSchoolNum("sStudent=sStudent+1", s.getSId());
			if(upS==1) {
				result = 1;
			}
			return result;
			
		} catch (SQLException e1) {
			
//			JOptionPane.showMessageDialog(null, "Student Data did not add, Try Again!"+e1);
			e1.printStackTrace();
		}finally {
			try {
				pst.close();
			}catch(Exception ef){
				
			}
		}
		
		return result;
	}
	

	public int checkStudent(String stdId) {
		int result = 0;
		
		sqlaY = "SELECT * FROM students WHERE  stdId LIKE ?  order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, stdId);

	
			rs = pst.executeQuery();
			
			if(rs.next()) {
				
				result = 1;
				return result;
			}else {
				result = 0;
				return result;
			}
			
			
		} catch (SQLException e1) {
			
//			JOptionPane.showMessageDialog(null, "Data base not Connected!");

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
	
	public student getStudentData(int stdId) {
		
		student s = new student();
		sql = "SELECT * FROM students WHERE id = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, stdId);
			rs = pst.executeQuery();
			if(rs.next()) {
				
				s.setUId(rs.getString("uId"));
				s.setSId(rs.getString("sId"));
				s.setstdId(rs.getString("stdId"));
				s.setstdName(rs.getString("stdName"));
				s.setstdPhone(rs.getString("stdPhone"));
				s.setstdReligion(rs.getString("stdReligion"));
				s.setstdEmail(rs.getString("stdEmail"));
				s.setstdPass(rs.getString("stdPass"));
				s.setAddress(rs.getString("address"));
				s.setstdImg(rs.getString("stdImg"));
				s.setdob(rs.getString("dob"));
				s.setcountry(rs.getString("country"));
				s.setUnionWord(rs.getString("UnionWord"));
				s.setfatherName(rs.getString("fatherName"));
				s.setmotherName(rs.getString("motherName"));
				s.setfNid(rs.getString("fNid"));
				s.setmNid(rs.getString("mNid"));
				s.setgName(rs.getString("gName"));
				s.setgAddress(rs.getString("gAddress"));
				s.setgPhone(rs.getString("gPhone"));
				s.setgEmail(rs.getString("gEmail"));
				s.setsMajor(rs.getString("sMajor"));
				s.setaStatus(rs.getInt("aStatus"));
				s.setnidBirth(rs.getString("nidBirth"));
				s.setgender(rs.getString("gender"));
				s.setProfilePic(rs.getBytes("proPic"));
				
				return s;
				
			}else {
				
			}
			
			pst.close();
			rs.close();
			
		} catch (SQLException e1) {
			
//			JOptionPane.showMessageDialog(null, "Data base not Connected!");

			e1.printStackTrace();
		}finally {
			try {
				pst.close();
				rs.close();
			}catch(Exception ef){
				
			}
		}
		
		
		return s;
		
	}
	
	public student getStudentData(String uId) {
		
		student s = new student();
		sql = "SELECT * FROM students WHERE uId = ?;";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1,uId);
			rs = pst.executeQuery();
			if(rs.next()) {
				
				s.setUId(rs.getString("uId"));
				s.setSId(rs.getString("sId"));
				s.setstdId(rs.getString("stdId"));
				s.setstdName(rs.getString("stdName"));
				s.setstdPhone(rs.getString("stdPhone"));
				s.setstdReligion(rs.getString("stdReligion"));
				s.setstdEmail(rs.getString("stdEmail"));
				s.setstdPass(rs.getString("stdPass"));
				s.setAddress(rs.getString("address"));
				s.setstdImg(rs.getString("stdImg"));
				s.setdob(rs.getString("dob"));
				s.setcountry(rs.getString("country"));
				s.setUnionWord(rs.getString("UnionWord"));
				s.setfatherName(rs.getString("fatherName"));
				s.setmotherName(rs.getString("motherName"));
				s.setfNid(rs.getString("fNid"));
				s.setmNid(rs.getString("mNid"));
				s.setgName(rs.getString("gName"));
				s.setgAddress(rs.getString("gAddress"));
				s.setgPhone(rs.getString("gPhone"));
				s.setgEmail(rs.getString("gEmail"));
				s.setsMajor(rs.getString("sMajor"));
				s.setaStatus(rs.getInt("aStatus"));
				s.setnidBirth(rs.getString("nidBirth"));
				s.setgender(rs.getString("gender"));
				s.setProfilePic(rs.getBytes("proPic"));
				
				return s;
				
			}else {
				
			}
			
			pst.close();
			rs.close();
			
		} catch (SQLException e1) {
			
//			JOptionPane.showMessageDialog(null, "Data base not Connected!");

			e1.printStackTrace();
		}finally {
			try {
				pst.close();
				rs.close();
			}catch(Exception ef){
				
			}
		}
		
		
		return s;
		
	}
	
	
public student getStudentByStdId(String stdId) {
		
		student s = new student();
		
		sql = "SELECT * FROM students WHERE stdId = ?;";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1,stdId);
			rs = pst.executeQuery();
			if(rs.next()) {
				
				s.setUId(rs.getString("uId"));
				s.setSId(rs.getString("sId"));
				s.setstdId(rs.getString("stdId"));
				s.setstdName(rs.getString("stdName"));
				s.setstdPhone(rs.getString("stdPhone"));
				s.setstdReligion(rs.getString("stdReligion"));
				s.setstdEmail(rs.getString("stdEmail"));
				s.setstdPass(rs.getString("stdPass"));
				s.setAddress(rs.getString("address"));
				s.setstdImg(rs.getString("stdImg"));
				s.setdob(rs.getString("dob"));
				s.setcountry(rs.getString("country"));
				s.setUnionWord(rs.getString("UnionWord"));
				s.setfatherName(rs.getString("fatherName"));
				s.setmotherName(rs.getString("motherName"));
				s.setfNid(rs.getString("fNid"));
				s.setmNid(rs.getString("mNid"));
				s.setgName(rs.getString("gName"));
				s.setgAddress(rs.getString("gAddress"));
				s.setgPhone(rs.getString("gPhone"));
				s.setgEmail(rs.getString("gEmail"));
				s.setsMajor(rs.getString("sMajor"));
				s.setaStatus(rs.getInt("aStatus"));
				s.setnidBirth(rs.getString("nidBirth"));
				s.setgender(rs.getString("gender"));
				s.setProfilePic(rs.getBytes("proPic"));
				
				return s;
				
			}else {
				
			}
			
			pst.close();
			rs.close();
			
		} catch (SQLException e1) {
			
//			JOptionPane.showMessageDialog(null, "Data base not Connected!");

			e1.printStackTrace();
		}finally {
			try {
				pst.close();
				rs.close();
			}catch(Exception ef){
				
			}
		}
		
		
		return s;
		
	}
	
	public student getStudent(String uIdPhone) {
		
		student s = new student();
		sql = "SELECT * FROM students WHERE stdId = ? OR uId = ? OR stdPhone = ?;";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1,uIdPhone);
			pst.setString(2, uIdPhone);
			pst.setString(3, uIdPhone);
			rs = pst.executeQuery();
			if(rs.next()) {
				
				s.setUId(rs.getString("uId"));
				s.setSId(rs.getString("sId"));
				s.setstdId(rs.getString("stdId"));
				s.setstdName(rs.getString("stdName"));
				s.setstdPhone(rs.getString("stdPhone"));
				s.setstdReligion(rs.getString("stdReligion"));
				s.setstdEmail(rs.getString("stdEmail"));
				s.setstdPass(rs.getString("stdPass"));
				s.setAddress(rs.getString("address"));
				s.setstdImg(rs.getString("stdImg"));
				s.setdob(rs.getString("dob"));
				s.setcountry(rs.getString("country"));
				s.setUnionWord(rs.getString("UnionWord"));
				s.setfatherName(rs.getString("fatherName"));
				s.setmotherName(rs.getString("motherName"));
				s.setfNid(rs.getString("fNid"));
				s.setmNid(rs.getString("mNid"));
				s.setgName(rs.getString("gName"));
				s.setgAddress(rs.getString("gAddress"));
				s.setgPhone(rs.getString("gPhone"));
				s.setgEmail(rs.getString("gEmail"));
				s.setsMajor(rs.getString("sMajor"));
				s.setaStatus(rs.getInt("aStatus"));
				s.setnidBirth(rs.getString("nidBirth"));
				s.setgender(rs.getString("gender"));
				s.setProfilePic(rs.getBytes("proPic"));
				
				return s;
				
			}else {
//				JOptionPane.showMessageDialog(null, "Student Not Found Or Try again!");
			}
			
			pst.close();
			rs.close();
			
		} catch (SQLException e1) {
			
//			JOptionPane.showMessageDialog(null, "Student Not Found Or Not connected!");

			e1.printStackTrace();
		}finally {
			try {
				pst.close();
				rs.close();
			}catch(Exception ef){
				
			}
		}
		
		
		return s;
		
	}
	
	public int updateStudent(String uId ,student s, int a) {
		
		int result=0;
		
		sqlu = "UPDATE students set stdId=?, stdName = ?, nidBirth=?, stdPhone = ?, stdEmail = ?, stdPass = ?,  address = ?, homePhone = ?, stdReligion=?, dob=?, country=?, UnionWord=?, fatherName=?, motherName=?, fNid=?, mNid=?, gName=?, gAddress=?, gPhone=?, gEmail=?, sMajor=?, aStatus=?, gender=?  WHERE  sId = ? AND  uId = ?";
		
		try {
			pst = con.prepareStatement(sqlu);
			pst.setString(1, s.getStdId());
			pst.setString(2, s.getStdName());
			pst.setString(3, s.getnidBirth());
			pst.setString(4, s.getstdPhone());
			pst.setString(5, s.getstdEmail());
			pst.setString(6, s.getstdPass());
			pst.setString(7, s.getAddress());
			pst.setString(8, s.gethomePhone());
			pst.setString(9, s.getstdReligion());
			pst.setString(10, s.getdob());
			pst.setString(11, s.getcountry());
			pst.setString(12, s.getUnionWord());
			pst.setString(13, s.getfatherName());
			pst.setString(14, s.getmotherName());
			pst.setString(15, s.getfNid());
			pst.setString(16, s.getmNid());
			pst.setString(17, s.getgName());
			pst.setString(18, s.getgAddress());
			pst.setString(19, s.getgPhone());
			pst.setString(20, s.getgEmail());
			pst.setString(21, s.getsMajor());
			pst.setInt(22, a);
			pst.setString(23, s.getgender());
			pst.setString(24, s.getSId());
			pst.setString(25, uId);
			
			pst.execute();
			
			pst.close();
			
			result = 1;
			return 1;
			
		} catch (SQLException e1) {
			
//			JOptionPane.showMessageDialog(null, "Session Data not updated, Try Again!"+e1);
			e1.printStackTrace();
		}finally {
			try {
				pst.close();
			}catch(Exception ef){
				
			}
		}
		
		return result;
		
	}
	
	
	

	public ResultSet getstudentInfo(String sId, String select, String condition){
		ResultSet res = null;
		
		sqlaY = "SELECT "+select+" FROM students WHERE sId LIKE ? "+condition+" order by id asc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
//			pst.setString(1, select);
			pst.setString(1, sId);
	
			res = pst.executeQuery();
			
//			String[] stdRow = new String[8]; 
//			
//			try {
//				while(res.next()) {
//					stdRow[0] = res.getString(1);
//					stdRow[1] = res.getString(2);
//					stdRow[2] = res.getString(3);
//					stdRow[3] = res.getString(4);
//					stdRow[4] = res.getString(5);
////					stdRow[5] = rs.getString(6);
//////					stdRow[6] = rs.getString("gEmail");
////					stdRow[6] = rs.getString(7);
////					stdRow[] = rs.getString("");
//					
//					System.out.println(""+stdRow[0]+" "+stdRow[1]);
//					
////					stdModel.addRow(stdRow);
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			return res;
			
		} catch (SQLException e1) {
			
//			JOptionPane.showMessageDialog(null, "Data base not Connected!");

			e1.printStackTrace();
		}finally {
			try {
//				pst.close();
//				res.close();
			}catch(Exception ef){
				
			}
		}
		
		return res;
	}
	
	
	
	
	public ArrayList<student> getstudentDetails(String sId){
		ArrayList<student> list = new ArrayList<student>();
		
		
		sql = "SELECT * FROM students WHERE sId LIKE ? AND aStatus LIKE ? order by id asc";
		
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, sId);
			pst.setInt(2, 1);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				
				student s = new student();
				
				s.setUId(rs.getString("uId"));
				s.setSId(rs.getString("sId"));
				s.setstdId(rs.getString("stdId"));
				s.setstdName(rs.getString("stdName"));
				s.setstdPhone(rs.getString("stdPhone"));
				s.setstdReligion(rs.getString("stdReligion"));
				s.setstdEmail(rs.getString("stdEmail"));
				s.setstdPass(rs.getString("stdPass"));
				s.setAddress(rs.getString("address"));
				s.setstdImg(rs.getString("stdImg"));
				s.setdob(rs.getString("dob"));
				s.setcountry(rs.getString("country"));
				s.setUnionWord(rs.getString("UnionWord"));
				s.setfatherName(rs.getString("fatherName"));
				s.setmotherName(rs.getString("motherName"));
				s.setfNid(rs.getString("fNid"));
				s.setmNid(rs.getString("mNid"));
				s.setgName(rs.getString("gName"));
				s.setgAddress(rs.getString("gAddress"));
				s.setgPhone(rs.getString("gPhone"));
				s.setgEmail(rs.getString("gEmail"));
				s.setsMajor(rs.getString("sMajor"));
				s.setaStatus(rs.getInt("sStatus"));
				s.setnidBirth(rs.getString("nidBirth"));
				s.setgender(rs.getString("gender"));
				
				list.add(s);
				
			}
			
			pst.close();
			rs.close();
			
		} catch (SQLException e1) {
			
//			JOptionPane.showMessageDialog(null, "Data base not Connected!");

			e1.printStackTrace();
		}finally {
			try {
				pst.close();
				rs.close();
			}catch(Exception ef){
				
			}
		}
		
		return list;
	}
	

	public ArrayList<student> getstudentDetails(String condition, int a){
		ArrayList<student> list = new ArrayList<student>();
		
		
		sql = "SELECT * FROM students WHERE sId LIKE ? AND aStatus LIKE ? order by id asc";
		
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, condition);
			pst.setInt(2, a);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				
				student s = new student();
				
				s.setUId(rs.getString("uId"));
				s.setSId(rs.getString("sId"));
				s.setstdId(rs.getString("stdId"));
				s.setstdName(rs.getString("stdName"));
				s.setstdPhone(rs.getString("stdPhone"));
				s.setstdReligion(rs.getString("stdReligion"));
				s.setstdEmail(rs.getString("stdEmail"));
				s.setstdPass(rs.getString("stdPass"));
				s.setAddress(rs.getString("address"));
				s.setstdImg(rs.getString("stdImg"));
				s.setdob(rs.getString("dob"));
				s.setcountry(rs.getString("country"));
				s.setUnionWord(rs.getString("UnionWord"));
				s.setfatherName(rs.getString("fatherName"));
				s.setmotherName(rs.getString("motherName"));
				s.setfNid(rs.getString("fNid"));
				s.setmNid(rs.getString("mNid"));
				s.setgName(rs.getString("gName"));
				s.setgAddress(rs.getString("gAddress"));
				s.setgPhone(rs.getString("gPhone"));
				s.setgEmail(rs.getString("gEmail"));
				s.setsMajor(rs.getString("sMajor"));
				s.setaStatus(rs.getInt("sStatus"));
				s.setnidBirth(rs.getString("nidBirth"));
				s.setgender(rs.getString("gender"));
				
				list.add(s);
				
			}
			
			pst.close();
			rs.close();
			
		} catch (SQLException e1) {
			
//			JOptionPane.showMessageDialog(null, "Data base not Connected!");

			e1.printStackTrace();
		}finally {
			try {
				pst.close();
				rs.close();
			}catch(Exception ef){
				
			}
		}
		
		return list;
	}
	
	
	
	
	public boolean isActive(String userid)
	{
		try
		{
			String query="select aStatus from students where uId='"+userid+"'";
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(query);
			rs.next();
			boolean active=rs.getBoolean(1);
			st.close();
			rs.close();
			return active;
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		return false;
	}
	
	public String getStudentName(String userid)
	{
		String name="";
		try
		{
			String query="select stdName from students where uId='"+userid+"'";
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(query);
			rs.next();
			name=rs.getString(1);
			
			rs.close();
			st.close();
		
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		return name;
	}
	public int setActiveStatus(boolean activestatus,String userid)
	{
		int result=0;
		try
		{
			String query="update students set aStatus="+activestatus+" where uId='"+userid+"'";
			PreparedStatement pr=con.prepareStatement(query);
			result=pr.executeUpdate();
			pr.close();
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		return result;
	}
	public String getLastLogin(String userid)
	{
		try
		{
			String query="select lastlogin from students where uId='"+userid+"'";
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(query);
			rs.next();
			return rs.getString(1);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
			return "deleted";
		}
	}
	public Bitmap getProfilePic(String userId) {
		Bitmap image = null;
		try {
			String query = "SELECT proPic FROM students WHERE uId='" + userId + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			rs.next();
			byte[] imageData = rs.getBytes(1);
			image = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
			rs.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}

	public int changePassword(String userid,String password)
	{
		try
		{
			String query="update students set stdPass='"+password+"' where uId='"+userid+"'";
			PreparedStatement pr=con.prepareStatement(query);
			return pr.executeUpdate();
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		return 0;
	}
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
