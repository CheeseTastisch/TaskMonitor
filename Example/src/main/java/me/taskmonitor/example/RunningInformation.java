package me.taskmonitor.example;

import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PhysicalMemory;

public class RunningInformation {

    public static void main(String[] args) {
        HardwareAbstractionLayer hal = new SystemInfo().getHardware();

        for (HWDiskStore diskStore : hal.getDiskStores()) {

        }

    }

}
