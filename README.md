##Einleitung

Dies ist ein Projekt, um einen PC zu alarmieren, wenn über firEmergency ein Alarm eingeht. Wir verwenden es dazu,
um auf einem Schulungsrechner eine Meldung und einen Alarmton abzuspielen, wenn ein Einsatz eingeht.

Das Programm ist in Java geschrieben. Folglich muss auf dem Empfänger und dem Sender Java installiert sein.


##Einrichtung firEmergency

Hierzu wird in firEmergency in den gewünschten Alarmablauf ein Plugin "Alarmtext (eigene Parameter)" gesetzt
und das Plugin "Programm ausführen" wird diesem untergeordnet. Im Alarmtext-Plugin werden die Parameter definiert,
welche übergeben werden sollen.

#####Beispiel Alarmablauf:

	Alarmtext [eigene Parameter]
	|----	Textersetzung
		|----	Programm ausführen


#####Beispiel Alarmtext:

	-stichw#&STICHW& -adresse#&ZIEL& -datum#&D& -zeit#&U&
	
#####Benötigte Textersetzung:

	[LEERZEICHEN];?

Beachte: Leerzeichen müssen mit einem "?" ersetzt werden, da ein Leerzeichen den Beginn eines neuen Parameters anzeigt.
Die Reihenfolge der Parameter ist an sich unwichtig. Die Reihenfolge bestimmt nur die Darstellung am Client.

Das "#"-Zeichen ist als Trennzeichen zwischen den Parameternamen angegeben. Die Parameternamen sind nur für die bessere
Lesbarkeit. Auswirkungen auf den Programmablauf haben diese nicht.

Im Plugin "Programm ausführen" wird der Pfad zur Client JAR-Datei angegeben. Den Downloadlink zu den JARs gibt es weiter unten.

##Einrichtung Server und Client

#####Server

Die Datei "AlarmAppServer.jar" wird auf dem Rechner eingerichtet, auf dem firEmergency läuft. Zusätzlich muss die Datei "ServerClient.properties" im gleichen Verzeichnis wie die JAR-Datei sein.

#####Client

Die Datei "AlarmAppClient.jar" wird auf dem Rechner eingerichtet, welcher bei einem Alarm eine Benachrichtigung und einen Alarmton abspielen soll. Zusätzlich muss die Datei "ServerClient.properties" im gleichen Verzeichnis wie die JAR-Datei sein. Alarmtöne können in einem beliebigen Verzeichnis sein, empfohlen ist jedoch das gleiche Verzeichnis wie die JAR-Datei.



