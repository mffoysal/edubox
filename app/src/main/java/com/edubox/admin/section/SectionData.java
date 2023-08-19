package com.edubox.admin.section;

import com.edubox.admin.db.DBConnection;
import com.edubox.admin.scl.schoolData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SectionData {
	
	private Connection con = null;
	private PreparedStatement pst = null, pstt=null, pstaY=null;
	private ResultSet rs = null, rsp=null;
	private String sql,sqlu,sqlaY;
	
	public SectionData(){
		con = DBConnection.ConnectionDB();
	}
	
	public int updateNumStd(String set,String sId, int id) {
		int result = 0;
		sqlu = "UPDATE sections set "+set+" WHERE  sId LIKE ? AND id LIKE ?";
		
		try {
			pst = con.prepareStatement(sqlu);
			pst.setString(1, sId);
			pst.setInt(2, id);
			
			pst.execute();
			
			pst.close();
			
			result = 1;
			return 1;
			
		} catch (SQLException e1) {
			
//			JOptionPane.showMessageDialog(null, "Session Student not updated, Try Again!"+e1);
			e1.printStackTrace();
		}finally {
			try {
				pst.close();
			}catch(Exception ef){
				
			}
		}
		return result;
	}
	
	public int addSection(Section s) {
		int result = 0;
		
		sql = "INSERT INTO sections (sId,clsId,secName,secCode,secFee,secTeaId,secNumStd,aStatus) VALUES(?,?,?,?,?,?,?,?)";
		
		try {
			pst = con.prepareStatement(sql);
			
			pst.setString(1, s.getsId());
			pst.setString(2, s.getclsId());
			pst.setString(3, s.getsecName());
			pst.setString(4, s.getsecCode());
			pst.setString(5, s.getsecFee());
			pst.setString(6, s.getsecTeaId());
			pst.setInt(7, s.getsecNumStd());
			pst.setInt(8, s.getaStatus());
			
			pst.execute();
			
			int upS = new schoolData().updateSchoolNum("sSec=sSec+1", s.getsId());
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
	
	public Section getSection(int id, String sId) {
		Section s = new Section();
			
		sqlaY = "SELECT * FROM sections WHERE sId LIKE ? AND id LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, sId);
			pst.setInt(2, id);
	
			rs = pst.executeQuery();
			
			if(rs.next()) {
				
				s.setid(rs.getInt("id"));
				s.setsId(rs.getString("sId"));
				s.setclsId(rs.getString("clsId"));
				s.setsecName(rs.getString("secName"));
				s.setsecCode(rs.getString("secCode"));
				s.setsecFee(rs.getString("secFee"));
				s.setsecTeaId(rs.getString("secTeaId"));
				s.setsecNumStd(rs.getInt("secNumStd"));
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
	
	public int deleteSection(int id, String sId) {
		int result = 0;
		
		sql = "DELETE FROM sections WHERE id = ? and sId = ?";
		
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
	
	public ResultSet getSectionInfo(String sId){
		ResultSet res = null;
		
		sqlaY = "SELECT id,sId,clsId,secName,secCode,secFee,secTeaId,secNumStd,aStatus FROM sections WHERE sId LIKE ?  order by id desc;";
		
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
	
	public ResultSet getSectionInfo(String sId,String clsId){
		ResultSet res = null;
		
		sqlaY = "SELECT id,sId,clsId,secName,secCode,secFee,secTeaId,secNumStd,aStatus FROM sections WHERE sId LIKE ? AND clsId LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, sId);
			pst.setString(2, clsId);
	
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
	
	
	public ResultSet getSectionInfo(String sId,int subId){
		ResultSet res = null;
		
		sqlaY = "SELECT s.id,s.sId,s.clsId,s.secName,s.secCode,s.secFee,s.secTeaId,s.secNumStd,s.aStatus, a.id, a.subAId  FROM sections s, subOnsec a WHERE  s.sId LIKE ? AND a.sId LIKE ? AND a.subId LIKE ? AND s.id = a.secId order by s.id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, sId);
			pst.setString(2, sId);
			pst.setInt(3, subId);
	
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
