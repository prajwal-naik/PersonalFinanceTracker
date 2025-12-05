package org.PersonalFinanceTracker.io;

import java.util.Scanner;

public class SystemIO implements IO{
    private final Scanner scanner = new Scanner(System.in);
    public String readLine() {
        return scanner.nextLine();
    }

    public void print(String s) {
        System.out.println(s);
    }
}
