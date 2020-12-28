package cn.tedu.shoot;

import java.awt.image.BufferedImage;
import java.util.Random;
//   С�۷䣺  ���Ƿ����Ҳ�ǽ���
public class Bee extends FlyingObject implements EnemyAward{
	
	private int xSpeed;
	private int ySpeed;
	private int awardType;
	public Bee(){
		super(60,51);
		Random rand= new Random();
		
		
		xSpeed= 1;
		ySpeed= 2;
		awardType= rand.nextInt(2);
	}
	
	public void step() {//5
		x+= xSpeed;
		y+= ySpeed;//����Ϊ+
		if(x<=0 || x>= World.WIDTH-width) {
			xSpeed*= -1;
		}
	
		
		
	}//5
	
	private int index = 1; //3
	public BufferedImage getImage() {
		if (isLive()) {
			return Images.bees[0];
		}else if (isDead()) {// 10����һ��
			BufferedImage img = Images.bees[index++ % 4];
			if (index== Images.bees.length) {
				state = REMOVE;
			}
			return img;
		}
		return null;
	}//3
	
	
	public int getAwardType() {//6            ��д��ȡ����
		
		return awardType;
		
	}
}















