package book;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BookMain extends JFrame implements ItemListener,ActionListener{

	JPanel p_west; //좌측 등록폼
	JPanel p_content; //우측영역전체
	JPanel p_north;//우측 선택 모드 영역
	JPanel p_table;//JTable이 붙여질 패널
	JPanel p_grid;//그리드방식으로 보여질 패널
	Choice ch_top;
	Choice ch_sub;
	JTextField t_name;
	JTextField t_price;
	Canvas can;
	JButton bt_regist;
	CheckboxGroup group;
	Checkbox ch_table;
	Checkbox ch_grid;
	DBManager dm;
	PreparedStatement pstmt;
	Connection con;
	ResultSet rs;
	String[] top_list;
	Toolkit kit=Toolkit.getDefaultToolkit();
	Image img;
	JFileChooser chooser; 
	
	public BookMain() {
	
		p_west = new JPanel();
		p_content = new JPanel();
		p_north = new JPanel();
		p_table = new JPanel();
		p_grid = new JPanel();
		ch_top =  new Choice();
		ch_sub =  new Choice();
		t_name = new JTextField(12);
		t_price = new JTextField(12);
		URL url = this.getClass().getResource("/default.gif");
		try {
			img=ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		can = new Canvas(){
			public void paint(Graphics g) {
				g.drawImage(img, 0,0,140,140, this);
			}
		};
		
		can.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				openFile();
			}
			
		});
		bt_regist= new JButton("등록");
		group = new CheckboxGroup();
		ch_table = new Checkbox("테이블목록", group, true);
		ch_grid = new Checkbox("그리드목록",group, false);
		chooser = new JFileChooser("C:/html_workspace/images");
		
		//크기조정
		ch_top.setPreferredSize(new Dimension(140, 30));
		ch_sub.setPreferredSize(new Dimension(140, 30));
		ch_top.addItemListener(this);
	
		p_west.setBackground(Color.green);
		p_west.add(ch_top);
		p_west.add(ch_sub);
		p_west.add(t_name);
		p_west.add(t_price);
		p_west.add(can);
		can.setPreferredSize(new Dimension(140, 140));
		p_west.add(bt_regist);
		p_west.setPreferredSize(new Dimension(150, 600));
		
		p_north.add(ch_table);
		p_north.add(ch_grid);
		p_north.setPreferredSize(new Dimension(600,40));
		p_north.setBackground(Color.orange);
		
		p_content.setLayout(new BorderLayout());
		p_content.add(p_north,BorderLayout.NORTH);
		p_table.setBackground(Color.yellow);
		p_content.add(p_table);
		
		add(p_west,BorderLayout.WEST);
		add(p_content);
		
		setSize(800,600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		init();
	}
	//초이스컴포넌트에 최상위 목록보이기
	public void init(){
		dm = DBManager.getInstance();
		con= dm.getConnection();
		if(con!=null){
			String sql  ="select category_name from topcategory";
			try {
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()){
					ch_top.add(rs.getString("category_name"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(rs!=null){
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}if(pstmt!=null){
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	//하위카테고리 가져오기
	public void getSubCategory(String value){
		//기존에 이미 채워진 아이템이 있다면 먼저 싹 지운다
		ch_sub.removeAll();
		//String topcategory = ch_top.getSelectedItem();
		StringBuffer sb= new StringBuffer();
		sb.append("select category_name from subcategory ");
		sb.append("where topcategory_id =( ");
		sb.append("select topcategory_id from topcategory where category_name = '"+ value+"')");
		
		try {
			pstmt = con.prepareStatement(sb.toString());
			rs =pstmt.executeQuery();
		
			while(rs.next()){
				ch_sub.add(rs.getString("category_name"));
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
			}if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void itemStateChanged(ItemEvent e) {
		Choice ch = (Choice)e.getSource();
		getSubCategory(ch.getSelectedItem());
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("나눌렀어?");
		Object obj= e.getSource();
		
	}
	//그림파일 불러오기
	public void openFile(){
		int result= chooser.showOpenDialog(this);
		if(result== JFileChooser.APPROVE_OPTION){
			//선택한 이미지를 canvas에 그릴것이다.
			File file = chooser.getSelectedFile();
			img = kit.getImage(file.getAbsolutePath());
			can.repaint();
		}
	}
	public static void main(String[] args) {
		new BookMain();
	}
}
