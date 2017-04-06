package book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
/*
 * JTable이 얹혀질 패널
 * */
public class TablePanel extends JPanel{

	JTable table;
	JScrollPane scroll;
	TableModel model;
	//동기화 문제가 중요한 프로그램이면 벡터
	//속도가 중요한 프로그램이라면 어레이리스트
	//다른건 같음
	Vector<Vector> list = new Vector<Vector>();
	
	Connection con;
	int cols;
	
	public TablePanel(){
		table = new JTable();
		scroll = new JScrollPane(table);
		
		setLayout(new BorderLayout());
		add(scroll);
		this.setBackground(Color.pink);
		setPreferredSize(new Dimension(630, 570));
	}
	public void setConnection(Connection con){
		this.con = con;
		
		init();
		
		//테이블 모델을 jtable에 적용
		model = new AbstractTableModel() {
		
			public int getRowCount() {
				return list.size();
			}
			public int getColumnCount() {
			
				return cols;
			}
			public Object getValueAt(int row, int col) {
				Vector vec = (Vector)list.get(row);
				return vec.elementAt(col);
			}
		};
	 table.setModel(model);
	}
	//book테이블의 레코드 가져오기
	public void init(){
		String sql = "select * from book order by book_id asc";
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		 try {
			pstmt =con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			cols=rs.getMetaData().getColumnCount();
			list.removeAll(list);
			//rs의 정보를 컬렉션의 DTO로 옮겨담자
			while(rs.next()){
				Vector<String> columnName = new Vector<String>();
				columnName.add(rs.getString("book_id"));
				columnName.add(rs.getString("book_name"));
				columnName.add(rs.getString("price"));
				columnName.add(rs.getString("img"));
				columnName.add(rs.getString("subcategory_id"));

				list.add(columnName);//기존벡터에 벡터추가
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}	
		}
	}
}
