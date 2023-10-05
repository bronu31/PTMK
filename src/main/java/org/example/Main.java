package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner in= new Scanner(System.in);
        DataBaseConnect connect= new DataBaseConnect();





        switch(in.nextInt()){
            case 1: Tasks.Task1(connect);break;
            case 2: Tasks.Task2(connect, in);break;
            case 3: Tasks.Task3(connect);break;
            case 4: Tasks.Task4(connect);break;
            case 5: Tasks.Task5(connect);break;
            case 6: break;
        }
        in.close();
        connect.closeConnection();
    }
}