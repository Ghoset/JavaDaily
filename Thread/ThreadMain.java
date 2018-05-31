package com.ghoset.thread;

import java.util.ArrayList;
import java.util.List;

public class ThreadMain {
    private List<Egg > eggs = null;
    /**
     * 鸡蛋
     * @author Administrator
     *
     */
    private class Egg{
	private int egg_id;
	public Egg(int egg_id ) {
	    this.egg_id = egg_id;
	}
	public void setEgg_id(int egg_id) {
	    this.egg_id = egg_id;
	}
	public int getEgg_id() {
	    return egg_id;
	}
    }
    
    /**
     * 母鸡 , 生产鸡蛋
     * @author Administrator
     *
     */
    private class Hen{
	private int egg_id;
	public void setEgg_id(int egg_id) {
	    this.egg_id = egg_id;
	}
//	public Hen(int egg_id ) {
//	    this.setEgg_id(egg_id );
//	}
	
	/**
	 * 下蛋
	 */
	public void addEgg() {
	    if(null == eggs ) {
		return;
	    }
	    eggs.add(new Egg(egg_id ) );
	    System.out.println("你好, 我是母鸡 , 我下蛋了 , 蛋的编号为:" + egg_id );
	}
	
    }
    
    /**
     * 小孩 , 吃鸡蛋
     */
    private class Boy{
	public void eatEgg() {
	    if(null == eggs && eggs.size() == 0 ) {
		return;
	    }
	    Egg egg = eggs.remove(0 );
	    System.out.println("我吃掉了 , 编号为:" + egg.getEgg_id() );
	}
	
    }

    
    public class HenThread extends Thread{
	@Override
	public void run() {
	    Hen hen = new Hen();
	    int egg_id = 0;
	    while(true ) {
		synchronized (eggs ) {
		    hen.setEgg_id(egg_id );
		    hen.addEgg();
		    eggs.notify(); //通知其它线程执行
		    egg_id++;
		}
		try {
		    Thread.sleep(2000 ); //让其它线程有时间来执行
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	}
	
    }
    
    public class BoyThread extends Thread{
	@Override
	public void run() {
	    Boy boy = new Boy();
	    while(true ) {
		synchronized (eggs ) {
		    try {
			eggs.wait();
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }
		    boy.eatEgg();
		}
		try {
		    Thread.sleep(1000 );
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	    
	}
	
    }
    public void test() {
	eggs = new ArrayList<Egg >();
	HenThread ht = new HenThread();
	BoyThread bt = new BoyThread();
	ht.start();
	bt.start();
    }
    
    
    public static void main(String[] args) {
	new ThreadMain().test();
    }

}
