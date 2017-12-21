package application;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Http;

public class CaptureThread extends Service {


	public CaptureThread() { //const

	}


	@Override
	protected Task createTask() {
		return new Task() {

			@Override
			protected Object call() throws Exception {
				// TODO Auto-generated method stub
				while (initialScreenController.capturing) {
					initialScreenController.pcap.loop(1, initialScreenController.jpacketHandler, "");
				}
				return null;
			}

		};


		// TODO Auto-generated method stub
		//return null;
	}



}


