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
	public void step() {//5 ��д
		
	}//5
	
	
	private int index = 0; //2
	public  BufferedImage getImage() {//2   10����һ��
		return Images.heros[index++ % Images.heros.length];
	}//2
	
	
	public Bullet[] shoot() {//4       �ӵ������ɸ���
    	int xStep = this.width/4; // 1/4Ӣ�ۻ��Ŀ�
    	int yStep= 20;
    	if(fire>0) {
    		Bullet[] bs = new Bullet[2];
    		bs[0] = new Bullet(this.x + 1* xStep, this.y - yStep);//�ӵ�1��λ    this.xӢ�ۻ�xλ��
    		bs[1] = new Bullet(this.x + 3* xStep, this.y - yStep);//�ӵ�2��λ
    		
    		fire-=2; // ����һ�λ���ֵ��2
    		
    		return bs;
    	}else {
    		Bullet[] bs = new Bullet[1];
    		bs[0] = new Bullet(this.x + 2* xStep, this.y - yStep); //�ӵ�λ��
    		return bs;
    	}
    	    
    }//4
    
    public void moveTo(int x, int y) {//5  Ӣ�ۻ�������ƶ�, x��y�������x��y����
    	
    	this.x = x - this.width/2; // Ӣ�ۻ�������this.x  = ���x - Ӣ�ۻ��Ŀ�
    	this.y = y - this.height/2;
    }//5
   

    public void addLife() {//6    ��Ϊ������Ӣ�ۻ�����
    	life++;
    }
    
    public int getLife() {//6   ��Ϊlife˽�У���������������life
    	return life;
    }
    
    
    
    public void addFire() {//6    Ӣ�ۻ�������
    	fire+= 40;
    }
    
    public void subtractLife() {//6   ������1
    	life--;
    }
    public void clearFire() {//6     ��ջ���ֵ
    	fire=0;
    }
    
    
    
    
    
    

}
