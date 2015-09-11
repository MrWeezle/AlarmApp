EINLEITUNG


Dies ist ein Projekt, um einen PC zu alarmieren, wenn über firEmergency ein Alarm eingeht. Wir verwenden es dazu,
um auf einem Schulungsrechner eine Meldung und einen Alarmton abzuspielen, wenn ein Einsatz eingeht.

Das Programm ist in Java geschrieben. Folglich muss auf dem Empfänger und dem Sender Java installiert sein.


INSTALLATION & BENUTZUNG


Hierzu wird in firEmergency in den gewünschten Alarmablauf ein Plugin "Alarmtext (eigene Parameter)" gesetzt
und das Plugin "Programm ausführen" wird diesem untergeordnet. Im Alarmtext-Plugin werden die Parameter definiert,
welche übergeben werden sollen.

Beispiel Alarmablauf:

Alarmtext [eigene Parameter]
-- Textersetzung
--Programm ausführen


Beispiel Parameter:

-stichw#B3?Person -adresse#Berliner?Allee?30,?Augsburg -datum#11.09.2015 -zeit#11:15:00

Beachte: Leerzeichen müssen mit einem "?" ersetzt werden, da ein Leerzeichen den Beginn eines neuen Parameters anzeigt.
Die Reihenfolge der Parameter ist an sich unwichtig. Die Reihenfolge bestimmt nur die Darstellung am Client.

Das "#"-Zeichen ist als Trennzeichen zwischen den Parameternamen angegeben. Die Parameternamen sind nur für die bessere
Lesbarkeit. Auswirkungen auf den Programmablauf haben diese nicht.



