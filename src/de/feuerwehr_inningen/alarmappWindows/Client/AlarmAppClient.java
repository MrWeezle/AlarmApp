package de.feuerwehr_inningen.alarmappWindows.Client;

import java.net.*;
import java.util.Properties;
import java.io.*;

import com.alee.managers.notification.NotificationIcon;

 
public class AlarmAppClient extends Thread{
	private String arr;
	private static int notifDuration = 30;
	public static Properties ServerClientProp = new Properties();
	static ServerSocket serverSocket;
    public AlarmAppClient(String alarmtext) {
    	this.arr = alarmtext;
	}

	public static void main(String[] args) throws IOException {
		FileInputStream is = null;
		try {
			// is =
			// ClassLoader.getSystemResourceAsStream("Resource/ServerPath.properties");
			is = new FileInputStream(System.getProperty("user.dir")
					+ "/ServerClient.properties");
			ServerClientProp.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		int portNumber = Integer.parseInt(ServerClientProp.getProperty("receiverPort"));
		notifDuration = Integer.parseInt(ServerClientProp.getProperty("notification"));
 
        try {
            serverSocket = new ServerSocket(portNumber);
        	while(serverSocket != null) {
        		System.out.println("Client ready to receive...");
        		new AlarmHandler("<html><body>Client bereit zum Empfangen</body></html>", false, 5, NotificationIcon.information);
        		Socket clientSocket = serverSocket.accept();
        		System.out.println("Client connected. Waiting for Input-Data...");

        		new AlarmHandler("<html><body>Empfange Alarmmeldung von Server...</body></html>", false, 5, NotificationIcon.information);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                
                String inputLine,alarmtext = "<html><body><font size=6>";
            	
                while ((inputLine = in.readLine()) != null) {
                	alarmtext = alarmtext + inputLine+"<br>";
                }
                alarmtext = alarmtext + "</font></body></html>";
                System.out.println("Input-Data received. Starting Notification...");
                
                new AlarmAppClient(alarmtext).start();
                
        	}
        	serverSocket.close();   
        	
        }  catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            e.printStackTrace();
            
            if (e.getClass().toString().equals("class java.net.SocketException")) {
            	new AlarmHandler("<html><body><font size=5>FEHLER</font><br>Verbindung wurde vom Server unterbrochen.</body></html>", false, 20, NotificationIcon.error);
            	serverSocket.close();
            	main(null);
            }
            else {
            	new AlarmHandler("<html><body><font size=5>FEHLER</font><br>Konnte Port "+portNumber+" nicht verwenden! Port ist bereits <br>in Benutzung. Programm bitte neu starten oder andere<br>Port-Nummer für Server und Client definieren!</body></html>", false, 20, NotificationIcon.error);
            	try {
    				Thread.sleep(10000);
    			} catch (InterruptedException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
                System.exit(1);
            }  		
        }
    }

	public void run() {
    	new AlarmHandler(arr, true, notifDuration, NotificationIcon.warning);
    }
}
