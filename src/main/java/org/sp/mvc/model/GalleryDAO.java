package org.sp.mvc.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sp.mvc.domain.Gallery;
import org.sp.mvc.util.PoolManager;

public class GalleryDAO {
	PoolManager pool=PoolManager.getInstance();
	
	//글 등록
	public int insert(Gallery gallery) {
		Connection con=null;
		PreparedStatement pstmt=null;
		int result=0; //성공 실패 여부를 판단할 수 있는 변수
		
		con=pool.getConnection();
		
		String sql="insert into gallery(gallery_idx, title, writer, content, filename)";
		sql+=" values(seq_gallery.nextval, ?, ?, ?, ?)";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, gallery.getTitle());
			pstmt.setString(2, gallery.getWriter());
			pstmt.setString(3, gallery.getContent());
			pstmt.setString(4, gallery.getFilename());
			
			result=pstmt.executeUpdate(); //DML 실행
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			pool.release(con, pstmt);
		}
		
		return result;
	}
	
	//모든 레코드 가져오기
	public List selectAll() {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List list=new ArrayList();
		
		con=pool.getConnection();
		
		String sql="select * from gallery order by gallery_idx desc";
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			//레코드 수 만큼
			while(rs.next()) {
				Gallery gallery=new Gallery(); //빈 DTO
				gallery.setGallery_idx(rs.getInt("gallery_idx"));
				gallery.setTitle(rs.getString("title"));
				gallery.setWriter(rs.getString("writer"));
				gallery.setContent(rs.getString("content"));
				gallery.setFilename(rs.getString("filename"));
				gallery.setRegdate(rs.getString("regdate"));
				gallery.setHit(rs.getInt("hit"));
				
				//DTO를 리스트에 담기
				list.add(gallery);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			pool.release(con, pstmt, rs);
		}
		
		return list;
	}
}
