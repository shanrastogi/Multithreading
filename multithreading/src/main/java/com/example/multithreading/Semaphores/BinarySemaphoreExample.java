package com.example.multithreading.Semaphores;

import java.util.concurrent.Semaphore;

public class BinarySemaphoreExample {
    private static final Semaphore mutex = new Semaphore(1); // Binary semaphore with 1 permit

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> accessCriticalSection("Thread-1"));
        Thread t2 = new Thread(() -> accessCriticalSection("Thread-2"));
        Thread t3 = new Thread(() -> accessCriticalSection("Thread-3"));
        t1.start();
        t2.start();
        t3.start();
    }

    private static void accessCriticalSection(String threadName) {
        try {
            System.out.println(threadName + " is attempting to acquire the lock.");
            mutex.acquire(); // Acquire the semaphore
            Thread.sleep(1000);
            System.out.println(threadName + " acquired the lock.");
            Thread.sleep(2000); // Simulate work in the critical section
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            mutex.release(); // Release the semaphore
            System.out.println(threadName + " released the lock.");
        }
    }
}