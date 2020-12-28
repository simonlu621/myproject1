package cn.tedu.shoot;

import java.awt.image.BufferedImage;

public class Hero extends FlyingObject{
	
	private int life;
	private int fire;
	public Hero(){
		super(97,139,140,400);
		life= 3;
		fire= 0;
		
	}
	public void step() {//5 重写
		
	}//5
	
	
	private int index = 0; //2
	public  BufferedImage getImage() {//2   10毫秒一次
		return Images.heros[index++ % Images.heros.length];
	}//2
	
	
	public Bullet[] shoot() {//4       子弹的生成概率
    	int xStep = this.width/4; // 1/4英雄机的宽
    	int yStep= 20;
    	if(fire>0) {
    		Bullet[] bs = new Bullet[2];
    		bs[0] = new Bullet(this.x + 1* xStep, this.y - yStep);//子弹1定位    this.x英雄机x位置
    		bs[1] = new Bullet(this.x + 3* xStep, this.y - yStep);//子弹2定位
    		
    		fire-=2; // 发射一次火力值减2
    		
    		return bs;
    	}else {
    		Bullet[] bs = new Bullet[1];
    		bs[0] = new Bullet(this.x + 2* xStep, this.y - yStep); //子弹位置
    		return bs;
    	}
    	    
    }//4
    
    public void moveTo(int x, int y) {//5  英雄机随鼠标移动, x，y代表鼠标x，y坐标
    	
    	this.x = x - this.width/2; // 英雄机的坐标this.x  = 鼠标x - 英雄机的宽
    	this.y = y - this.height/2;
    }//5
   

    public void addLife() {//6    行为公开，英雄机增命
    	life++;
    }
    
    public int getLife() {//6   因为life私有，所以做方法调用life
    	return life;
    }
    
    
    
    public void addFire() {//6    英雄机增火力
    	fire+= 40;
    }
    
    public void subtractLife() {//6   命数减1
    	life--;
    }
    public void clearFire() {//6     清空火力值
    	fire=0;
    }
    
    
    
    
    
    

}
