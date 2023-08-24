package org.sp.mvc.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sp.mvc.domain.Board;
import org.sp.mvc.util.PoolManager;

//DB의 board테이블에 대한 crud를 수행하는 객체
//중립적일수록 재사용성 높음
//따라서 javaEE, javaSE 플랫폼을 가리지 않고 사용할 수 있는 객체여야함
public class BoardDAO {
	PoolManager pool=PoolManager.getInstance(); //오직 1개의 PoolManager만을 이용함
	
	//모든 레코드 가져오기
	public List selectAll() {
		
		//앞으로 JNDI를 이용하므로 Class.forName()으로 드라이버를 직접 얻어올 필요없음 
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List list=new ArrayList();
		
		try {
			con=pool.getConnection(); //대여
			
			String sql="select * from board order by board_idx desc";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery(); //select문 수행 후 표 반환
			
			//rs가 곧 닫힐 예정이므로, rs를 대신할 DTO를 생성하여 옮겨담은 후, 
			//그 DTO를 다시 list에 담기
			while(rs.next()) {
				Board board=new Board(); //빈 DTO생성
				
				board.setBoard_idx(rs.getInt("board_idx"));
				board.setTitle(rs.getString("title"));
				board.setWriter(rs.getString("writer"));
				board.setRegdate(rs.getString("regdate"));
				board.setContent(rs.getString("content"));
				board.setHit(rs.getInt("hit"));
				
				list.add(board); //리스트에 DTO담기
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			pool.release(con, pstmt, rs);
			
		}
		return list;
	}
	
	//레코드 1건 넣기
	public int insert(Board board) {
		int result=0;
		String url="jdbc:oracle:thin:@localhost:1521:XE";
		String user="jsp";
		String pass="1234";
		
		Connection con=null;
		PreparedStatement pstmt=null;
		
		//DB에 insert작업
		try {
			
			//접속
			con=pool.getConnection(); //대여
			
			//쿼리 실행
			String sql="insert into board(board_idx, title, writer, content)";
			sql+=" values(seq_board.nextval, ?, ?, ?)";
			
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContent());
			
			result=pstmt.executeUpdate(); //DML수행
			//out.print()는 response객체가 보유한 문자기반 출력스트림에 문자열을 쌓아놓는 것.
			//이렇게 하면 추후 응답 시 이 문자열을 넘겨받은 클라이언트인 웹브라우저가 문자열 내에 들어있는 js를 해석하여, alert()수행 후 재접속 시도

			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			pool.release(con, pstmt);
		}
		return result;
	}
	
	//레코드 1건 가져오기
	public Board select(int board_idx) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Board board=null;
		
		con=pool.getConnection(); //대여
		String sql="select * from board where board_idx=?";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, board_idx);
			rs=pstmt.executeQuery();
			
			//레코드가 있다면
			if(rs.next()) {
				board=new Board(); //빈 DTO
				
				board.setBoard_idx(rs.getInt("board_idx"));
				board.setTitle(rs.getString("title"));
				board.setWriter(rs.getString("writer"));
				board.setRegdate(rs.getString("regdate"));
				board.setContent(rs.getString("content"));
				board.setHit(rs.getInt("hit"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			pool.release(con, pstmt, rs);
		}
		return board;
	}
	
	//글 1건 수정하기
	public int update(Board board) {
		Connection con=null;
		PreparedStatement pstmt=null;
		int result=0; //DML수행 후 그 결과를 알 수 있는 변수
		
		con=pool.getConnection(); //대여
		
		String sql="update board set title=?, writer=?, content=?";
		sql+=" where board_idx=?";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContent());
			pstmt.setInt(4, board.getBoard_idx());
			result=pstmt.executeUpdate(); //DML 수행
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			pool.release(con, pstmt);
		}
		return result;
	}
	
	//레코드 1건 삭제
	public int delete(int board_idx) {
		Connection con=null;
		PreparedStatement pstmt=null;
		int result=0;
		
		con=pool.getConnection(); //대여
		
		String sql="delete board where board_idx=?";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, board_idx);
			result=pstmt.executeUpdate(); //DML 수행
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.release(con, pstmt);
		}
		return result;
	}
}
