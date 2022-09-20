package me.taskmonitor.client;

import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PhysicalMemory;
import oshi.software.os.OperatingSystem;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class ClientTest {

    public static void main(String[] args) throws UnknownHostException {
        // Spezifikationen

        // Computername
        String hostname = InetAddress.getLocalHost().getHostName();
        hostname = hostname.endsWith(".local") ? hostname.replace(".local", "") : hostname;
        System.out.println(hostname);

        SystemInfo systemInfo = new SystemInfo();



        HardwareAbstractionLayer hal = systemInfo.getHardware();

        System.out.println("----");
        System.out.println(hal.getProcessor().getProcessorIdentifier().getName());
        System.out.println(hal.getProcessor().getPhysicalProcessorCount());
        System.out.println(hal.getProcessor().getLogicalProcessorCount());
        System.out.println(hal.getProcessor().getMaxFreq());

        System.out.println("----");
        PhysicalMemory firstMemory = hal.getMemory().getPhysicalMemory().get(0);
        System.out.println(firstMemory.getClockSpeed());
        System.out.println(firstMemory.getMemoryType());
        System.out.println(hal.getMemory().getTotal());

        for (GraphicsCard graphicsCard : hal.getGraphicsCards()) {
            System.out.println("----");
            System.out.println(graphicsCard.getName());
            System.out.println(graphicsCard.getVRam());
        }

        System.out.println(hal.getComputerSystem().getManufacturer());

        // Jeder Tick
    }

}
