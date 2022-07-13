import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Store
{
    Customer customer;
    Cashier cashier;
    List<Item> storeItems = new ArrayList<Item>();

    int menu = 0;
    int storeItemMenu = 0;
    int cartMenu = 0;
    Scanner in;

    public Store (Customer customer, Cashier cashier) {
        this.customer = customer;
        this.cashier = cashier;
    }
    
    public void shop(){
        showStoreMenu();
    }
    
    public void showStoreMenu(){
        do {
            System.out.println("--------------------------------------------------------------");
            System.out.println("Press [1] Add item | [2] Remove item | [3] Checkout | [0] Exit");
            System.out.println("--------------------------------------------------------------");
           
            Scanner in = new Scanner (System.in);
            menu = Integer.parseInt(in.nextLine());
            switch (menu) {
                case 1: 
                    showStoreItems();
                    break;
                case 2:
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    
                    break;
            }
        } while (menu != 0);
    }

    public void showStoreItems(){
        System.out.println("");
        System.out.println("Store Items");
        System.out.println("--------------------------------------------------------------");
        for (int i = 0; i < storeItems.size(); i++) {
            System.out.println(
                "[" + i + "] " +
                storeItems.get(i).getName() + " " +
                Double.toString(storeItems.get(i).getPrice())
            );
        }
        do {
            System.out.println("--------------------------------------------------------------");
            System.out.println("[0] - [6] to select items");
            System.out.print("Select item to add: ");
            in = new Scanner (System.in);
            storeItemMenu = Integer.parseInt(in.nextLine());
            customer.addToCart(storeItems.get(storeItemMenu));
            System.out.println("");
            System.out.println("Cart Items");
            System.out.println("--------------------------------------------------------------");
            ShoppingCart shoppingCart = customer.getShoppingCart();
            List<Item> shoppingCartItems = shoppingCart.getItems();
            for (int i = 0; i < shoppingCartItems.size(); i++) {
                System.out.println(
                    "[" + i + "] " +
                    shoppingCartItems.get(i).getName() + " " +
                    Double.toString(shoppingCartItems.get(i).getPrice())
                );
            }
            System.out.println("--------------------------------------------------------------");
            showStoreMenu();
            
        } while(storeItemMenu != -1);
    }

    public void showCartItems(){
        do {
            System.out.println("");
            System.out.println("Select Items to remove");
            System.out.println("--------------------------------------------------------------");
            in = new Scanner (System.in);
            cartMenu = Integer.parseInt(in.nextLine());
            customer.removeFromCart(storeItems.get(storeItemMenu));
            System.out.println("");
            System.out.println("Cart Items");
            System.out.println("--------------------------------------------------------------");
            ShoppingCart shoppingCart = customer.getShoppingCart();
            List<Item> shoppingCartItems = shoppingCart.getItems();
            for (int i = 0; i < shoppingCartItems.size(); i++) {
                System.out.println(
                    "[" + i + "] " +
                    shoppingCartItems.get(i).getName() + " " +
                    Double.toString(shoppingCartItems.get(i).getPrice())
                );
            }
            in = new Scanner (System.in);
            cartMenu = Integer.parseInt(in.nextLine());
            
        } while(storeItemMenu != -1);
    }

    public void readStoreItemsFromFile(String fileName) throws FileNotFoundException, IOException{
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                storeItems.add(new Item(values[0], Double.parseDouble(values[1])));
            }
        }
    }
    
    public void printReceipt(){
        
    }
    
    public void saveReceiptToFile(String fileName){

    }
}