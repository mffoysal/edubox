package com.edubox.admin.teacher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.edubox.admin.db.DBConnection;
import com.edubox.admin.main.User;
import com.edubox.admin.scl.schoolData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class teacher extends User {
	
	private Connection con = null;
	private PreparedStatement pst = null, pstt=null, pstaY=null;
	private ResultSet rs = null, rsp=null;
	private String sql,sqlu,sqlaY;
	
	public teacher() {
		super();
		con = DBConnection.ConnectionDB();
	}
	
	
	
	public static void main(String[] args) {
		
  
	}
	
	
	public int addTeacher(User s) {
		int result=0;
		
		sqlu = "INSERT INTO teacher (sId,tId,uId,tName,nidBirth,tPhone,tEmail,tPass,tAddress,tMajor,tBal,tLogo,aStatus,uType,proPic,addDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			pst = con.prepareStatement(sqlu);
			
			pst.setString(1, s.getsId());
			pst.setString(2, s.gettId());
			pst.setString(3, s.getuId());
			pst.setString(4, s.gettName());
			pst.setString(5, s.getnidBirth());
			pst.setString(6, s.gettPhone());
			pst.setString(7, s.gettEmail());
			pst.setString(8, s.gettPass());
			pst.setString(9, s.gettAddress());
			pst.setString(10, s.gettMajor());
			pst.setString(11, s.gettBal());
			pst.setString(12, s.gettLogo());
			pst.setInt(13, s.getaStatus());
			pst.setInt(14, s.getuType());
			pst.setBytes(15, s.getProfilePicInBytes());
			pst.setString(16, s.getAdmissionDate());
			
			
//			System.out.println(""+s.getProfilePicInBytes()+" stdudentData "+s.getImgData());
			
			pst.execute();
			
			pst.close();
			int upS = new schoolData().updateSchoolNum("sTeacher=sTeacher+1", s.getsId());
			if(upS==1) {
				result = 1;
			}
			return result;
			
		} catch (SQLException e1) {
			
//			JOptionPane.showMessageDialog(null, "Teacher's Data did not add, Try Again!"+e1);
			e1.printStackTrace();
		}finally {
			try {
				pst.close();
			}catch(Exception ef){
				
			}
		}
		
		return result;
	}
	

	public int checkTeacher(String stdId) {
		int result = 0;
		
		sqlaY = "SELECT * FROM teacher WHERE  tId LIKE ? OR tPhone LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, stdId);
			pst.setString(2, stdId);

	
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
	
	public User getTeacherData(String tId) {
		
		User s = new User();
		sql = "SELECT * FROM teacher WHERE tId = ? OR tPhone = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, tId);
			pst.setString(2, tId);
			rs = pst.executeQuery();
			if(rs.next()) {
				s.setid(rs.getInt("id"));
				s.setuId(rs.getString("uId"));
				s.setsId(rs.getString("sId"));
				s.settId(rs.getString("tId"));
				s.settName(rs.getString("tName"));
				s.settPhone(rs.getString("tPhone"));
				s.settEmail(rs.getString("tEmail"));
				s.settPass(rs.getString("tPass"));
				s.settAddress(rs.getString("tAddress"));
				s.settLogo(rs.getString("tLogo"));
				s.settMajor(rs.getString("tMajor"));
				s.setaStatus(rs.getInt("aStatus"));
				s.setnidBirth(rs.getString("nidBirth"));
				s.setuType(rs.getInt("uType"));
				s.setProfilePic(rs.getBytes("proPic"));
				s.settBal(rs.getString("tBal"));
				s.setAdmissionDate(rs.getString("addDate"));
				
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
	
	
	public int deleteTeacher(int id, String sId) {
		int result = 0;
		
		sql = "DELETE FROM teacher WHERE id = ? and sId = ?";
		
		try {
			pst = con.prepareStatement(sql);
			
			pst.setInt(1, id);
			pst.setString(2, sId);
			pst.execute();
				
			int upS = new schoolData().updateSchoolNum("sTeacher=sTeacher-1", sId);
			if(upS==1) {
				result = 1;
			}
			return result;
			
		}  catch (SQLException e1) {
			
//			JOptionPane.showMessageDialog(null, "Teacher Data didn't delete, Try Again!"+e1);
			e1.printStackTrace();
		}finally {
			try {
				pst.close();
			}catch(Exception ef){
				
			}
		}
		
		return result;
	}
	
	public User getTeacherData(int tId) {
		
		User s = new User();
		sql = "SELECT * FROM teacher WHERE id = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, tId);
			
			rs = pst.executeQuery();
			if(rs.next()) {
				
				s.setid(rs.getInt("id"));
				s.setuId(rs.getString("uId"));
				s.setsId(rs.getString("sId"));
				s.settId(rs.getString("tId"));
				s.settName(rs.getString("tName"));
				s.settPhone(rs.getString("tPhone"));
				s.settEmail(rs.getString("tEmail"));
				s.settPass(rs.getString("tPass"));
				s.settAddress(rs.getString("tAddress"));
				s.settLogo(rs.getString("tLogo"));
				s.settMajor(rs.getString("tMajor"));
				s.setaStatus(rs.getInt("aStatus"));
				s.setnidBirth(rs.getString("nidBirth"));
				s.setuType(rs.getInt("uType"));
				s.setProfilePic(rs.getBytes("proPic"));
				s.settBal(rs.getString("tBal"));
				s.setAdmissionDate(rs.getString("addDate"));
				
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
	
	
	public int updateTeacher(String uId , User s, int a) {
		
		int result=0;
		
		sqlu = "UPDATE teacher set tId=?, tName = ?, nidBirth=?, tPhone = ?, tEmail = ?, tPass = ?,  tAddress = ?, tMajor=?, aStatus=?, uType=?, tLogo=?, proPic=?  WHERE  sId = ? AND  uId = ?";
		
		try {
			pst = con.prepareStatement(sqlu);
			
			pst.setString(1, s.gettId());
			pst.setString(2, s.gettName());
			pst.setString(3, s.getnidBirth());
			pst.setString(4, s.gettPhone());
			pst.setString(5, s.gettEmail());
			pst.setString(6, s.gettPass());
			pst.setString(7, s.gettAddress());
			pst.setString(8, s.gettMajor());
			pst.setInt(9, s.getaStatus());
			pst.setInt(10, s.getuType());
			pst.setString(11, s.gettLogo());
			pst.setBytes(12, s.getProfilePicInBytes());
			
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
	
	
	

	public ResultSet getTeacherInfo(String sId, String select, String condition){
		ResultSet res = null;
		
		sqlaY = "SELECT "+select+" FROM teacher WHERE sId LIKE ? "+condition+" order by id asc;";
		
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
	
	
	
	
	public ArrayList<User> getTeacherDetails(String sId){
		ArrayList<User> list = new ArrayList<User>();
		
		
		sql = "SELECT * FROM teacher WHERE sId LIKE ? AND aStatus LIKE ? order by id asc";
		
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, sId);
			pst.setInt(2, 1);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				
				User s = new User();
				
				s.setid(rs.getInt("id"));
				s.setuId(rs.getString("uId"));
				s.setsId(rs.getString("sId"));
				s.settId(rs.getString("tId"));
				s.settName(rs.getString("tName"));
				s.settPhone(rs.getString("tPhone"));
				s.settEmail(rs.getString("tEmail"));
				s.settPass(rs.getString("tPass"));
				s.settAddress(rs.getString("tAddress"));
				s.settLogo(rs.getString("tLogo"));
				s.settMajor(rs.getString("tMajor"));
				s.setaStatus(rs.getInt("aStatus"));
				s.setnidBirth(rs.getString("nidBirth"));
				s.setuType(rs.getInt("uType"));
				s.setProfilePic(rs.getBytes("proPic"));
				s.settBal(rs.getString("tBal"));
				s.setAdmissionDate(rs.getString("addDate"));
				
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
	

	public ArrayList<User> getTeacherDetails(String condition, int a){
		ArrayList<User> list = new ArrayList<User>();
		
		
		sql = "SELECT * FROM teacher WHERE sId LIKE ? AND aStatus LIKE ? order by id asc";
		
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, condition);
			pst.setInt(2, a);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				
				User s = new User();
				
				s.setid(rs.getInt("id"));
				s.setuId(rs.getString("uId"));
				s.setsId(rs.getString("sId"));
				s.settId(rs.getString("tId"));
				s.settName(rs.getString("tName"));
				s.settPhone(rs.getString("tPhone"));
				s.settEmail(rs.getString("tEmail"));
				s.settPass(rs.getString("tPass"));
				s.settAddress(rs.getString("tAddress"));
				s.settLogo(rs.getString("tLogo"));
				s.settMajor(rs.getString("tMajor"));
				s.setaStatus(rs.getInt("aStatus"));
				s.setnidBirth(rs.getString("nidBirth"));
				s.setuType(rs.getInt("uType"));
				s.setProfilePic(rs.getBytes("proPic"));
				s.settBal(rs.getString("tBal"));
				s.setAdmissionDate(rs.getString("addDate"));
				
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
			String query="select aStatus from teacher where uId='"+userid+"'";
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
			String query="select tName from teacher where uId='"+userid+"'";
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
			String query="update teacher set aStatus="+activestatus+" where uId='"+userid+"'";
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
			String query="select lastlogin from teacher where uId='"+userid+"'";
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
			String query = "select proPic from teacher where uId='" + userId + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			rs.next();
			byte[] imageData = rs.getBytes(1);
			image = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
			rs.close();
			st.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return image;
	}

	public int changePassword(String userid,String password)
	{
		try
		{
			String query="update teacher set tPass='"+password+"' where uId='"+userid+"'";
			PreparedStatement pr=con.prepareStatement(query);
			return pr.executeUpdate();
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		return 0;
	}
	

}
