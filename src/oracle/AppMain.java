package oracle;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class AppMain extends JFrame implements ItemListener{
	ConnectionManager manager;
	Connection con;//모든객체가 공유하기 위해
	JTable table;
	JScrollPane scroll;
	JPanel p_west,p_center;
	Choice choice;
	String[][] item ={
			{"▼테이블을 선택하세요",""},
			{"사원테이블","emp"},
			{"부서테이블","dept"}
	};
	TableModel[] model;
	
	
	public AppMain() {
		//디자인과 로직을 분리시키기 위해 중간자(controller)
		//의 존재가 필요하다. Jtable에서는 이 컨트롤러의 역할을 tablemodel이 해준다.
		//tablemodel을 사용할 경우 jtable은 자신이 보여줘야 할 데이터를 tablemodel로 부터 
		//정보를 얻어와 정보를 출력한다.
		//아래함수는 제이테이블이 호출하는거다.
		//getColumnCount()
		//getRowCount() 
		//getValueAt()
		manager =  ConnectionManager.getInstance();
		con = manager.getConnection();
		model=new TableModel[item.length];
		table = new JTable();
		scroll = new JScrollPane(table);
		p_west = new JPanel();
		p_center = new JPanel();
		choice = new Choice();
		
		//테이블모델들을 올려놓자
		model[0]=new DefaultTableModel();
		model[1]=new EmpModel(con);
		model[2]=new deptModel(con);
		
		//초이스 구성
		for(int i=0;i<item.length;i++){
			choice.add(item[i][0]);
		}
		p_west.add(choice);
		p_center.add(scroll);
		add(p_west,BorderLayout.WEST);
		add(p_center);
		
		choice.addItemListener(this);
		//윈도우창 닫을때 오라클 접속 끊기
		this.addWindowFocusListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				manager.disConnect(con);
				//프로그램 종료
				System.exit(0);
			}
		});
		pack();
		setVisible(true);

	}
	public void showData(int index){
		System.out.println("당신이 보게될 테이블은"+item[index][1]);
		table.setModel(model[index]);
		//해당되는 테이블모델을 사용하면 된다.
		//emp->EmpModel
		//dept->DeptModel
		//아무것도 아니면 ->defaultTablemodel
		
	}
	
	public void itemStateChanged(ItemEvent e) {
		Choice ch  = (Choice) e.getSource();
		int index =ch.getSelectedIndex();
		showData(index);
	}
	
	public static void main(String[] args) {
		new AppMain();
	}
	
}
