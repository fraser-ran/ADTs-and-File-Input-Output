//Class representing an electronic store
//Has an array of products that represent the items the store can sell
import javax.xml.parsers.SAXParser;
import java.io.*;
import java.sql.ClientInfoStatus;
import java.util.*;


public class ElectronicStore implements Serializable{
  public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
  private int curProducts;
  private String name;
  private int numOfCustomers;
  private List<Customer> customers = new ArrayList<Customer>();
  private List<String> customerNames = new ArrayList<String>();
  private List<String> prodNames = new ArrayList<String>();

  private List<Product> stock;
  private double revenue;
  
  public ElectronicStore(String initName){
    revenue = 0.0;
    name = initName;
    stock = new ArrayList<Product>();
    curProducts = 0;
  }

  public void setProdNames(List<String> prods){
    prodNames = prods;
  }
  
  public String getName(){
    return name;
  }

  public void setCustomers (List <Customer> c){
    customers = c;
  }

  public void  setStock(List <Product> s){
    stock = s;
  }
  
  //Adds a product and returns true if there is space in the array
  //Returns false otherwise
  public boolean addProduct(Product newProduct){
    if (!prodNames.contains(newProduct.toString())) {
      stock.add(newProduct);
      prodNames.add(newProduct.toString());
      curProducts++;
      return true;
    }
    return false;
  }
  public List<Product> getStock(){return stock;}

  public List<Customer>getCustomers(){return customers;}

  public void setRevenue(double rev){revenue=rev;}


  public void setName(String n){name = n;}
  public void setCustomerNames(List<String> names){
    customerNames = names;
  }


  public boolean registerCustomer(Customer c){
    if (!customerNames.contains(c.getName())){
      customers.add(c);
      customerNames.add(c.getName());
      numOfCustomers++;
      return true;
    }
    else {
      return false;
    }
  }
//Simple searchProducts-------------------------
  public List<Product> searchProducts(String x) {
    List<Product> matchProds = new ArrayList<Product>();

    for (int i = 0; i < stock.size(); i++) {
      String lCase = stock.get(i).toString().toLowerCase();
      if (lCase.contains(x.toLowerCase())){
        matchProds.add(stock.get(i));
      }
    }
    return matchProds;
  }


  public List<Product> searchList(String x, List<Product> prods) {
    List<Product> matchProds = new ArrayList<Product>();

    for (Product prod : prods) {
      String lCase = prod.toString().toLowerCase();
      if (lCase.contains(x.toLowerCase())) {
        matchProds.add(prod);
      }
    }
    return matchProds;
  }

//  Complicated searchProducts-------------------------------
  public List<Product> searchProducts(String x, double minPrice, double maxPrice){
    List<Product> matchProds;
    List<Product> priceMatch = new ArrayList<Product>();
    if (minPrice < 0 && maxPrice < 0){
      matchProds = searchProducts(x);
    }
    else if (minPrice<0 && maxPrice>= 0) {

      for (int i = 0; i < stock.size(); i++) {
        if (stock.get(i).getPrice() <= maxPrice) {
          priceMatch.add(stock.get(i));
        }
      }
      matchProds = searchList(x,priceMatch);

    }
    else if (minPrice >= 0 && maxPrice <0){
      for (int i = 0; i < stock.size(); i++) {
        if (stock.get(i).getPrice() >= minPrice) {
          priceMatch.add(stock.get(i));
        }
      }
      matchProds = searchList(x,priceMatch);

    }
    else {
      for (Product product : stock) {
        if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
          priceMatch.add(product);
        }
      }
      matchProds = searchList(x,priceMatch);
    }
    return matchProds;
  }

//  addStock
  public boolean addStock(Product p, int amount){
    if (stock.contains(p)){
      int pos = stock.indexOf(p);
      stock.get(pos).increaseStockQuantity(amount);
      return true;
    }
    else {
      return false;
    }
  }

  public boolean sellProduct(Product p, Customer c, int amount){
    if (amount <= p.getStockQuantity() && stock.contains(p)){
      for (int i = 0; i < amount; i++ ){
        c.buyItem(p);
        revenue = revenue+p.getPrice();
      }
      p.sellUnits(amount);

      return true;
    }
    else {
      return false;
    }
  }

  public List<Customer>getTopXCustomers(int x){
    List<Customer> bigSpenders = new ArrayList<>();
    Customer[] shoppers = new Customer[customers.size()];
    Customer[] temp;
    for (int i = 0; i < shoppers.length; i++) {
      shoppers[i] = customers.get(i);
    }
    temp = sort(shoppers);
    if (x < customers.size()) {
      for (int i = 0; i < x; i++) {
        bigSpenders.add(temp[i]);
      }
      return bigSpenders;
    }
    else {
      bigSpenders.addAll(Arrays.asList(temp));
      return bigSpenders;
    }
  }

  public Customer[] sort(Customer[] cList) {
    boolean sorted = false;
    Customer temp;
    while(!sorted) {
      sorted = true;
      for (int i = 0; i < cList.length - 1; i++) {
        if (cList[i].getDollSpent() < cList[i+1].getDollSpent()) {
          temp = cList[i];
          cList[i] = cList[i+1];
          cList[i+1] = temp;
          sorted = false;
        }
      }
    }
    return cList;
  }

  public boolean saveToFile(String fileName){
    try (FileOutputStream fs = new FileOutputStream(fileName)){
      ObjectOutputStream out = new ObjectOutputStream(fs);

      ElectronicStore store = new ElectronicStore(name);

      for (Product product : stock) {
        store.addProduct(product);
      }
      for (Customer c : customers) {
        store.registerCustomer(c);
      }
      store.setRevenue(revenue);

      out.writeObject(store);

      out.close();
      return true;
    } catch (FileNotFoundException e) {
      return false;
    } catch (IOException e) {
      return false;
    }
  }

  public static ElectronicStore loadFromFile(String filename) throws  IOException {
    try(FileInputStream fi = new FileInputStream(filename)) {
      ElectronicStore store;

      ObjectInputStream in = new ObjectInputStream(fi);

      store = (ElectronicStore) in.readObject();

      in.close();

      return store;
    }
    catch (ClassNotFoundException e) {
      return null;
    }
     catch (FileNotFoundException e) {
      return null;
    }
    catch (IOException e) {
      return null;
    }
  }


} 