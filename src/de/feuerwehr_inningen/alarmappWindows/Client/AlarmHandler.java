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
 * 
 * 
 * Diese Klasse behandelt alle Popups, die vom Server oder vom Client gestartet werden.
 * 
 * 
 * */

public class AlarmHandler extends WebFrame
{
	private static final long serialVersionUID = 8025922137450027854L;
	public static Properties ServerClientProp = new Properties();

	/*Parameter:
	 * 
	 * alarmtext: 	Text, der in dem Popup angezeigt werden soll. HTML-kompatibel
	 * alarm:		Ist das Popup ein Alarm oder nur ein(e) Information bzw. Fehler. Nur bei einem Alarm wird ein Ton abgespielt
	 * displaytime:	Wie lange soll das Popup angezeigt werden (in Sekunden)
	 * icon:		Welches Icon soll das Popup darstellen
	 * 
	 */
	public AlarmHandler(String alarmtext, boolean alarm, int displaytime, NotificationIcon icon) {

		super();
		
		//Lade Properties-Datei aus Work-Verzeichnis
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
			}
			System.exit(1);
		}
		
        // Install WebLaF as application L&F
        WebLookAndFeel.install ();
        
        //Initialisiere JavaFX
        new JFXPanel();
        
        //Erstelle ein neues Notification-Objekt
        final WebNotification notificationPopup = new WebNotification ();
        
        //Icon aus Parameter
        notificationPopup.setIcon ( icon );
        
        //Dauer aus Parameter * 1000. Ergibt Millisekunden
        notificationPopup.setDisplayTime (displaytime*1000);
        
        //Setzte Labeltext auf Alarmtext
        notificationPopup.setContent ( new WebLabel( alarmtext ) );
        
        //Zeige Popup an
        NotificationManager.showNotification ( notificationPopup );
        
        //Ist das Popup ein Alarm?
	    if (alarm) {
	    	try {
	    		//Wenn ja, wird ein Ton abgespielt
	        	System.out.println("Getting Alarmmedia...");
	        	File f = new File(ServerClientProp.getProperty("alarmTone"));
	        	System.out.println(f);
	        	String uri = f.toURI().toString();
	        	System.out.println(uri);
	        	//System.out.println(new File(ServerClientProp.getProperty("alarmTone")).toURI().toString());
			    //Media med = new Media(new File(ServerClientProp.getProperty("alarmTone")).toURI().toString());
	        	Media med = new Media(uri);
			    MediaPlayer player = new MediaPlayer(med);
			    System.out.println("Playing Alarmmedia...");
			    player.setCycleCount(Integer.parseInt(ServerClientProp.getProperty("alarmCycle")));
			    player.play();
			    System.out.println("Alarm displayed. Waiting for new Alarm...");
			//FEHLERBEHANDLUNG
	    	} catch (MediaException e) {
	    		//Audio-Datei fehlt
//	    		if (e.getType().equals("MEDIA_UNAVAILABLE")) {
	    			new AlarmHandler("<html><body><font size=5>FEHLER</font><br>Media-Datei wurde nicht gefunden.<br>Bitte überprüfen!</body></html>", false, 5, NotificationIcon.error);
//	    		}
	    	}
        }
	}
}