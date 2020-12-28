package cn.tedu.shoot;

import java.util.Random;
import java.awt.image.BufferedImage;

public abstract class FlyingObject {
	public static final int LIVE = 0;//2
	public static final int DEAD = 1;
	public static final int REMOVE = 2;
	protected int state= LIVE;//2
	
	protected int width; //1
	protected int height;
	protected int x;
	protected int y;
	public FlyingObject(int width,int height){
		
		this.width= width;
		this.height= height;
		Random rand= new Random();
		x= rand.nextInt(World.WIDTH-width);
		y= -height;  //5
	}	
	public FlyingObject(int width, int height, int x, int y){
		this.width= width;
		this.height= height;
		this.x= x;
		this.y= y;
		//1
		
		
	
	}	
	public abstract void step();//1
	
	
	public abstract BufferedImage getImage();//1
  
	public boolean isLive() {
		return state == LIVE;
//3
	}
	public boolean isDead() {
		return state == DEAD;
//3
	}
	public boolean isRemove() {
		return state == REMOVE;
//3
	}
	
	
	
	public boolean isOutBounds() {//5
		return y>= World.HEIGHT;// ����y���ڴ��ڸߣ�Խ��
	}
	
	
	public boolean isHit(FlyingObject other) {//6      ���������ӵ�/Ӣ�ۻ��Ƿ�ײ�ϣ�this: ����       other: �ӵ�/Ӣ�ۻ�
		int x1 = this.x - other.width; // x1:���˵�x - �ӵ�/Ӣ�ۻ��Ŀ�
		int x2 = this.x + this.width;  // ���ҵ��˵�x��y��      x2:���˵�x + ���˵Ŀ�
		int y1 = this.x - other.height;
		int y2 = this.y + other.height;
		int x = other.x;//  x:�ӵ�/Ӣ�ۻ���x
		int y = other.y;//  y:�ӵ�/Ӣ�ۻ���y
		
		return x>=x1 && x<=x2 && y>=y1 && y<=y2;  // x��x1��x2֮�䣬y��y1��y2֮��
	}//6
	
	/** ������ȥ�� */
	public void goDead() {
		state = DEAD; //������״̬�޸�ΪDEAD���˵�
	}
	

public static void main(String[] args) {
	
	Bullet bullet1= new Bullet(30,40);
	System.out.println(bullet1.width);
	System.out.println(bullet1.height);
//	System.out.println(bullet1.speed);
	System.out.println(bullet1.x);
	System.out.println(bullet1.y);
}	
	
}