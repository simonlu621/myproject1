  package cn.tedu.shoot;

import java.awt.image.BufferedImage;
public class Airplane extends FlyingObject implements EnemyScore{ //6
	
	private int speed;
	public Airplane(){
		super(48,50);
		speed= 2;
		
	}
	public void step() {//5
		y+= speed;//向下为+
	}//5
	
	
	

	private int index = 1; //3
	public BufferedImage getImage() {// 10毫秒一次
		if (isLive()) {
			return Images.airs[0];
		}else if (isDead()) {
			BufferedImage img = Images.airs[index++ % 4];
			if (index == Images.airs.length) {
				state = REMOVE;
			}
			return img;
			
			 		}
		return null;
	}//3
	
	
	public int getScore() {//6                打掉小敌机，得1分
		return 1;
	}
	
}
