package de.feuerwehr_inningen.alarmappWindows.Server;

import de.feuerwehr_inningen.alarmappWindows.Client.*;
import java.io.*;
import java.net.*;
import java.util.Properties;
import com.alee.managers.notification.NotificationIcon;

/**
 * 
 * Diese Klasse ist f�r die Ausf�hrung auf dem Alarmierungsserver gedacht. Diese Klasse empf�ngt die �bergebenen Parameter und leitet diese an den Client weiter
 *  
 * 
 **/

 
public class AlarmAppServer {
	
	public static Properties ServerClientProp = new Properties();
	
    public static void main(String[] args) throws IOException {
         
    	//Diese try-Block wird vor Release einkommentiert. Dies dient in der Entwicklungsumgebung zum Testen des Clients und Servers
    	//Die Test-Variablen k�nnen im verwendet werden, wenn als erster �bergabeparameter "debug" �bergeben wird.
    	
//    	try {
//    		if (args[0].equals("debug")) {
    			args = new String [4];    	
    	    	args[0] = "-stichw#Brand?B3";
    	    	args[1] = "-strasse#Oktavianstra�e?29a";
    	    	args[2] = "-ort#Inningen?Augsburg";
    	    	args[3] = "-datum#10.09.2015?13:09:15";
//    		}
//    	} catch (ArrayIndexOutOfBoundsException e) {
//    		    		
//    	}
    	
    	//Lade Properties-Datei aus Work-Verzeichnis
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
    	
		//Setzte Empf�nger und Port mit Werten aus den Properties
        String hostName = ServerClientProp.getProperty("receiverName");
        int portNumber = Integer.parseInt(ServerClientProp.getProperty("receiverPort"));
 
        
        try {
        	//�ffne Verbindung zu Client
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            
        	//Hole den Alarmtext aus den �bergabeparametern und formatiere ihn
        	String alarmtext = "";
            for (int i=0;i<args.length;i++) {
            	String param = args[i];
            	
            	//Ersetzte ? durch Leerzeichen
            	param = param.replace("?", " ");
            	
            	//Teile String nach Parametername
            	String param2[] = param.split("#");
            	
            	//Speichere zweiten Teilstring ab
            	alarmtext = alarmtext + param2[1]+"\n";
            }
            
//            System.out.println("Sending Alarmtext...");
            
            //Zeige Popup an, dass Nachricht an Client gesendet wird
            new AlarmHandler("<html><body>Sende Alarmtext an Client '" + hostName+"'</body></html>", false, 5, NotificationIcon.information);
            
            //Schicke Nachricht an Client
            out.println(alarmtext);
            
            //Schlie�e die Verbindung
            kkSocket.close();
            
        //FEHLERBEHANDLUNG
        } catch (UnknownHostException e) {
        	
        	//Clientadresse ung�ltig
        	new AlarmHandler("<html><body><font size=5>FEHLER</font><br>Host nicht gefunden. Hostname: " + hostName+"<br><br>Programm wird beendet</body></html>", false, 5, NotificationIcon.error);
            try {
				Thread.sleep(6000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
        } catch (IOException e) {
        	
        	//Verbindung nicht m�glich
        	new AlarmHandler("<html><body><font size=5>FEHLER</font><br>Konnte keine Verbindung zu '" + hostName+"' aufbauen.<br>Ist der Client gestartet oder eine Firewall aktiv?<br><br>Programm wird beendet</body></html>", false, 5, NotificationIcon.error);
        	try {
				Thread.sleep(6000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
        }
        System.exit(1);
    }
}