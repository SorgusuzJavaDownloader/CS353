package vfproject;

import java.sql.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Everything {
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		DBConnection db = new DBConnection();
		SpringApplication.run(Everything.class, args);
	}

}
