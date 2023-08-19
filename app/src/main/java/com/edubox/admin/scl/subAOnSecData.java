package com.edubox.admin.scl;

import com.edubox.admin.Admin;
import com.edubox.admin.db.DBConnection;
import com.edubox.admin.section.Section;
import com.edubox.admin.subject.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class subAOnSecData {

	private Connection con = null;
	private PreparedStatement pst = null, pstt=null, pstaY=null;
	private ResultSet rs = null, rsp=null;
	private String sql,sqlu,sqlaY;
	
	public subAOnSecData(){
		con = DBConnection.ConnectionDB();
	}
	
	
	public subAOnSec addSubAndSec(Subject s, Section se) {
		subAOnSec sOs = new subAOnSec();
		
			sOs.setsId(se.getsId());
			sOs.setsubAId(new Admin().userId());
			sOs.setsubId(s.getId());
			sOs.setsecId(se.getid());
			sOs.setsubFee(s.getsubFee());
			sOs.setsecFee(se.getsecFee());
			sOs.setaStatus(1);
			
		return sOs;
	}
	
	public int checkSubOnSec(int subjectId, int sectionId) {
		int result = 0;
		
		sqlaY = "SELECT * FROM subOnsec WHERE subId LIKE ? AND secId LIKE ? order by id desc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setInt(1, subjectId);
			pst.setInt(2, sectionId);
	
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
	
	public int addSubOnSec(subAOnSec s) {
		int result = 0;
		
		int i = checkSubOnSec(s.getsubId(),s.getsecId());
		
		if(i!=1) {
			sql = "INSERT INTO subOnsec (sId,subAId,subId,secId,subFee,secFee,aStatus) VALUES(?,?,?,?,?,?,?)";
			
			try {
				pst = con.prepareStatement(sql);
				
				pst.setString(1, s.getsId());
				pst.setString(2, s.getsubAId());
				pst.setInt(3, s.getsubId());
				pst.setInt(4, s.getsecId());
				pst.setString(5, s.getsubFee());
				pst.setString(6, s.getsecFee());
				pst.setInt(7, s.getaStatus());
				
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
//			JOptionPane.showMessageDialog(null, "Sorry! Your Subject is Already Assigned","Warning",JOptionPane.INFORMATION_MESSAGE);
		}
		
		return result;
	}
	
	public ResultSet getSubjectOnSec(String sId){
		ResultSet res = null;
		
		sqlaY = "SELECT id,sId,subAId,subId,secId,subFee,secFee,aStatus FROM subOnsec WHERE sId LIKE ? order by id desc;";
		
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
	
	
	public ResultSet getSubjectOnSec(String sId,int i){
		ResultSet res = null;
		
		sqlaY = "SELECT id,sId,subAId,subId,secId,subFee,secFee,aStatus FROM subOnsec WHERE sId LIKE ? AND secId LIKE ? order by id desc;";
		
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
