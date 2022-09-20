package me.taskmonitor.client;

import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OperatingSystem;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

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

        for (HWDiskStore diskStore : hal.getDiskStores()) {
            System.out.println("---------");
            System.out.println(diskStore.getName());
            System.out.println(diskStore.getModel());
            System.out.println(diskStore.getSerial());
            System.out.println(diskStore.getSize());
            for (HWPartition partition : diskStore.getPartitions()) {
                System.out.println("---");
                System.out.println(partition.getIdentification());
                System.out.println(partition.getName());
                System.out.println(partition.getType());
                System.out.println(partition.getUuid());
                System.out.println(partition.getSize());
                System.out.println(partition.getMajor());
                System.out.println(partition.getMinor());
                System.out.println(partition.getMountPoint());
            }
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
