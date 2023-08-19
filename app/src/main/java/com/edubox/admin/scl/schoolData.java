package com.edubox.admin.scl;

import com.edubox.admin.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class schoolData {
	
	private Connection con = null;
	private PreparedStatement pst = null, pstt=null, pstaY=null;
	private ResultSet rs = null, rsp=null;
	private String sql,sqlu,sqlaY;
	
	public schoolData(){
		con = DBConnection.ConnectionDB();
	}
	
	public int updateSchoolNum(String set, String sId) {
		int result = 0;
		sqlu = "UPDATE school set "+set+" WHERE sId LIKE ?";
		
		try {
			pst = con.prepareStatement(sqlu);
			pst.setString(1, sId);
			
			pst.execute();
			
			pst.close();
			
			result = 1;
			return 1;
			
		} catch (SQLException e1) {
			
//			JOptionPane.showMessageDialog(null, "School Studnet/Teacher/Course/Section Number not updated, Try Again!"+e1);
			e1.printStackTrace();
		}finally {
			try {
				pst.close();
			}catch(Exception ef){
				
			}
		}
		return result;
	}
	
	public School getSchoolData(int id) {
		
		School s = new School();
		sql = "SELECT * FROM school WHERE id = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if(rs.next()) {
				
				s.setsId(rs.getString("sId"));
				s.setsName(rs.getString("sName"));
				s.setsPhone(rs.getString("sPhone"));
				s.setsEiin(rs.getString("sEiin"));
				s.setsEmail(rs.getString("sEmail"));
				s.setsPass(rs.getString("sPass"));
				s.setsAdrs(rs.getString("sAdrs"));
				s.setsLogo(rs.getString("sLogo"));
				s.setsStudent(rs.getInt("sStudent"));
				s.setsTeacher(rs.getInt("sTeacher"));
				s.setsCourse(rs.getInt("sCourse"));
				s.setsSec(rs.getInt("sSec"));
				s.setsUser(rs.getInt("sUser"));
				s.setsClass(rs.getInt("sClass"));
				s.setsItp1(rs.getString("sItp1"));
				s.setsItp2(rs.getString("sItp2"));
				s.setsItEmail(rs.getString("sItEmail"));
				s.setsWeb(rs.getString("sWeb"));
				s.setsFundsBal(rs.getString("sFundsBal"));
				s.setsFundsBank(rs.getString("sFundsBank"));
				s.setsFundsAN(rs.getString("sFundsAN"));
				s.setsEmpl(rs.getInt("sEmpl"));
				s.setsActivate(rs.getInt("sActivate"));
				s.setsVerification(rs.getString("sVarification"));
				
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
	
	public School getSchoolData(String sId) {
		
		School s = new School();
		sql = "SELECT * FROM school WHERE sId = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1,sId);
			rs = pst.executeQuery();
			if(rs.next()) {
				
				s.setsId(rs.getString("sId"));
				s.setsName(rs.getString("sName"));
				s.setsPhone(rs.getString("sPhone"));
				s.setsEiin(rs.getString("sEiin"));
				s.setsEmail(rs.getString("sEmail"));
				s.setsPass(rs.getString("sPass"));
				s.setsAdrs(rs.getString("sAdrs"));
				s.setsLogo(rs.getString("sLogo"));
				s.setsStudent(rs.getInt("sStudent"));
				s.setsTeacher(rs.getInt("sTeacher"));
				s.setsCourse(rs.getInt("sCourse"));
				s.setsSec(rs.getInt("sSec"));
				s.setsUser(rs.getInt("sUser"));
				s.setsClass(rs.getInt("sClass"));
				s.setsItp1(rs.getString("sItp1"));
				s.setsItp2(rs.getString("sItp2"));
				s.setsItEmail(rs.getString("sItEmail"));
				s.setsWeb(rs.getString("sWeb"));
				s.setsFundsBal(rs.getString("sFundsBal"));
				s.setsFundsBank(rs.getString("sFundsBank"));
				s.setsFundsAN(rs.getString("sFundsAN"));
				s.setsEmpl(rs.getInt("sEmpl"));
				s.setsActivate(rs.getInt("sActivate"));
				s.setsVerification(rs.getString("sVarification"));
				
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
	
	public int updateSchool(String sId ,School s) {
		
		int result=0;
		
		sqlu = "UPDATE school set sName = ?, sPhone = ?, sPass = ?, sEmail = ?, sLogo = ?, sAdrs = ?, sEiin = ?, sItp1=?, sItp2=?, sItEmail=?, sWeb=?, sFundsBal=?, sFundsBank=?, sFundsAN=? WHERE  sId = ?";
		
		try {
			pst = con.prepareStatement(sqlu);
			pst.setString(1, s.getsName());
			pst.setString(2, s.getsPhone());
			pst.setString(3, s.getsPass());
			pst.setString(4, s.getsEmail());
			pst.setString(5, s.getsLogo());
			pst.setString(6, s.getsAdrs());
			pst.setString(7, s.getsEiin());
			pst.setString(8, s.getsItp1());
			pst.setString(9, s.getsItp2());
			pst.setString(10, s.getsEmail());
			pst.setString(11, s.getsWeb());
			pst.setString(12, s.getsFundsBal());
			pst.setString(13, s.getsFundsBank());
			pst.setString(14, s.getsFundsAN());
			pst.setString(15, sId);
			
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
	
	public static void main(String args[]) {
		
	}
}
