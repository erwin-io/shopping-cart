public class Customer extends Person {
    ShoppingCart shoppingCart = new ShoppingCart();
    public Customer(String name) {
        super(name);
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
    
    public void addToCart(Item item){
        this.shoppingCart.addItems(item);
    }
    public void removeFromCart(Item item){
        this.shoppingCart.removeItem(item);
    }
}