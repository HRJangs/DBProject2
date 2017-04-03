package oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

//emp 테이블의 데이터를 처리하는 컨트롤러
public class EmpModel extends AbstractTableModel{
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	String[] column; //컬럼을 넣을 배열
	String[][]  data; //레코드를 넣을 배열
	
	public EmpModel(Connection con) {
		this.con = con;
		/*
		 * 1.드라이버로드
		 * 2.접속
		 * 3.쿼리문
		 * 4.접속닫기
		 * */
		try {
			if(con!=null){
				//아래의 pstmt에 의해 생성되는 rs는 커서가 자유로울 수 있다.
				
				String sql = "select * from emp";
				pstmt =con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				//결과집합 반환
				rs =pstmt.executeQuery();
				//컬럼을 구해보자
				ResultSetMetaData meta =rs.getMetaData();
				int count=meta.getColumnCount();
				column = new String[count];
				//컬럼명을 채우자
				for(int i =0;i<column.length;i++){
					column[i]=meta.getColumnName(i+1);
				}
				rs.last();
				int total = rs.getRow();
				rs.beforeFirst();
				//총 레코드 수를 알았으니 이차원배열 생성
				data = new String[total][column.length];
				
				int rowCnt=0;
				for(int j=0;j<data.length;j++){
					rs.next();
					for(int i=0;i<data[j].length;i++){
						data[j][i]=rs.getString(column[i]);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	public int getRowCount() {

		return data.length;
	}
 
	public String getColumnName(int index) {
		return column[index];
	}
	
	public int getColumnCount() {

		return column.length;
	}

	public Object getValueAt(int row, int col) {
	
		return data[row][col];
	}
	

}
