package de.feuerwehr_inningen.alarmappWindows.Client;

import java.net.*;
import java.util.Properties;
import java.io.*;

 
public class AlarmAppClient extends Thread{
	private String arr;
	public static Properties ServerClientProp = new Properties();
	
    public AlarmAppClient(String alarmtext) {
    	this.arr = alarmtext;
	}

	@SuppressWarnings("null")
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
 
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
        	while(serverSocket != null) {
        		System.out.println("Client ready to receive...");
        		Socket clientSocket = serverSocket.accept();
        		System.out.println("Client connected. Waiting for Input-Data...");
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                
                String inputLine,alarmtext = "<html><body>";
            	
                while ((inputLine = in.readLine()) != null) {
                	alarmtext = alarmtext + inputLine+"<br>";
                }
                alarmtext = alarmtext + "</body></html>";
                System.out.println("Input-Data received. Starting Notification...");
                
                new AlarmAppClient(alarmtext).start();
                
        	}
        	serverSocket.close();   
        	
        }  catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            e.printStackTrace();
        }
    }

	public void run() {
    	new AlarmHandler(arr);
    }
}
