package book;
/*
 * ��ü���� ����� �ڹٿ����� ������ �繰�� class�� ����������
 * database �о߿����� ������ �繰�� entity��� ��ü ���� �������� ǥ���Ѵ�
 * �ᱹ ��ü�� ǥ���ϴ� ����� �ٸ��� ������ ����
 * ���� �ݿ��̶�� ���� �Ȱ���
 * 
 * ��ü������� Ŭ������ �ν��Ͻ��� ���� �س��� ��Ǫ���̶�� ,database�о߿��� ���̺��� ���ڵ带 ������ �� �ִ�Ʋ��
 * ���� ���� �̶� �ϳ��� ���ڵ�� �ᱹ �ϳ��� ��ü�� �����Ѵ�.
 * ���. ���̺� �����ϴ� ��ǰ���ڵ���� �� 5�����, �����ڴ� �� ������ ���ڵ带 5���� �ν��Ͻ��� ����������ȴ�.
 * 
 * �Ʒ��� Ŭ������ ���� �ۼ����� �ƴϴ�.
 * �� �Ѱ��� ���ڵ带 ������� ��������뵵�θ� ����� Ŭ�����̴�.
 * ���ø����̼� ����о߿��� �̷��� ������ Ŭ������ ������ VO,DTO�� �Ѵ�
 * VO  = Value object ���� ��� ��ü
 * DTO =  data tansfer object ���� �����ϱ����� ��ü
 * */
public class SubCategory {
	//������ ���� Ŭ����  Dummy Ŭ����
	//���� ������ ��� �׸���!! �迭�� ��������?
	//�迭�� ��ü�� ������ Ʋ�� ������� ���ߵǹǷ�
	//��ü�� ó���ϴ°��� �ξ��� �۾�����̳� ����� ����.
	private int subcategory_id;
	private int topcategory_id;
	private String category_name;
	

	
	public int getSubcategory_id() {
		return subcategory_id;
	}
	public void setSubcategory_id(int subcategory_id) {
		this.subcategory_id = subcategory_id;
	}
	public int getTopcategory_id() {
		return topcategory_id;
	}
	public void setTopcategory_id(int topcategory_id) {
		this.topcategory_id = topcategory_id;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	
}
