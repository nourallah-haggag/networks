package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class initialScreenController {
	@FXML
	private ComboBox dropdown ; // selecting interface 
	public static List<PcapIf> alldevs = new ArrayList<PcapIf>();
	public static  List<String> alldevsString = new ArrayList<String>();
	public static  StringBuilder errbuf = new StringBuilder();
	public static Pcap pcap ;
	static boolean capturing;

	static PcapPacketHandler<String> jpacketHandler;

	@FXML
	private Button start;
	@FXML
	private Button stop;
	int counter = 0 ;
	public void initialize ()
	{
		
		
		int r = Pcap.findAllDevs(alldevs, errbuf);
		for (PcapIf item: alldevs)
		{
			alldevsString.add(item.getDescription());
		}
		this.dropdown.getItems().addAll(alldevsString);
	}
	
	public  void selectInterfaceAction( ActionEvent event) // on select interface action 
	{
		int index=  this.dropdown.getSelectionModel().getSelectedIndex();
		
		System.out.println(alldevs.get(index));
		PcapIf device = alldevs.get(index);
	
		int snaplen = 64 * 1024;           // Capture all packets, no trucation  
        int flags = Pcap.MODE_PROMISCUOUS; // capture all packets  
        int timeout = 10 * 1000;           // 10 seconds in millis  
         pcap =  
            Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);  
  
        if (pcap == null) {  
            System.err.printf("Error while opening device for capture: "  
                + errbuf.toString());  
            return;  
        }

		
	}
	public void CaptureStart(ActionEvent event)
	{
		CaptureThread captureThread = new CaptureThread();
		capturing = true;

		jpacketHandler = new PcapPacketHandler<String>() {



			@Override
			public void nextPacket(PcapPacket packet, String user) {
				// TODO Auto-generated method stub
				 System.out.printf("Received packet at %s caplen=%-4d len=%-4d %s\n",
		                    new Date(packet.getCaptureHeader().timestampInMillis()),
		                    packet.getCaptureHeader().caplen(),  // Length actually captured
		                    packet.getCaptureHeader().wirelen(), // Original length
		                    user                                 // User supplied object
		                    );

			}
        };

        captureThread.start();


		counter++;
	}
	public void CaptureStop(ActionEvent event)
	{
		capturing = false;

	}
	

}
