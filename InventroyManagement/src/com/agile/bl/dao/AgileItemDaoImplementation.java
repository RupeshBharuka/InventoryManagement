package com.agile.bl.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.agile.bl.model.AgileItems;

public class AgileItemDaoImplementation implements AgileItemDao {
	
	private static final int MIN_ROW_COUNT = 0;
	
	@Override
	public String addItemDetails(AgileItems object) {
		Connection connection = DBConnection.getInstance().getConnection();
		String query = "insert into Items(itemname, quantity, description, lastmodifieddate) values(?,?,?,?)";

		try {
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setString(1, object.getItemName());
			ps.setInt(2, object.getQuantity());
			ps.setString(3, object.getDescription());
			ps.setTimestamp(4,  new Timestamp(System.currentTimeMillis()));

//			int rowCount = ps.executeUpdate();

			if(isRowAffected(ps.executeUpdate())){
				return "Record stored successfully..!";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "Record can not be stored..!";
	}

	private boolean isRowAffected(int rowCount){
		// row count is total count, to check number of rows affected
		return rowCount > MIN_ROW_COUNT;
	}

	@Override
	public List<AgileItems> getItemDetails() {
		Connection connection = DBConnection.getInstance().getConnection();
		String query = "select *from Items";
		List<AgileItems> listOfItems = new ArrayList<>();

		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while(rs.next()){

				listOfItems.add(new AgileItems(rs.getString("itemname"), rs.getInt("quantity"), 
						rs.getString("description"), rs.getTimestamp("lastmodifieddate")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return listOfItems;		
	}

	@Override
	public String updateItemDetails(AgileItems object, String oldItemName) {
		Connection connection = DBConnection.getInstance().getConnection();
		String query = "update Items set itemname=?, quantity=?, description=?, lastmodifieddate=? where oldItemName = ?";

		try {
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setString(1, object.getItemName());
			ps.setInt(2, object.getQuantity());
			ps.setString(3, object.getDescription());
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.setString(5, oldItemName);


//			int rowCount = ps.executeUpdate();
			
			System.out.println("Query is : " + ps.toString());

			if(isRowAffected(ps.executeUpdate())){
				return "Record updated successfully..!";
			}			

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "Record can not be updated";

	}

	@Override
	public String deleteItemDetails(String itemName) {
		Connection connection = DBConnection.getInstance().getConnection();
		String query = "delete from Items where itemName = ?";

		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, itemName);

//			int rowCount = ps.executeUpdate();

			if(isRowAffected(ps.executeUpdate())){
				return "Record deleted successfully..!";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "Record can not be deleted";
	}
	
	@Override
	public int getItemId(String itemName) {
		Connection connection = DBConnection.getInstance().getConnection();
		String query = "SELECT itemid FROM Items WHERE itemname = ?";
		int itemId = 0;

		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, itemName);

			ResultSet rs = ps.executeQuery();

			while(rs.next()){
				itemId = rs.getInt("itemid");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return itemId;
	}

}