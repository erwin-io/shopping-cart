import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public double computeTotalPrice(String itemName){;
        List<Item> group = items.stream().filter(a -> Objects.equals(a.getName(), itemName)).collect(Collectors.toList());
        return group.stream().mapToDouble(f -> f.getPrice()).sum();
    }

    public int computeTotalItem(String itemName){;
        return items.stream().filter(a -> Objects.equals(a.getName(), itemName)).collect(Collectors.toList()).size();
    }
    
}
