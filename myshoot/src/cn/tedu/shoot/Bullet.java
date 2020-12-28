package cn.tedu.shoot;

import java.awt.image.BufferedImage;

public class Bullet extends FlyingObject {

	
	private int speed;
	public Bullet(int x, int y){
		super(8,20,x,y);
		
		speed= 3;

	}
	public void step() {//5
		y-= speed;//ÏòÉÏÎª-
		
	}//5
	
	
	
	
	
	public  BufferedImage getImage() {//3
		
		if(isLive()) {
			return Images.bullet;
		}else if (isDead()) {
			state =REMOVE;
		}
		return null;
		}
		//3
	
	
	public boolean isOutBounds() {
		return y<= -height;
	}
	
		
	}
	
