package me.taskmonitor.example.print;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Locale;

public class PrintStreamRedirect extends PrintStream {

    private final PrintStream first;
    private final PrintStream second;


    public PrintStreamRedirect(PrintStream first, PrintStream second) {
        super(first);

        this.first = first;
        this.second = second;
    }

    @Override
    public void flush() {
        this.first.close();
        this.second.flush();
    }

    @Override
    public void close() {
        this.first.close();
        this.second.close();
    }

    @Override
    public boolean checkError() {
        return this.first.checkError() || this.second.checkError();
    }

    @Override
    public void write(int b) {
        this.first.write(b);
        this.second.write(b);
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        this.first.write(buf, off, len);
        this.second.write(buf, off, len);
    }

    @Override
    public void write(byte[] buf) throws IOException {
        this.first.write(buf);
        this.second.write(buf);
    }

    @Override
    public void writeBytes(byte[] buf) {
        this.first.writeBytes(buf);
        this.second.writeBytes(buf);
    }

    @Override
    public void print(boolean b) {
        this.first.print(b);
        this.second.print(b);
    }

    @Override
    public void print(char c) {
        this.first.print(c);
        this.second.print(c);
    }

    @Override
    public void print(int i) {
        this.first.print(i);
        this.second.print(i);
    }

    @Override
    public void print(long l) {
        this.first.print(l);
        this.second.print(l);
    }

    @Override
    public void print(float f) {
        this.first.print(f);
        this.second.print(f);
    }

    @Override
    public void print(double d) {
        this.first.print(d);
        this.second.print(d);
    }

    @Override
    public void print(char[] s) {
        this.first.print(s);
        this.second.print(s);
    }

    @Override
    public void print(String s) {
        this.first.print(s);
        this.second.print(s);
    }

    @Override
    public void print(Object obj) {
        this.first.print(obj);
        this.second.print(obj);
    }

    @Override
    public void println() {
        this.first.println();
        this.second.println();
    }

    @Override
    public void println(boolean x) {
        this.first.println(x);
        this.second.println(x);
    }

    @Override
    public void println(char x) {
        this.first.println(x);
        this.second.println(x);
    }

    @Override
    public void println(int x) {
        this.first.println(x);
        this.second.println(x);
    }

    @Override
    public void println(long x) {
        this.first.println(x);
        this.second.println(x);
    }

    @Override
    public void println(float x) {
        this.first.println(x);
        this.second.println(x);
    }

    @Override
    public void println(double x) {
        this.first.println(x);
        this.second.println(x);
    }

    @Override
    public void println(char[] x) {
        this.first.println(x);
        this.second.println(x);
    }

    @Override
    public void println(String x) {
        this.first.println(x);
        this.second.println(x);
    }

    @Override
    public void println(Object x) {
        this.first.println(x);
        this.second.println(x);
    }

    @Override
    public PrintStream printf(String format, Object... args) {
        this.first.printf(format, args);
        return this.second.printf(format, args);
    }

    @Override
    public PrintStream printf(Locale l, String format, Object... args) {
        this.first.printf(l, format, args);
        return this.second.printf(l, format, args);
    }

    @Override
    public PrintStream format(String format, Object... args) {
        this.first.format(format, args);
        return this.second.format(format, args);
    }

    @Override
    public PrintStream format(Locale l, String format, Object... args) {
        this.first.format(l, format, args);
        return this.second.format(l, format, args);
    }

    @Override
    public PrintStream append(CharSequence csq) {
        this.first.append(csq);
        return this.second.append(csq);
    }

    @Override
    public PrintStream append(CharSequence csq, int start, int end) {
        this.first.append(csq, start, end);
        return this.second.append(csq, start, end);
    }

    @Override
    public PrintStream append(char c) {
        this.first.append(c);
        return this.second.append(c);
    }

    @Override
    public Charset charset() {
        return this.first.charset();
    }
}
