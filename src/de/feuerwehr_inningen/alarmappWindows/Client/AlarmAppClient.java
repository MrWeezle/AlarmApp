package de.feuerwehr_inningen.alarmappWindows.Client;

import java.net.*;
import java.util.Properties;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.JOptionPane;

import com.alee.managers.notification.NotificationIcon;

 
/**
 * 
 * 
 * Diese Klasse ist für die Ausführung auf dem Client gedacht. Diese Klasse empfängt die Alarmierung vom Server, zeigt ein popup an und spielt einen Alarmton ab.
 * 
 * 
 **/


public class AlarmAppClient extends Thread{
	private String arr;
	//Fallback
	private static int notifDuration = 30, portNumber = 11114;
	public static Properties ServerClientProp = new Properties();
	static ServerSocket serverSocket;
	
	public static String sProgammName = "AlarmApp";
	public static String sVersion = "0.1b";
	
    public AlarmAppClient(String alarmtext) {
    	this.arr = alarmtext;
	}

	public static void main(String[] args) throws IOException {
		
		//Lade Properties-Datei aus Work-Verzeichnis
		FileInputStream is = null;
		try {
			is = new FileInputStream(System.getProperty("user.dir")
					+ "/ServerClient.properties");
			ServerClientProp.load(is);
			is.close();
		} catch (IOException e) {
			new AlarmHandler("<html><body><font size=5>FEHLER</font><br><br><b>Properties-Fehler</b><br>Datei hat einen Fehler order ist nicht vorhanden!<br>Programm wird beendet.</body></html>", false, 5, NotificationIcon.error);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
	        	new AlarmHandler("<html><body><font size=5>FEHLER</font><br><br>"+e.getStackTrace()+"</body></html>", false, 10, NotificationIcon.error);
	        	try {
					Thread.sleep(10000);
				} catch (Exception e2) {
				}
	        }
			System.exit(1);
		} 
		
		//Setzte Port und Benachrichtigungsdauer mit Werten aus den Properties
		try {
			portNumber = Integer.parseInt(ServerClientProp.getProperty("receiverPort"));
			notifDuration = Integer.parseInt(ServerClientProp.getProperty("notification"));
		} catch (NumberFormatException e) {
			new AlarmHandler("<html><body><font size=5>WARNUNG</font><br><br><b>Properties-Fehler</b><br>Portnummer oder Benachrichtigungsdauer ist keine Zahl! <br>Defaultwerte werden verwendet.<br><i>Port: 11114<br>Dauer: 30 Sekunden</i><br></body></html>", false, 10, NotificationIcon.warning);
		} catch (NullPointerException e) {
			new AlarmHandler("<html><body><font size=5>WARNUNG</font><br><br><b>Properties-Fehler</b><br>Portnummer oder Benachrichtigungsdauer Parameter ist nicht vorhanden!<br>Defaultwerte werden verwendet.<br><i>Port: 11114<br>Dauer: 30 Sekunden</i><br></body></html>", false, 10, NotificationIcon.error);
		} catch (Exception e) {
        	new AlarmHandler("<html><body><font size=5>FEHLER</font><br><br>"+e.getStackTrace()+"</body></html>", false, 10, NotificationIcon.error);
        	try {
				Thread.sleep(10000);
			} catch (Exception e1) {
			}
            System.exit(1);
        }
		
 
        try {
        	
        	//Öffne Port
            serverSocket = new ServerSocket(portNumber);
            
            //Erstelle Popup Menü für Systemtray
            final PopupMenu popup = new PopupMenu();
            
            //Erstelle Tray Icon
            Image im = Toolkit.getDefaultToolkit().getImage("src/libs/trayicon.png");
            
            //Skaliere das Icon auf die richtige Größe            
            int width = new TrayIcon(im).getSize().width;
            final TrayIcon trayIcon = new TrayIcon(im.getScaledInstance(width, -1, Image.SCALE_SMOOTH),sProgammName+" Client v"+sVersion);
            
            final SystemTray tray = SystemTray.getSystemTray();
            
         // Create a pop-up menu components
            MenuItem aboutItem = new MenuItem("Info");
            aboutItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "<html><body><font size=5><b>"+sProgammName+" v"+sVersion+"</b></font><br><br>Client-Tool des Programms \""+sProgammName+"\" der Feuerwehr Inningen<br><br>Aktive Parameter:<br>Port-Nummer: "+portNumber+"<br><br><font size=2>\u00a9 2015 Feuerwehr Inningen e. V.</font></body></html>", "Info", JOptionPane.INFORMATION_MESSAGE);         
                }
            });
            MenuItem exitItem = new MenuItem("Exit");            
            exitItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);             
                }
            });
            
            //Add components to pop-up menu
            popup.add(aboutItem);
            popup.addSeparator();
            popup.add(exitItem);
           
            trayIcon.setPopupMenu(popup);
           
            try {
                tray.add(trayIcon);
            } catch (Exception e) {
            	new AlarmHandler("<html><body><font size=5>FEHLER</font><br><br>"+e.getStackTrace()+"</body></html>", false, 10, NotificationIcon.error);
            	try {
    				Thread.sleep(10000);
    			} catch (Exception e1) {
    			}
                System.exit(1);
            }
            
            //Durchlaufe diese Schleife solange, bis der Port nicht mehr definiert ist. Benötigt für durchgehenden Alarmierungsempfang
        	while(serverSocket != null) {
        		
        		//Popup, wenn Client bereit ist, Alarmierungen zu empfangen
        		new AlarmHandler("<html><body>Client bereit zum Empfangen</body></html>", false, 3, NotificationIcon.information);
        		
        		//Warte auf Verbindung vom Server
        		Socket clientSocket = serverSocket.accept();

        		//Melde, wenn sich der Server verbunden hat
        		new AlarmHandler("<html><body>Empfange Alarmmeldung von Server...</body></html>", false, 2, NotificationIcon.information);
        		
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                
                //definiere Alarmtext mit Schriftgröße 6
                String inputLine,alarmtext = "<html><body><font size=7>";
            	
                //Hole einzelne Zeilen von Server und trenne diese mit einem Zeilenumbruch
                int i = 0;
                while ((inputLine = in.readLine()) != null) {
                	if (i==0) {
                		alarmtext = alarmtext + "<b><font size=9>"+inputLine+"</font></b><br>";
                		i=1;
                	} else
                		alarmtext = alarmtext + inputLine+"<br>";
                }
                
                //Schließe HTML-Tags
                alarmtext = alarmtext + "</font></body></html>";
//                System.out.println("Input-Data received. Starting Notification...");
                
                //Starte neuen Thread für Alarmdarstellung. Dadurch kann sofort ein neuer Alarm emfangen werden sollte dieses Szenario auftreten
                new AlarmAppClient(alarmtext).start();                
        	}
        	
        	//Gebe Port wieder frei
        	serverSocket.close();   
        	
        //FEHLERBEHANDLUNG
        }  catch (IOException e) {
            
        	//Verbindung unterbrochen
            if (e.getClass().toString().equals("class java.net.SocketException")) {
            	new AlarmHandler("<html><body><font size=5>FEHLER</font><br><br><b>Netzwerkfehler</b><br>Verbindung wurde vom Server unterbrochen.</body></html>", false, 10, NotificationIcon.error);
            	serverSocket.close();
            	main(null);
            }
            //Port in Verwendung
            else {
            	new AlarmHandler("<html><body><font size=5>FEHLER</font><br><br><b>Netzwerkfehler</b><br>Konnte Port "+portNumber+" nicht verwenden! Port ist bereits <br>in Benutzung. Programm bitte neu starten oder andere<br>Port-Nummer für Server und Client definieren!</body></html>", false, 10, NotificationIcon.error);
            	try {
    				Thread.sleep(10000);
    			} catch (Exception e1) {
    			}
                System.exit(1);
            }  		
        } catch (Exception e) {
        	new AlarmHandler("<html><body><font size=5>FEHLER</font><br><br>"+e.getStackTrace()+"</body></html>", false, 10, NotificationIcon.error);
        	try {
				Thread.sleep(10000);
			} catch (Exception e1) {
			}
            System.exit(1);
        }
    }
	//Thread-Start für Alarmdarstellung
	public void run() {
    	new AlarmHandler(arr, true, notifDuration, NotificationIcon.warning);
    }
}
