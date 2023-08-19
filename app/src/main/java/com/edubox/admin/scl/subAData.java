package com.edubox.admin.scl;

import com.edubox.admin.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class subAData {
	
	private Connection con = null;
	private PreparedStatement pst = null, pstt=null, pstaY=null;
	private ResultSet rs = null, rsp=null;
	private String sql,sqlu,sqlaY;
	
	public subAData(){
		con = DBConnection.ConnectionDB();
	}
	
	public int checkSubAssigned(int subjectId, int sectionId, String stdId, String sId) {
		int result = 0;
		
		sqlaY = "SELECT * FROM subAssigned WHERE subId LIKE ? AND secId LIKE ? AND stdId LIKE ? AND sId LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setInt(1, subjectId);
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
	
	public int checkSubAssigned(String stdId, String sId,int subjectId, int clsId) {
		int result = 0;
		
		sqlaY = "SELECT * FROM subAssigned WHERE subId LIKE ? AND clsId LIKE ? AND stdId LIKE ? AND sId LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setInt(1, subjectId);
			pst.setInt(2, clsId);
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
	
	public int addSubjectStd(subAssigned s) {
		int result = 0;
		
		int i = checkSubAssigned(s.getsubId(),s.getsecId(),s.getstdId(),s.getsId());
		
		if(i!=1) {
			sql = "INSERT INTO subAssigned (sId,subAId,subId,secId,feeTk,stdId,aStatus,clsId) VALUES(?,?,?,?,?,?,?,?)";
			
			try {
				pst = con.prepareStatement(sql);
				
				pst.setString(1, s.getsId());
				pst.setString(2, s.getsubAId());
				pst.setInt(3, s.getsubId());
				pst.setInt(4, s.getsecId());
				pst.setString(5, s.getsubFee());
				pst.setString(6, s.getstdId());
				pst.setInt(7, s.getaStatus());
				pst.setInt(8, s.getclsId());
				
				pst.execute();
				
				result = 1;
				return 1;
				
			} catch (SQLException e) {

				e.printStackTrace();
			}finally {
				try {
					pst.close();
				}catch(Exception ef){
					
				}
			}
		}else {
//			JOptionPane.showMessageDialog(null, "Sorry! This "+s.getsubId()+" is Already Assigned for that Student","Warning",JOptionPane.INFORMATION_MESSAGE);
		}
		
		return result;
	}
	
	public int deleteSubjectStd(int id, String sId) {
		int result = 0;
		
		sql = "DELETE FROM subAssigned WHERE id = ? and sId = ?";
		
		try {
			pst = con.prepareStatement(sql);
			
			pst.setInt(1, id);
			pst.setString(2, sId);
			pst.execute();
				
			result =1;
			return result;
			
		}  catch (SQLException e1) {
			
//			JOptionPane.showMessageDialog(null, "Assigned Subject and Student Data didn't delete, Try Again!"+e1);
			e1.printStackTrace();
		}finally {
			try {
				pst.close();
			}catch(Exception ef){
				
			}
		}
		
		return result;
	}
	
	public int deleteSubjectStd(int subId,int clsId,String stdId ,String sId) {
		int result = 0;
		
		sql = "DELETE FROM subAssigned WHERE subId = ? AND clsId = ? AND stdId = ? AND sId = ?";
		
		try {
			pst = con.prepareStatement(sql);
			
			pst.setInt(1, subId);
			pst.setInt(2, clsId);
			pst.setString(3, stdId);
			pst.setString(4, sId);
			pst.execute();
				
			result =1;
			return result;
			
		}  catch (SQLException e1) {
			
//			JOptionPane.showMessageDialog(null, "Assigned Subject and Student Data didn't delete, Try Again!"+e1);
			e1.printStackTrace();
		}finally {
			try {
				pst.close();
			}catch(Exception ef){
				
			}
		}
		
		return result;
	}
	
	public subAssigned getSubjectStd(int id, String sId) {
		subAssigned s = new subAssigned();
			
		sqlaY = "SELECT * FROM subAssigned WHERE sId LIKE ? AND id LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, sId);
			pst.setInt(2, id);
	
			rs = pst.executeQuery();
			
			if(rs.next()) {
				
				s.setid(rs.getInt("id"));
				s.setsId(rs.getString("sId"));
				s.setsubAId(rs.getString("subAId"));
				s.setsubId(rs.getInt("subId"));
				s.setsecId(rs.getInt("secId"));
				s.setsubFee(rs.getString("feeTk"));
				s.setstdId(rs.getString("stdId"));
				s.setclsId(rs.getInt("clsId"));
				s.setaStatus(rs.getInt("aStatus"));
				
				return s;
				
			}else {
//				JOptionPane.showMessageDialog(null, "Assigned Subject Not Found!");
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

	public subAssigned getSubjectStd(int subId,int clsId,String stdId ,String sId) {
		subAssigned s = new subAssigned();
			
		sqlaY = "SELECT * FROM subAssigned WHERE subId LIKE ? AND clsId LIKE ? AND stdId LIKE ? AND sId LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setInt(1, subId);
			pst.setInt(2, clsId);
			pst.setString(3, stdId);
			pst.setString(4, sId);
	
			rs = pst.executeQuery();
			
			if(rs.next()) {
				
				s.setid(rs.getInt("id"));
				s.setsId(rs.getString("sId"));
				s.setsubAId(rs.getString("subAId"));
				s.setsubId(rs.getInt("subId"));
				s.setsecId(rs.getInt("secId"));
				s.setsubFee(rs.getString("feeTk"));
				s.setstdId(rs.getString("stdId"));
				s.setclsId(rs.getInt("clsId"));
				s.setaStatus(rs.getInt("aStatus"));
				
				return s;
				
			}else {
//				JOptionPane.showMessageDialog(null, "Assigned Subject Not Found!");
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
	
	public ResultSet getStudentInfo(String sId,int subId){
		ResultSet res = null;
		
		sqlaY = "SELECT s.id, s.uId, s.stdId, s.stdName, s.stdPhone, s.stdEmail, s.sMajor, a.id,a.sId,a.subAId,a.subId,a.secId,a.feeTk,a.stdId,a.clsId,a.aStatus FROM students s, subAssigned a WHERE s.sId = a.sId AND s.stdId = a.stdId AND s.sId LIKE ? AND a.sId LIKE ? AND a.subId LIKE ? order by a.id desc;";
		
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
	
	

	public ResultSet getStudentInfo(String sId,int subId,int secId){
		ResultSet res = null;
		
		sqlaY = "SELECT s.id, s.uId, s.stdId, s.stdName, s.stdPhone, s.stdEmail, s.sMajor, a.id,a.sId,a.subAId,a.subId,a.secId,a.feeTk,a.stdId,a.clsId,a.aStatus FROM students s, subAssigned a WHERE s.sId = a.sId AND s.stdId = a.stdId AND s.sId LIKE ? AND a.sId LIKE ? AND a.subId LIKE ? AND a.secId LIKE ? order by a.id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, sId);
			pst.setString(2, sId);
			pst.setInt(3, subId);
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
	
	
	public ResultSet getSubjectInfo(String sId){
		ResultSet res = null;
		
		sqlaY = "SELECT id,sId,subAId,subId,secId,feeTk,stdId,clsId,aStatus FROM subAssigned WHERE sId LIKE ?  order by id desc;";
		
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
		
		sqlaY = "SELECT id,sId,subAId,subId,secId,feeTk,stdId,clsId,aStatus FROM subAssigned WHERE sId LIKE ? AND clsId LIKE ? order by id desc;";
		
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
	
	
	public ResultSet getSubjectInfo(int i,String sId){
		ResultSet res = null;
		
		sqlaY = "SELECT id,sId,subAId,subId,secId,feeTk,stdId,clsId,aStatus FROM subAssigned WHERE sId LIKE ? AND clsId LIKE ? order by id desc;";
		
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
	
	public ResultSet getSubjectInfo(String sId,String stdId){
		ResultSet res = null;
		
		sqlaY = "SELECT id,sId,subAId,subId,secId,feeTk,stdId,clsId,aStatus FROM subAssigned WHERE sId LIKE ? AND stdId LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, sId);
			pst.setString(2, stdId);
	
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
	
	
	public ResultSet getSubjectInfo(String sId,String stdId,String condition){
		ResultSet res = null;
		
		sqlaY = "SELECT id,sId,subAId,subId,secId,feeTk,stdId,clsId,aStatus FROM subAssigned WHERE sId LIKE ? AND stdId LIKE ? "+condition+" order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, sId);
			pst.setString(2, stdId);
	
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
	
	public ResultSet getSubjectInfo(String sId,String stdId,int clsId){
		ResultSet res = null;
		
		sqlaY = "SELECT id,sId,subAId,subId,secId,feeTk,stdId,clsId,aStatus FROM subAssigned WHERE sId LIKE ? AND stdId LIKE ? AND clsId LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, sId);
			pst.setString(2, stdId);
			pst.setString(3, clsId+"");
	
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
	
	public ResultSet getSubjectInfoByUser(String sId,String stdId){
		ResultSet res = null;
		
		sqlaY = "SELECT id,sId,subAId,subId,secId,feeTk,stdId,clsId,aStatus FROM subAssigned WHERE sId LIKE ? AND stdId LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, sId);
			pst.setString(2, stdId);
	
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
	
	public ResultSet getSubjectInfoByCond(String sId,String condition){
		ResultSet res = null;
		
		sqlaY = "SELECT id,sId,subAId,subId,secId,feeTk,stdId,clsId,aStatus FROM subAssigned WHERE sId LIKE ? AND stdId LIKE ? "+condition+" order by id desc;";
		
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
	
	public static void main(String[] args) {


	}

}
