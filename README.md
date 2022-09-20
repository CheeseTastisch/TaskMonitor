# Task Monitor
## Über Task Monitor
Der Task Monitor besteht aus drei Hauptkomponenten
- Client
- Server
- Web-Server

Der Client list in regelmäßigen Abständen Informationen aus und sendet diese an den Server.
Dieser speichert die Informationen wiederrum in einer Datenbank.
Der Web-Server zeigt die in der Datenbank gespeicherten Daten dann grafisch aufbereitet als http(s) server an.
Des Weiteren soll der Server Daten filtern und diese unter gewissen Umständen (z.B. hohe CPU oder RAM Auslastung) als kritisch markieren.
Sollten bei einem Client in einer gewissen Zeit viele kritische Daten erkannt werden, wird der gesamte Client als kritisch angezeigt.

## Client
Der Client liest beim start alle Systemspezifikationen aus und sendet diese einmal an den Server, von diesem erhält er eine ID,
mit der der Client weitere Informationen an den Server senden um diesen Eindeutig zuzuordnen.

Die Systemspezifikationen enthalten
- Name des Geräts
- CPU
  - Modell
  - Kerne
  - Threads
  - Frequenz
  - Cache
    - L1
    - L2
    - L3
- RAM
  - Modell
  - Frequenz
  - Typ
  - Größe
- GPU
  - Modell
  - Frequenz
    - Kern
    - Speicher
  - Speichergröße
- Sekundärspeicher (je Einheit)
  - Größe
  - Typ
  - Frequenz
- Netzwerkkarten
  - Typ
  - Frequenz
  - Mac-Address
- Betriebssystem
  - Name
  - Version
  - Architektur
- Externe Schnittstellen
- Regionale Einstellungen
  - Sprache
  - Währung
  - Tastaturlayout
- Partitionen
- Angemeldete Benutzer

Nach der Übermittlung der Spezifikationen wird ein Timer gestartet, der in regelmäßigen Abständen ausgeführt wird.
In welchem Abstand dies erfolgt kann am Server eingestellt werden und wird dem Client dann übermittelt.

Bei jeder Ausführung des Timers erfolgen zwei Aktionen
1. Es wird überprüft, ob sich Systemspezifikationen geändert haben und
2. es werden derzeitige Daten ermittelt.

Die derzeitigen Daten beinhalten
- CPU
  - Auslastung einzelner Kerne
  - Cache Belegung
    - L1
    - L2
    - L3
  - Benutze Frequenz
- RAM
  - Belegt
  - Benutze Frequenz
- GPU
  - Belegter Speicher
  - Benutze Frequenz
- Sekundärspeicher (je Einheit)
  - Belegt
  - Leserate
  - Speicherrate
- Netzwerkkarten
  - Empfangsrate
  - Senderate
- Prozesse
  - ID
  - children?
  - parent?
  - Info
    - Command
    - Arguments
    - Startzeit
    - CPU Zeit
    - Benutzer
  - Alive
  - Memory
    - Used
    - Max
- Partitionen
  - Belegt

Änderungen an den Spezifikationen und die gesammelten Daten werden dann an den Server übergeben.

## Server
## Web-Server