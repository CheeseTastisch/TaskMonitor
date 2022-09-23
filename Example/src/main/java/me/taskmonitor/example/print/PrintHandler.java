package me.taskmonitor.example.print;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PrintHandler implements Closeable, AutoCloseable {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private final boolean console;
    private final boolean file;
    private final String filename;

    private PrintStream systemStream;

    private PrintStream fileStream;

    private Long startTime;

    public PrintHandler(boolean console, boolean file, String filename) {
        this.console = console;
        this.file = file;
        this.filename = filename;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void start() {
        try {
            this.systemStream = System.out;

            if (file) {
                File dumpDir = new File(".", "dump");
                if (!dumpDir.exists()) dumpDir.mkdirs();

                String dateTime = dateFormat.format(Calendar.getInstance().getTime());
                File writeFile = new File(dumpDir, filename + "-" + dateTime + ".txt");
                if (!writeFile.exists()) writeFile.createNewFile();
                this.fileStream = new PrintStream(writeFile);
            }

            if (file && console) {
                PrintStreamRedirect printStreamRedirect = new PrintStreamRedirect(this.systemStream, this.fileStream);
                System.setOut(printStreamRedirect);
            } else if (file) System.setOut(this.fileStream);

            this.startTime = System.nanoTime();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if (this.systemStream != null && this.startTime != null) {
            long endTime = System.nanoTime();

            System.out.println("Took: " + getTime(endTime));
            System.setOut(this.systemStream);
        }
    }

    private final List<Map.Entry<Integer, String>> units = new ArrayList<>() {{
        add(new AbstractMap.SimpleEntry<>(1000, "ns"));
        add(new AbstractMap.SimpleEntry<>(1000, "µs"));
        add(new AbstractMap.SimpleEntry<>(1000, "ms"));
        add(new AbstractMap.SimpleEntry<>(60, "s"));
        add(new AbstractMap.SimpleEntry<>(60, "m"));
        add(new AbstractMap.SimpleEntry<>(24, "h"));
    }};

    private String getTime(long endTime) {
        double took = endTime - startTime;

        for (Map.Entry<Integer, String> unit : units) {
            if (took / unit.getKey() > 1) took /= unit.getKey();
            else return (Math.round(took * 100) / 100d) + unit.getValue();
        }

        return took + "d";
    }


}
