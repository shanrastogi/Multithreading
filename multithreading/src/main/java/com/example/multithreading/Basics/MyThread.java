package com.example.multithreading.Basics;

// extends Thread class to create a custom thread
public class MyThread extends Thread {
    @Override
    public void run() {
        // code to be executed by the thread
        for (int i = 0; i < 5; i++) {
            System.out.println("Thread " + Thread.currentThread().getName() + " is running: " + i);
            try {
                Thread.sleep(1000); // sleep for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
