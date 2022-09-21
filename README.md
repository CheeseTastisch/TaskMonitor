# Task Monitor
## Inhaltsverzeichnis
1. [Grundaufbau](#basicstructure)
2. [Client](#client)
   1. [OSHI](#client_ohsi)
   2. [Informationen](#client_information)
3. [Grundablauf Client-Server](#basicflow_client_server)

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

<a name="client_information"></a>
### ii. Informationen
<details>
  <summary>Allgemeine Informationen</summary>

Allgemeine Informationen sind Information, die sich wären des laufenden Betriebes nicht oder selten ändern.
Diese beinhalten:
- Hostname
- Mainboard
  - Hersteller
  - Model
  - Hardware UID
- Firmware/BIOS
  - Name
  - Version
  - Hersteller
  - Veröffentlichungsdatum
- CPU (je CPU)
  - Kennung
    - Hersteller
    - Name
    - Famille
    - Model
    - Stepping
    - Prozessor Id
    - 64 Bit
    - Mikroarchitektur
  - Frequenz
  - Kerne
  - Threads
  - Cache (je Cache)
    - Level
    - Cache Size
    - Line Size
- Primärspeicher
  - Kapazität
  - Page Anzahl
  - Swap Kapazität
  - Virtuelle Kapazität
  - Physisch (je Stick)
    - Steckplatz
    - Kapazität
    - Frequenz
    - Hersteller
    - Typ
- Sekundärspeicher (je Festplatte)
  - Name
  - Model
  - Kapazität
  - Partition (je Partition auf Festplatte)
    - Identifikation
    - Typ
    - UUID
    - Kapazität
    - Einbindepunkt
- GPU (je GPU)
  - Name
  - Hersteller
  - VRam
- Netzwerkschnittstellen (je Schnittstelle inkl. lokale)
  - Name
  - Anzeigename
  - Schnittstellenalias
  - Maximal Übertragungseinheit
  - Mac Adresse
  - IPv4 Adressen (je Adresse)
    - Adresse
    - Subnetmask
  - IPv6 Adresse (je Adresse)
    - Adresse
    - Prefix
  - Geschwindigkeit
- Netzteile (je Netzteil)
  - Name
- Soundkarten
  - Name
  - Codec
- USB-Geräte
  - Name
  - Hersteller
  - ProduktId
  - SerialId
  - Angeschlossene USB-Geräte (je Gerät, USB-Gerät)
- Betriebssystem
  - Familie
  - Hersteller
  - Version
  - Codec
  - Buildnummer 
- Internet
  - DNS (je Eintrag)
  - IPv4 Standartgateway
  - IPv6 Standartgateway
- Filesystem
  - Maximale Anzahl Datei Deskriptoren
  - Dateispeicher (je Dateispeicher)
    - Name 
    - Volumen
    - Lable
    - Mount
    - Beschreibung
    - Typ
    - Kapazität
- Benutzer (je angemeldetem Benutzer) (möglich, Administrator rechte?)
  - Sprache
  - Währung
  - Tastaturlayout
</details>

<details>
  <summary>Laufende Informationen</summary>

Laufende Informationen sind Information, die sich wären des Betriebes laufend ändern.
Zu jeder Information über eine Komponent wird noch eine Id hinzugefügt, über die eine eindeutige zuteilung möglich ist.
Diese beinhalten:
- CPU (je CPU)
  - Auslastung gesamt
  - Auslastung je Thread
  - Temerpatur
- Primärspeicher
  - Benutzt
  - Swap Benutzt
  - Virtuell Benutzt
  - Page In
  - Page Out
- Sekundärspeicher (je Festplatte)
  - Verfügbare Kapazität
  - Queuelänge
  - Leseoperationen
  - Gelesene Bytes
  - Schreiboperationen
  - Geschriebene Bytes
  - Übertragungszeit
- Netzwerkschnittstellen (je Schnittstelle inkl. lokale)
  - Empfangene Bytes
  - Gesendete Bytes
  - Empfangene Pakete
  - Gesendete Pakete
  - Eingangsfehler
  - Ausgangsfehler
  - Kollisionen
- Internet
  - DNS (103.86.96.100, je Eintrag)
  - IPv4 Standartgateway (192.168.188.1)
  - IPv6 Standartgateway (fe80:0:0:0:18eb:1eb6:79ed:d46e)
- Filesystem
  - Maximale Anzahl Datei Deskriptoren (16711680)
  - Dateispeicher (je Dateispeicher)
    - Name (Lokale Festplatte (E:))
    - Volumen (\\?\Volume{5ce94733-359b-4f6f-85d2-fde0986e55b6}\)
    - Lable (Entwicklung)
    - Mount (E:\)
    - Beschreibung (Fixed drive)
    - Typ (NTFS)
    - Kapazität (52428795904)
- Benutzer (je angemeldetem Benutzer) (möglich, Administrator rechte?)
  - Sprache
  - Währung
  - Tastaturlayout
</details>

<a name="basicflow_client_server"></a>
## 3. Grundablauf Client-Server

Beim Starten des Clients werden allgemeine Informationen gesammelt und an den Server übermittelt.

<!--
Die derzeitigen Daten beinhalten
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