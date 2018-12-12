import java.util.Vector;
import java.util.*;


public class Product {
	String name,description,category,sellerID;
	int productID;
	ArrayList<ArrayList<String>> bidderList=new ArrayList<ArrayList<String>>();
	boolean status;
	int startBid,lastBid;

	
	public void viewProductDetails(){
		System.out.println("ProductID: "+this.productID);
		System.out.println("Product Name: "+this.name);
		System.out.println("Description: "+this.description);
		System.out.println("Category: "+this.category);		
		System.out.println("Status: "+this.status);
		System.out.println("Start Bid: "+this.startBid);
		System.out.println("Last Bid: "+this.lastBid);
	}
	
	public Product(int productID, String name,String description,String category,boolean status,int startBid,int lastBid,String sellerID) {
		this.lastBid=lastBid;
		this.productID = productID;
		this.category = category;
		this.description = description;
		this.name = name;
		this.startBid = startBid;
		this.status = status;
		this.sellerID = sellerID;
		
	}
	
	public void printBidderList() {
		for(int i=0;i<bidderList.size();i++) {
			System.out.println(bidderList.get(i));
		}
	}
	
}

