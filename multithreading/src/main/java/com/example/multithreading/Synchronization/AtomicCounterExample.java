package com.example.multithreading.Synchronization;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounterExample {
    // The AtomicInteger counter provides atomic methods for thread-safe operations.
    private AtomicInteger counter = new AtomicInteger(0);

    // This method atomically increments the counter and prints the updated value.
    public void increment() {
        int newValue = counter.incrementAndGet(); // Atomically increments the value.
        System.out.println(Thread.currentThread().getName() + " incremented counter to " + newValue);
    }

    // Retrieves the current counter value.
    public int getCounter() {
        return counter.get();
    }

    // Main method to run the AtomicCounterExample.
    public static void main(String[] args) {
        final AtomicCounterExample example = new AtomicCounterExample();
        int numberOfThreads = 10;
        // Each thread will perform 100 increments.
        int incrementsPerThread = 100;
        Thread[] threads = new Thread[numberOfThreads];
        // Create and start threads that perform increments on the atomic counter.
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < incrementsPerThread; j++) {
                        example.increment();
                    }
                }
            }, "Thread-" + (i + 1));
            threads[i].start();
        }
        // Wait for all threads to complete execution.
        for (int i = 0; i < numberOfThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Display the final counter value.
        System.out.println("Final counter value: " + example.getCounter());
    }
}