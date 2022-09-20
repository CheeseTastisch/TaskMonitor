package me.taskmonitor.client;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
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
//        String hostname = InetAddress.getLocalHost().getHostName();
//        hostname = hostname.endsWith(".local") ? hostname.replace(".local", "") : hostname;
//        System.out.println(hostname);
//
        SystemInfo systemInfo = new SystemInfo();



        HardwareAbstractionLayer hal = systemInfo.getHardware();

        System.out.println(hal.getProcessor().getProcessorIdentifier().getVendor());
        System.out.println(hal.getProcessor().getProcessorIdentifier().getName());
        System.out.println(hal.getProcessor().getProcessorIdentifier().getFamily());
        System.out.println(hal.getProcessor().getProcessorIdentifier().getModel());
        System.out.println(hal.getProcessor().getProcessorIdentifier().getStepping());
        System.out.println(hal.getProcessor().getProcessorIdentifier().getProcessorID());
        System.out.println(hal.getProcessor().getProcessorIdentifier().getIdentifier());
        System.out.println(hal.getProcessor().getProcessorIdentifier().isCpu64bit());
        System.out.println(hal.getProcessor().getProcessorIdentifier().getVendorFreq());
        System.out.println(hal.getProcessor().getProcessorIdentifier().getMicroarchitecture());
        System.out.println("----");
        System.out.println(hal.getProcessor().getMaxFreq());
        System.out.println(Arrays.toString(hal.getProcessor().getCurrentFreq()));

        for (CentralProcessor.LogicalProcessor logicalProcessor : hal.getProcessor().getLogicalProcessors()) {
            System.out.println("----");
            System.out.println(logicalProcessor.getProcessorNumber());
            System.out.println(logicalProcessor.getPhysicalProcessorNumber());
            System.out.println(logicalProcessor.getPhysicalPackageNumber());
            System.out.println(logicalProcessor.getNumaNode());
            System.out.println(logicalProcessor.getProcessorGroup());
        }

        for (CentralProcessor.PhysicalProcessor physicalProcessor : hal.getProcessor().getPhysicalProcessors()) {
            System.out.println("----");
            System.out.println(physicalProcessor.getPhysicalProcessorNumber());
            System.out.println(physicalProcessor.getPhysicalPackageNumber());
            System.out.println(physicalProcessor.getEfficiency());
            System.out.println(physicalProcessor.getIdString());
        }

        System.out.println("----");
        System.out.println(Arrays.toString(hal.getProcessor().getSystemCpuLoadTicks()));
        System.out.println(Arrays.toString(hal.getProcessor().getSystemCpuLoadTicks()));
        System.out.println(hal.getProcessor().getLogicalProcessorCount());
        System.out.println(hal.getProcessor().getPhysicalProcessorCount());
        System.out.println(hal.getProcessor().getContextSwitches());
        System.out.println(hal.getProcessor().getInterrupts());


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
