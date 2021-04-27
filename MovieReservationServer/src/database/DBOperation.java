package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Model;
import model.Reservation;
import model.Member;

public class DBOperation {
	private DBConnection db;

	public DBOperation() {
		db = DBConnection.getInstance();
	}

	public boolean insert(Model m) {
		Connection conn = null;
		String sql = null;
		boolean success = true;
		
		if(m instanceof Member) 
			sql = "insert into member values(?, ?, ?)";
		else if(m instanceof Reservation) 
			sql = "insert into reservation values(?, ?, ?, ?, ?, ?)";

		PreparedStatement pstmt = null;
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			if(m instanceof Member) {
				pstmt.setString(1, ((Member) m).getUsername());
				pstmt.setString(2, ((Member) m).getPassword());
				pstmt.setString(3, ((Member) m).getEmail());
			}
			else if(m instanceof Reservation) {
				pstmt.setString(1, ((Reservation) m).getUsername());
				pstmt.setString(2, ((Reservation) m).getMovietitle());
				pstmt.setString(3, ((Reservation) m).getDate());
				pstmt.setString(4, ((Reservation) m).getTimeslot());
				pstmt.setString(5, ((Reservation) m).getLocation());
				pstmt.setString(6, ((Reservation) m).getSeatID());
			}

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			success = false;
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return success;
	}

	public Model select(String model, String id) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Model m = null;
		String sql = null;
		
		if(model.equals("member"))
			sql = "select * from member where username=?";
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if(model.equals("member")) {
					return new Member(rs.getString(1), rs.getString(2), rs.getString(3));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		return null;
	}

	public void update(Model m) {
		Connection conn = null;
		String sql = null;
		
		if(m instanceof Member) 
			sql = "update member set password=?, email=? where username=?";

		PreparedStatement pstmt = null;
		
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement(sql);

			if(m instanceof Member) {
				pstmt.setString(1, ((Member) m).getPassword());
				pstmt.setString(3, ((Member) m).getEmail());
				pstmt.setString(10, ((Member) m).getUsername());
			}
			
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	public void delete(String model, String id) {
		Connection conn = null;
		String sql = null;
		
		if(model.equals("member"))
			sql = "delete member where username=?";
		
		PreparedStatement pstmt = null;
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
}
