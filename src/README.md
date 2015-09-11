##Einleitung

Dies ist ein Projekt, um einen PC zu alarmieren, wenn über firEmergency ein Alarm eingeht. Wir verwenden es dazu,
um auf einem Schulungsrechner eine Meldung und einen Alarmton abzuspielen, wenn ein Einsatz eingeht.

Das Programm ist in Java geschrieben. Folglich muss auf dem Empfänger und dem Sender Java installiert sein.

###Demo-Screenshots

#####Client bereit für Empfang

![Client bereit](https://github.com/MrWeezle/AlarmApp/blob/master/src/libs/clientbereit.jpg)

#####Client empfängt Alarm

![client empfängt](https://github.com/MrWeezle/AlarmApp/blob/master/src/libs/clientempfang.jpg)

#####Client hat einen Fehler

![Client Fehler](https://github.com/MrWeezle/AlarmApp/blob/master/src/libs/clientfehler.jpg)

#####Alarmdarstellung

![Alarmdarstellung](https://github.com/MrWeezle/AlarmApp/blob/master/src/libs/alarmmeldung.jpg)

#####Server sendet Nachricht

![Server sendet](https://github.com/MrWeezle/AlarmApp/blob/master/src/libs/serversendet.jpg)

#####Server hat einen Fehler

![Serverfehler](https://github.com/MrWeezle/AlarmApp/blob/master/src/libs/serverfehler.jpg)


##Einrichtung firEmergency

Hierzu wird in firEmergency in den gewünschten Alarmablauf ein Plugin "Alarmtext (eigene Parameter)" gesetzt
und das Plugin "Programm ausführen" wird diesem untergeordnet. Im Alarmtext-Plugin werden die Parameter definiert,
welche übergeben werden sollen.

#####Empfohlener Alarmablauf:

	Alarmtext [eigene Parameter]
	|----	Textersetzung
		|----   Alarmtext [eigene Parameter]
			|----	Programm ausführen


#####Screenshots zu Einstellungen
######Alarmablauf:

![Alarmablauf](https://github.com/MrWeezle/AlarmApp/blob/master/src/libs/alarmablauf.jpg)

######Alarmtext 1:

Das "#"-Zeichen ist als Trennzeichen zwischen dem Parameternamen und dem Übergabewert angegeben. Die Parameternamen sind nur für die bessere Lesbarkeit. Auswirkungen auf den Programmablauf haben diese nicht. Jeder neue Parameter wird in der Notifikation in einer neuen Zeile dargestellt.

![Alarmtext](https://github.com/MrWeezle/AlarmApp/blob/master/src/libs/alarmtext1.jpg)

######Textersetzung:

Beachte: Leerzeichen müssen mit einem "?" ersetzt werden, da ein Leerzeichen den Beginn eines neuen Parameters anzeigt.
Die Reihenfolge der Parameter ist an sich unwichtig. Die Reihenfolge bestimmt nur die Darstellung am Client.

![Textersetzung](https://github.com/MrWeezle/AlarmApp/blob/master/src/libs/textersetzung.jpg)

######Alarmtext2:

Hier werden die Argumente zum ausführen des Programms zusammengesetzt. Da firEmergency nur .exe-Dateien starten kann muss hier der Umweg über die Kommandozeile gemacht werden. Die Pfadabgane (hier mit firEmergency) kann natürlich nach Belieben angepasst werden.

![Alarmtext2](https://github.com/MrWeezle/AlarmApp/blob/master/src/libs/alarmtext2.jpg)

######Programm ausführen:

![Programm ausführen](https://github.com/MrWeezle/AlarmApp/blob/master/src/libs/programmausf%C3%BChren.jpg)


##Einrichtung Server und Client

#####Server

Die Datei "AlarmAppServer.jar" wird auf dem Rechner eingerichtet, auf dem firEmergency läuft. Zusätzlich muss die Datei "ServerClient.properties" im gleichen Verzeichnis wie die JAR-Datei sein. Empfohlenes Verzeichnis ist das firEmergency-Verzeichnis (kann natürlich geändert werden).

#####Client

Die Datei "AlarmAppClient.jar" wird auf dem Rechner eingerichtet, welcher bei einem Alarm eine Benachrichtigung und einen Alarmton abspielen soll. Zusätzlich muss die Datei "ServerClient.properties" im gleichen Verzeichnis wie die JAR-Datei sein. Alarmtöne können in einem beliebigen Verzeichnis sein, empfohlen ist jedoch das gleiche Verzeichnis wie die JAR-Datei. Anschließend muss noch in der Firewall der konfigurierte Port in eingehender Richtung freigegeben werden.

#####ServerClient.properties

In dieser Datei können mehrere Parameter festgelegt werden.

**receiverPort** --> Der Port auf welchem Server und der Client miteinander kommunizieren. Diese Zahl muss ein Wert zwischen 1024 und 65535 sein und sowohl beim Server als auch beim Client in der Properties-Datei gleich sein.

**receiverName** --> Dies ist der Hostname des Clients. Dieser Parameter wird vom Server verwendet, um den Alarm zu verschicken.

**alarmTone** --> Hier kann eine Audio-Datei angegeben werden, die im Alarmfall angespielt werden soll. Standardmäßig d bereits zwei Dateien beim Download dabei. "Akkord.m4a" und "hurricane.m4a".

**alarmCycle** --> Hier kann angegeben werden, wie oft der Alarmton abgespielt werden soll.

**notification** --> Hier kann angegeben werden, wie lange das Notification-popup angezeigt werden soll.



