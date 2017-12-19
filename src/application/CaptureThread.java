package application;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class CaptureThread extends Service {
	void CaptureThread() { //const
		
	}
	
	
	@Override
	protected Task createTask() {  
		return new Task (){

			@Override
			protected Object call() throws Exception {
				// TODO Auto-generated method stub
				while(initialScreenController.capturing)
				{
					initialScreenController.pcap.loop(1, initialScreenController.jpacketHandler , "");
				}
				return null;
			}
		
		};
		
		
		// TODO Auto-generated method stub
		//return null;
	} 
	
	

}
