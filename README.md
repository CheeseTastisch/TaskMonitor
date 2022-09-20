# Task Monitor
## Inhaltsverzeichnis
1. [Grundaufbau](#basicstructure)
2. [Client](#client)
   1. [OSHI](#client_ohsi)
   2. [Grundablauf](#client_basicflow)

<a name="basicstructure"></a>
## 1. Grundlegendes 
Der Task Monitor besteht aus drei Hauptkomponenten
- Client
- Server
- Web-Server

Zusätzlich wird noch eine Datenbank benötigt, in der alle nötigen Informationen abgespeichert werden können.

Hierzu liest der Client in regelmäßigen Abständen Informationen aus und sendet diese an den Server,
der die Informationen in die Datenbank schreibt. Der Web-Server zeigt die in der Datenbank gespeicherten Daten dann
grafisch aufbereitet als http(s) server an. Des Weiteren soll der Server Daten filtern und diese unter gewissen Umständen 
(z.B. hohe CPU oder RAM Auslastung) als kritisch markieren. Sollten bei einem Client in einer gewissen Zeit
viele kritische Daten erkannt werden, wird der gesamte Client als kritisch angezeigt.

<img src="diagramms/images/01_architecture.png" alt="Grundlegende Architektur">

<a name="client"></a>
## 2. Client
Der Client bekommt über zwei Arten Informationen
1. JVM (Java Standard Bibliotheken) und
2. OSHI

<img src="diagramms/images/02_client_architecture.png" alt="Grundaufbau Abstraktion Client">

<a name="client_ohsi"></a>
### i. [OSHI](https://github.com/oshi/oshi)
OSHI steht für Operating System and Hardware Information, es ist eine API die auf [Java Native Access (JNA)](https://github.com/java-native-access/jna) basiert.
Die API unterstützt
- macOS,
- Linux,
- Windows,
- Solaris,
- FreeBSD
- OpenBSD,
- WindowsCE,
- AIX,
- Android,
- GNU,
- kFreeBSD und
- NetBSD.

Dabei wendet die API unter anderem JNA an, um eigene native Methoden in Java einzufügen.
Dies bedeutet, dass die Informationen direkt auf Betriebssystem-Ebene Abgefragt werden,
was zum einen den nachteil hat, dass die API Betriebssystem abhängig ist, jedoch auch den Vorteil,
dass die Einschränkungen der JVM umgangen werden können und es somit möglich ist mehr Daten abzufragen.
Einige Informationen werden jedoch auch ohne JNA durch JVM Standards (Dateien lesen, ENV-Abfragen, ...) bereitgestellt.

<a name="client_basicflow"></a>
### ii. Grundablauf

Beim Starten des Clients werden allgemeine Informationen gesammelt und an den Server übermittelt.

<!--
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
  - Frequenz
  - Typ
  - Größe
- GPU
  - Modell
  - VRam
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

Änderungen an den Spezifikationen und die gesammelten Daten werden dann an den Server übertragen.

## Server
## Web-Server
-->