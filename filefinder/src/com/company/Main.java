package com.company;


public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.exit(0);
        } else {
            FileSearcher fsearcher = new FileSearcher(args[0]);
            fsearcher.run();
        }
    }
}
