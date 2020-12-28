package cn.tedu.shoot;

import java.util.Arrays;

public class Test {
	package cn.tedu.shoot;
	//**������*/
	import javax.swing.JPanel;
	import javax.swing.JFrame;
	import java.util.Arrays;
	import java.awt.Graphics;//3
	import java.util.Timer;//4
	import java.util.TimerTask;//4
	import java.util.Random;//4
	import java.util.Arrays;//4

	import java.awt.event.MouseAdapter;//5
	import java.awt.event.MouseEvent;//5
	public class World extends JPanel{
		
		public static final int WIDTH = 400;
		public static final int HEIGHT = 700;
		public static final int START = 0;//7            ����״̬
		public static final int RUNNING = 1;
		public static final int PAUSE = 2;
		public static final int GAMEOVER = 3;
		private int state = START; //7           ��ǰ״̬Ĭ��ΪSTART
		
		private Sky sky= new Sky() ;//2
		private Hero hero= new Hero();//2
		private FlyingObject[] enemies= {};//3
//			new Airplane(),//3
//			new BigAirplane(),//3
//			new Bee()	//3
		private Bullet[] bullets= {};//3
//			new Bullet(100,200)//3
		
		
		
		public FlyingObject nextOne() {//4    ���˵����ɸ���
			Random rand = new Random();
			int type= rand.nextInt(20);
			if(type<5) {
				return new Bee();
			}else if (type<13) {
				
				return new Airplane();
			}else {
				return new BigAirplane();
			}
		}//4
		
		private int enterIndex = 0;//4      �����볡
		public void enterAction() {//4      ÿ10����һ��(̫���ˣ�������)
			enterIndex++;
			if(enterIndex%40 == 0) {//   ÿ400����һ��
				FlyingObject obj = nextOne();// ��ȡ���˶���
				enemies = Arrays.copyOf(enemies, enemies.length+1);
				enemies[enemies.length-1] = obj;
			}
			
		}//4
		
		
		private int shootIndex = 0;//4   �ӵ��볡
		public void shootAction() { //  ÿ10msһ��
			shootIndex++;
			if(shootIndex%30 == 0) {
				Bullet[] bs = hero.shoot();
				bullets = Arrays.copyOf(bullets, bullets.length + bs.length); //������+1����+2
				System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);// �ڵ���1��2λ �����bs��Ԫ��
			}
		}//4
		
		
		public void stepAction() {//5       �������ƶ� ÿ10msһ
			sky.step();//��ն�
			for(int i = 0;i<enemies.length;i++) {
				enemies[i].step();//���˶�
			}
			for(int i = 0;i<bullets.length;i++) {
				bullets[i].step();//�ӵ���
			}
		}
		
		
		public void outOfBoundsAction() {//5  ɾ��Խ��Ԫ��    ÿ10msһ�Ρ���
			for(int i=0;i<enemies.length;i++) {//  �������е���
				if(enemies[i].isOutBounds() || enemies[i].isRemove()) {//7    �������  ���� Remove   ɾ��
					enemies[i]= enemies[enemies.length-1];// ��ƻ���滻��ƻ��
					enemies= Arrays.copyOf(enemies, enemies.length-1);// ȥ�����һ��Ԫ��
				}
			}
			for(int i=0; i<bullets.length;i++) {// �����ӵ� 
				if(bullets[i].isOutBounds() || bullets[i].isRemove()) {
					bullets[i]= bullets[bullets.length-1];
					bullets= Arrays.copyOf(bullets, bullets.length-1);
				}
			}
		}//5
		
		
		private int score = 0;//6            ��ҵĵ÷�
		public void bulletBangAction() {//6     �ӵ�����˵���ײ    ÿ10msһ��
			for(int i=0; i<bullets.length;i++) {//����ÿ���ӵ�
				Bullet b= bullets[i];
				for(int j=0; j<enemies.length;j++) {//ÿ���ӵ��� ÿ�����˱Ƚ�
					FlyingObject f = enemies[j];
					if(b.isLive() && f.isLive() && f.isHit(b)) {//�������Ų�����ײ
						b.goDead();// �ӵ�ȥ��
						f.goDead();// ����ȥ��
						if(f instanceof EnemyScore) {//����ײ����Ϊ  ��
							EnemyScore es = (EnemyScore)f;//ǿתΪ�÷ֽӿ�
							score+= es.getScore();
						}
						if(f instanceof EnemyAward) {//����ײ����Ϊ  ����
							EnemyAward ea = (EnemyAward)f;//ǿתΪ�����ӿ�
							int type = ea.getAwardType();// ��ȡ��������
							switch(type) {
							case EnemyAward.FIRE://����������  �ǻ���
								hero.addFire();
								break;
							case EnemyAward.LIFE://����������  ������
								hero.addLife();
								break;
							}
						}
					}
				}
			}
		}//6
		
		public void heroBangAction() {//   Ӣ�ۻ����ӵ���ײ
			for(int i=0; i<enemies.length;i++) {//   ��������
				FlyingObject f = enemies[i];
				if(hero.isLive() && f.isLive() && f.isHit(hero)) {//�����ײ
					f.goDead();//  ������
					hero.subtractLife();//Ӣ�ۻ�������
					hero.clearFire();//Ӣ�ۻ���ջ���
				}
			}
		}
		
		
		
		
		public void checkGameOverAction() {//7   �����Ϸ����   ÿ10msһ��
			if(hero.getLife() <= 0) { // ��С�ڵ���0
				state = GAMEOVER;
			}
		}
		
		
		
		
		
		public void action() {
			
//			FlyingObject[] enemies= {};//2
//			FlyingObject[] enemies= new FlyingObject[0];
//			System.out.println(enemies.length);
//			FlyingObject[] b= Arrays.copyOf(enemies, 2);
//			System.out.println(b.length);
			
//			enemies= new FlyingObject[8];
//			enemies[0]= new Airplane();
//			enemies[1]= new Airplane();
//			enemies[2]= new BigAirplane();
//			enemies[3]= new BigAirplane();
//			enemies[4]= new Bee();
//			enemies[5]= new Sky();
//			enemies[6]= new Hero();
//			enemies[7]= new Bullet(30,200);
//			for(int i=0;i<enemies.length;i++) {
//				enemies[i].step();
//			}
//			System.out.println(Images.heros.length);//2
			
			MouseAdapter m = new MouseAdapter() {//5     ������
				public void mouseMoved(MouseEvent e) {//     ��дMouseAdapter�����ͨ����------���    �ƶ��ƶ��ƶ�
					if(state == RUNNING) {//7          ��������״̬��ִ��
						int x = e.getX();//   �õ�����x��y����
						int y = e.getY();
						hero.moveTo(x, y);
					}	
				}
				public void mouseClicked(MouseEvent e) {//   ��дMouseAdapter�����ͨ����-------���   ���������
					switch(state) {//     ���ݵ�ǰ״̬����ͬ����
					case START:
						state = RUNNING;//  ����  ���޸�Ϊ ����
						break;
					case GAMEOVER:
						score = 0;//      ��Ϸ��Ҫ��ʼ������
						sky = new Sky();// �ڴ��ַ����0x2222��ԭ����sky������������
						hero = new Hero();
						enemies = new FlyingObject[0];
						bullets = new Bullet[0];
						state = START;// ��Ϸ���� ���Ϊ  ����
						break;
					}
				}	
				
				public void mouseExited(MouseEvent e) {//     ����Ƴ�������ͣ
					if(state == RUNNING) {
						state = PAUSE;
					}
					
				}
				
				public void mouseEntered(MouseEvent e) {//    ������룬������
					if(state == PAUSE) {
						state = RUNNING;
					}
				}
				
				
				
			};//5         
			this.addMouseListener(m);//    �����������¼�
			this.addMouseMotionListener(m);//    ������껬���¼�
			
			
			Timer timer= new Timer();//4         ��ʱ��
			int intervel = 10;
			timer.schedule(new TimerTask() {
				 public void run() {    //10msһ��
					if(state == RUNNING) {//7               ��������֮��ִ��
						enterAction();// ���˽���
					
						shootAction();// �ӵ����
					
						stepAction();//5     �������ƶ�
					
						outOfBoundsAction();//5         ɾ��Խ��Ԫ��
					
						bulletBangAction();//6       �ӵ�����˵���ײ
					
						heroBangAction(); //6         Ӣ�ۻ�����˵���ײ
				
						checkGameOverAction(); //7        �����Ϸ����
					}
					repaint();//4   10msһ��
				}
			}, intervel, intervel);//4
		}
			
		
			public void paint(Graphics g) {//3    10msһ��
				g.drawImage(Images.sky,sky.x,sky.y,null);//�����
				g.drawImage(sky.getImage(),sky.x,sky.getY1(),null);//�����2
				g.drawImage(hero.getImage(),hero.x,hero.y,null);
				for(int i=0;i<enemies.length;i++) {
					FlyingObject f = enemies[i];
					g.drawImage(f.getImage(),f.x,f.y,null);
				}
				for(int i=0;i<bullets.length;i++) {
					Bullet b = bullets[i];
					g.drawImage(b.getImage(),b.x,b.y,null);
				}//3
				
				g.drawString("SCORE: "+ score, 10, 25 );//6   ����
				g.drawString("LIFE: "+ hero.getLife(), 10, 45);//6   ����
			
				switch(state) {//7          ����״̬ͼ
				case START:
					g.drawImage(Images.start,0,0,null);
					break;
				case PAUSE:
					g.drawImage(Images.pause,0,0,null);
					break;	
				case GAMEOVER:
					g.drawImage(Images.gameover,0,0,null);
					break;	
				}
				
			
			}
			
			
			
			

		public static void main(String[] args) {
			JFrame frame = new JFrame();
			World world = new World();
			frame.add(world);
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(WIDTH, HEIGHT);
			frame.setLocationRelativeTo(null); 
			frame.setVisible(true); //���ڿɼ�+����paint�������ڳ���jpanel��Ĭ��ʲô��û�У����ã�Ҫ��д
			
			world.action();
			
		}

	}

}
