package com.edubox.admin.cls;

import com.edubox.admin.db.DBConnection;
import com.edubox.admin.scl.schoolData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClassData {

	private Connection con = null;
	private PreparedStatement pst = null, pstt=null, pstaY=null;
	private ResultSet rs = null, rsp=null;
	private String sql,sqlu,sqlaY;
	
	public ClassData(){
		con = DBConnection.ConnectionDB();
	}
	
	public int getClassRow(int se,int de) {
		int ss = 0;
		sqlaY = "SELECT COUNT(*) AS count FROM classes WHERE aYearId LIKE ? AND depId LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setInt(1, se);
			pst.setInt(2, de);
	
			rs = pst.executeQuery();
			
			ss = rs.getInt("count");
			return ss;
			
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
		return ss;
	}
	
	public int addClass(Class c) {
		int result = 0;
		
		sql = "INSERT INTO classes (sId,clsId,clsName,clsCode,aYear,aYearId,aStatus,depId) VALUES(?,?,?,?,?,?,?,?)";
		
		try {
			pst = con.prepareStatement(sql);
			
			pst.setString(1, c.getSId());
			pst.setString(2, c.getclsId());
			pst.setString(3, c.getclsName());
			pst.setString(4, c.getclsCode());
			pst.setString(5, c.getaYear());
			pst.setInt(6, c.getaYearId());
			pst.setInt(7, c.getaStatus());
			pst.setInt(8, c.getdepId());
			
			pst.execute();
			
			int upS = new schoolData().updateSchoolNum("sClass=sClass+1", c.getSId());
			if(upS==1) {
				result = 1;
			}
			return result;
			
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			try {
				pst.close();
			}catch(Exception ef){
				
			}
		}
		
		return result;
	}
	
	public Class getClassData(int id) {
		Class s = new Class();
		sqlaY = "SELECT * FROM classes WHERE id LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setInt(1, id);
	
			rs = pst.executeQuery();
			
			if(rs.next()) {
				
				s.setId(rs.getInt("id"));
				s.setSId(rs.getString("sId"));
				s.setclsId(rs.getString("clsId"));
				s.setclsName(rs.getString("clsName"));
				s.setclsCode(rs.getString("clsCode"));
				s.setaYear(rs.getString("aYear"));
				s.setaYearId(rs.getInt("aYearId"));
				s.setaStatus(rs.getInt("aStatus"));
				s.setdepId(rs.getInt("depId"));
				
				return s;
				
			}else {
//				JOptionPane.showMessageDialog(null, "Subject Not Found!");
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
		return s;
	}
	
	public Class getClassData(String uid, String classCode) {
		Class s = new Class();
		sqlaY = "SELECT * FROM classes WHERE clsId LIKE ? AND clsCode LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, uid);
			pst.setString(2, classCode);
	
			rs = pst.executeQuery();
			
			if(rs.next()) {
				
				s.setId(rs.getInt("id"));
				s.setSId(rs.getString("sId"));
				s.setclsId(rs.getString("clsId"));
				s.setclsName(rs.getString("clsName"));
				s.setclsCode(rs.getString("clsCode"));
				s.setaYear(rs.getString("aYear"));
				s.setaYearId(rs.getInt("aYearId"));
				s.setaStatus(rs.getInt("aStatus"));
				s.setdepId(rs.getInt("depId"));
				
				return s;
				
			}else {
//				JOptionPane.showMessageDialog(null, "Subject Not Found!");
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
		return s;
	}
	
	public int getClassId(String uid,String classCode) {
		Class s = new Class();
		int ss = 0;
		sqlaY = "SELECT * FROM classes WHERE clsId LIKE ? AND clsCode LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, uid);
			pst.setString(2, classCode);
	
			rs = pst.executeQuery();
			
			if(rs.next()) {
				
				s.setId(rs.getInt("id"));
				s.setSId(rs.getString("sId"));
				s.setclsId(rs.getString("clsId"));
				s.setclsName(rs.getString("clsName"));
				s.setclsCode(rs.getString("clsCode"));
				s.setaYear(rs.getString("aYear"));
				s.setaYearId(rs.getInt("aYearId"));
				s.setaStatus(rs.getInt("aStatus"));
				s.setdepId(rs.getInt("depId"));
				ss = rs.getInt("id");
				return ss;
				
			}else {
//				JOptionPane.showMessageDialog(null, "Class Not Found!");
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
		return ss;
	}
	
	public int deleteClass(int id, String sId) {
		int result = 0;
		
		sql = "DELETE FROM classes WHERE id = ? and sId = ?";
		
		try {
			pst = con.prepareStatement(sql);
			
			pst.setInt(1, id);
			pst.setString(2, sId);
			pst.execute();
				
			int upS = new schoolData().updateSchoolNum("sClass=sClass-1", sId);
			if(upS==1) {
				result = 1;
			}
			return result;
			
		}  catch (SQLException e1) {
			
//			JOptionPane.showMessageDialog(null, "Session Data not Deleted, Try Again!"+e1);
			e1.printStackTrace();
		}finally {
			try {
				pst.close();
			}catch(Exception ef){
				
			}
		}
		
		return result;
	}
	
	public ResultSet getClassInfo(String sId){
		ResultSet res = null;
		
		sqlaY = "SELECT id,sId,clsId,clsName,clsCode,aYear,aYearId,aStatus,depId FROM classes WHERE sId LIKE ?  order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, sId);
	
			res = pst.executeQuery();
			
			
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
	
	public ResultSet getClassInfo(String sId,int yId){
		ResultSet res = null;
		
		sqlaY = "SELECT id,sId,clsId,clsName,clsCode,aYear,aYearId,aStatus,depId FROM classes WHERE sId LIKE ? AND aYearId LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, sId);
			pst.setInt(2, yId);
	
			res = pst.executeQuery();
			
			
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
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
