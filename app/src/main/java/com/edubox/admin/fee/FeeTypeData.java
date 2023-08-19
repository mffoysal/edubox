package com.edubox.admin.fee;

import com.edubox.admin.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class FeeTypeData{
	
	private Connection con = null;
	private PreparedStatement pst = null, pstt=null, pstaY=null;
	private ResultSet rs = null, rsp=null;
	private String sql,sqlu,sqlaY;
	
	public FeeTypeData(){
		con = DBConnection.ConnectionDB();
	}
	
	public int addFeeType(FeeType s) {
		int result = 0;
		
		sql = "INSERT INTO feeType (sId,subId,typeName,typeId,Amount,sessionId,status,clsId,depId,discount,secId) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			pst = con.prepareStatement(sql);
			
			pst.setString(1, s.getSId());
			pst.setInt(2, s.getSubId());
			pst.setString(3, s.getTypeName());
			pst.setInt(4, s.getTypeId());
			pst.setString(5, s.getAmount());
			pst.setInt(6, s.getSessionId());
			pst.setInt(7, s.getStatus());
			pst.setInt(8, s.getClsId());
			pst.setInt(9, s.getDepId());
			pst.setInt(10, s.getDiscount());
			pst.setInt(11, s.getSecId());
			
			pst.execute();
			
//			int upS = new schoolData().updateSchoolNum("sCourse=sCourse+1", s.getsId());
//			if(upS==1) {
//				result = 1;
//			}
			result=1;
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
	
	public FeeType getFeeType(int id, String sId) {
		FeeType s = new FeeType();
			
		sqlaY = "SELECT * FROM feeType WHERE sId LIKE ? AND id LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, sId);
			pst.setInt(2, id);
	
			rs = pst.executeQuery();
			
			if(rs.next()) {
				
				s.setId(rs.getInt("id"));
				s.setSId(rs.getString("sId"));
				s.setTypeId(rs.getInt("typeId"));
				s.setTypeName(rs.getString("typeName"));
				s.setAmount(rs.getString("Amount"));
				s.setSessionId(rs.getInt("sessionId"));
				s.setSubId(rs.getInt("subId"));
				s.setDepId(rs.getInt("depId"));
				s.setStatus(rs.getInt("status"));
				s.setClsId(rs.getInt("clsId"));
				s.setSecId(rs.getInt("secId"));
				s.setDiscount(rs.getInt("discount"));
				
				return s;
			}else {
//				JOptionPane.showMessageDialog(null, "fee Not Found!");
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
	
	public ResultSet getFeeTypeDataInfo(String sId){
		ResultSet res = null;
		
		sqlaY = "SELECT id,typeName,sId,Amount,typeId,sessionId,depId,subId,secId,clsId,status,discount FROM feeType WHERE sId LIKE ?  order by id desc;";
		
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
	
	
	public ResultSet getFeeTypeDataInfo(String sId,int sessionId){
		ResultSet res = null;
		
		sqlaY = "SELECT id,typeName,sId,Amount,typeId,sessionId,depId,subId,secId,clsId,status,discount FROM feeType WHERE sId LIKE ? AND sessionId LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, sId);
			pst.setInt(2, sessionId);
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
	
	
	public ResultSet getFeeTypeDataInfoByDep(String sId,int depId){
		ResultSet res = null;
		
		sqlaY = "SELECT id,typeName,sId,Amount,typeId,sessionId,depId,subId,secId,clsId,status,discount FROM feeType WHERE sId LIKE ? AND depId LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, sId);
			pst.setInt(2, depId);
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
	
	
	public int deleteFeeType(int id, String sId) {
		int result = 0;
		
		sql = "DELETE FROM feeType WHERE id = ? and sId = ?";
		
		try {
			pst = con.prepareStatement(sql);
			
			pst.setInt(1, id);
			pst.setString(2, sId);
			pst.execute();
				
//			int upS = new schoolData().updateSchoolNum("sCourse=sCourse-1", sId);
//			if(upS==1) {
//				result = 1;
//			}
			result = 1;
			return result;
			
		}  catch (SQLException e1) {
			
//			JOptionPane.showMessageDialog(null, "FeeType Data didn't delete, Try Again!"+e1);
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