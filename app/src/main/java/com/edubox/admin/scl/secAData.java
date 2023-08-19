package com.edubox.admin.scl;

import com.edubox.admin.Admin;
import com.edubox.admin.db.DBConnection;
import com.edubox.admin.section.SectionData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class secAData {
	
	private Connection con = null;
	private PreparedStatement pst = null, pstt=null, pstaY=null;
	private ResultSet rs = null, rsp=null;
	private String sql,sqlu,sqlaY;
	
	public secAData(){
		con = DBConnection.ConnectionDB();
	}
	
	public int checkSecAssigned(int clsId, int sectionId, String stdId, String sId) {
		int result = 0;
		
		sqlaY = "SELECT * FROM secAssigned WHERE clsId LIKE ? AND secId LIKE ? AND stdId LIKE ? AND sId LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setInt(1, clsId);
			pst.setInt(2, sectionId);
			pst.setString(3, stdId);
			pst.setString(4, sId);
	
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
	
	public int checkSecAssigned(int sectionId, String stdId, String sId) {
		int result = 0;
		
		sqlaY = "SELECT * FROM secAssigned WHERE secId LIKE ? AND stdId LIKE ? AND sId LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setInt(1, sectionId);
			pst.setString(2, stdId);
			pst.setString(3, sId);
	
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
	
	public int addSecAData(secAssigned s) {
		int result = 0;
		
		int i = checkSecAssigned(s.getsecId(),s.getstdId(),s.getsId());
		if(i!=1) {
			sql = "INSERT INTO secAssigned (sId,clsId,secId,secAId,stdId,aYear,aYearId,aStatus,feeTk) VALUES(?,?,?,?,?,?,?,?,?)";
			
			try {
				pst = con.prepareStatement(sql);
				
				pst.setString(1, s.getsId());
				pst.setInt(2, s.getclsId());
				pst.setInt(3, s.getsecId());
				pst.setString(4, new Admin().uId());
				pst.setString(5, s.getstdId());
				pst.setString(6, s.getaYear());
				pst.setInt(7, s.getaYearId());
				pst.setInt(8, s.getaStatus());
				pst.setString(9, s.getfeeTk());
				
				
				pst.execute();
				
				int upS = new SectionData().updateNumStd("secNumStd=secNumStd+1",s.getsId(), s.getsecId());
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
		}else {
//			JOptionPane.showMessageDialog(null, "Sorry! "+s.getsecId()+" is Already Assigned for "+s.getstdId(),"Warning",JOptionPane.INFORMATION_MESSAGE);
		}
		
		return result;
	}
	
	public int deleteSecAData(int id, String sId) {
		int result = 0;
		
		sql = "DELETE FROM secAssigned WHERE id = ? and sId = ?";
		
		try {
			pst = con.prepareStatement(sql);
			
			pst.setInt(1, id);
			pst.setString(2, sId);
			pst.execute();
				
			result =1;
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
	
	public secAssigned getSecAData(int id, String sId) {
		secAssigned s = new secAssigned();
			
		sqlaY = "SELECT * FROM secAssigned WHERE sId LIKE ? AND id LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, sId);
			pst.setInt(2, id);
	
			rs = pst.executeQuery();
			
			if(rs.next()) {
				
				s.setid(rs.getInt("id"));
				s.setsId(rs.getString("sId"));
				s.setsecId(rs.getInt("secId"));
				s.setclsId(rs.getInt("clsId"));
				s.setaYear(rs.getString("aYear"));
				s.setaYearId(rs.getInt("aYearId"));
				s.setstdId(rs.getString("stdId"));
				s.setsecAId(rs.getString("secAId"));
				s.setaStatus(rs.getInt("aStatus"));
				
				return s;
				
			}else {
//				JOptionPane.showMessageDialog(null, "Section Not Found!");
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
	
	public ResultSet getSecAInfo(String sId){
		ResultSet res = null;
		
		sqlaY = "SELECT id,sId,stdId,secId,clsId,aYearId,secAId,aYear,aStatus FROM secAssigned WHERE sId LIKE ?  order by id desc;";
		
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
		
		sqlaY = "SELECT id,sId,stdId,secId,clsId,aYearId,secAId,aYear,aStatus FROM secAssigned WHERE sId LIKE ? AND secId LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, sId);
			pst.setInt(2, i);
	
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
	
	public ResultSet getSectionInfo(String sId,int secId){
		ResultSet res = null;
		
		sqlaY = "SELECT  s.id, s.uId, s.stdId, s.stdName, s.stdPhone, s.stdEmail, s.sMajor, a.id,a.sId,a.secAId,a.clsId,a.secId,a.stdId,a.aYearId,a.aStatus FROM students s, secAssigned a WHERE s.sId = a.sId AND s.stdId = a.stdId AND s.sId LIKE ? AND a.sId LIKE ? AND a.secId LIKE ? order by a.id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, sId);
			pst.setString(2, sId);
			pst.setInt(3, secId);
	
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
	
	public ResultSet getSectionInfo(String sId,int clsId,int secId){
		ResultSet res = null;
		
		sqlaY = "SELECT s.id, s.uId, s.stdId, s.stdName, s.stdPhone, s.stdEmail, s.sMajor, a.id,a.sId,a.secAId,a.clsId,a.secId,a.stdId,a.aYearId,a.aStatus FROM students s, secAssigned a WHERE s.sId = a.sId AND s.stdId = a.stdId AND s.sId LIKE ? AND a.sId LIKE ? AND a.clsId LIKE ? AND a.secId LIKE ? order by a.id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, sId);
			pst.setString(2, sId);
			pst.setInt(3, clsId);
			pst.setInt(4, secId);
	
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

