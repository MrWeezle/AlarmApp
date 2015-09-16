package de.feuerwehr_inningen.alarmappWindows.Server;

import de.feuerwehr_inningen.alarmappWindows.Client.AlarmHandler;

import java.io.*;
import java.net.*;
import java.util.Properties;

import com.alee.managers.notification.NotificationIcon;

/**
 * 
 * Diese Klasse ist für die Ausführung auf dem Alarmierungsserver gedacht. Diese Klasse empfängt die übergebenen Parameter und leitet diese an den Client weiter
 *  
 * 
 **/

 
public class AlarmAppServer {
	
	public static Properties ServerClientProp = new Properties();
	private static int portNumber = 11114;
	public static String hostName = "";
	
    public static void main(String[] args) throws IOException {
         
    	//Diese try-Block wird vor Release einkommentiert. Dies dient in der Entwicklungsumgebung zum Testen des Clients und Servers
    	//Die Test-Variablen können im verwendet werden, wenn als erster Übergabeparameter "debug" übergeben wird.
    	
    	try {
    		if (args[0].equals("debug")) {
    			args = new String [4];    	
    	    	args[0] = "-stichw#Brand?B3";
    	    	args[1] = "-strasse#Oktavianstraße?29a,";
    	    	args[2] = "-ort#Inningen?Augsburg";
    	    	args[3] = "-datum#10.09.2015?13:09:15";
    		}
    	} catch (ArrayIndexOutOfBoundsException e) {
    		    		
    	}
    	
    	//Lade Properties-Datei aus Work-Verzeichnis
    	FileInputStream is = null;
		try {
			is = new FileInputStream(System.getProperty("user.dir") + "/ServerClient.properties");
			ServerClientProp.load(is);
			is.close();
		} catch (IOException e) {
			new AlarmHandler("<html><body><font size=5>FEHLER</font><br><br><b>Properties-Fehler</b><br>Datei hat einen Fehler order ist nicht vorhanden!<br>Programm wird beendet.</body></html>", false, 5, NotificationIcon.error,null);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
	        	new AlarmHandler("<html><body><font size=5>FEHLER</font><br><br>"+e.getMessage()+"</body></html>", false, 10, NotificationIcon.error,null);
	        	try {
					Thread.sleep(10000);
				} catch (InterruptedException e2) {
				}
	        }
			System.exit(1);
		}     	
    	
		//Setzte Empfänger und Port mit Werten aus den Properties
        try {
        	hostName = ServerClientProp.getProperty("receiverName","localhost");
        	portNumber = Integer.parseInt(ServerClientProp.getProperty("receiverPort","11114"));
        } catch (NumberFormatException e) {
			new AlarmHandler("<html><body><font size=5>FEHLER</font><br><br><b>Properties-Fehler</b><br>Portnummer ist keine Zahl!<br>defaultwerte werden verwendet.<br>Port: 11114<br>Dauer: 30 Sekunden<br><br>Programm wird beendet.</body></html>", false, 5, NotificationIcon.error,null);
		} catch (NullPointerException e) {
			new AlarmHandler("<html><body><font size=5>FEHLER</font><br><br><b>Properties-Fehler</b><br>Portnummer oder Hostname Parameter ist nicht vorhanden!<br>Programm wird beendet.</body></html>", false, 5, NotificationIcon.error,null);
		} catch (Exception e) {
        	new AlarmHandler("<html><body><font size=5>FEHLER</font><br><br>"+e.getMessage()+"</body></html>", false, 10, NotificationIcon.error,null);
        	try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
			}
            System.exit(1);
        }
 
        
        try {
        	//Öffne Verbindung zu Client
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            
        	//Hole den Alarmtext aus den Übergabeparametern und formatiere ihn
        	String alarmtext = "";
            for (int i=0;i<args.length;i++) {
            	String param = args[i];
            	
            	//Ersetzte ? durch Leerzeichen
            	param = param.replace("?", " ");
            	
            	if (Integer.parseInt(ServerClientProp.getProperty("adressDisplay", "2")) >= 2)
            		param = param.replace("§", "<br>");
            	else
            		param = param.replace("§", ",");
            	
            	//Teile String nach Parametername
            	String param2[] = param.split("#");
            	
            	//Speichere zweiten Teilstring ab
            	alarmtext = alarmtext + param2[1]+"\n";
            }
            
//            System.out.println("Sending Alarmtext...");
            
            //Zeige Popup an, dass Nachricht an Client gesendet wird
            new AlarmHandler("<html><body>Sende Alarmtext an Client '" + hostName+"'</body></html>", false, 5, NotificationIcon.information,null);
            
            //Schicke Nachricht an Client
            out.println(alarmtext);
            
            //Schließe die Verbindung
            kkSocket.close();
            
        //FEHLERBEHANDLUNG
        } catch (UnknownHostException e) {
        	
        	//Clientadresse ungültig
        	new AlarmHandler("<html><body><font size=5>FEHLER</font><br><br><b>Netzwerkfehler</b><br>Host nicht gefunden. Hostname: " + hostName+"<br><br>Programm wird beendet</body></html>", false, 5, NotificationIcon.error,null);
            try {
				Thread.sleep(6000);
			} catch (Exception e1) {
				new AlarmHandler("<html><body><font size=5>FEHLER</font><br><br>"+e.getMessage()+"</body></html>", false, 10, NotificationIcon.error,null);
				try {
					Thread.sleep(10000);
				} catch (Exception e2) {
				}
	        	System.exit(1);
			} 
        } catch (IOException e) {
        	
        	//Verbindung nicht möglich
        	new AlarmHandler("<html><body><font size=5>FEHLER</font><br><br><b>Netzwerkfehler</b><br>Konnte keine Verbindung zu '" + hostName+"' aufbauen.<br>Ist der Client gestartet oder eine Firewall aktiv?<br><br>Programm wird beendet</body></html>", false, 5, NotificationIcon.error,null);
        	try {
				Thread.sleep(6000);
			} catch (Exception e1) {
	        	new AlarmHandler("<html><body><font size=5>FEHLER</font><br><br>"+e.getMessage()+"</body></html>", false, 10, NotificationIcon.error,null);	
	        	try {
					Thread.sleep(10000);
				} catch (Exception e2) {
				}
	        	
			}
        	System.exit(1);
        }  catch (Exception e) {
        	new AlarmHandler("<html><body><font size=5>FEHLER</font><br><br>"+e.getMessage()+"</body></html>", false, 10, NotificationIcon.error,null);
        	try {
				Thread.sleep(10000);
			} catch (Exception e2) {
			}
        	
        }
        System.exit(1);
    }
    
}