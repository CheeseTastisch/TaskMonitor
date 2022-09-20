# Task Monitor
## Inhaltsverzeichnis
1. [Grundaufbau](#basicstructure)
2. [Client](#client)
   1. [OSHI](#client_ohsi)
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

### ii. Informationen
<details>
  <summary>Allgemeine Informationen</summary>

Allgemeine Informationen sind Information, die sich wären des laufenden Betriebes nicht oder selten ändern.
Diese beinhalten:
- Hostname (DESKTOP-LRMLFB0)
- Mainboard
  - Hersteller (Gigabyte Technology Co., Ltd.)
  - Model (Z490 GAMING X)
  - Hardware UID (03000200-0400-0500-0006-000700080009)
- Firmware/BIOS
  - Name (F4)
  - Version (ALASKA - 1072009)
  - Hersteller (American Megatrends Inc.)
  - Veröffentlichungsdatum (2020-06-17)
- CPU (je CPU)
  - Kennung
    - Hersteller (GenuineIntel)
    - Name (Intel(R) Core(TM) i7-10700 CPU @ 2.90GHz)
    - Famille (6)
    - Model (165)
    - Stepping (5)
    - Prozessor Id (BFEBFBFF000A0655)
    - 64 Bit (true)
    - Mikroarchitektur (AMD)
  - Frequenz - evtl. unter Windows nicht verfügbar
  - Kerne (8)
  - Threads (16)
  - Cache (je Cache)
    - Level (L1)
    - Cache Size (512 000)
    - Line Size (1024)
- Primärspeicher
  - Kapazität (17096159232)
  - Page Anzahl (4096)
  - Swap Kapazität (12884901888)
  - Virtuelle Kapazität (29981061120)
  - Physisch (je Stick)
    - Steckplatz (BANK 1)
    - Kapazität (8589934592)
    - Frequenz (2133000000)
    - Hersteller (04CD)
    - Typ (DDR4)
- Sekundärspeicher (je Festplatte)
  - Name (\\.\PHYSICALDRIVE0)
  - Model (Samsung SSD 970 EVO Plus 250GB (Standardlaufwerke))
  - Kapazität (250056737280)
  - Partition (je Partition auf Festplatte)
    - Identifikation (Datenträgernr. 0, Partitionsnr. 1)
    - Typ (GPT: Standarddaten)
    - UUID (f0cacdc2-d5bc-442f-a934-59dd0e8fe8a3)
    - Kapazität (248831735808)
    - Einbindepunkt (C:\) - nur unter Windows
- GPU (je GPU)
  - Name (NVIDIA GeForce RTX 2060)
  - Hersteller (NVIDIA (0x10de))
  - VRam (4293918720)
- Netzwerkschnittstellen (je Schnittstelle inkl. lokale)
  - Name (eth0)
  - Index (7)
  - Anzeigename (Intel(R) Ethernet Connection (11) I219-V)
  - Schnittstellenalias (Ethernet)
  - Maximal Übertragungseinheit (1500)
  - Mac Adresse (18:c0:4d:63:c4:d7)
  - IPv4 Adressen (je Adresse)
    - Adresse (192.168.188.43)
    - Subnetmask (24)
  - IPv6 Adresse (je Adresse)
    - Adresse (fe80:0:0:0:2db9:607a:a651:18e0)
    - Prefix (64)
  - Geschwindigkeit (1000000000)
  - Unterschnittstellen (je Schnittstelle, vom Typ Netzwerkschnittstelle)
- Netzteile (je Netzteil)
  - Name (InternalBattery-0)
- Soundkarten
  - Name (NVIDIA Corporation NVIDIA High Definition Audio)
  - Codec (NVIDIA High Definition Audio)
- Betriebssystem
  - Familie (Windows)
  - Hersteller (Microsoft)
  - Version (10)
  - Codec (Home)
  - Buildnummer (19044) 
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