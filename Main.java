import java.io.IOException;
import java.time.LocalTime;

public class Main
{
    public static void main (String [] args) {
        try {
            Customer customer = new Customer("Erwin");
            Cashier cashier = new Cashier("Erwin", LocalTime.now(), LocalTime.now());
            Store store = new Store(customer, cashier);
            store.readStoreItemsFromFile("store-items.csv");
            store.shop();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}