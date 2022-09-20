package me.taskmonitor.client;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PhysicalMemory;
import oshi.software.os.OperatingSystem;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientTest {

    public static void main(String[] args) throws UnknownHostException {
        // Spezifikationen

        // Computername
//        String hostname = InetAddress.getLocalHost().getHostName();
//        hostname = hostname.endsWith(".local") ? hostname.replace(".local", "") : hostname;
//        System.out.println(hostname);

        // CPU - Modell
        SystemInfo systemInfo = new SystemInfo();

        HardwareAbstractionLayer hal = systemInfo.getHardware();
        System.out.println(hal.getProcessor().getProcessorIdentifier().toString());
        System.out.println(hal.getProcessor().getPhysicalProcessorCount());
        



        // Jeder Tick
    }

}
