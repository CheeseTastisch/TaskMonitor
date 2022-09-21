package me.taskmonitor.example;

import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.FileSystem;
import oshi.software.os.NetworkParams;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

public class GeneralInformation {

    private OperatingSystem os;
    private HardwareAbstractionLayer hal;

    public static void main(String[] args) {
        GeneralInformation.test();
    }

    public static void test() {
        GeneralInformation generalInformation = new GeneralInformation();

        generalInformation.setup();

        generalInformation.hostname();
        generalInformation.mainboard();
        generalInformation.firmware();
        generalInformation.cpu();
        generalInformation.ram();
        generalInformation.hwDisk();
        generalInformation.gpu();
        generalInformation.networkIf();
        generalInformation.psu();
        generalInformation.soundcard();
        generalInformation.os();
        generalInformation.internet();
        generalInformation.fileSystem();
    }

    public void setup() {
        SystemInfo systemInfo = new SystemInfo();

        this.os = systemInfo.getOperatingSystem();
        this.hal = systemInfo.getHardware();
    }

    public void hostname() {
        String hostname = this.os.getNetworkParams().getHostName();

        System.out.printf("Hostname: %s\n\n", hostname);
    }

    public void mainboard() {
        ComputerSystem computerSystem = this.hal.getComputerSystem();
        Baseboard baseboard = computerSystem.getBaseboard();

        String manufacturer = better(baseboard.getManufacturer(), computerSystem.getManufacturer());
        String model = better(baseboard.getModel(), computerSystem.getModel());
        String hardwareUID = computerSystem.getHardwareUUID();

        System.out.printf("""
                Mainboard:
                  Hersteller: %s
                  Model: %s
                  HardwareUID: %s
                                        
                """, manufacturer, model, hardwareUID);
    }

    public void firmware() {
        Firmware firmware = this.hal.getComputerSystem().getFirmware();

        String name = firmware.getName();
        String version = firmware.getVersion();
        String manufacturer = firmware.getManufacturer();
        String releaseDate = firmware.getReleaseDate();

        System.out.printf("""
                Firmware:
                  Name: %s
                  Version: %s
                  Hersteller: %s
                  Veröffentlichungsdatum: %s
                                
                """, name, version, manufacturer, releaseDate);
    }

    public void cpu() {
        CentralProcessor centralProcessor = this.hal.getProcessor();

        System.out.println("CPU:");

        // identifier
        CentralProcessor.ProcessorIdentifier identifier = centralProcessor.getProcessorIdentifier();
        String manufacturer = identifier.getVendor();
        String name = identifier.getName();
        String family = identifier.getFamily();
        String stepping = identifier.getStepping();
        String processorId = identifier.getProcessorID();
        boolean x64 = identifier.isCpu64bit();
        String architecture = identifier.getMicroarchitecture();

        System.out.printf("""
                  Kennung:
                    Hersteller: %s
                    Name: %s
                    Familie: %s
                    Stepping: %s
                    ProzessorId: %s
                    x64: %s
                    Mikroarchitektur: %s
                """, manufacturer, name, family, stepping, processorId, x64, architecture);

        // General
        long frequenz = centralProcessor.getMaxFreq();
        int cores = centralProcessor.getPhysicalProcessorCount();
        int threads = centralProcessor.getLogicalProcessorCount();

        System.out.printf("""
                  Frequenz: %s
                  Kerne: %s
                  Threads: %s
                """, frequenz, cores, threads);

        System.out.println("""
                  Cache: DOTO
                """);
    }

    public void ram() {
        GlobalMemory memory = this.hal.getMemory();

        long capacity = memory.getTotal();
        long pages = memory.getPageSize();
        long swapCapacity = memory.getVirtualMemory().getSwapTotal();
        long virtualCapacity = memory.getVirtualMemory().getVirtualMax();

        System.out.printf("""
                Hauptspeicher:
                  Kapazität: %s
                  Page Anzahl: %s
                  Swap Kapazität: %s
                  Virtuelle Kapazität: %s
                  Physisch:
                """, capacity, pages, swapCapacity, virtualCapacity);

        for (PhysicalMemory physicalMemory : memory.getPhysicalMemory()) {
            String bank = physicalMemory.getBankLabel();
            long physicalCapacity = physicalMemory.getCapacity();
            long frequenz = physicalMemory.getClockSpeed();
            String manufacturer = physicalMemory.getManufacturer();
            String type = physicalMemory.getMemoryType();

            System.out.printf("""
                        %s:
                          Kapazität: %s
                          Frequenz: %s
                          Hersteller: %s
                          Typ: %s
                    """, bank, physicalCapacity, frequenz, manufacturer, type);
        }

        System.out.println();
    }

    public void hwDisk() {
        System.out.println("Sekundärspeicher:");

        for (HWDiskStore diskStore : this.hal.getDiskStores()) {
            String name = diskStore.getName();
            String model = diskStore.getModel();
            long capacity = diskStore.getSize();

            System.out.printf("""
                      %s:
                        Model: %s
                        Kapazität: %s
                        Partitionen:
                    """, name, model, capacity);

            for (HWPartition partition : diskStore.getPartitions()) {
                String partitionIdentification = partition.getIdentification();
                String partitionType = partition.getType();
                String partitionUuid = partition.getUuid();
                long partitionCapacity = partition.getSize();
                String partitionMountPoint = partition.getMountPoint();

                System.out.printf("""
                              %s:
                                Typ: %s
                                UUID: %s
                                Kapazität: %s
                                Einbindepunkt: %s
                        """, partitionIdentification, partitionType, partitionUuid, partitionCapacity, partitionMountPoint);
            }

            System.out.println();
        }
    }

    public void gpu() {
        System.out.println("GPU:");
        for (GraphicsCard graphicsCard : this.hal.getGraphicsCards()) {
            String name = graphicsCard.getName();
            String manufacturer = graphicsCard.getVendor();
            long vram = graphicsCard.getVRam();

            System.out.printf("""
                      %s:
                        Hersteller: %s
                        Vram: %s
                    """, name, manufacturer, vram);
        }

        System.out.println();
    }

    public void networkIf() {
        System.out.println("Netzwerkschnittstellen:");
        for (NetworkIF networkIf : this.hal.getNetworkIFs(true)) {

            String name = networkIf.getName();
            String displayName = networkIf.getDisplayName();
            String alias = networkIf.getIfAlias();
            long mtu = networkIf.getMTU();
            String macAddress = networkIf.getMacaddr();
            long speed = networkIf.getSpeed();

            System.out.printf("""
                      %s:
                        Anzeigenname: %s
                        Alias: %s
                        Maximale Übertragungseinheit: %s
                        Mac Adresse: %s
                        Geschwindigkeit: %s
                        IPv4-Adressen:
                    """, name, displayName, alias, mtu, speed, macAddress);

            for (int i = 0; i < networkIf.getIPv4addr().length; i++) {
                String ip = networkIf.getIPv4addr()[i];
                short subnetMask = networkIf.getSubnetMasks()[i];

                System.out.printf("      %s/%s\n", ip, subnetMask);
            }

            System.out.println("    IPv6-Adressen:");

            for (int i = 0; i < networkIf.getIPv6addr().length; i++) {
                String ip = networkIf.getIPv6addr()[i];
                short prefix = networkIf.getPrefixLengths()[i];

                System.out.printf("      %s/%s\n", ip, prefix);
            }
        }

        System.out.println();
    }

    public void psu() {
        System.out.println("Netzteile:");
        for (PowerSource powerSource : this.hal.getPowerSources()) {
            String name = powerSource.getName();

            System.out.printf("  %s\n", name);
        }

        System.out.println();
    }

    public void soundcard() {
        System.out.println("Soundkarten:");

        for (SoundCard soundCard : this.hal.getSoundCards()) {
            String name = soundCard.getName();
            String codec = soundCard.getCodec();

            System.out.printf("""
                      %s:
                        Codec: %s
                    """, name, codec);
        }

        System.out.println();
    }

    public void os() {
        String family = this.os.getFamily();
        String manufacturer = this.os.getManufacturer();
        String version = this.os.getVersionInfo().getVersion();
        String codec = this.os.getVersionInfo().getCodeName();
        String buildnumber = this.os.getVersionInfo().getBuildNumber();

        System.out.printf("""
                Betriebssystem:
                  Familie: %s
                  Hersteller: %s
                  Version: %s
                  Codec: %s
                  Buildnummer: %s
                  
                """, family, manufacturer, version, codec, buildnumber);
    }

    public void internet() {
        NetworkParams networkParams = this.os.getNetworkParams();

        System.out.print("""
                Internet:
                  DNS:
                """);
        for (String dnsServer : networkParams.getDnsServers()) {
            System.out.printf("    %s\n", dnsServer);
        }

        String ipv4DefaultGateway = networkParams.getIpv4DefaultGateway();
        String ipv6DefaultGateway = networkParams.getIpv6DefaultGateway();

        System.out.printf("""
                  Standartgateways:
                    IPv4: %s
                    IPv6: %s
                                
                """, ipv4DefaultGateway, ipv6DefaultGateway);
    }

    public void fileSystem() {
        FileSystem fileSystem = this.os.getFileSystem();

        long maxFileDescriptors = fileSystem.getMaxFileDescriptors();

        System.out.printf("""
                Filesystem:
                  Maximale Dateideskriptoren: %s
                  Dateispeicher:
                """, maxFileDescriptors);

        for (OSFileStore fileStore : fileSystem.getFileStores()) {
            String name = fileStore.getName();
            String volume = fileStore.getVolume();
            String label = fileStore.getLabel();
            String mountPoint = fileStore.getMount();
            String description = fileStore.getDescription();
            String type = fileStore.getType();
            long capacity = fileStore.getTotalSpace();

            System.out.printf("""
                        %s:
                          Volumen: %s
                          Label: %s
                          Einbindepunkt: %s
                          Beschreibung: %s
                          Typ: %s
                          Kapazität: %s
                    """, name, volume, label, mountPoint, description, type, capacity);
        }
    }

    public String better(String a, String b) {
        if (a != null && !a.equals("unknown") && !a.equals("Default String")) return a;
        return b;
    }


}
