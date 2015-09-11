package de.feuerwehr_inningen.alarmappWindows.Server;

import java.io.*;
import java.net.*;
import java.util.Properties;
 
public class AlarmAppServer {
	
	public static Properties ServerClientProp = new Properties();
	
    public static void main(String[] args) throws IOException {
         
//        if (args.length != 2) {
//            System.err.println(
//                "Usage: java EchoClient <host name> <port number>");
//            System.exit(1);
//        }
    	try {
    		if (args[0].equals("debug")) {
    			args = new String [4];    	
    	    	args[0] = "-stichw#Brand?B3";
    	    	args[1] = "-adresse#Oktavianstraﬂe?29a,?Inningen?Augsburg";
    	    	args[2] = "-datum#10.09.2015";
    	    	args[3] = "-zeit#13:09:15";
    		}
    	} catch (ArrayIndexOutOfBoundsException e) {
    		    		
    	}
    	
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
    	
    	
        String hostName = ServerClientProp.getProperty("receiverName");
        int portNumber = Integer.parseInt(ServerClientProp.getProperty("receiverPort"));
 
        try (
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(kkSocket.getInputStream()));
        ) {
//            BufferedReader stdIn =
//                new BufferedReader(new InputStreamReader(System.in));
//            String fromServer;
//            String fromUser;
        	
        	String alarmtext = "";
            for (int i=0;i<args.length;i++) {
            	String param = args[i];
            	param = param.replace("?", " ");
            	String param2[] = param.split("#");
            	alarmtext = alarmtext + param2[1]+"\n";
            }
            System.out.println("Client: "+alarmtext);
            System.out.println("Sending Alarmtext...");
 
//            while ((fromServer = in.readLine()) != null) {
//                System.out.println("Server: " + fromServer);
//                if (fromServer.equals("Bye."))
//                    break;
//                 
//                fromUser = stdIn.readLine();
//                if (fromUser != null) {
//                    System.out.println("Client: " + fromUser);
            out.println(alarmtext);
//                }
//            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
}