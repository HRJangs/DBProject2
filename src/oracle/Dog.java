package oracle;
//������ Ŭ������ �ν��Ͻ��� ���� �Ѱ��� �����
//�̱��� ����
public class Dog {
	private static Dog Instance;
	
	//new�� ���� ������ ����!
	private Dog(){	
		
	}
	public static Dog getInstance() {
		if(Instance ==null){
			Instance =new Dog();
		}
		return Instance;
	}
}
