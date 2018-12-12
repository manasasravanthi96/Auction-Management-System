import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;

public class main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		int one=1,two=2,three=3,four=4,five=5;
		ArrayList<Product> p = new ArrayList<>();
		ArrayList<user> u = new ArrayList<>();
		ArrayList<openAuction> open = new ArrayList<>();
		Administrator admin = new Administrator("admin","admin");
		
		boolean loginFlag=true,productStart = true;
		Class.forName("com.mysql.jdbc.Driver");

		while(true) {
			
		Scanner in2 = new Scanner(System.in);
		System.out.println("[1]. Sign In");
		System.out.println("[2]. Sign Up");
		System.out.println("Enter the desired choice:");
		int n = in2.nextInt();
		if(n==one) {
				Scanner in = new Scanner(System.in);
				System.out.println("Enter the username: ");
				String name = in.nextLine();
				System.out.println("Enter your password: ");
				String pass = in.nextLine();
				
				// TODO Auto-generated method stub
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
				if(loginFlag) {
					PreparedStatement s1 = con.prepareStatement("select * from names");
					ResultSet r1 = s1.executeQuery();
					while(r1.next()) {
						user u1 = new user(r1.getString("userID"),r1.getString("name"),r1.getString("address"),r1.getString("contact"));
						u.add(u1);
					}
					loginFlag = false;
				}
				if(productStart) {
					PreparedStatement s1 = con.prepareStatement("select * from product");
					ResultSet r1 = s1.executeQuery();
					while(r1.next()) {
						Product pro = new Product(r1.getInt("pid"),r1.getString("name"),r1.getString("description"),r1.getString("category"),r1.getBoolean("status"),r1.getInt("startBid"),r1.getInt("lastBid"),r1.getString("sellerID"));
						p.add(pro);
						//System.out.println(r1.getString(2));
						openAuction o = new openAuction();
						o.auctionID = open.size()+1;
						o.date = java.time.LocalDate.now();
						o.auctionProduct = pro;
						open.add(o);
						for(int i=0;i<u.size();i++)
						{
							if(u.get(i).userID.equals(r1.getString("sellerID"))) {
								u.get(i).auction.add(o);
							}
						}
					}
					
					productStart = false;
				}
				PreparedStatement statement = con.prepareStatement("select distinct * from names where userid='"+name+"' and password='"+pass+"'");
				ResultSet result = statement.executeQuery();
		//		while(result.next()) {
		//			System.out.println(result.getString(1)+" "+result.getString(2));
		//		}
				
				
				
				if(result.next()) {
					System.out.println("Welcome to the Auction, "+name+" !!");
					if(name.equals("admin")) {
						admin.login(open,u,p);
					}
				}
				else
				{
					System.out.println("Invalid Username or password");
					continue;
				}
				while(true) {
						System.out.println("Choose from the following options: ");
						System.out.println("[1]. Search");
						System.out.println("[2]. Show ProductList for bidding on a product");
						System.out.println("[3]. Sell a product");
						System.out.println("[4]. Show the product list");
						System.out.println("[5]. Show ongoing auctions.");
						System.out.println("[6]. Close any of your aucions");
						System.out.println("[7]. Log Out");
						BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
						String number = bf.readLine();
						in.reset();
						if(number.equals("1")) {
							System.out.println("Search by: ");
							System.out.println("[1]. Category");
							System.out.println("[2]. Product Name");
//							BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
							String search = bf.readLine();
							if(search.equals("1")) {
								System.out.println("Enter the category");
								String cat=bf.readLine();
								statement = con.prepareStatement("select * from product where category='"+cat+"'");
								result = statement.executeQuery();
								if(result.next()) {
									result.beforeFirst();
									while(result.next()) {
										System.out.println("ProductID: "+result.getString(1));
										System.out.println("Product Name: "+result.getString(2));
										System.out.println("Description: "+result.getString(3));
										System.out.println("Category: "+result.getString(4));
										System.out.println("Status: "+result.getString(5));
										System.out.println("startBid: "+result.getString(6));
										System.out.println("lastBid: "+result.getString(7));
										System.out.println();
										System.out.println("******************");
										System.out.println();
										
									}
								}
								else
								{
									System.out.println("Sorry!! No products found.");
								}
								System.out.println("Enter the productID of the Product on which you want to bid.");
								int num = in.nextInt();
								System.out.println("Enter the amount to bid: ");
								int biddingAmount = in.nextInt();
								
								for(int i=0;i<u.size();i++)
								{
									boolean flag=false;
									if(u.get(i).userID.compareTo(name)==0)
									{
										//System.out.println("oiohouigougigiuuhuhu");
										for(int j=0;j<p.size();j++)
										{
											if(p.get(j).productID == num) {
												if(p.get(j).sellerID.equals(u.get(i).userID)) {
													System.out.println("The seller cannot bid on their own product.");
													break;
												}
												if(biddingAmount<p.get(j).startBid) {
													System.out.println("The bidding amount must be greater than the start bid. Sorry!!");
												}
												else {
													u.get(i).bid(p.get(j), biddingAmount);
													
												}
												flag=true;
												break;
											}
										}
										if(!flag) {
											System.out.println("The is no such product.");
											break;
										}
										
									}
								}
								
							}
							else if(search.equals("2")) {
								System.out.println("Enter the product name");
								String cat=bf.readLine();
								statement = con.prepareStatement("select * from product where name='"+cat+"'");
								result = statement.executeQuery();
								if(result.next()) {
									result.beforeFirst();
									while(result.next()) {
										System.out.println("ProductID: "+result.getString(1));
										System.out.println("Product Name: "+result.getString(2));
										System.out.println("Description: "+result.getString(3));
										System.out.println("Category: "+result.getString(4));
										System.out.println("Status: "+result.getString(5));
										System.out.println("startBid: "+result.getString(6));
										System.out.println("lastBid: "+result.getString(7));
										System.out.println();
										System.out.println("******************");
										System.out.println();
										
									}
								}
								else
								{
									System.out.println("Sorry!! No products found.");
								}
								
								System.out.println("Enter the productID of the Product on which you want to bid.");
								int num = in.nextInt();
								System.out.println("Enter the amount to bid: ");
								int biddingAmount = in.nextInt();
								
								for(int i=0;i<u.size();i++)
								{
									boolean flag=false;
									if(u.get(i).userID.compareTo(name)==0)
									{
										//System.out.println("oiohouigougigiuuhuhu");
										for(int j=0;j<p.size();j++)
										{
											if(p.get(j).productID == num) {
												if(p.get(j).sellerID.equals(u.get(i).userID)) {
													System.out.println("The seller cannot bid on their own product.");
													break;
												}
												if(biddingAmount<p.get(j).startBid) {
													System.out.println("The bidding amount must be greater than the start bid. Sorry!!");
												}
												else {
													u.get(i).bid(p.get(j), biddingAmount);
													
												}
												flag=true;
												break;
											}
										}
										if(!flag) {
											System.out.println("The is no such product.");
											break;
										}
										
									}
								}
								
							}
							else
							{
								System.out.println("Invalid input.");
							}
							
							
								
						}
						else if(number.equals("2")) {
							//Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
//							statement = con.prepareStatement("select * from product");
//							result = statement.executeQuery();
//							while(result.next()) {
//								System.out.println("ProductID: "+result.getString(1));
//								System.out.println("Product Name: "+result.getString(2));
//								System.out.println("Description: "+result.getString(3));
//								System.out.println("Category: "+result.getString(4));
//								System.out.println("Status: "+result.getString(5));
//								System.out.println("startBid: "+result.getString(6));
//								System.out.println("lastBid: "+result.getString(7));
//								System.out.println();
//								System.out.println("******************");
//								System.out.println();
								
//								//Product p1 = new Product(result.getInt(1),result.getString(2),result.getString(3),result.getString(4),result.getBoolean(5),result.getInt(6),result.getInt(7));
//								//p.add(p1);
//							}
							
							for(int i=0;i<p.size();i++) {
								p.get(i).viewProductDetails();
								//System.out.println("******************");
								//System.out.println();
								System.out.println("BidderList:");
								p.get(i).printBidderList();
								System.out.println("******************");
								System.out.println();
							}
					
							System.out.println("Enter the productID of the Product on which you want to bid.");
							int num = in.nextInt();
							System.out.println("Enter the amount to bid: ");
							int biddingAmount = in.nextInt();
							
							for(int i=0;i<u.size();i++)
							{
								boolean flag=false;
								if(u.get(i).userID.compareTo(name)==0)
								{
									//System.out.println("oiohouigougigiuuhuhu");
									for(int j=0;j<p.size();j++)
									{
										if(p.get(j).productID == num) {
											if(p.get(j).sellerID.equals(u.get(i).userID)) {
												System.out.println("The seller cannot bid on their own product.");
												break;
											}
											if(biddingAmount<p.get(j).startBid) {
												System.out.println("The bidding amount must be greater than the start bid. Sorry!!");
											}
											else {
												u.get(i).bid(p.get(j), biddingAmount);
												
											}
											flag=true;
											break;
										}
									}
									if(!flag) {
										System.out.println("The is no such product.");
										break;
									}
									
								}
							}
							
						}
						else if(number.equals("3")) {
							Scanner in1 = new Scanner(System.in);
							System.out.println("Enter Product Name");
							String pname = in1.nextLine();
		//					System.out.println();
							System.out.println("Enter Product Description");
							String des = in1.nextLine();
							System.out.println("Enter category");
							String cat = in1.nextLine();
							System.out.println("Status");
							boolean stat = in1.nextBoolean();
							System.out.println("Enter StartBid");
							int startB = in1.nextInt();
							int lastB = 0;
							System.out.println("Waiting for verification from administrator.....................");
							boolean request=admin.verification();
							if(request) {
								System.out.println("Request accepted by admin.");
								statement = con.prepareStatement("select pid from product");
								ResultSet rs = statement.executeQuery();
								rs.last();
								int pd = rs.getInt("pid")+1;
								String query = " insert into product (pid,name, description, category,status, startBid,lastBid, sellerID)"
								        + " values (?,?, ?, ?, ?, ?,'0',?)";
								statement=con.prepareStatement(query);
								statement.setInt(1,pd);
								statement.setString(2,pname);
								statement.setString(3,des);
								statement.setString(4,cat);
								statement.setBoolean(5,stat);
								statement.setInt(6,startB);
								statement.setString(7,name);
								statement.execute();
//								Product p1 = new Product(count,pname,des,cat,stat,startB,lastB);
//								p1.sellerID = name;
//								p.add(p1);
							
								openAuction o = new openAuction();
								o.uploadProductDetails(pd, pname, des, cat, stat, startB, lastB, name, p);
								o.auctionID = open.size() + 1;
								o.date = java.time.LocalDate.now();
								open.add(o);
								for(int i=0;i<u.size();i++) {
									if(u.get(i).userID.equals(name)) {
										u.get(i).auction.add(o);
									}
								}
								

							}
							else {
								System.out.println("Request has been denied by the administrator. Your ad was not genuine.");
							}
														
							//statement = con.prepareStatement("insert into product(name,description,category,status,startBid,lastBid) values ('"+pname+"','"+des+"','"+cat+"','"+stat+"','"+startB+"','0');");
							//statement.executeQuery();
						}
						else if(number.equals("4")) {
							for(int i=0;i<p.size();i++) {
								System.out.println();
								System.out.println("******************");
								p.get(i).viewProductDetails();
								//System.out.println("******************");
							//	System.out.println();
								System.out.println("BidderList:");
								p.get(i).printBidderList();
								System.out.println("******************");
								System.out.println();
							}
						}
						else if(number.equals("5")) {
							for(int i=0;i<open.size();i++) {
								open.get(i).viewAuctionDetails();
							}
						}
						else if(number.equals("6")) {
							user a1 = null;
							for(int i=0;i<u.size();i++) {
								if(u.get(i).userID.equals(name)) {
									a1 = u.get(i);
									for(int j=0;j<u.get(i).auction.size();j++) {
										u.get(i).auction.get(j).viewAuctionDetails();
									}
								}
							}
							System.out.println("Enter the auctionID of the auction you want to close.");
							String aID = bf.readLine();
							for(int i=0;i<a1.auction.size();i++) {
								if(a1.auction.get(i).auctionID==Integer.parseInt(aID)) {
									closeAuction close = new closeAuction();
									close.sort_bidding_amount(a1.auction.get(i).auctionProduct);
									close.completeAuction(a1.auction.get(i).auctionID,p,open);
									close.printDetails(a1.auction.get(i));
									break;
								}
							}
						}
						else if(number.equals("7")) {
							System.out.println("********************Thank you!!**********************");
							System.out.println();
							System.out.println();
							break;
						}
						else {
							System.out.println("Invalid Input");
						}
				}
		}
		else if(n==two) {
			System.out.println("Enter Username to register");
			Scanner in1 = new Scanner(System.in);
			String s = in1.nextLine();
			System.out.println("Enter the password");
			String p1 = in1.nextLine();
			System.out.println("Enter your name");
			String nm = in1.nextLine();
			System.out.println("Enter your Contact Number");
			String contact = in1.nextLine();
			System.out.println("Enter your Address");
			String add = in1.nextLine();
			String query = " insert into names (userid,password,name,contact,address)"
			        + " values (?, ?,?,?,?)";
//			Class.forName("com.mysql.jdbc.Driver");
			user u1 = new user(s,nm,add,contact);
			u.add(u1);
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, s);
			statement.setString(2, p1);
			statement.setString(3, nm);
			statement.setString(4,contact);
			statement.setString(5, add);
			statement.execute();
			
		}
	}
	}
}
