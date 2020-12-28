package cn.tedu.shoot;

import java.util.Arrays;

public class Test {
	package cn.tedu.shoot;
	//**窗口类*/
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
		public static final int START = 0;//7            启动状态
		public static final int RUNNING = 1;
		public static final int PAUSE = 2;
		public static final int GAMEOVER = 3;
		private int state = START; //7           当前状态默认为START
		
		private Sky sky= new Sky() ;//2
		private Hero hero= new Hero();//2
		private FlyingObject[] enemies= {};//3
//			new Airplane(),//3
//			new BigAirplane(),//3
//			new Bee()	//3
		private Bullet[] bullets= {};//3
//			new Bullet(100,200)//3
		
		
		
		public FlyingObject nextOne() {//4    敌人的生成概率
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
		
		private int enterIndex = 0;//4      敌人入场
		public void enterAction() {//4      每10毫秒一次(太快了！！！！)
			enterIndex++;
			if(enterIndex%40 == 0) {//   每400毫秒一次
				FlyingObject obj = nextOne();// 获取敌人对象
				enemies = Arrays.copyOf(enemies, enemies.length+1);
				enemies[enemies.length-1] = obj;
			}
			
		}//4
		
		
		private int shootIndex = 0;//4   子弹入场
		public void shootAction() { //  每10ms一次
			shootIndex++;
			if(shootIndex%30 == 0) {
				Bullet[] bs = hero.shoot();
				bullets = Arrays.copyOf(bullets, bullets.length + bs.length); //先扩容+1或者+2
				System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);// 在倒数1或2位 ，添加bs的元素
			}
		}//4
		
		
		public void stepAction() {//5       飞行物移动 每10ms一
			sky.step();//天空动
			for(int i = 0;i<enemies.length;i++) {
				enemies[i].step();//敌人动
			}
			for(int i = 0;i<bullets.length;i++) {
				bullets[i].step();//子弹动
			}
		}
		
		
		public void outOfBoundsAction() {//5  删除越界元素    每10ms一次··
			for(int i=0;i<enemies.length;i++) {//  遍历所有敌人
				if(enemies[i].isOutBounds() || enemies[i].isRemove()) {//7    如果出界  或者 Remove   删除
					enemies[i]= enemies[enemies.length-1];// 好苹果替换坏苹果
					enemies= Arrays.copyOf(enemies, enemies.length-1);// 去掉最后一个元素
				}
			}
			for(int i=0; i<bullets.length;i++) {// 遍历子弹 
				if(bullets[i].isOutBounds() || bullets[i].isRemove()) {
					bullets[i]= bullets[bullets.length-1];
					bullets= Arrays.copyOf(bullets, bullets.length-1);
				}
			}
		}//5
		
		
		private int score = 0;//6            玩家的得分
		public void bulletBangAction() {//6     子弹与敌人的碰撞    每10ms一次
			for(int i=0; i<bullets.length;i++) {//遍历每个子弹
				Bullet b= bullets[i];
				for(int j=0; j<enemies.length;j++) {//每个子弹和 每个敌人比较
					FlyingObject f = enemies[j];
					if(b.isLive() && f.isLive() && f.isHit(b)) {//若都活着并且相撞
						b.goDead();// 子弹去死
						f.goDead();// 敌人去死
						if(f instanceof EnemyScore) {//若被撞敌人为  分
							EnemyScore es = (EnemyScore)f;//强转为得分接口
							score+= es.getScore();
						}
						if(f instanceof EnemyAward) {//若被撞敌人为  奖励
							EnemyAward ea = (EnemyAward)f;//强转为奖励接口
							int type = ea.getAwardType();// 获取奖励类型
							switch(type) {
							case EnemyAward.FIRE://若奖励类型  是火力
								hero.addFire();
								break;
							case EnemyAward.LIFE://若奖励类型  是生命
								hero.addLife();
								break;
							}
						}
					}
				}
			}
		}//6
		
		public void heroBangAction() {//   英雄机与子弹相撞
			for(int i=0; i<enemies.length;i++) {//   遍历敌人
				FlyingObject f = enemies[i];
				if(hero.isLive() && f.isLive() && f.isHit(hero)) {//如果相撞
					f.goDead();//  敌人死
					hero.subtractLife();//英雄机减生命
					hero.clearFire();//英雄机清空火力
				}
			}
		}
		
		
		
		
		public void checkGameOverAction() {//7   检测游戏结束   每10ms一次
			if(hero.getLife() <= 0) { // 命小于等于0
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
			
			MouseAdapter m = new MouseAdapter() {//5     侦听器
				public void mouseMoved(MouseEvent e) {//     重写MouseAdapter里的普通方法------鼠标    移动移动移动
					if(state == RUNNING) {//7          仅在运行状态下执行
						int x = e.getX();//   得到鼠标的x，y坐标
						int y = e.getY();
						hero.moveTo(x, y);
					}	
				}
				public void mouseClicked(MouseEvent e) {//   重写MouseAdapter里的普通方法-------鼠标   点击点击点击
					switch(state) {//     根据当前状态做不同处理
					case START:
						state = RUNNING;//  启动  后修改为 运行
						break;
					case GAMEOVER:
						score = 0;//      游戏需要初始化数据
						sky = new Sky();// 内存地址换成0x2222，原来的sky对象变成了垃圾
						hero = new Hero();
						enemies = new FlyingObject[0];
						bullets = new Bullet[0];
						state = START;// 游戏结束 后改为  启动
						break;
					}
				}	
				
				public void mouseExited(MouseEvent e) {//     鼠标移出，变暂停
					if(state == RUNNING) {
						state = PAUSE;
					}
					
				}
				
				public void mouseEntered(MouseEvent e) {//    鼠标移入，变运行
					if(state == PAUSE) {
						state = RUNNING;
					}
				}
				
				
				
			};//5         
			this.addMouseListener(m);//    处理鼠标操作事件
			this.addMouseMotionListener(m);//    处理鼠标滑动事件
			
			
			Timer timer= new Timer();//4         定时器
			int intervel = 10;
			timer.schedule(new TimerTask() {
				 public void run() {    //10ms一次
					if(state == RUNNING) {//7               仅在运行之后执行
						enterAction();// 敌人进场
					
						shootAction();// 子弹射出
					
						stepAction();//5     飞行物移动
					
						outOfBoundsAction();//5         删除越界元素
					
						bulletBangAction();//6       子弹与敌人的碰撞
					
						heroBangAction(); //6         英雄机与敌人的碰撞
				
						checkGameOverAction(); //7        检测游戏结束
					}
					repaint();//4   10ms一次
				}
			}, intervel, intervel);//4
		}
			
		
			public void paint(Graphics g) {//3    10ms一次
				g.drawImage(Images.sky,sky.x,sky.y,null);//画天空
				g.drawImage(sky.getImage(),sky.x,sky.getY1(),null);//画天空2
				g.drawImage(hero.getImage(),hero.x,hero.y,null);
				for(int i=0;i<enemies.length;i++) {
					FlyingObject f = enemies[i];
					g.drawImage(f.getImage(),f.x,f.y,null);
				}
				for(int i=0;i<bullets.length;i++) {
					Bullet b = bullets[i];
					g.drawImage(b.getImage(),b.x,b.y,null);
				}//3
				
				g.drawString("SCORE: "+ score, 10, 25 );//6   画分
				g.drawString("LIFE: "+ hero.getLife(), 10, 45);//6   画命
			
				switch(state) {//7          画出状态图
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
			frame.setVisible(true); //窗口可见+调用paint（），在超类jpanel，默认什么都没有，不好，要重写
			
			world.action();
			
		}

	}

}
