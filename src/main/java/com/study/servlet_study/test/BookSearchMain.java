package com.study.servlet_study.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.study.servlet_study.config.DBConnectionMgr;
import com.study.servlet_study.entity.Author;
import com.study.servlet_study.entity.Book;
import com.study.servlet_study.entity.Publisher;

public class BookSearchMain {
	
	public static void main(String[] args) {
		
		/**
		 * 검색할 도서명을 입력하세요 >>> 
		 * 도서명 / 저자명 / 출판사
		 */
		Scanner scanner = new Scanner(System.in);
		String searchValue = null;
		System.out.print("검색할 도서명을 입력하세요 >>> ");
		searchValue = scanner.nextLine();
		
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Book> bookList = new ArrayList<>();
		
		try {
			con = pool.getConnection();
			String sql = "select\r\n"
					+ "	bt.book_id,\r\n"			//1
					+ "	bt.book_name,\r\n"			//2
					+ "    bt.author_id,\r\n"		//3
					+ "    at.author_name,\r\n"		//4
					+ "    bt.publisher_id,\r\n"	//5
					+ "    pt.publisher_name\r\n"	//6
					+ "from\r\n"
					+ "	book_tb bt\r\n"
					+ "    left outer join author_tb at on(at.author_id = bt.author_id)\r\n"
					+ "    left outer join publisher_tb pt on(pt.publisher_id = bt.publisher_id)\r\n"
					+ "where\r\n"
					+ "	bt.book_name like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + searchValue + "%");
			rs = pstmt.executeQuery();
			
			
			
			while(rs.next()) {
				Author author = Author.builder()
						.authorId(rs.getInt(3))
						.authorName(rs.getString(4))
						.build();
				
				Publisher publisher = Publisher.builder()
						.publisherId(rs.getInt(5))
						.publisherName(rs.getString(6))
						.build();
				
				Book book = Book.builder()
						.bookId(rs.getInt(1))
						.bookName(rs.getString(2))
						.author(author)
						.publisher(publisher)
						.build();
				
				bookList.add(book);
				
				//
//				bookList.add(Book.builder()
//						.bookId(rs.getInt(1))
//						.bookName(rs.getString(2))
//						.author(Author.builder()
//								.authorId(rs.getInt(3))
//								.authorName(rs.getString(4))
//								.build())
//						.publisher(Publisher.builder()
//								.publisherId(rs.getInt(5))
//								.publisherName(rs.getString(6))
//								.build())
//						.build());	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		System.out.println("도서명 / 저자명 / 출판사");
		
		for(Book book : bookList) {
			System.out.println(book.getBookName() + " / " + book.getAuthor().getAuthorName() + " / " + book.getPublisher().getPublisherName());
		}
		
	}
}
