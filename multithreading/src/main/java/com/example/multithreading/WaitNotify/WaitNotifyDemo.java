package com.example.multithreading.WaitNotify;

public class WaitNotifyDemo {
    // Lock object used for synchronization
    private final Object lock = new Object();
    // Condition flag that threads check to decide whether to continue
    private boolean conditionMet = false;

    // Method where threads wait until conditionMet is true.
    public void doWait() {
        synchronized (lock) {
            while (!conditionMet) { // Loop to avoid spurious wakeups
                try {
                    System.out.println(Thread.currentThread().getName() + " is waiting.");
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println(Thread.currentThread().getName() + " was interrupted.");
                }
            }
            System.out.println(Thread.currentThread().getName() + " resumed execution.");
        }
    }

    // Sets the condition to true and calls notify() so that one waiting thread is
    // awakened.
    public void doNotify() {
        synchronized (lock) {
            conditionMet = true;
            System.out.println(Thread.currentThread().getName() + " called notify().");
            lock.notify(); // Wakes up one waiting thread (if any)
        }
    }

    // Sets the condition to true and calls notifyAll() so that all waiting threads
    // are awakened.
    public void doNotifyAll() {
        synchronized (lock) {
            conditionMet = true;
            System.out.println(Thread.currentThread().getName() + " called notifyAll().");
            lock.notifyAll(); // Wakes up all waiting threads
        }
    }

    public static void main(String[] args) {
        // ******************************
        // Demonstrating notifyAll()
        // ******************************
        System.out.println("Demonstrating notifyAll():");
        WaitNotifyDemo demoAll = new WaitNotifyDemo();
        Thread waiter1 = new Thread(() -> demoAll.doWait(), "Waiter-1");
        Thread waiter2 = new Thread(() -> demoAll.doWait(), "Waiter-2");
        Thread waiter3 = new Thread(() -> demoAll.doWait(), "Waiter-3");
        waiter1.start();
        waiter2.start();
        waiter3.start();
        // Sleep to ensure all waiting threads have started and are waiting.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Thread notifierAll = new Thread(() -> demoAll.doNotifyAll(), "Notifier-All");
        notifierAll.start();
        try {
            waiter1.join();
            waiter2.join();
            waiter3.join();
            notifierAll.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // ******************************
        // Demonstrating notify()
        // ******************************
        System.out.println("nDemonstrating notify():");
        WaitNotifyDemo demoNotify = new WaitNotifyDemo();
        Thread waiterN1 = new Thread(() -> demoNotify.doWait(), "Waiter-N1");
        Thread waiterN2 = new Thread(() -> demoNotify.doWait(), "Waiter-N2");
        waiterN1.start();
        waiterN2.start();
        // Sleep to ensure waiting threads are waiting.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Thread notifier = new Thread(() -> demoNotify.doNotify(), "Notifier");
        notifier.start();
        // After calling notify(), only one waiting thread will resume while the other
        // remains waiting.
        // To ensure the program finishes, we call notifyAll() later to wake any
        // remaining waiting thread.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Calling notifyAll() to wake the remaining waiting thread.");
        Thread notifier2 = new Thread(() -> demoNotify.doNotifyAll(), "Notifier2");
        notifier2.start();
        try {
            waiterN1.join();
            waiterN2.join();
            notifier.join();
            notifier2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Main thread: Execution finished.");
    }
}