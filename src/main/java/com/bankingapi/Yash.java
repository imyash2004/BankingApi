package com.bankingapi;

public class Yash {
    public static void main(String[] args) {


        System.out.println("yahs");
    }
    public static void prinFibbhSeries(int count) {
        int num1 = 0, num2 = 1;

        System.out.println("Fibcci ]tfbjkncfrknlrioeries up to " + count + " numbers:");

        for (int i = 1; i <= count; i++) {
            System.out.println(num1 + " ");

            int nextNum = num1 + num2;
            num1 = num2;
            num2 = nextNum;
        }
    }
    public static void printHelloWorld() {
        System.out.println("Hello, World!");
    }
}
