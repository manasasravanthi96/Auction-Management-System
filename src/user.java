import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class user {
	String contactNumber;
	String name,userID,address;
	ArrayList<openAuction> auction = new ArrayList<>();
 
	
	public void bid(Product p,int biddingAmount) throws ClassNotFoundException, SQLException{
		p.lastBid = biddingAmount;
		ArrayList<String> re = new ArrayList<>();
		re.add(this.userID);
		re.add(Integer.toString(biddingAmount));
		p.bidderList.add(re);
		p.printBidderList();
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
		System.out.println("in the bid method");
		PreparedStatement statement = con.prepareStatement("update product set lastBid='"+biddingAmount+"' where pid='"+p.productID+"'");
		statement.execute();
	}
	

	public void login(){
		//check in database if the user is valid or not
	}
	
	public void register(){
		//if the user is not registered then register
	}
	public void printUserDetails() {
		System.out.println();
		System.out.println("**********************");
		System.out.println("Username: "+this.userID);
		System.out.println("Name: "+this.name);
		System.out.println("Contact Number: "+this.contactNumber);
		System.out.println("Address: "+this.address);
		System.out.println("**********************");
		System.out.println();
	}
	
	public user(String userID,String name,String address,String contactNumber) {
		this.userID = userID;
		this.name = name;
		this.address = address;
		this.contactNumber = contactNumber;
		
	}
	
}

