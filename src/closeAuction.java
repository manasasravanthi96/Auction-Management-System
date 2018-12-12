import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class closeAuction {
	String maxBidID;
	LocalDate auctionDate;
	
	
	public void sort_bidding_amount(Product p){
		//get the bidders from the database from the auction ID passed in the parameter.
		int maxBid = 0;
		for(int i=0;i<p.bidderList.size();i++){
			if(Integer.parseInt(p.bidderList.get(i).get(1))>maxBid){
				maxBid = Integer.parseInt(p.bidderList.get(i).get(1));
				maxBidID = p.bidderList.get(i).get(0);
			}
		}
	}
	
	public void completeAuction(int auctionID,ArrayList<Product> product,ArrayList<openAuction> open) throws ClassNotFoundException, SQLException{
		//remove the product details using the auction ID from the database
		
		for(int i=0;i<open.size();i++) {
			if(auctionID==open.get(i).auctionID) {
				for(int j=0;j<product.size();j++) {
					if(open.get(i).auctionProduct.productID==product.get(j).productID) {
						product.remove(j);
						Class.forName("com.mysql.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
						PreparedStatement statement = con.prepareStatement("delete from product where pid = '"+open.get(i).auctionID+"'");
						statement.execute();						
					}
				}
				open.remove(i);
			}
		}
		
		
	}
	
	public void printDetails(openAuction auction) {
		System.out.println("Auction has been closed!!!");
		System.out.println();
		System.out.println("Auction ID"+auction.auctionID);
		System.out.println("Winning Bidder: "+this.maxBidID);
		System.out.println("Auction Date: "+auction.date);
	}
	
	
}
