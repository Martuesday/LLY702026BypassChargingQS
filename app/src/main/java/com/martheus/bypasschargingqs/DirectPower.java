package com.martheus.bypasschargingqs;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/** Reads/writes the direct_power_supply sysfs node through su. Blocking: call off the main thread. */
public final class DirectPower {
    public static final String PATH = "/sys/class/power_supply/battery/device/direct_power_supply";
    public static final int ON = 1, OFF = 0, UNSUPPORTED = -1;

    private DirectPower() {}

    /** Returns ON, OFF or UNSUPPORTED (no root, node missing, or unexpected content). */
    public static int read() {
        String out = su("cat " + PATH);
        if (out == null) return UNSUPPORTED;
        out = out.trim();
        if (out.startsWith("1")) return ON;
        if (out.startsWith("0")) return OFF;
        return UNSUPPORTED;
    }

    /** Writes the value, then returns the state actually read back. */
    public static int set(boolean enable) {
        su("echo " + (enable ? 1 : 0) + " > " + PATH);
        return read();
    }

    private static String su(String command) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("su");
            try (DataOutputStream os = new DataOutputStream(p.getOutputStream())) {
                os.writeBytes(command + "\n");
                os.writeBytes("exit\n");
                os.flush();
            }
            StringBuilder sb = new StringBuilder();
            try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = r.readLine()) != null) sb.append(line).append('\n');
            }
            if (p.waitFor(5, TimeUnit.SECONDS)) {
                return sb.toString();
            } else {
                p.destroyForcibly();
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            if (p != null) p.destroy();
        }
    }
}
