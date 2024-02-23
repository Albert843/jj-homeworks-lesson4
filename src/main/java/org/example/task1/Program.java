package org.example.task1;
import org.example.models.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Program {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "root";

        Connection connection = DriverManager.getConnection(url, user, password);
        Random random = new Random();

        createDatabase(connection);
        System.out.println("Database <schoolDB> created");

        useDatabase(connection);
        System.out.println("Database <schoolDB> using");

        createTable(connection);
        System.out.println("Table <courses> created");

        // Create
        int count = random.nextInt(5, 11);
        for (int i = 0; i < count; i++) {
            insertData(connection, Course.create());
        }
        System.out.println("Insert data into table <courses>");

        // Read
        Collection<Course> courses = readDate(connection);
        for(var course:courses) {
            System.out.println(course);
        }
        System.out.println("Read data from table <courses>");

        //Update
        for(var course:courses) {
            course.updateTitle();
            course.updateDuration();
            updateData(connection, course);
        }
        System.out.println("Update data from table <courses>");

        //  Delete
        for(var course:courses) {
            deleteData(connection, course.getId());
        }
        System.out.println("Deleted data from table <courses>");

        connection.close();
        System.out.println("Connection closed");
    }

    private static void createDatabase(Connection connection) throws SQLException {
        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS schoolDB;";
        try(PreparedStatement statement = connection.prepareStatement(createDatabaseSQL)) {
            statement.execute();
        }
    }

    private static void useDatabase(Connection connection) throws SQLException {
        String useDatabaseSQL = "USE schoolDB;";
        try(PreparedStatement statement = connection.prepareStatement(useDatabaseSQL)) {
            statement.execute();
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS courses (id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(45), duration INT);";
        try(PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.execute();
        }
    }

    //  Create data
    private static void insertData(Connection connection, Course course) throws SQLException {
        String insertDataSQL = "INSERT INTO courses (title, duration) VALUES (?, ?);";
        try(PreparedStatement statement = connection.prepareStatement(insertDataSQL)) {
            statement.setString(1, course.getTitle());
            statement.setInt(2, course.getDuration());
            statement.executeUpdate();
        }
    }

    //  Read data
    private static Collection<Course> readDate(Connection connection) throws SQLException {
        ArrayList<Course> courseList = new ArrayList<>();
        String readDataSQL = "SELECT * FROM courses;";
        try(PreparedStatement statement = connection.prepareStatement(readDataSQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int duration = resultSet.getInt("duration");
                courseList.add(new Course(id, title, duration));
            }
        }
        return courseList;
    }

    //  Update data
    private static void updateData(Connection connection, Course course) throws SQLException {
        String updateDataSQL = "UPDATE courses SET title=?, duration=? WHERE id=?;";
        try(PreparedStatement statement = connection.prepareStatement(updateDataSQL)) {
            statement.setString(1, course.getTitle());
            statement.setInt(2, course.getDuration());
            statement.setInt(3, course.getId());
            statement.executeUpdate();
        }
    }

    //  Delete data
    private static void deleteData(Connection connection, int id) throws SQLException {
        String deleteDataSQL = "DELETE FROM courses WHERE id=?;";
        try(PreparedStatement statement = connection.prepareStatement(deleteDataSQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}