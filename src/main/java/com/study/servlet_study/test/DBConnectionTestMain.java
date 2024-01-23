package com.study.servlet_study.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.study.servlet_study.config.DBConnectionMgr;
import com.study.servlet_study.entity.Author;

public class DBConnectionTestMain {
	public static void main(String[] args) {
		
		DBConnectionMgr pool = DBConnectionMgr.getInstance(); 	
		// 싱글톤 하나가 객체 관리 (pool)생성 -> getConnection 
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = pool.getConnection();		// connection: DB - JAVA 연결 
			String name = "junil";	// "%" + "aaa" + "%";
			String sql = "select * from author_tb where author_name = ?";
			pstmt = con.prepareStatement(sql); 
			pstmt.setString(1, name); 	// 1: sql 에 ? 순서대로 값 넣음
			rs = pstmt.executeQuery();	// 쿼리실행 (ctrl + enter)
			
			List<Author> authorList = new ArrayList<>();
			
			while(rs.next()) {	// (next) 다음이 없을때까지(false) 반복
//				System.out.println("id: " + rs.getInt(1));
//				System.out.println("name: " + rs.getString(2));
				authorList.add(Author.builder()
						.authorId(rs.getInt(1))
						.authorName(rs.getString(2))
						.build());
			}
			
//			System.out.println(authorList);
			authorList.forEach(author -> System.out.println(author));
//			for(Author author : authorList) {
//				System.out.println(author);
//			}
//			for(int i = 0; i < authorList.size(); i++) {
//				Author author = authorList.get(i);
//				System.out.println(author);
//			}
			//forEach 전체 반복, 중간에 못끊음
			//향상된 for 문, index for 문 중간에 끊어야 될 때
			
		
		} catch (Exception e) {	
			e.printStackTrace();
		} finally {		//try 나 catch 마지막에 무조건 실행
			pool.freeConnection(con, pstmt, rs);
		}
	}

}
