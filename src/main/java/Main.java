import java.sql.Connection;
import java.sql.SQLException;

import com.dao.DbConfig;

public class Main {

	public static void main(String[] args) {
		try {Connection conn = DbConfig.getConnection();
		        System.out.println("Successfully connected to the database!");
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
