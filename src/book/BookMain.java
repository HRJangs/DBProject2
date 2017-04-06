package book;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BookMain extends JFrame implements ItemListener,ActionListener{

	JPanel p_west; //���� �����
	JPanel p_content; //����������ü
	JPanel p_north;//���� ���� ��� ����
	JPanel p_center;//flow�� ����Ǿ� p_table�� p_grid�� ��� �����ų �����̳ʿ���
	JPanel p_table;//JTable�� �ٿ��� �г�
	JPanel p_grid;//�׸��������� ������ �г�
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
	File file;
	//html option���� �ٸ��Ƿ�, choice ������Ʈ�� ���� �̸� �޾Ƴ���
	//�� �÷����� rs��ü�� ��ü�Ұ��̴�.
	//�׷����ν� ��� ����? ���̻� rs.last rs.getrow��� �� �Ƚᵵ �ȴ�
	
	ArrayList<SubCategory> subcategory = new ArrayList<SubCategory>();
	private FileInputStream fis;
	private FileOutputStream fos;
	
	
	public BookMain() {
		p_west = new JPanel();
		p_content = new JPanel();
		p_north = new JPanel();
		p_center = new JPanel();
		p_table = new TablePanel();
		p_grid = new GridPanel();
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
		bt_regist= new JButton("���");
		group = new CheckboxGroup();
		ch_table = new Checkbox("���̺���", group, true);
		ch_grid = new Checkbox("�׸�����",group, false);
		chooser = new JFileChooser("C:/html_workspace/images");
		
		bt_regist.addActionListener(this);
		//ũ������
		ch_top.setPreferredSize(new Dimension(140, 30));
		ch_sub.setPreferredSize(new Dimension(140, 30));
		ch_top.addItemListener(this);
		ch_table.addItemListener(this);
		ch_grid.addItemListener(this);
		ch_sub.addItemListener(this);
		
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
		p_north.setPreferredSize(new Dimension(600,30));
		p_north.setBackground(Color.orange);
		
		p_content.setLayout(new BorderLayout());
		p_content.add(p_north,BorderLayout.NORTH);
		p_center.setBackground(Color.yellow);
		
		p_center.add(p_table);
		p_center.add(p_grid);
		p_content.add(p_center);
		
		
		add(p_west,BorderLayout.WEST);
		add(p_content);
		
		setSize(800,600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		init();
	}
	//���̽�������Ʈ�� �ֻ��� ��Ϻ��̱�
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
		//���̺� �гΰ� �׸��� �гο��� Connection ����
		((TablePanel)p_table).setConnection(con);
		((GridPanel)p_grid).setConnection(con);
	}
	//��ǰ��� �޼���
	public void regist(){
		//������ ��ϵ� �߰��� ������Ʈ�� �ִٸ�, ������Ʈ �����
		//Component[] grid = p_grid.getComponents();
		//System.out.println(grid.length);
		//���� ���� ������ ����ī�װ� ���̽��� index�� ���ؼ�, �� index�� ArrayList��
		//�����Ͽ� ��ü�� ��ȯ������ ������ �����ϰ� �����ִ�.
		int index = ch_sub.getSelectedIndex();
		System.out.println(index);
		SubCategory dto= subcategory.get(index);
		String book_name=t_name.getText();
		int book_price=Integer.parseInt(t_price.getText());
		String img= file.getName();//���ϸ� �������� 
		 
		StringBuffer sb= new StringBuffer();
		sb.append("insert into book(book_id,subcategory_id,book_name,price,img)");
		sb.append("values(seq_book.nextval,"+ dto.getSubcategory_id()+","+"'"+book_name+"',"+book_price+",'"+img+"')");
		
		PreparedStatement pstmt2 = null;
		try {
			pstmt2 = con.prepareStatement(sb.toString());
			System.out.println(sb.toString());
			//sql���� DML(insert,delete,update)�϶� 
			
			int result=pstmt2.executeUpdate();
			//���� �޼���� ���ڰ��� ��ȯ�ϸ�, �̼��ڰ��� �� ������ ���� ������ �޴� ���ڵ���� ��ȯ�Ѵ�
			//insert�� ��� ������ 1�� ��ȯ�ȴ�. �� ���ڵ常 �߰��Ǵϱ�
			if(result !=0){
				//System.out.println(book_name+"��� ����");
				copy();
				((TablePanel)p_table).init();
				((TablePanel)p_table).table.updateUI();
				
				((GridPanel)p_grid).loadData();
				p_center.revalidate();
				
			}else{
				System.out.println(book_name+"��� ����");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(pstmt2!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	//����ī�װ� ��������
	public void getSubCategory(String value){
		//������ �̹� ä���� �������� �ִٸ� ���� �� �����
		ch_sub.removeAll();
		//String topcategory = ch_top.getSelectedItem();
		StringBuffer sb= new StringBuffer();
		sb.append("select * from subcategory ");
		sb.append("where topcategory_id =( ");
		sb.append("select topcategory_id from topcategory where category_name = '"+ value+"')");
		try {
			pstmt = con.prepareStatement(sb.toString());
			rs =pstmt.executeQuery();
			//rs�������� ��̸���Ʈ ����  �� ���ڵ庰�� Ŭ���� ���� ���Ѵ㿡 ����Ʈ�� ����
			//rs�� ����� ���ڵ�1 ���� SubCategoryŬ������ �ν��Ͻ� 1���� ����
			
			while(rs.next()){
				SubCategory dto = new SubCategory();
				dto.setSubcategory_id(rs.getInt("subcategory_id"));
				dto.setCategory_name(rs.getString("category_name"));
				dto.setTopcategory_id(rs.getInt("topcategory_id"));
			
				subcategory.add(dto);//�÷��ǿ� ���
				ch_sub.add(dto.getCategory_name());
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
		Object obj = e.getSource();
		if(obj==ch_top){
			Choice ch = (Choice)e.getSource();
			getSubCategory(ch.getSelectedItem());	
		}else if(obj==ch_table){
			p_table.setVisible(true);
			p_grid.setVisible(false);
		}else if(obj==ch_grid){
			p_table.setVisible(false);
			p_grid.setVisible(true);
		}
	}
	/*
	 * �̹��� �����ϱ�
	 * ������ ������ �̹����� �����ڰ� ������ ��ġ�� ���縦 �س���
	 * */
	public void copy(){
		try {
			fis = new FileInputStream(file);
			fos = new FileOutputStream("C:/java_workspace2/DBProject2/data/"+file.getName());
			
			int data;//�о���� �����Ͱ� ������� �ʰ� ������ ����ִ�.
			byte[]b =new byte[1024];
			while(true){
				data =fis.read(b);
				if(data==-1)break;
				fos.write(b);
			}
			JOptionPane.showMessageDialog(this, "��ϿϷ�");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void actionPerformed(ActionEvent e) {
		regist();	
	}
	//�׸����� �ҷ�����
	public void openFile(){
		int result= chooser.showOpenDialog(this);
		if(result== JFileChooser.APPROVE_OPTION){
			//������ �̹����� canvas�� �׸����̴�.
			file = chooser.getSelectedFile();
			img = kit.getImage(file.getAbsolutePath());
			can.repaint();
		}
	}
	public static void main(String[] args) {
		new BookMain();
	}
}
