package book;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * 책 1권을 표현하는 UI컴포넌트
 * 우리만의 컴포넌트
 * */
public class BookItem extends JPanel{
	Canvas can;
	JLabel la_name,la_price;
	
	public BookItem(Image img,String name, String price) {
		can =  new Canvas(){
			public void paint(Graphics g) {
				g.drawImage(img, 0, 0, 120, 120 ,this);
			}
		};
		
		la_name = new JLabel(name);
		la_price = new JLabel(price);
		can.setPreferredSize(new Dimension(120, 120));
		la_name.setPreferredSize(new Dimension(120, 20));
		la_price.setPreferredSize(new Dimension(120, 20));
		add(can);
		add(la_name);
		add(la_price);
		setVisible(true);
		setPreferredSize(new Dimension(120, 180));
		setBackground(Color.gray);
	}
}
