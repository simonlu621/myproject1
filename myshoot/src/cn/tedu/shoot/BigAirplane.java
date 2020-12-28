package cn.tedu.shoot;

import java.awt.image.BufferedImage;

public class BigAirplane extends FlyingObject implements EnemyScore{//6
	
	private int speed;
	public BigAirplane(){
		super(66,89);
		speed= 2;
	}
	public void step() {//5
		y+= speed;//  向下为+
		
	}//5
	
	
	
	private int index = 1; //3
	public BufferedImage getImage() {
		if (isLive()) {
			return Images.bairs[0];
		}else if (isDead()) {//   10毫秒一次
			BufferedImage img = Images.bairs[index++ % 4];
			if (index== Images.bairs.length) {
				state = REMOVE;
			}
			return img;
		}
		return null;
	}//3

	public int getScore() {//6      大敌机得3分
		return 1;
	}


}
