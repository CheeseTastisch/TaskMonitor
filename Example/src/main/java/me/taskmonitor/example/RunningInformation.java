package me.taskmonitor.example;

import me.taskmonitor.example.print.PrintHandler;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class RunningInformation {

    private OperatingSystem os;
    private HardwareAbstractionLayer hal;

    public static void main(String[] args) {
        RunningInformation.test();
    }

    public static void test() {
        try (PrintHandler printHandler = new PrintHandler(false, true, "running")) {
            printHandler.start();

            RunningInformation runningInformation = new RunningInformation();

            runningInformation.setup();
            runningInformation.cpu();
            runningInformation.ram();
            runningInformation.hwDisk();
            runningInformation.networkIf();
            runningInformation.os();
//            runningInformation.processes(true);
//            runningInformation.services();
            runningInformation.windows();
            runningInformation.internet();
            runningInformation.filesystem();
        }
    }

    public void setup() {
        SystemInfo systemInfo = new SystemInfo();

        this.os = systemInfo.getOperatingSystem();
        this.hal = systemInfo.getHardware();
    }

    public void cpu() {
        CentralProcessor processor = this.hal.getProcessor();

        double cpuUsage = processor.getSystemCpuLoad(0);
        long contextSwitches = processor.getContextSwitches();
        long interrupts = processor.getInterrupts();
        double temperatur = this.hal.getSensors().getCpuTemperature();

        System.out.printf("""
                        CPU:
                          Auslastung: %s
                          Kontextwechsel: %s
                          Unterbrechungen: %s
                          Temperatur: %s
                                        
                        """,
                cpuUsage, contextSwitches, interrupts, temperatur);

    }

    public void ram() {
        GlobalMemory memory = this.hal.getMemory();

        long used = memory.getTotal() - memory.getAvailable();
        long swapUsed = memory.getVirtualMemory().getSwapUsed();
        long virtualUsed = memory.getVirtualMemory().getVirtualInUse();
        long pageIn = memory.getVirtualMemory().getSwapPagesIn();
        long pageOut = memory.getVirtualMemory().getSwapPagesOut();

        System.out.printf("""
                        Hauptspeicher:
                          Benutzt: %s
                          Swap benutzt: %s
                          Virtuell benutzt: %s
                          Page In: %s
                          Page Out: %s
                                        
                        """,
                used, swapUsed, virtualUsed, pageIn, pageOut);
    }

    public void hwDisk() {
        System.out.println("Sekundärspeicher:");
        for (HWDiskStore diskStore : this.hal.getDiskStores()) {
            String name = diskStore.getName();
            long queue = diskStore.getCurrentQueueLength();
            long writeOperations = diskStore.getWrites();
            long writeBytes = diskStore.getWriteBytes();
            long readOperations = diskStore.getReads();
            long readBytes = diskStore.getReadBytes();
            long transferTime = diskStore.getTransferTime();

            System.out.printf("""
                              %s:
                                Warteschlangenlänge: %s
                                Leseoperationen: %s
                                Gelesene Bytes: %s
                                Schreiboperationen: %s
                                Geschriebene Bytes: %s
                                Übertragungsrate: %s
                            """,
                    name, queue, writeOperations, writeBytes, readOperations, readBytes, transferTime);
        }
        System.out.println();
    }

    public void networkIf() {
        System.out.println("Netzwerkschnittstellen:");

        for (NetworkIF networkIf : this.hal.getNetworkIFs()) {
            String name = networkIf.getName();
            long bytesReceived = networkIf.getBytesRecv();
            long bytesSent = networkIf.getBytesSent();
            long packetsReceived = networkIf.getPacketsRecv();
            long packetsSent = networkIf.getPacketsSent();
            long inErrors = networkIf.getInErrors();
            long outErrors = networkIf.getOutErrors();
            long collisions = networkIf.getCollisions();

            System.out.printf("""
                              %s:
                                Empfangene Bytes: %s
                                Gesendete Bytes: %s
                                Empfangene Pakete: %s
                                Gesendete Pakete: %s
                                Eingehende Fehler: %s
                                Ausgehente Fehler: %s
                                Kollisionen: %s
                            """,
                    name, bytesReceived, bytesSent, packetsReceived, packetsSent, inErrors, outErrors, collisions);
        }

        System.out.println();
    }

    public void os() {
        int processes = this.os.getProcessCount();
        int threads = this.os.getThreadCount();
        long uptime = this.os.getSystemUptime();

        System.out.printf("""
                        Betriebssystem:
                          Prozesse: %s
                          Threads: %s
                          Betriebszeit: %s
                                        
                        """,
                processes, threads, uptime);
    }

    public void processes(boolean listThreads) {
        System.out.println("Prozesse:");

        for (OSProcess process : this.os.getProcesses()) {
            int id = process.getProcessID();
            String name = process.getName();
            String path = process.getPath();
            String commandLine = process.getCommandLine();
            List<String> arguments = process.getArguments();
            Map<String, String> environment = process.getEnvironmentVariables();
            String workingDirectory = process.getCurrentWorkingDirectory();
            String user = process.getUser();
            String userId = process.getUserID();
            String group = process.getGroup();
            String groupId = process.getGroupID();
            OSProcess.State state = process.getState();
            int parentProcess = process.getParentProcessID();
            int priority = process.getPriority();
            long virtualSize = process.getVirtualSize();
            long residentSize = process.getResidentSetSize();
            long cpuTime = process.getKernelTime();
            long userTime = process.getUserTime();
            long startTime = process.getStartTime();
            long bytesWritten = process.getBytesWritten();
            long bytesRead = process.getBytesRead();
            long openFiles = process.getOpenFiles();
            double cumulativeProcessorLoad = process.getProcessCpuLoadCumulative();
            long minorFaults = process.getMinorFaults();
            long majorFaults = process.getMajorFaults();
            long contextSwitches = process.getContextSwitches();
            int threads = process.getThreadCount();

            System.out.printf("""
                              %s:
                                Name: %s
                                Pfad: %s
                                Befehl: %s
                                Argumente:
                            """,
                    id, name, path, commandLine);

            for (String argument : arguments) System.out.printf("      %s\n", argument);

            System.out.println("    Umgebungsvariablen:");

            for (Map.Entry<String, String> entry : environment.entrySet())
                System.out.printf("      %s: %s\n", entry.getKey(), entry.getValue());

            System.out.printf("""
                                Arbeitsverzeichnis: %s
                                Benutzer: %s
                                BenutzerId: %s
                                Gruppe: %s
                                GruppenId: %s
                                Status: %s
                                ElternprozessId: %s
                                Prioritär: %s
                                Virtuelle Größe: %s
                                Resistente Größe: %s
                                CPU Zeit: %s
                                Benutzer Zeit: %s
                                Startzeit: %s
                                Gelesene bytes: %s
                                Geschriebene bytes: %s
                                Geöffnete Dateien: %s
                                Kumulierte Prozessorlast: %s
                                Kleine Fehler: %s
                                Große Fehler: %s
                                Kontextänderungen: %s
                                Threads: %s
                                Threads:
                            """,
                    workingDirectory, user, userId, group, groupId, state, parentProcess, priority, virtualSize,
                    residentSize, cpuTime, userTime, startTime, bytesRead, bytesWritten, openFiles,
                    cumulativeProcessorLoad, minorFaults, majorFaults, contextSwitches, threads);

            if (listThreads) {
                for (OSThread threadDetail : process.getThreadDetails()) {
                    int threadId = threadDetail.getThreadId();
                    String threadName = threadDetail.getName();
                    OSProcess.State threadState = threadDetail.getState();
                    double threadCumulativeProcessorLoad = threadDetail.getThreadCpuLoadCumulative();
                    long threadMemoryStart = threadDetail.getStartMemoryAddress();
                    long threadContextSwitches = threadDetail.getContextSwitches();
                    long threadMinorFaults = threadDetail.getMinorFaults();
                    long threadMajorFaults = threadDetail.getMajorFaults();
                    long threadCpuTime = threadDetail.getKernelTime();
                    long threadUserTime = threadDetail.getUserTime();
                    long threadStartTime = threadDetail.getStartTime();
                    int threadPriority = threadDetail.getPriority();

                    System.out.printf("""
                                        %s:
                                          Name: %s
                                          Status: %s
                                          Kumulierte Prozessorlast: %s
                                          Beginnende Hauptspeicheradresse: %s
                                          Kontext änderungen: %s
                                          Kleine Fehler: %s
                                          Große Fehler: %s
                                          CPU Zeit: %s
                                          Benutzer Zeit: %s
                                          Startzeit: %s
                                          Priorität: %s
                                    """,
                            threadId, threadName, threadState, threadCumulativeProcessorLoad, threadMemoryStart,
                            threadContextSwitches, threadMinorFaults, threadMajorFaults, threadCpuTime, threadUserTime,
                            threadStartTime, threadPriority);

                }
            }
        }

        System.out.println();
    }

    public void services() {
        System.out.println("Service:");
        for (OSService service : this.os.getServices()) {
            int processId = service.getProcessID();
            String name = service.getName();
            OSService.State state = service.getState();

            System.out.printf("""
                              %s:
                                ProzessId: %s
                                Status: %s
                            """,
                    name, processId, state);
        }
        System.out.println();
    }

    public void windows() {
        System.out.println("Fenster:");
        for (OSDesktopWindow desktopWindow : this.os.getDesktopWindows(false)) {
            long id = desktopWindow.getWindowId();
            String title = desktopWindow.getTitle();
            String command = desktopWindow.getCommand();

            Rectangle position = desktopWindow.getLocAndSize();
            int positionX = position.x;
            int positionY = position.y;
            int positionWidth = position.width;
            int positionHeight = position.height;

            long processId = desktopWindow.getOwningProcessId();
            int order = desktopWindow.getOrder();
            boolean visible = desktopWindow.isVisible();

            System.out.printf("""
                              %s:
                                Title: %s
                                Befehl: %s
                                Position:
                                  X: %s
                                  Y: %s
                                  Höhe: %s
                                  Breite: %s
                                Prozess: %s
                                Anordnung: %s
                                Sichtbar: %s
                            """,
                    id, title, command, positionX, positionY, positionHeight, positionWidth,
                    processId, order, visible);
        }
        System.out.println();
    }

    public void internet() {
        System.out.println("Internet:");

        InternetProtocolStats stats = this.os.getInternetProtocolStats();

        System.out.println("  TCPv4:");
        this.printTcpStats(stats.getTCPv4Stats());

        System.out.println("  TCPv6:");
        this.printTcpStats(stats.getTCPv6Stats());

        System.out.println("  UDPv4:");
        this.printUdpStats(stats.getUDPv4Stats());

        System.out.println("  UDPv6:");
        this.printUdpStats(stats.getUDPv4Stats());

        System.out.println("  Verbindungen:");

        int id = 1;
        for (InternetProtocolStats.IPConnection connection : stats.getConnections()) {
            String type = connection.getType();
            String localAddress = new String(connection.getLocalAddress());
            int localPort = connection.getLocalPort();
            String foreignAddress = new String(connection.getForeignAddress());
            int foreignPort = connection.getForeignPort();
            InternetProtocolStats.TcpState state = connection.getState();
            int transmitQueue = connection.getTransmitQueue();
            int receiveQueue = connection.getReceiveQueue();
            int owningProcess = connection.getowningProcessId();

            System.out.printf("""
                                %s:
                                  Typ: %s
                                  Lokale Adresse: %s
                                  Lokaler Port: %s
                                  Fremde Adresse: %s
                                  Fremder Port: %s
                                  Status: %s
                                  Sendewarteschlange: %s
                                  Empfangswarteschlange: %s
                                  Besitzender Prozess: %s
                            """,
                    id++, type, localAddress, localPort, foreignAddress, foreignPort, state, transmitQueue,
                    receiveQueue, owningProcess);
        }

        System.out.println();
    }

    private void printTcpStats(InternetProtocolStats.TcpStats stats) {
        long establishedConnections = stats.getConnectionsEstablished();
        long activeConnections = stats.getConnectionsActive();
        long passiveConnections = stats.getConnectionsPassive();
        long connectionFailures = stats.getConnectionFailures();
        long resetConnections = stats.getConnectionsReset();
        long sentSegments = stats.getSegmentsSent();
        long receivedSegments = stats.getSegmentsReceived();
        long retransmittedSegments = stats.getSegmentsRetransmitted();
        long outRests = stats.getOutResets();
        long inErrors = stats.getInErrors();

        System.out.printf("""
                            Aufgebaute Verbindungen: %s
                            Aktive Verbindungen: %s
                            Passive Verbindungen: %s
                            Verbindungsfehler: %s
                            Zurückgesetzte Verbindungen: %s
                            Gesendete Segmente: %s
                            Erhaltene Segmente: %s
                            Erneut gesendete Segmente: %s
                            Ausgehente Zurücksetzungen: %s
                            Eingehende Fehler: %s
                        """,
                establishedConnections, activeConnections, passiveConnections, connectionFailures, resetConnections,
                sentSegments, receivedSegments, retransmittedSegments, outRests, inErrors);
    }

    private void printUdpStats(InternetProtocolStats.UdpStats stats) {
        long datagramsSent = stats.getDatagramsSent();
        long datagramsReceived = stats.getDatagramsReceived();
        long datagramsNoPort = stats.getDatagramsNoPort();
        long datagramsReceivedErrors = stats.getDatagramsReceivedErrors();

        System.out.printf("""
                            Erhaltene Datagrame: %s
                            Gesendete Datagrame: %s
                            Erhaltene Datagrame ohne Port: %s
                            Fehlerhafte erhaltene Datagrame: %s
                        """,
                datagramsSent, datagramsReceived, datagramsNoPort, datagramsReceivedErrors);
    }

    public void filesystem() {
        FileSystem fileSystem = this.os.getFileSystem();

        long openDescriptors = fileSystem.getOpenFileDescriptors();

        System.out.printf("""
                        Filesystem:
                          Offene Deskriptoren: %s
                          Dateispeicher:
                        """,
                openDescriptors);

        for (OSFileStore fileStore : fileSystem.getFileStores()) {
            String name = fileStore.getName();
            long freeSpace = fileStore.getFreeSpace();
            long usableSpace = fileStore.getUsableSpace();

            System.out.printf("""
                              %s:
                                Freie Kapazität: %s
                                Benutzbare Kapazität: %s
                            """,
                    name, freeSpace, usableSpace);

        }

        System.out.println();
    }

}
