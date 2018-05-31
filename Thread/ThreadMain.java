package com.ghoset.thread;

import java.util.ArrayList;
import java.util.List;

public class ThreadMain {
    private List<Egg > eggs = null;
    /**
     * ����
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
     * ĸ�� , ��������
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
	 * �µ�
	 */
	public void addEgg() {
	    if(null == eggs ) {
		return;
	    }
	    eggs.add(new Egg(egg_id ) );
	    System.out.println("���, ����ĸ�� , ���µ��� , ���ı��Ϊ:" + egg_id );
	}
	
    }
    
    /**
     * С�� , �Լ���
     */
    private class Boy{
	public void eatEgg() {
	    if(null == eggs && eggs.size() == 0 ) {
		return;
	    }
	    Egg egg = eggs.remove(0 );
	    System.out.println("�ҳԵ��� , ���Ϊ:" + egg.getEgg_id() );
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
		    eggs.notify(); //֪ͨ�����߳�ִ��
		    egg_id++;
		}
		try {
		    Thread.sleep(2000 ); //�������߳���ʱ����ִ��
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
