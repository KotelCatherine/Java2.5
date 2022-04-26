package ru.geekbrains.java2;

import java.util.Arrays;

public class ThreadApp {

    private static final int SIZE = 10_000_000;
    private static float[] array = new float[SIZE];

    public static void main(String[] args) {
        firstMethod();
        secondMethod();
    }

    private static void firstMethod() {
        Arrays.fill(array, 1.0f);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < array.length; i++) {
            array[i] = mathematicalFormula(array[i], i);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("One thread time: " + (endTime - startTime) + "ms.");
    }

    private static void secondMethod() {
        int halfSize = SIZE / 2;
        float[] leftHalfArray = new float[halfSize];
        float[] rightHalfArray = new float[halfSize];

        Arrays.fill(array, 1.0f);

        long startTime = System.currentTimeMillis();

        System.arraycopy(array, 0, leftHalfArray, 0, halfSize);
        System.arraycopy(array, halfSize, rightHalfArray, 0, halfSize);

        threadLeftArray(leftHalfArray);
        threadRightArray(rightHalfArray);

        System.arraycopy(leftHalfArray, 0, array, 0, halfSize);
        System.arraycopy(rightHalfArray, 0, array, halfSize, halfSize);

        long endTime = System.currentTimeMillis();

        System.out.println("Two thread time: " + (endTime - startTime) + "ms.");
    }

    private static void threadLeftArray(float[] leftHalfArray) {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < leftHalfArray.length; i++) {
                leftHalfArray[i] = mathematicalFormula(leftHalfArray[i], i);
            }
        });
        thread1.start();

        try {
            thread1.join();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }
    }

    private static void threadRightArray(float[] rightHalfArray) {
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < rightHalfArray.length; i++) {
                rightHalfArray[i] = mathematicalFormula(rightHalfArray[i], i);
            }
        });
        thread2.start();

        try {
            thread2.join();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }
    }

    private static float mathematicalFormula(float leftHalfArray, int index) {
        return (float) (leftHalfArray * Math.sin(0.2f + index / 5.0) * Math.cos(0.2f + index
                / 5.0) * Math.cos(0.4f + index / 2.0));
    }
}
