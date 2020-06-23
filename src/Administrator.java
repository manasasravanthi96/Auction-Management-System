import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.sql.*;


public class Administrator {
	String username,password;
	Random random;
	public boolean verification() {
		random = new Random();
		return random.nextBoolean();
	}

	public Administrator(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public void deleteAuction(int auctionID,ArrayList<Product> product,ArrayList<openAuction> open) throws ClassNotFoundException, SQLException{
		closeAuction close = new closeAuction();
		close.completeAuction(auctionID, product, open);
	}
	public void login(ArrayList<openAuction> open,ArrayList<user> u,ArrayList<Product> p) throws ClassNotFoundException, SQLException, IOException {
		System.out.println("**************Administrator Login******************");
		System.out.println();
		System.out.println("[1]. Delete an Auction.");
		System.out.println("[2]. Delete a User.");
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		switch(bf.readLine()) {
		case "1":
			System.out.println();
			for(int i=0;i<open.size();i++) {
				open.get(i).viewAuctionDetails();
				System.out.println("***************************");
				System.out.println();
			}
			System.out.println("Enter the auction ID");
			String id = bf.readLine();
			for(int i=0;i<open.size();i++) {
				if(open.get(i).auctionID==Integer.parseInt(id)) {
					deleteAuction(Integer.parseInt(id),p,open);
					break;
				}
			}
			for(int i=0;i<open.size();i++) {
				open.get(i).viewAuctionDetails();
				System.out.println("***************************");
				System.out.println();
			}
			
			break;
		case "2":
			System.out.println();
			for(int i=0;i<u.size();i++) {
				u.get(i).printUserDetails();
			}
			System.out.println("Enter the userID to delete:");
			String number = bf.readLine();
			deleteUser(u,number);
			for(int i=0;i<u.size();i++) {
				u.get(i).printUserDetails();
			}
			break;
		}
		
		
	}
	
	public void deleteUser(ArrayList<user> u,String userID) throws ClassNotFoundException, SQLException {
		for(int i=0;i<u.size();i++)
		{
			if(u.get(i).userID.equals(userID)) {
				u.remove(i);
			}
		}
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
		PreparedStatement statement = con.prepareStatement("delete from names where userid='"+userID+"'");
		statement.execute();
		
		System.out.println("User deleted!!");
	}
}

