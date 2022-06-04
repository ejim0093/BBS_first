package bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BbsDAO {
	
	private Connection conn;
	private DataSource dataFactory;
	private ResultSet rs;	
	
	public BbsDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context)ctx.lookup("java:comp/env");
			dataFactory = (DataSource)envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getDate() {
		
		//커넥션
			try {
				conn = dataFactory.getConnection();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		// 'SELECT SYSDATE FROM DUAL'로 바로 대입했더니
		// String으로 변수 대입했을 때 '[Oracle] ORA-01861: 리터럴이 형식 문자열과 일치하지 않음' 오류 뜨는것을 확인함
		// 반드시 SQL문을 가져올 때 TO_CHAR을 이용해서 형변환을 시켜줘야 함
		String SQL ="SELECT TO_CHAR(SYSDATE,'YYYY-MM-DD') FROM DUAL";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ""; //데이터베이스 오류
	}
	
	public int getNext() {
		
		//커넥션
				try {
					conn = dataFactory.getConnection();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		String SQL ="SELECT BBSID FROM BBS ORDER BY BBSID DESC";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1)+1;
			}
			return 1; //첫번째 게시글인 경우
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	public int write(String bbsTitle, String userId, String bbsContent) {
		
		//커넥션
				try {
					conn = dataFactory.getConnection();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		String SQL ="INSERT INTO BBS VALUES (?,?,?,?,?,?)";
		System.out.println("PreparedStatement : "+SQL);
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userId);
			pstmt.setString(4, getDate());
			pstmt.setString(5, bbsContent);
			pstmt.setInt(6, 1);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
}
