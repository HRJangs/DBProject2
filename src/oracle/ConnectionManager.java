package oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * �� TeableModel ���� ���������� ���Ӱ�ü�� �ΰԵǸ�
 * ���������� �ٲ� ��� Ŭ������ �ڵ嵵 �����ؾ� �ϴ�
 * ������������ ������ �ƴ϶�, �� tableModel���� Connection��
 * �����ϱ� ������ ������ ������ �߻��ϰԵ�
 * �ϳ��� ���ø����̼��� ����Ŭ�� �δ� ������ 1�������ε� ����ϴ�
 * �׸��� ������ �������̸� �ϳ��� ���ǿ��� �߻���Ű�� ���� DML�۾���
 * ���ϵ��� ���ϰ� �ȴ�. �� �ٸ�������� �νĵ�
 * 
 * 
 * ��ü�� �ν��Ͻ��� �޸� ���� �Ѱ��� ����� ���
 * 
 * 
 * */
public class ConnectionManager {
	private static ConnectionManager instance;
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="batman";
	String password="1234";
	
	
	Connection con;
	//�����ڰ� �����ϴ� ��� �̿��� ������ �ƿ� �������� ������� ���� ���� ������ ����
	private ConnectionManager(){
		try {
			Class.forName(driver);
			con=DriverManager.getConnection(url, user, password);
		} catch(ClassNotFoundException e){
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//�ν��Ͻ��� �������̵� �ܺο��� �޼��带 ȣ���Ͽ��� ��ü�� �ν��Ͻ���
	//�������� �ֵ��� getter�� ����������
	public static ConnectionManager getInstance(){
		if(instance==null){
			instance = new ConnectionManager();
		}
		return instance;
	}
	
	
	//�� �޼��带 ȣ���ϴ� ȣ���ڴ� Connection��ü�� ��ȯ�ް� �ȴ�
	public Connection getConnection(){
		return con;
	}
	//Ŀ�ؼ��� �� ����� �ݱ�
	public void disConnect(Connection con){
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
