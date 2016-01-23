import java.sql.*;

public class DBConnections {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost";
	
	private String USER = "root";
	private String PASS = "boomer";
	
	private Connection conn = null;
	private Statement stmt = null;
	
	public boolean registerUser(String username, String password){
		try {
			stmt.executeUpdate("use elibrary;");
			String sqlQuery = "INSERT INTO users (`username`, `password`, `type`) VALUES ('" + username + "', '" + password + "', '0');";
			stmt.executeUpdate(sqlQuery);
			return true;
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		}
		return false;
	}
	
	public int checkLogin(String user, String pass){
		try{
			 stmt.executeUpdate("use elibrary;");
			 String sqlQuery = "select * from users where username = '" + user + "';";	        				 			 
			 ResultSet rs = stmt.executeQuery(sqlQuery);
			 while(rs.next()){
				 if(rs.getString("password").equals(pass)){
					 if (rs.getString("type").equals("0")){
						 return 1;
					 }
					 else{
						 return 2;
					 }
				 }
			 }
	   	 }catch(Exception e){
			 System.out.println("Error : " + e);
		 }
		return 0;
	}
	
	public boolean bookTheBook(String bookID, String userID){
		try {
			stmt.executeUpdate("use elibrary;");
			String sqlQuery = "update books set ownedby='" + userID + "' where bookid=" + bookID + ";";
			stmt.executeUpdate(sqlQuery);
			return true;
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		}
		return false;
	}
	
	public String searchBook(String bookID){
		try {
			stmt.executeUpdate("use elibrary;");
			String sqlQuery = "select * from books where bookid=" + bookID + ";";
			ResultSet rs = stmt.executeQuery(sqlQuery);
			rs.next();
			return rs.getString("bookname") + "&&&" + rs.getString("bookid") + "&&&" + rs.getString("bookdept") + "&&&" + rs.getString("ownedby");
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		}
		return "";
	}
	
	public String getBooks(String searchString, String searchDept){
		String books = "";
		
		try {
			stmt.executeUpdate("use elibrary;");
			String sqlQuery = "";
			
			if (searchString.equals("") && searchDept.equals("ALL")){
				sqlQuery += "select * from books";
			}
			else if (!searchString.equals("") && searchDept.equals("ALL")){
				sqlQuery += "select * from books where bookname like '%" + searchString + "%';";
			}
			else if (searchString.equals("") && !searchDept.equals("ALL")){
				sqlQuery += "select * from books where bookdept='" + searchDept + "';";
			}
			else{
				sqlQuery += "select * from books where bookname like '%" + searchString + "%' and bookdept='" + searchDept + "';";
			}
			
			ResultSet rs = stmt.executeQuery(sqlQuery);
			
			while(rs.next()){
				books += rs.getString("bookname") + "&&&" + rs.getString("bookid") + "&&&" + rs.getString("bookdept") + "&&&";
			}			
		} catch (SQLException e){
			System.out.println("Error : " + e);
		}
		return books;
	}
	
	public DBConnections(){
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER, PASS);
			stmt = conn.createStatement();
		}catch(SQLException | ClassNotFoundException e){
			System.out.println("SQL Error");
		}
	}
	
	public boolean deleteTheBook(String bookID) {
		try {
			stmt.executeUpdate("use elibrary;");
			String sqlQuery = "delete from books where bookid=" + bookID + ";";
			stmt.executeUpdate(sqlQuery);
			return true;
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		}
		return false;
	}

	public void freeThisBook(String bookID) {
		try {
			stmt.executeUpdate("use elibrary;");
			String sqlQuery = "update books set ownedby = '1234567890' where bookid=" + bookID + ";";
			stmt.executeUpdate(sqlQuery);
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		}
	}

	public boolean insertABook(String bookName, String bookDept) {
		try {
			stmt.executeUpdate("use elibrary;");
			String sqlQuery = "INSERT INTO books (`bookname`, `bookdept`) VALUES ('" + bookName + "','" + bookDept + "');";
			stmt.executeUpdate(sqlQuery);
			return true;
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		}
		return false;
	}
}
