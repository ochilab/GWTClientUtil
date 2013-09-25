package org.ochilab.gwt.client.util;

import java.util.LinkedList;
import com.google.gwt.user.client.Timer;

public class SynchronizedRPCMethodController extends Timer{

	LinkedList<SynchronizedMethodHandler> mList = new LinkedList<SynchronizedMethodHandler>();
	
	public boolean flag = false;//ここがtrueにならないと次のメソッドを実行しない
	
	private SynchronizedMethodHandler c; //メソッドが定義されたクラス（インタフェースのほうがいいかも）
	private int msec;
		
	public SynchronizedRPCMethodController(int msec){
		this.msec=msec;
	}
	
	public SynchronizedRPCMethodController(){
		this.msec=10;
	}
	
	
	public void add(SynchronizedMethodHandler c){

		//メソッドリストに格納
		mList.add(c);
		this.scheduleRepeating(10);
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub	
   	 if(flag==true){		 		 
		 if(mList.size()>0){
			// flag=false;
			 this.cancel();
			 c = mList.getFirst();
			 mList.removeFirst();
			 c.run(); //メソッドの実行
			 //this.scheduleRepeating(10);
			
		 }
	 }
	 else{
		 //System.out.println("false");

	 }
   	 //System.out.println("Timer working!");
		
	}
	
	public void start(){
		flag=true;
		scheduleRepeating(msec);
	}
	
	public void next(){
		flag=true; 
		scheduleRepeating(msec);
	}
	
	public void end(){
		  add(new SynchronizedMethodHandler(){
			  public void run() {
					mList.removeAll(mList);
					System.out.println("removeAll");
					cancel();
					flag=false;
				}
		  });
	}
	
}
