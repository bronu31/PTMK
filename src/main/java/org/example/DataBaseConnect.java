package org.example;

import java.sql.*;
import java.util.List;

public class DataBaseConnect {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public DataBaseConnect() {
        try {
            connection= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ptmk_tasks","root","root"
            );
        } catch (SQLException e) {
            System.out.println("Connect to data base");
        }
    }



    public void closeConnection(){
        try {
            this.connection.close();
        } catch (SQLException e) {
            System.out.println("Connection isn't open");
        }
    }
    public void saveItem(Worker worker){
        try {
            PreparedStatement statement=connection.prepareStatement("SELECT MAX(id) FROM workers");
            ResultSet id=statement.executeQuery();

            if (!id.isBeforeFirst()&& id.getRow() == 0) worker.setID(0L);
            else {id.next();
                worker.setID(id.getLong(1)+1);}

            statement =connection.prepareStatement("INSERT INTO `workers` (`id`, `fullName`,`birthdate`,`gender`)" +
                    " VALUES ( ?, ?, ?,?)");
            statement.setLong(1,worker.getID());
            statement.setString(2,worker.getFullName());
            statement.setDate(3,  worker.getBirthDate());
            statement.setString(4,worker.getGender());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Cannot save data, check data base connection");
        }

    }

    public void saveBatchItem(List<Worker> workers){
        try {
            PreparedStatement statement=connection.prepareStatement("SELECT MAX(id) FROM workers");
            ResultSet id=statement.executeQuery();
            long idCycle;
            if (!id.isBeforeFirst()&& id.getRow() == 0) idCycle=0L;
            else {id.next();
                idCycle=id.getLong(1)+1;}
            id.close();
            statement =connection.prepareStatement("INSERT INTO `workers` (`id`, `fullName`,`birthdate`,`gender`)" +
                    " VALUES ( ?, ?, ?,?)");
            for (int i=0;i<workers.size();i++) {
                statement.setLong(1, idCycle+i);
                statement.setString(2, workers.get(i).getFullName());
                statement.setDate(3, workers.get(i).getBirthDate());
                statement.setString(4, workers.get(i).getGender());
                statement.addBatch();
            }
            statement.executeBatch();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Cannot save data, check data base connection");
        }

    }



    public void createTable(){

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) "
                    + "FROM information_schema.tables "
                    + "WHERE table_name = ?"
                    + "LIMIT 1;");

        preparedStatement.setString(1, "workers");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        if (resultSet.getInt(1) == 0){
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE workers " +
                    "(id INTEGER not NULL, " +
                    " fullName VARCHAR(255), " +
                    " birthdate DATE, " +
                    " gender VARCHAR(255), " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("Table created ");
        }
        else {
            System.out.println("Table exist");
        }
        } catch (SQLException e) {
            System.out.println("Cannot create table");
        }
    }
}
