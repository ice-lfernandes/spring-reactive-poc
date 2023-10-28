package br.com.ldf.springreactivepoc.service;

public final class SleepUtil {

    private SleepUtil() {
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
