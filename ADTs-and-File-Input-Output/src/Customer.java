import java.io.Serializable;
import java.util.*;
public class Customer  implements Serializable {
    private String name;
    private Product [] purchasedProd = new Product[500];
    private int prodCount = 0;
    private List<Product> itemsBought = new ArrayList<Product>();
    private List<String> boughtString = new ArrayList<String>();
    private double dollSpent;

    public Customer(String n){
        name = n;
    }


    public String getName(){return name;}

    public List<Product> getItemsBought(){return itemsBought;}

    public double getDollSpent(){return dollSpent;}

    public void setDollSpent(double m){
        dollSpent = m;
    }

    public void buyItem(Product prod){
        dollSpent = dollSpent+ prod.getPrice();
        purchasedProd[prodCount] = prod;
        prodCount++;
        boolean isIn = false;

        if(!itemsBought.contains(prod)){
            boughtString.add("1 x "+prod.toString());
            itemsBought.add(prod);
        }
        else {
            itemsBought.add(prod);
            int itemIndex = 0;
            int itemCount = 0;
                for (int i = 0; i < purchasedProd.length; i++){
                    if (purchasedProd[i]!=null){
                        if (purchasedProd[i] == prod){
                            itemCount++;
                        }
                    }
                }
                String newString = itemCount+" x "+prod.toString();
                String replace = itemCount-1+" x "+prod.toString();
                int index = boughtString.indexOf(replace);
                boughtString.set(index,newString);
            }


    }

    public String toString(){
        return name+" who has spent $"+dollSpent;
    }

    public void printPurchaseHistory(){
        System.out.println(boughtString);
    }


}
