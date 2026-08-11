package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class BookDatabase {
    private static final String JDBC_URL = "jdbc:sqlite:books.db";

    static {
        initialize();
    }

    private BookDatabase() {
    }

    private static void initialize() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS books ("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "title TEXT NOT NULL,"
                            + "author TEXT NOT NULL"
                            + ")");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize SQLite database", e);
        }
    }

    public static Book insert(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book must not be null");
        }
        String sql = "INSERT INTO books(title, author) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getInt(1));
                }
            }
            return book;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert book", e);
        }
    }

    public static List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT id, title, author FROM books";
        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                books.add(new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to query books", e);
        }
        return books;
    }
}
