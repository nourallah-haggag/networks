package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Http;

public class initialScreenController {
	public static Ip4 ip = new Ip4();
	public static Http http = new Http();
	@FXML
	private ComboBox dropdown ; // selecting interface 
	public static List<PcapIf> alldevs = new ArrayList<PcapIf>();
	public static  List<String> alldevsString = new ArrayList<String>();
	public static  StringBuilder errbuf = new StringBuilder();
	public static Pcap pcap ;
	static boolean capturing;

	static PcapPacketHandler<String> jpacketHandler;

	public static ObservableList<RowObject> packetsList = FXCollections.observableArrayList();
	public static ObservableList<RowObject> filteredList = FXCollections.observableArrayList();

	@FXML
	private Button start;
	@FXML
	private Button stop;
	int counter = 0 ;
	@FXML
	TableView<RowObject> table;
	@FXML
	TableColumn<RowObject, String> numCol;
	@FXML
	TableColumn<RowObject, Date> timeCol;
	@FXML
	TableColumn<RowObject, String> sourceCol;
	@FXML
	TableColumn<RowObject, String> destCol;
	@FXML
	TableColumn<RowObject, String> protocolCol;
	@FXML
	TableColumn<RowObject, String> lenCol;
	@FXML
	TableColumn<RowObject, String> infoCol;
	@FXML
	TextArea details;


	@FXML
			private TextField selectionField ;
	@FXML
			private Button filterButton;
	@FXML
	private Button save;
	@FXML
	private Button load;


	//SortedList<RowObject> sorted ;



	public void initialize ()
	{
		numCol.setCellValueFactory(cellData -> cellData.getValue().noProperty());
		timeCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		sourceCol.setCellValueFactory(cellData -> cellData.getValue().sourceIPProperty());
		destCol.setCellValueFactory(cellData -> cellData.getValue().destinationIPProperty());
		protocolCol.setCellValueFactory(cellData -> cellData.getValue().protocolProperty());
		lenCol.setCellValueFactory(cellData -> cellData.getValue().lengthProperty());
		infoCol.setCellValueFactory(cellData -> cellData.getValue().infoProperty());

		table.setItems(packetsList);
		
		int r = Pcap.findAllDevs(alldevs, errbuf);
		for (PcapIf item: alldevs)
		{
			alldevsString.add(item.getDescription());
		}
		this.dropdown.getItems().addAll(alldevsString);



	}

	public void onFilterAction(ActionEvent event)
	{
		if (this.selectionField.getText().isEmpty())
		{
			filteredList = packetsList; // if the user pressed the filter button and the filter field is empty the old list is returned
		}
		else if (!this.selectionField.getText().isEmpty())
		{
			//if(RowObject.getProtocol())
			System.out.println(this.selectionField.getText());

			for(int i=0 ; i<packetsList.size() ; i++)
			{
				if( ! (packetsList.get(i).getProtocol().trim().equalsIgnoreCase( this.selectionField.getText().trim())))
				{
					System.out.println(packetsList.get(i).getProtocol());
					packetsList.remove(i);
					i--;
					System.out.println(i);
				}

			}

		}

	}


	public void onRowSelection(){
		RowObject selectedPacketDetails = table.getSelectionModel().getSelectedItem();
		String text = selectedPacketDetails.packet.toString();
//        String text = "Package Numer: " + selectedPacketDetails.numProperty().get() + "\nDate: " + selectedPacketDetails.dateProperty().get() + "\nSource IP: " + selectedPacketDetails.sourceIPProperty().get() + "\nDestination IP: " + selectedPacketDetails.destIPProperty().get() + "\nProtocol: " + selectedPacketDetails.protocolProperty().get() + "\nPackage Length: " + selectedPacketDetails.origLenProperty().get();
		details.setText(text);
		System.out.println(text);
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

				bindPacket(packet);
			}

        };

        captureThread.start();


		counter++;
	}
	public static void bindPacket(PcapPacket packet) {

		if (packet.hasHeader(http)) {
			packet.hasHeader(ip);
			String date = String.valueOf(packet.getCaptureHeader().timestampInMillis());
			String sourceIP = FormatUtils.ip(ip.source());
			String destIP = FormatUtils.ip(ip.destination());
			String protocol = "HTTP";
			String origLen = String.valueOf(packet.getCaptureHeader().wirelen());
			String info = "info";

			RowObject pd = new RowObject(date, sourceIP, destIP, origLen, info, protocol, packet);
			initialScreenController.packetsList.add(pd);
		}
		else if(packet.hasHeader(ip)) {
			String date = String.valueOf(packet.getCaptureHeader().timestampInMillis());
			String sourceIP = FormatUtils.ip(ip.source());
			String destIP = FormatUtils.ip(ip.destination());
			String protocol = ip.typeEnum().toString();
			String origLen = String.valueOf(packet.getCaptureHeader().wirelen());
			String info = "info";

			RowObject pd = new RowObject(date, sourceIP, destIP, info, origLen, protocol, packet);
			initialScreenController.packetsList.add(pd);
		}


	}
	public void CaptureStop(ActionEvent event)
	{
		capturing = false;

	}

	public void filter(){
		//sorted.comparatorProperty().bind(table.comparatorProperty());
		table.setItems(filteredList);
	}

	public void save(ActionEvent event){
		PacketIO.saveFile(pcap);
	}

	public void load ( ActionEvent event)
	{
		PacketIO fileLoad = new PacketIO("haggag.cap");
		try {
			fileLoad.loadFile();
		} catch (Exception exceptionReadingPcapFiles) {
			exceptionReadingPcapFiles.printStackTrace();
		}
	}




	

}
