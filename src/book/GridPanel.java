package book;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/*
 * JTable�� ������ �г�
 * */
public class GridPanel extends JPanel{
	private Connection con;
	String path="C:/java_workspace2/DBProject2/data/";
	ArrayList<Book> list = new ArrayList<Book>();
	ArrayList<BookItem> list_item = new ArrayList<BookItem>();
	
	public GridPanel() {
		
		setVisible(false);
		this.setBackground(Color.CYAN);
		setPreferredSize(new Dimension(650, 570));
	}
	public void setConnection(Connection con){
		this.con = con;
		loadData();
	}
	public void loadData(){
		list.removeAll(list);
		
		System.out.println(list.size());
		String sql = "select * from book order by book_id asc";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt  = con.prepareStatement(sql);
			rs = pstmt.executeQuery();//��������

			while(rs.next()){
				Book dto = new Book();//���ڵ� �Ѱ� ������� �ν��Ͻ�
				dto.setBook_id(rs.getInt("book_id"));
				dto.setBook_name(rs.getString("book_name"));
				dto.setImg(rs.getString("img"));
				dto.setPrice(rs.getInt("price"));
				dto.setSubcategory_id(rs.getInt("subcategory_id"));
				list.add(dto);
			}
			//�����ͺ��̽��� ��� ���������Ƿ� �����ο� �ݿ�
			
			init();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void init(){
		//����Ʈ�� ����ִ� Book ��ü��ŭ bookitem�� �����Ͽ� ȭ�鿡 �������� 
		list_item.removeAll(list_item);
		for(int i =0;i<list.size();i++){
			Book book = list.get(i);
			try {
				Image img =ImageIO.read(new File(path+book.getImg()));
				String name = book.getBook_name();
				String price =  Integer.toString(book.getPrice());
				BookItem item = new BookItem(img,name,price);
				add(item);
				list_item.add(item);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
