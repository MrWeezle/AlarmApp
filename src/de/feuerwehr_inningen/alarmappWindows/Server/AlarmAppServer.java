package de.feuerwehr_inningen.alarmappWindows.Server;

import de.feuerwehr_inningen.alarmappWindows.Client.*;

import java.io.*;
import java.net.*;
import java.util.Properties;

import com.alee.managers.notification.NotificationIcon;
 
public class AlarmAppServer {
	
	public static Properties ServerClientProp = new Properties();
	
    public static void main(String[] args) throws IOException {
         
//        if (args.length != 2) {
//            System.err.println(
//                "Usage: java EchoClient <host name> <port number>");
//            System.exit(1);
//        }
//    	try {
//    		if (args[0].equals("debug")) {
    			args = new String [3];    	
    	    	args[0] = "-stichw#Brand?B3";
    	    	args[1] = "-adresse#Oktavianstraﬂe?29a,?Inningen?Augsburg";
    	    	args[2] = "-datum#10.09.2015?13:09:15";
//    	    	args[3] = "-zeit#13:09:15";
//    		}
//    	} catch (ArrayIndexOutOfBoundsException e) {
//    		    		
//    	}
    	
    	FileInputStream is = null;
		try {
			is = new FileInputStream(System.getProperty("user.dir") + "/ServerClient.properties");
			ServerClientProp.load(is);
			is.close();
		} catch (IOException e) {
			new AlarmHandler("<html><body><font size=5>FEHLER</font><br>Properties-Datei hat einen Fehler order ist nicht vorhanden!<br>Programm wird beendet</body></html>", false, 5, NotificationIcon.error);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.exit(1);
		} 
    	
    	
        String hostName = ServerClientProp.getProperty("receiverName");
        int portNumber = Integer.parseInt(ServerClientProp.getProperty("receiverPort"));
 
        try (
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(kkSocket.getInputStream()));
        ) {
        	
        	String alarmtext = "";
            for (int i=0;i<args.length;i++) {
            	String param = args[i];
            	param = param.replace("?", " ");
            	String param2[] = param.split("#");
            	alarmtext = alarmtext + param2[1]+"\n";
            }
            
            System.out.println("Client: "+alarmtext);
            System.out.println("Sending Alarmtext...");
            new AlarmHandler("<html><body>Sende Alarmtext an Client '" + hostName+"'</body></html>", false, 5, NotificationIcon.information);
 
            out.println(alarmtext);

        } catch (UnknownHostException e) {
        	new AlarmHandler("<html><body><font size=5>FEHLER</font><br>Host nicht gefunden. Hostname: " + hostName+"<br>Programm wird beendet</body></html>", false, 5, NotificationIcon.error);
            try {
				Thread.sleep(6000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            System.exit(1);
        } catch (IOException e) {
        	new AlarmHandler("<html><body><font size=5>FEHLER</font><br>Konnte keine Verbindung zu '" + hostName+"' aufbauen.<br>Ist der Client gestartet?<br>Programm wird beendet</body></html>", false, 5, NotificationIcon.error);
        	try {
				Thread.sleep(6000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        System.exit(1);
    }
}