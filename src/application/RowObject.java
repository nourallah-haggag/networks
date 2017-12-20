package application;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jnetpcap.packet.PcapPacket;
import java.time.LocalTime;
import java.util.Date;

public class RowObject {


    private ObjectProperty<Date> date ;



    static private int count=0 ;
    private  final StringProperty no;
    private  final StringProperty sourceIP;
    private   final StringProperty destinationIP;
    private   final StringProperty length;
    private   final StringProperty info;
    private   final StringProperty protocol;
    PcapPacket packet;
    public  RowObject(String date /*,String no*/ , String sourceIP , String destinationIP , String info , String length , String protocol, PcapPacket packet ){
      this.date=new SimpleObjectProperty(date);
        this.no = new SimpleStringProperty(String.valueOf(++count));
        this.sourceIP = new SimpleStringProperty(sourceIP);
        this.destinationIP = new SimpleStringProperty(destinationIP);
        this.length = new SimpleStringProperty(length);
        this.info = new SimpleStringProperty(info);
        this.packet = packet;
        this.protocol = new SimpleStringProperty(protocol);






    }
    public Date getDate() {
        return date.get();
    }

    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public static int getCount() {
        return count;
    }

    public String getNo() {
        return no.get();
    }

    public StringProperty noProperty() {
        return no;
    }
    public String getProtocol() {
        return protocol.get();
    }

    public StringProperty protocolProperty() {
        return protocol;
    }

    public String getSourceIP() {
        return sourceIP.get();
    }

    public StringProperty sourceIPProperty() {
        return sourceIP;
    }

    public String getDestinationIP() {
        return destinationIP.get();
    }

    public StringProperty destinationIPProperty() {
        return destinationIP;
    }

    public String getLength() {
        return length.get();
    }

    public StringProperty lengthProperty() {
        return length;
    }

    public String getInfo() {
        return info.get();
    }

    public StringProperty infoProperty() {
        return info;
    }

    public PcapPacket getPacket() {
        return packet;
    }

}
