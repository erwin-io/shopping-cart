import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Store
{
    Customer customer;
    Cashier cashier;
    List<Item> storeItems = new ArrayList<Item>();

    int menu = 0;
    int storeItemMenu = 0;
    int cartMenu = 0;

    public Store (Customer customer, Cashier cashier) {
        this.customer = customer;
        this.cashier = cashier;
    }
    
    public void shop(){
        ShoppingCart shoppingCart;
        List<Item> shoppingCartItems;
        do {
            System.out.println("--------------------------------------------------------------");
            System.out.println("Press [1] Add item | [2] Remove item | [3] Checkout | [0] Exit");
            System.out.println("--------------------------------------------------------------");
           
            menu = getUserInput();
            switch (menu) {
                case 1: 
                    if(storeItems.size() > 0){
                        showStoreItems();
                    }
                    else{
                        System.out.println("No items found.");
                        System.out.println("--------------------------------------------------------------");
                        shop();
                    }
                    break;
                case 2:
                    shoppingCart = customer.getShoppingCart();
                    shoppingCartItems = shoppingCart.getItems();
                    if(shoppingCartItems.size() > 0){
                        System.out.println("");
                        System.out.println("");
                        showCartItems(true);
                    }
                    else{
                        System.out.println("");
                        System.out.println("Cart is empty. No items to remove.");
                        shop();
                    }
                    break;
                case 3:
                    shoppingCart = customer.getShoppingCart();
                    shoppingCartItems = shoppingCart.getItems();
                    if(shoppingCartItems.size() > 0){
                        System.out.println("");
                        printReceipt();
                        saveReceiptToFile("receipt.txt");
                        List<Item> cartItems = customer.shoppingCart.getItems();
                        for (int i = 0; i < cartItems.size(); i++) {
                            customer.removeFromCart(cartItems.get(i));
                        }
                        PrintStream consoleStream = new PrintStream(new FileOutputStream(FileDescriptor.out));
                        System.setOut(consoleStream);
                    }
                    else{
                        System.out.println("");
                        System.out.println("Cart is empty. No items to remove.");
                        shop();
                    }
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    
                    break;
            }
        } while (menu != 0);
    }

    public int getUserInput(){
        Scanner in = new Scanner (System.in);
        int input = -1;
        if(!in.hasNextInt()){
            System.out.println("Invalid input");
            shop();
        }
        input = Integer.parseInt(in.nextLine());
        return input;
    }

    public void showStoreItems(){
        System.out.println("");
        System.out.println("Store Items");
        System.out.println("--------------------------------------------------------------");
        for (int i = 0; i < storeItems.size(); i++) {
            System.out.format("%1s%20s%10s","[" + i + "] ", storeItems.get(i).getName(),Double.toString(storeItems.get(i).getPrice()));
            System.out.println("");
        }
        do {
            System.out.println("--------------------------------------------------------------");
            System.out.println("[0] - [" + (storeItems.size()-1) + "] to select items");
            System.out.print("Select item to add: ");
            storeItemMenu = getUserInput();
            if(storeItemMenu != -1 && storeItemMenu <= (storeItems.size()-1)){
                customer.addToCart(storeItems.get(storeItemMenu));
                System.out.println("");
                showCartItems(false);
            }else{
                System.out.println("Invalid input");
                shop();
            }
            
        } while(storeItemMenu != -1);
    }

    public void showCartItems(boolean removeItem){
        do {
            System.out.println("--------------------------------------------------------------");
            System.out.println("Cart Items");
            System.out.println("--------------------------------------------------------------");
            ShoppingCart shoppingCart = customer.getShoppingCart();
            List<Item> shoppingCartItems = shoppingCart.getItems();
            for (int i = 0; i < shoppingCartItems.size(); i++) {
                System.out.format("%1s%20s%10s","[" + i + "] ", shoppingCartItems.get(i).getName(),Double.toString(shoppingCartItems.get(i).getPrice()));
                System.out.println("");
            }
            if(removeItem){
                System.out.println("Select Items to remove");
                cartMenu = getUserInput();
                if(cartMenu != -1 && cartMenu < shoppingCartItems.size()){
                    customer.removeFromCart(shoppingCartItems.get(cartMenu));
                    System.out.println("");
                    showCartItems(false);
                }else{
                    System.out.println("Invalid input");
                    shop();
                }
            }else{
                System.out.println("");
                shop();
            }
            
        } while(cartMenu != -1);
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
        System.out.println("--------------------------------------------------------------");
        System.out.format("%32s","RECEIPT");
        System.out.println("");
        System.out.println("--------------------------------------------------------------");
        System.out.print("Cashier ");
        System.out.println(cashier.getName() + " ");
        System.out.print("Shift: ");
        System.out.println(DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH).format(cashier.getStartOfShift()) + " - " + 
        DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH).format(cashier.getEndOfShift()));
        System.out.print("Date: ");
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(LocalDateTime.now()));
        System.out.println("--------------------------------------------------------------");
        System.out.println("Items: ");
        ShoppingCart shoppingCart = customer.getShoppingCart();
        List<Item> shoppingCartItems = shoppingCart.getItems();
        System.out.format("%20s%10s%16s","Item name","Qty","Price");
        System.out.println("");
        for (int i = 0; i < shoppingCartItems.stream().collect(Collectors.groupingBy(w -> w.getName())).size(); i++) {
            System.out.format("%20s%10d%16s", 
                shoppingCartItems.get(i).getName(), 
                shoppingCart.computeTotalItem(shoppingCartItems.get(i).getName()), 
                shoppingCart.computeTotalPrice(shoppingCartItems.get(i).getName())
            );
            System.out.println("");
        }
        System.out.println("");
        System.out.println("--------------------------------------------------------------");
        System.out.print("TOTAL: ");
        System.out.print(shoppingCart.computeTotalPrice());
        System.out.println("");
    }
    
    public void saveReceiptToFile(String fileName){
        try{
            PrintStream fileStream = new PrintStream(fileName);
            System.setOut(fileStream);
            printReceipt();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
}