package oracle;
//강아지 클래스의 인스턴스를 오직 한개만 만들기
//싱글톤 패턴
public class Dog {
	private static Dog Instance;
	
	//new의 의한 생성을 막자!
	private Dog(){	
		
	}
	public static Dog getInstance() {
		if(Instance ==null){
			Instance =new Dog();
		}
		return Instance;
	}
}
