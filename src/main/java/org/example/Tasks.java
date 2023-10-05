package org.example;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Tasks {


    public static void Task1(DataBaseConnect connect){
        connect.createTable();
    }
    public static void Task2(DataBaseConnect connect, Scanner in){
        String[] str;
        Worker worker= new Worker();
        str=in.nextLine().split("\\s");;
        worker.setFullName(str[1]+" "+str[2]+" "+str[3]);

        worker.setBirthDate(Date.valueOf(str[4]));


        worker.setGender(str[5]);
        worker.saveWorker(connect);

    }
    public static void Task3(DataBaseConnect connect){

        try {
            PreparedStatement statement=connect.getConnection().prepareStatement("SELECT DISTINCT fullName, birthdate, gender FROM `workers` ORDER BY fullName");
            ResultSet resultSet=statement.executeQuery();
            Worker worker=new Worker();
            while (resultSet.next()){
                worker.setFullName(resultSet.getString(1));
                worker.setBirthDate(resultSet.getDate(2));
                worker.setGender(resultSet.getString(3));
                System.out.println(worker.toString());
            }
            resultSet.close();
            statement.close();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void Task4(DataBaseConnect connect){

        Random random= new Random();
        byte[] array = new byte[10]; // length is bounded by 7
        String generatedString;
        int[] ints=new int[26];
        Date date= Date.valueOf(LocalDate.EPOCH);
        ArrayList<Worker> workers=new ArrayList<>(1000100);
        for (int i=0;i<1000000;i++) {
            random.nextBytes(array);
             generatedString = new String(array, StandardCharsets.ISO_8859_1);
             workers.add(new Worker((char)('A'+i%26)+generatedString,date,
                     random.nextInt()>0.5?"Male":"Female" ));

        }
        for (int i=0;i<100;i++) {
            random.nextBytes(array);
            generatedString = new String(array, StandardCharsets.ISO_8859_1);
            workers.add(new Worker('F'+generatedString,date,"Male" ));
        }
        connect.saveBatchItem(workers);

    }
    public static void Task5(DataBaseConnect connect){
        try {
            long start=System.currentTimeMillis();
            PreparedStatement statement=connect.getConnection().prepareStatement("SELECT * FROM WORKERS \n" +
                    "WHERE gender='Male' AND  fullName LIKE'F%'");

            ResultSet resultSet=statement.executeQuery();
            Worker worker=new Worker();
            while (resultSet.next()){
                worker.setFullName(resultSet.getString(2));
                worker.setBirthDate(resultSet.getDate(3));
                worker.setGender(resultSet.getString(4));
                System.out.println(worker.toString());
            }
            System.out.printf("Time taken in milliseconds %d",System.currentTimeMillis()-start);
            resultSet.close();
            statement.close();


        } catch (SQLException e) {
            System.out.println("Cannot load data, check data base connection");
        }

    }
    public static void Task6(DataBaseConnect connect){

    }
}
