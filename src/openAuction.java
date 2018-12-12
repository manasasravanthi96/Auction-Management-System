import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class openAuction {
	int initBid,currBid,auctionID;
	LocalDate date;
	Product auctionProduct;


public void uploadProductDetails(int productID,String name,String description,String category,boolean status,int startBid,int lastBid,String sellerID,ArrayList<Product> pro){
	
	Product p = new Product(productID,name,description,category,status,startBid,lastBid,sellerID);
	
	pro.add(p);
	//Upload these details of the product to database
	this.auctionProduct = p;
}

public void viewAuctionDetails() {
	System.out.println();
	System.out.println("*****************************************");
	System.out.println("AuctionID: "+this.auctionID);
	System.out.println("Auction date: "+this.date);
	System.out.println("Product ID: "+this.auctionProduct.productID);
	System.out.println("Product Name: "+this.auctionProduct.name);
	System.out.println("Seller: "+this.auctionProduct.sellerID);
}



}