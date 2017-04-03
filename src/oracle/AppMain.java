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
	Connection con;//��簴ü�� �����ϱ� ����
	JTable table;
	JScrollPane scroll;
	JPanel p_west,p_center;
	Choice choice;
	String[][] item ={
			{"�����̺��� �����ϼ���",""},
			{"������̺�","emp"},
			{"�μ����̺�","dept"}
	};
	TableModel[] model;
	
	
	public AppMain() {
		//�����ΰ� ������ �и���Ű�� ���� �߰���(controller)
		//�� ���簡 �ʿ��ϴ�. Jtable������ �� ��Ʈ�ѷ��� ������ tablemodel�� ���ش�.
		//tablemodel�� ����� ��� jtable�� �ڽ��� ������� �� �����͸� tablemodel�� ���� 
		//������ ���� ������ ����Ѵ�.
		//�Ʒ��Լ��� �������̺��� ȣ���ϴ°Ŵ�.
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
		
		//���̺�𵨵��� �÷�����
		model[0]=new DefaultTableModel();
		model[1]=new EmpModel(con);
		model[2]=new deptModel(con);
		
		//���̽� ����
		for(int i=0;i<item.length;i++){
			choice.add(item[i][0]);
		}
		p_west.add(choice);
		p_center.add(scroll);
		add(p_west,BorderLayout.WEST);
		add(p_center);
		
		choice.addItemListener(this);
		//������â ������ ����Ŭ ���� ����
		this.addWindowFocusListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				manager.disConnect(con);
				//���α׷� ����
				System.exit(0);
			}
		});
		pack();
		setVisible(true);

	}
	public void showData(int index){
		System.out.println("����� ���Ե� ���̺���"+item[index][1]);
		table.setModel(model[index]);
		//�ش�Ǵ� ���̺���� ����ϸ� �ȴ�.
		//emp->EmpModel
		//dept->DeptModel
		//�ƹ��͵� �ƴϸ� ->defaultTablemodel
		
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
