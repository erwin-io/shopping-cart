import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    List<Item> items = new ArrayList<Item>();
    public ShoppingCart(){

    }
    
    public List<Item> getItems(){
        return items;
    }

    public void addItems(Item item){
        this.items.add(item);
    }

    public void removeItem(Item item){
        this.items.remove(item);
    }

    public double computeTotalPrice(){
        return this.items.stream().mapToDouble(f -> f.getPrice()).sum();
    }
}
