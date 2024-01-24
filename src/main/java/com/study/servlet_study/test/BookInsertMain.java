package com.study.servlet_study.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import com.study.servlet_study.config.DBConnectionMgr;
import com.study.servlet_study.entity.Author;
import com.study.servlet_study.entity.Book;
import com.study.servlet_study.entity.Publisher;

public class BookInsertMain {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String bookName = null;
		String authorName = null;
		String publisherName = null;
		
		System.out.print("도서명 >>> ");
		bookName = scanner.nextLine();
		System.out.print("저자명 >>> ");
		authorName = scanner.nextLine();
		System.out.print("출판사 >>> ");
		publisherName = scanner.nextLine();
		
		Book book = Book.builder()
				.bookName(bookName)
				.author(Author.builder().authorName(authorName).build())
				.publisher(Publisher.builder().publisherName(publisherName).build())
				.build();
		
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		
		//author_tb insert
		try {
			con = pool.getConnection();
			String sql = "insert into author_tb values (0, ?)";
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	// insert 할 때만 (Statement.RETURN_GENERATED_KEYS : AI(Auto Increment) 되는 값을 가져올수있게해줌)
			pstmt.setString(1, book.getAuthor().getAuthorName());
			pstmt.executeUpdate();	// (insert, delete, update 할때씀)   수정 건수
			ResultSet rs = pstmt.getGeneratedKeys();	// executeUpdate 된 값을 키값으로 가져온다
			if(rs.next()) {
				book.getAuthor().setAuthorId(rs.getInt(1)); // rs.getInt() - 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt); 
		}
		
		//publisher_tb insert
		try {
			con = pool.getConnection();
			String sql = "insert into publisher_tb values (0, ?)";
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	// insert 할 때만 (Statement.RETURN_GENERATED_KEYS : AI(Auto Increment) 되는 값을 가져올수있게해줌)
			pstmt.setString(1, book.getPublisher().getPublisherName());
			pstmt.executeUpdate();	// (insert, delete, update 할때씀)   수정 건수
			ResultSet rs = pstmt.getGeneratedKeys();	// executeUpdate 된 값을 키값으로 가져온다
			if(rs.next()) {
				book.getPublisher().setPublisherId(rs.getInt(1)); // rs.getInt() - 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt); 
		}
		
		//book_tb insert
		try {
			con = pool.getConnection();
			String sql = "insert into book_tb values (0, ?, ?, ?)";
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	// insert 할 때만 (Statement.RETURN_GENERATED_KEYS : AI(Auto Increment) 되는 값을 가져올수있게해줌)
			pstmt.setString(1, book.getBookName());
			pstmt.setInt(2,  book.getAuthor().getAuthorId());
			pstmt.setInt(3,  book.getPublisher().getPublisherId());
			pstmt.executeUpdate();	// (insert, delete, update 할때씀)   수정 건수
			ResultSet rs = pstmt.getGeneratedKeys();	// executeUpdate 된 값을 키값으로 가져온다
			if(rs.next()) {
				book.getPublisher().setPublisherId(rs.getInt(1)); // rs.getInt() - 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt); 
		}
		
		System.out.println("추가된 도서 정보");
		System.out.println(book);
	}
}
