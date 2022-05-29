package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User2DAO {
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;	
	
	public User2DAO() {
		try {
			String dbURL = "jdbc:oracle:thin:@localhost:1521:XE";
			String dbID = "ezen";
			String dbPassword = "0824";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int login(String userId, String userPassword) {
		String SQL = "SELECT USERID FROM USER2 WHERE USERID = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if (rs.getString(1).equals(userPassword)) {
					return 1; //로그인 성공
				} else {
					return 0; //비밀번호 불일치
				}
			}
			return -1; // 아이디가 없음
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2;	// 데이터베이스 오류
	}
	
}
