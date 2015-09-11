package de.feuerwehr_inningen.alarmappWindows.Client;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.label.WebLabel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.WebNotification;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.*;


/**
 * Text notifications example.
 *
 * @author Mikle Garin
 */

public class AlarmHandler extends WebFrame
{
	private static final long serialVersionUID = 8025922137450027854L;
	public static Properties ServerClientProp = new Properties();

	public AlarmHandler(String alarmtext, boolean alarm, int displaytime, NotificationIcon icon) {

		super();
		
		FileInputStream is = null;
		try {
			is = new FileInputStream(System.getProperty("user.dir")+ "/ServerClient.properties");
			ServerClientProp.load(is);
			is.close();
		} catch (IOException e) {
			new AlarmHandler("<html><body><font size=5>FEHLER</font><br>Properties-Datei hat einen Fehler oder ist nicht vorhanden!<br>Programm wird beendet</body></html>", false, 5, NotificationIcon.error);
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.exit(1);
		}
		
        // Install WebLaF as application L&F
        WebLookAndFeel.install ();        
        new JFXPanel();
               
        final WebNotification notificationPopup = new WebNotification ();
        notificationPopup.setIcon ( icon );
        notificationPopup.setDisplayTime (displaytime*1000);
        notificationPopup.setContent ( new WebLabel( alarmtext ) );
        
        NotificationManager.showNotification ( notificationPopup );
        
	    if (alarm) {
	    	try {
	        	System.out.println("Getting Alarmmedia...");        
			    Media med = new Media(new File(ServerClientProp.getProperty("alarmTone")).toURI().toString());
			    MediaPlayer player = new MediaPlayer(med);
			    System.out.println("Playing Alarmmedia...");
			    player.setCycleCount(Integer.parseInt(ServerClientProp.getProperty("alarmCycle")));
			    player.play();
			    System.out.println("Alarm displayed. Waiting for new Alarm...");
	    	} catch (MediaException e) {
	    		if (e.getType().equals("MEDIA_UNAVAILABLE")) {
	    			new AlarmHandler("<html><body><font size=5>FEHLER</font><br>Media-Datei wurde nicht gefunden.<br>Bitte überprüfen!</body></html>", false, 5, NotificationIcon.error);
	    		}
	    	}
        }
	}
}