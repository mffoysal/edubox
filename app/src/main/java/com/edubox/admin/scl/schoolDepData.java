package com.edubox.admin.scl;

import com.edubox.admin.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class schoolDepData {

	private Connection con = null;
	private PreparedStatement pst = null, pstt=null, pstaY=null;
	private ResultSet rs = null, rsp=null;
	private String sql,sqlu,sqlaY;
	
	public schoolDepData(){
		con = DBConnection.ConnectionDB();
	}
	
	
	public int addDep(schoolDep d) {
		int result = 0;
		
		sqlu = "INSERT INTO Major (sId,mName,mStart,mEnd,currentId,mStatus) VALUES(?,?,?,?,?,?)";
		try {
			pst = con.prepareStatement(sqlu);

			pst.setString(1,d.getsId());
			pst.setString(2,d.getmName());
			pst.setString(3,d.getstartId());
			pst.setString(4,d.getendId());
			pst.setString(5,d.getcurrentId());
			pst.setInt(6, 1);
			
//			System.out.println(""+s.getProfilePicInBytes()+" stdudentData "+s.getImgData());
			
			pst.execute();
			
			pst.close();
			
			result = 1;
			return 1;
			
		} catch (SQLException e1) {

			e1.printStackTrace();
		}finally {
			try {
				pst.close();
			}catch(Exception ef){
				
			}
		}
		
		return result;
	}
	
	
	public ResultSet getDepInfo(String sId){
		ResultSet res = null;
		
		sqlaY = "SELECT id,sId,mName,mStart,mEnd,currentId,mStatus FROM Major WHERE sId LIKE ?  order by id asc;";
		
		try {
			pst = con.prepareStatement(sqlaY);
			pst.setString(1, sId);
	
			res = pst.executeQuery();
			
			
			return res;
			
		} catch (SQLException e1) {


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
	
	
	public ArrayList<schoolDep> getDepDetails(String sId){
		ArrayList<schoolDep> list = new ArrayList<schoolDep>();
		
		
		sql = "SELECT * FROM Major WHERE sId LIKE ? AND mStatus LIKE ? order by id asc";
		
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, sId);
			pst.setInt(2, 1);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				
				schoolDep d = new schoolDep();
				
				d.setid(rs.getInt("id"));
				d.setmStatus(rs.getInt("mStatus"));
				d.setmName(rs.getString("mName"));
				d.setstartId(rs.getString("mStart"));
				d.setendId(rs.getString("mEnd"));
				d.setcurrentId(rs.getString("currentId"));
				d.setsId(rs.getString("sId"));
				
				list.add(d);
				
			}
			
			pst.close();
			rs.close();
			
		} catch (SQLException e1) {


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
	
	public int updateDep(schoolDep d) {
		int result =0;
		
		sql = "UPDATE Major set mName = ?, mStart = ?, mEnd = ?, mStatus = ? WHERE id = ? AND sId = ?";
		
		try {
			pst = con.prepareStatement(sql);
			
			
			pst.setString(1, d.getmName());
			pst.setString(2, d.getstartId());
			pst.setString(3, d.getendId());
			pst.setInt(4, d.getmStatus());
			pst.setInt(5, d.getid());
			pst.setString(6,d.getsId());

			
			pst.execute();
			
			result =1;
			return result;
			
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}finally {
			try {
				pst.close();
			}catch(Exception ef){
				
			}
		}
		
		return result;
	}
	
	
	public int deleteDep(int id, String sId) {
		int result = 0;
		
		sql = "DELETE FROM Major WHERE id = ? and sId = ?";
		
		try {
			pst = con.prepareStatement(sql);
			
			pst.setInt(1, id);
			pst.setString(2, sId);
			pst.execute();
				
			result =1;
			return result;
			
		}  catch (SQLException e1) {
			
			e1.printStackTrace();
		}finally {
			try {
				pst.close();
			}catch(Exception ef){
				
			}
		}
		
		return result;
	}
	
	public int updateDepId(int id,String cId) {
		int result = 0;
			sql = "UPDATE Major SET currentId=? WHERE id=?;";
			
			try {
				pst = con.prepareStatement(sql);
				pst.setString(1, cId);
				pst.setInt(2, id);
				
				pst.execute();
				
				result = 1;
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
	
	public String depId(int id) {
		String s = null;
		
		sql = "SELECT * FROM Major WHERE id LIKE ?;";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1,id);
			rs = pst.executeQuery();
			if(rs.next()) {
				if(rs.getString("currentId")!=null) {
					s = rs.getString("currentId");
					int start = Integer.parseInt(s);
					start = start+1;
					s = Integer.toString(start);
					return s;
				}else {
					s = rs.getString("mStart");
					int start = Integer.parseInt(s);
					start = start+1;
					s = Integer.toString(start);
					return s;
				}
			}else {

			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pst.close();
				rs.close();
			}catch(Exception ef){
				
			}
		}
		
		return s;
	}
	
	
	public static void main(String[] args) {


	}

}
