package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class item {
	
	private static Statement stmt = null;
	static int id = 0;
	
	public Connection connect(){
		
		Connection con = null;
		
		try{
				
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root", "M@ng@th@9093");

			//For Testing
			System.out.println("Successfully connected");
			
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	
	public String insertItem(String code, String name, String price, String desc) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if(con == null) {
				
				return "Error while connecting to Database";
				
			}
			
			//Create Prepared Statement
			String query = "INSERT INTO item(`itemID`,`itemCode`,`itemName`,`itemPrice`,`itemDesc`) VALUES(?,?,?,?,?)" ;
			
			PreparedStatement preparedStmt  = con.prepareStatement(query);
			
			//binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, code);
			preparedStmt.setString(3, name);
			preparedStmt.setDouble(4, Double.parseDouble(price));
			preparedStmt.setString(5, desc);
			
			//execute the statement
			preparedStmt.execute();
			con.close();
			
			output = "Inserted Succesfully";
			
			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
			
			
		}catch(Exception e) {
			output = output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	public String readItems(){
		String output = "";
	try{
		Connection con = connect();
		
		if (con == null){
			return "Error while connecting to the database for reading.";
		}
		
		// Prepare the html table to be displayed
		output = "<table border='1'>"
					+ "<tr>"
						+ "<th>Item Code</th>"
						+ "<th>Item Name</th>"
						+ "<th>Item Price</th>"
						+ "<th>Item Description</th>"
						+ "<th>Update</th>"
						+ "<th>Remove</th>"
					+ "</tr>";
		
		String query = "select * from item";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		// iterate through the rows in the result set
		while (rs.next())
		{
			String itemID = Integer.toString(rs.getInt("itemID"));
			String itemCode = rs.getString("itemCode");
			String itemName = rs.getString("itemName");
			String itemPrice = Double.toString(rs.getDouble("itemPrice"));
			String itemDesc = rs.getString("itemDesc");
			
			// Add into the html table
			output += itemCode + "</td>";
			output += "<td>" + itemName + "</td>";
			output += "<td>" + itemPrice + "</td>";
			output += "<td>" + itemDesc + "</td>";
			
			// buttons
			output += "<td>"
					+ 	"<input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-itemid='"+itemID+"'>"
					+ "</td>"
					+ "<td>"
					+ 	"<input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid='"+itemID+"'>"
					+ "</td></tr>";
		}
		
		con.close();
		// Complete the html table
		output += "</table>";
	}
	
	catch (Exception e){
		
		output = "Error while reading the items.";
		System.err.println(e.getMessage());
	}
		return output;
	}
	
	public String deleteData(String itemId) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if(con == null) {
				
				return "Error while connecting to Database";
				
			}
			
			//create prepared statement
			String query = "delete from item where itemId = ? ";
			
			PreparedStatement preparedStmt  = con.prepareStatement(query);
			
			preparedStmt.setInt(1, Integer.valueOf(itemId));
			
			preparedStmt.execute();
			con.close();
			
			
			
			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" +newItems + "\"}";
			
		}catch(Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the item.\"}";
			System.err.println(e.getMessage());
			
		}
		
		return output;
	}
	
	public String updateItem(String itemID, String itemCode, String itemName, String itemPrice, String itemDesc)  
	{
		String output = "";	
		try {
						
			Connection con = connect();
		
			
			if (con == null){
				return "Error while connecting to the database for reading.";
			}

			stmt = con.createStatement();
						
			String update="UPDATE test.item"
					+ " SET itemCode='"+itemCode+"',"
							+ "itemName= '"+itemName+"',"
							+ "itemPrice = '"+itemPrice+"',"
							+ "itemDesc = '"+itemDesc+"'"
					+ " WHERE itemId = '"+itemID+"'";
			
			int rsv = stmt.executeUpdate(update);
			

			if(rsv > 0) {
				output = "Updated successfully";
			}
			con.close();
			
			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
			
		}catch(Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
		
		
	}
	
	
}
