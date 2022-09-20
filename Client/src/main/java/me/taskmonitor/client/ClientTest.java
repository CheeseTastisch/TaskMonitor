package me.taskmonitor.client;

import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OperatingSystem;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;

public class ClientTest {

    public static void main(String[] args) throws UnknownHostException {
        // Spezifikationen

        // Computername
//        String hostname = InetAddress.getLocalHost().getHostName();
//        hostname = hostname.endsWith(".local") ? hostname.replace(".local", "") : hostname;
//        System.out.println(hostname);
//
        SystemInfo systemInfo = new SystemInfo();



        HardwareAbstractionLayer hal = systemInfo.getHardware();

        for (PowerSource powerSource : hal.getPowerSources()) {
            System.out.println(powerSource.getName());
            System.out.println(powerSource.getDeviceName());
            System.out.println(powerSource.getRemainingCapacityPercent());
            System.out.println(powerSource.getTimeRemainingEstimated());
            System.out.println(powerSource.getTimeRemainingInstant());
            System.out.println(powerSource.getPowerUsageRate());
            System.out.println(powerSource.getVoltage());
            System.out.println(powerSource.getAmperage());
            System.out.println(powerSource.isPowerOnLine());
            System.out.println(powerSource.isCharging());
            System.out.println(powerSource.isDischarging());
            System.out.println(powerSource.getCapacityUnits());
            System.out.println(powerSource.getCurrentCapacity());
            System.out.println(powerSource.getMaxCapacity());
            System.out.println(powerSource.getDesignCapacity());
            System.out.println(powerSource.getCycleCount());
            System.out.println(powerSource.getChemistry());
            System.out.println(powerSource.getManufactureDate());
            System.out.println(powerSource.getManufacturer());
            System.out.println(powerSource.getSerialNumber());
            System.out.println(powerSource.getTemperature());
        }



//
//        System.out.println("----");
//        PhysicalMemory firstMemory = hal.getMemory().getPhysicalMemory().get(0);
//        System.out.println(firstMemory.getClockSpeed());
//        System.out.println(firstMemory.getMemoryType());
//        System.out.println(hal.getMemory().getTotal());
//
//        for (GraphicsCard graphicsCard : hal.getGraphicsCards()) {
//            System.out.println("----");
//            System.out.println(graphicsCard.getName());
//            System.out.println(graphicsCard.getVRam());
//        }

        // Jeder Tick
    }

}
