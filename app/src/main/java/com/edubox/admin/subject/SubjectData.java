package com.edubox.admin.subject;

import com.edubox.admin.db.DBConnection;
import com.edubox.admin.scl.schoolData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectData {
	
	private Connection con = null;
	private PreparedStatement pst = null, pstt=null, pstaY=null;
	private ResultSet rs = null, rsp=null;
	private String sql,sqlu,sqlaY;
	
	public SubjectData(){
		con = DBConnection.ConnectionDB();
	}
	 
	
	
	
	public int addSubject(Subject s) {
		int result = 0;
		
		sql = "INSERT INTO subject (sId,subId,subName,subCode,subFee,subTeacherId,aStatus,clsId) VALUES(?,?,?,?,?,?,?,?)";
		
		try {
			pst = con.prepareStatement(sql);
			
			pst.setString(1, s.getSId());
			pst.setString(2, s.getsubId());
			pst.setString(3, s.getsubName());
			pst.setString(4, s.getsubCode());
			pst.setString(5, s.getsubFee());
			pst.setString(6, s.getsubTeacherId());
			pst.setInt(7, s.getaStatus());
			pst.setString(8, s.getclsId());
			
			pst.execute();
			
			int upS = new schoolData().updateSchoolNum("sCourse=sCourse+1", s.getSId());
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
	
	public int deleteSubject(int id, String sId) {
		int result = 0;
		
		sql = "DELETE FROM subject WHERE id = ? and sId = ?";
		
		try {
			pst = con.prepareStatement(sql);
			
			pst.setInt(1, id);
			pst.setString(2, sId);
			pst.execute();
				
			int upS = new schoolData().updateSchoolNum("sCourse=sCourse-1", sId);
			if(upS==1) {
				result = 1;
			}
			return result;
			
		}  catch (SQLException e1) {
			
//			JOptionPane.showMessageDialog(null, "Section Data didn't delete, Try Again!"+e1);
			e1.printStackTrace();
		}finally {
			try {
				pst.close();
			}catch(Exception ef){
				
			}
		}
		
		return result;
	}
	
	public Subject getSubject(int id, String sId) {
		Subject s = new Subject();
			
		sqlaY = "SELECT * FROM subject WHERE sId LIKE ? AND id LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, sId);
			pst.setInt(2, id);
	
			rs = pst.executeQuery();
			
			if(rs.next()) {
				
				s.setId(rs.getInt("id"));
				s.setSId(rs.getString("sId"));
				s.setsubId(rs.getString("subId"));
				s.setsubName(rs.getString("subName"));
				s.setsubCode(rs.getString("subCode"));
				s.setsubFee(rs.getString("subFee"));
				s.setsubTeacherId(rs.getString("subTeacherId"));
				s.setclsId(rs.getInt("clsId")+"");
				s.setaStatus(rs.getInt("aStatus"));
				
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
	
	
	public ResultSet getSubjectInfo(String sId){
		ResultSet res = null;
		
		sqlaY = "SELECT id,sId,subId,subName,subCode,subFee,subTeacherId,clsId,aStatus FROM subject WHERE sId LIKE ?  order by id desc;";
		
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
	
	public ResultSet getSubjectInfo(String sId,int i){
		ResultSet res = null;
		
		sqlaY = "SELECT id,sId,subId,subName,subCode,subFee,subTeacherId,clsId,aStatus FROM subject WHERE sId LIKE ? AND clsId LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, sId);
			pst.setString(2, i+"");
	
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


	}

}
