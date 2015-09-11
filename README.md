##Einleitung

Dies ist ein Projekt, um einen PC zu alarmieren, wenn über firEmergency ein Alarm eingeht. Wir verwenden es dazu,
um auf einem Schulungsrechner eine Meldung und einen Alarmton abzuspielen, wenn ein Einsatz eingeht.

Das Programm ist in Java geschrieben. Folglich muss auf dem Empfänger und dem Sender Java installiert sein.


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
Alarmablauf:
![Alarmablauf](https://github.com/MrWeezle/AlarmApp/blob/master/src/libs/alarmablauf.jpg)

Alarmtext 1:

![Alarmtext](https://github.com/MrWeezle/AlarmApp/blob/master/src/libs/alarmtext1.jpg)

Textersetzung:

![Textersetzung](https://github.com/MrWeezle/AlarmApp/blob/master/src/libs/textersetzung.jpg)

Alarmtext2:

![Alarmtext2](https://github.com/MrWeezle/AlarmApp/blob/master/src/libs/alarmtext2.jpg)

Programm ausführen:

![Programm ausführen](https://github.com/MrWeezle/AlarmApp/blob/master/src/libs/programmausf%C3%BChren.jpg)


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

#####ServerClient.properties

In dieser Datei können mehrere Parameter festgelegt werden.

**receiverPort** --> Der Port auf welchem Server und der Client miteinander kommunizieren. Diese Zahl muss ein Wert zwischen 1024 und 65535 sein und sowohl beim Server als auch beim Client in der Properties-Datei gleich sein.

**receiverName** --> Dies ist der Hostname des Clients. Dieser Parameter wird vom Server verwendet, um den Alarm zu verschicken.

**alarmTone** --> Hier kann eine Audio-Datei angegeben werden, die im Alarmfall angespielt werden soll. Standardmäßig d bereits zwei Dateien beim Download dabei. "Akkord.m4a" und "hurricane.m4a".

**alarmCycle** --> Hier kann angegeben werden, wie oft der Alarmton abgespielt werden soll.

**notification** --> Hier kann angegeben werden, wie lange das Notification-popup angezeigt werden soll.



