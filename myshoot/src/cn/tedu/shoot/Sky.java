   package cn.tedu.shoot;

import java.awt.image.BufferedImage;

public class Sky extends FlyingObject {
	
	private int speed;
	private int y1;//�ڶ�����յ�y����
	
	
	public Sky(){
		super(World.WIDTH,World.HEIGHT,0,0);
																				
		speed= 1;
		y1= -700;
	}
	public void step() {//5
		y+= speed;//����Ϊ+
		y1+=speed;
		if(y>= World.HEIGHT) {
			y= -World.HEIGHT;
		}
		if(y1>= World.HEIGHT) {
			y1= -World.HEIGHT;
		}
		
		
	}//5
	
	public  BufferedImage getImage() {//2
		return Images.sky; //����skyͼƬ
	}
	
	
	public int getY1() {//3
		return y1;
		
	}//3
		
		
		
	}

