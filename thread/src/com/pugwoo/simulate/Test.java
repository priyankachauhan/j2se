package com.pugwoo.simulate;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		Internet internet = new Internet();
		internet.start();

		final Computer computers[] = new Computer[10];
		for (int i = 0; i < computers.length; i++) {
			computers[i] = new Computer("c" + i, i) {
				protected List<Integer> hosts = new ArrayList<Integer>();
				@Override
				protected void handle(Object message) {
					if(this.getIp() == 0)
						send("to 1", 1);
					else if(this.getIp() == 1) 
						send("to 0", 0);
					super.handle(message);
				}
			};
			internet.add(computers[i]);
			computers[i].start();
		}
		
		computers[0].send("begin", 1);

//		for (int i = 0; i < computers.length; i++) {
//			Thread.sleep(10);
//			computers[i].send("hello from " + i, 0);
//		}
		
//		Thread.sleep(100);
//		for (int i = 0; i < computers.length; i++) {
//			computers[i].halt();
//		}
//		internet.halt();
	}

}
