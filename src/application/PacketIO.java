package application;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapDLT;
import org.jnetpcap.PcapDumper;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.PeeringException;
import org.jnetpcap.util.PcapPacketArrayList;

import java.nio.ByteBuffer;
import java.util.List;

public class PacketIO {
    String Address;
    public PacketIO(String Address)
    {
        this.Address = Address;
    }

    public void loadFile(){


       /* Pcap pcap = Pcap.openOffline(Address, initialScreenController.errbuf);
        if (pcap == null) {
            System.err.printf("Error while opening device for capture: "
                    + errbuf.toString());
            return;
        }*/

    }

    public static void saveFile(Pcap pcap){
try{
        PcapDumper pdumper =  pcap.dumpOpen("saeed.cap");
        for (RowObject pd : initialScreenController.packetsList) {
            ByteBuffer bbuf = ByteBuffer.allocateDirect(pd.packet.getCaptureHeader().wirelen());
            byte[] bytes = new byte[pd.packet.size()];
            System.out.println("bytes : " + bytes.length);
            pd.packet.transferStateAndDataTo(bytes);
            System.out.println("bytes : " + bytes.length);
            pdumper.dump(pd.packet.getCaptureHeader().timestampInMillis(),pd.packet.getCaptureHeader().hdr_len(),pd.packet.getCaptureHeader().caplen(),pd.packet.getCaptureHeader().wirelen(),bbuf);

        }}
        catch (Exception e){};

    }
    private class ExceptionReadingPcapFiles extends Exception {

    }

    }




