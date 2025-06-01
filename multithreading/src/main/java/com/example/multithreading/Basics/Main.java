package com.example.multithreading.Basics;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {
        // extends Thread class to create a custom thread
        // MyThread thread1 = new MyThread();
        // MyThread thread2 = new MyThread();

        // thread1.setName("Thread-1");
        // thread2.setName("Thread-2");
        // thread1.start();
        // thread2.start();

        // implements Runnable interface to create a custom thread
        // MyRunnable runnable = new MyRunnable();
        // Thread thread1 = new Thread(runnable);
        // Thread thread2 = new Thread(runnable);
        // thread1.setName("Thread-1");
        // thread2.setName("Thread-2");
        // thread1.start();
        // thread2.start();

        // implements Callable interface to create a custom thread
        // Create ExecutorService with a fixed thread pool
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Create Callable instances
        Callable<String> callable1 = new MyCallable("Task 1");
        Callable<String> callable2 = new MyCallable("Task 2");

        try {
            // Submit Callable tasks to the executor and get Future objects
            Future<String> future1 = executor.submit(callable1);
            Future<String> future2 = executor.submit(callable2);

            // Get results from Future objects
            System.out.println("Result from first task:");
            System.out.println(future1.get()); // Blocks until the task completes

            System.out.println("Result from second task:");
            System.out.println(future2.get()); // Blocks until the task completes

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Task execution interrupted: " + e.getMessage());
        } finally {
            // Shutdown the executor
            executor.shutdown();
        }
    }
}