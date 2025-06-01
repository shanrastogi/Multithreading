package com.example.multithreading.WaitNotify;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer {
    private final Queue<Integer> buffer = new LinkedList<>();
    private final int capacity = 5;

    public void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            synchronized (this) {
                while (buffer.size() == capacity) {
                    System.out.println("Buffer is full, producer is waiting.");
                    wait(); // Wait until space is available
                }
                buffer.add(value);
                System.out.println("Producer produced: " + value);
                value++;
                notifyAll(); // Notify consumers that an item has been produced
            }
            Thread.sleep(1000); // Simulate time taken to produce an item
        }
    }

    public void consume() throws InterruptedException {
        while (true) {
            synchronized (this) {
                while (buffer.isEmpty()) {
                    System.out.println("Buffer is empty, consumer is waiting.");
                    wait(); // Wait until an item is available
                }
                int value = buffer.poll();
                System.out.println("Consumer consumed: " + value);
                notifyAll(); // Notify producers that space is available
            }
            Thread.sleep(2000); // Simulate time taken to consume an item
        }
    }

    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();

        // Creating the producer thread.
        Thread producerThread = new Thread(new Runnable() {
            public void run() {
                try {
                    pc.produce();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Producer thread interrupted.");
                }
            }
        }, "ProducerThread");

        // Creating the consumer thread.
        Thread consumerThread = new Thread(new Runnable() {
            public void run() {
                try {
                    pc.consume();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Consumer thread interrupted.");
                }
            }
        }, "ConsumerThread");

        producerThread.start();
        consumerThread.start();
    }
}
