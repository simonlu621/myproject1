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
		return y>= World.HEIGHT;// 敌人y大于窗口高，越界
	}
	
	
	public boolean isHit(FlyingObject other) {//6      检测敌人与子弹/英雄机是否撞上，this: 敌人       other: 子弹/英雄机
		int x1 = this.x - other.width; // x1:敌人的x - 子弹/英雄机的宽
		int x2 = this.x + this.width;  // 先找敌人的x，y。      x2:敌人的x + 敌人的宽
		int y1 = this.x - other.height;
		int y2 = this.y + other.height;
		int x = other.x;//  x:子弹/英雄机的x
		int y = other.y;//  y:子弹/英雄机的y
		
		return x>=x1 && x<=x2 && y>=y1 && y<=y2;  // x在x1，x2之间，y在y1，y2之间
	}//6
	
	/** 飞行物去死 */
	public void goDead() {
		state = DEAD; //将对象状态修改为DEAD死了的
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