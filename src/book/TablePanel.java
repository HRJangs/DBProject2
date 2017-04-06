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
 * JTable�� ������ �г�
 * */
public class TablePanel extends JPanel{

	JTable table;
	JScrollPane scroll;
	TableModel model;
	//����ȭ ������ �߿��� ���α׷��̸� ����
	//�ӵ��� �߿��� ���α׷��̶�� ��̸���Ʈ
	//�ٸ��� ����
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
		
		//���̺� ���� jtable�� ����
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
	//book���̺��� ���ڵ� ��������
	public void init(){
		String sql = "select * from book order by book_id asc";
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		 try {
			pstmt =con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			cols=rs.getMetaData().getColumnCount();
			list.removeAll(list);
			//rs�� ������ �÷����� DTO�� �Űܴ���
			while(rs.next()){
				Vector<String> columnName = new Vector<String>();
				columnName.add(rs.getString("book_id"));
				columnName.add(rs.getString("book_name"));
				columnName.add(rs.getString("price"));
				columnName.add(rs.getString("img"));
				columnName.add(rs.getString("subcategory_id"));

				list.add(columnName);//�������Ϳ� �����߰�
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
