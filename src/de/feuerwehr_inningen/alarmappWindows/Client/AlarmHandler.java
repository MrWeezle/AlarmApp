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

	public AlarmHandler(String alarmtext) {

		super();
		
		FileInputStream is = null;
		try {
			is = new FileInputStream(System.getProperty("user.dir")+ "/ServerClient.properties");
			ServerClientProp.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        // Install WebLaF as application L&F
        WebLookAndFeel.install ();        
        new JFXPanel();
        // Create you Swing application here
        
        int notifDuration = Integer.parseInt(ServerClientProp.getProperty("notification"));
        
        final WebNotification notificationPopup = new WebNotification ();
        notificationPopup.setIcon ( NotificationIcon.clock );
        notificationPopup.setDisplayTime (notifDuration * 1000);

//        final WebClock clock = new WebClock ();
//        clock.setClockType ( ClockType.timer );
//        clock.setTimeLeft ( notifDuration * 1000 + 1000 );
//        clock.setTimePattern ( "'This notification will close in' ss 'seconds'" );
//        clock.setTimePattern("'"+alarmtext+"'");
        notificationPopup.setContent ( new WebLabel( alarmtext ) );

        NotificationManager.showNotification ( notificationPopup );
//        clock.start ();
        
        System.out.println("Getting Alarmmedia...");
        Media med = new Media(new File(ServerClientProp.getProperty("alarmTone")).toURI().toString());
//        Media med = new Media(getClass().getResource(ServerClientProp.getProperty("alarmTone")).toExternalForm());
        MediaPlayer player = new MediaPlayer(med);
        System.out.println("Playing Alarmmedia...");
        player.setCycleCount(Integer.parseInt(ServerClientProp.getProperty("alarmCycle")));
        player.play();
        System.out.println("Alarm displayed. Waiting for new Alarm...");
	}
}