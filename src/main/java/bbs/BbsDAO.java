package bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
			conn = dataFactory.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getDate() {
		
		// 'SELECT SYSDATE FROM DUAL'로 바로 대입했더니
		// String으로 변수 대입했을 때 '[Oracle] ORA-01861: 리터럴이 형식 문자열과 일치하지 않음' 오류 뜨는것을 확인함
		// 반드시 SQL문을 가져올 때 TO_CHAR을 이용해서 형변환을 시켜줘야 함
		String SQL ="SELECT TO_CHAR(SYSDATE,'yyyy/mm/dd') FROM DUAL";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			System.out.println("PreparedStatement : "+SQL);
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
		
		String SQL ="SELECT BBSID FROM BBS ORDER BY BBSID DESC";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			System.out.println("PreparedStatement : "+SQL);
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
	
	// 글쓰기
	public int write(String bbsTitle, String userId, String bbsContent) {
		
		String SQL ="INSERT INTO BBS VALUES (?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			System.out.println("PreparedStatement : "+SQL);
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
	
	// 페이지 생성을 위한 리스트 생성
	public ArrayList<Bbs> getList(int pageNumber){
		
		//최근 글 기준 10개만. 삭제되지 않은 글로
		String SQL ="SELECT * FROM (SELECT * FROM BBS WHERE BBSID < ? AND BBSAVAILABLE = 1 ORDER BY BBSID DESC) WHERE ROWNUM <= 10";
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			System.out.println("PreparedStatement : "+SQL);
			//글번호에서 10의 자리수는 절삭한 수
			pstmt.setInt(1, getNext()- (pageNumber - 1)*10);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsId(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserId(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 다음페이지가 있는지 없는지 여부 확인
	public boolean nextPage(int pageNumber) {
		
		String SQL = "SELECT * FROM BBS WHERE BBSID < ? AND BBSAVAILABLE = 1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			System.out.println("PreparedStatement : "+SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Bbs getBbs(int bbsID) {
		
		String SQL = "SELECT * FROM BBS WHERE BBSID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			System.out.println("PreparedStatement : "+SQL);
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsId(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserId(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				return bbs;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
