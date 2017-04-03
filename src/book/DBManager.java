package book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * 1.�����ͺ��̽� ���� ���� ������ �������� ���� �ʱ� ����
 * 2.�̱������� ���������ν�, �ν��Ͻ��� ���ʿ��ϰ� ���� ������ �ʾƵ� �ȴ�.
 * 3.�̱������ Connection�� ����� �����ϰ� �����Ƿ� Connection�� �ѹ��� �����Ҽ� �ִ�.
 * */
public class DBManager {
	private static DBManager instance;
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "batman";
	private String password = "1234";
	private Connection con;
	
	private DBManager(){
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static DBManager getInstance(){
		if(instance == null){
			instance =  new DBManager();
		}
		return instance;
	}
	public Connection getConnection() {
		return con;
	}
	public void disConnect(Connection con){
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}