package src;
import java.util.HashMap;
import java.util.Map;

public class Menu {
    private HashMap<MenuItem, Integer> menuItems; //hash map maps the name of the menu item to its price.

    public Menu(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Size cannot be negative");
        }
        this.menuItems = new HashMap<>();
    }
    public Menu() {
        this(20);
    }


    public void addMenuItem(String restaurantID, String name, int price) {
        MenuItem item = MenuItem.find_item(name);
        if (item == null) {
            System.out.println("ERROR: Menu item doesn't exist");
        } else {

            if (menuItems.containsKey(item)) {
                System.out.println("ERROR: item has already been added to this restaurant, try again");
                return;
            }

            menuItems.put(item, price);
            System.out.printf("%s) Menu item added: %s - $%d\n", restaurantID, name,price);
        }
    }

    public Integer getPrice(MenuItem item) {
        return menuItems.get(item);
    }

    public MenuItem removeMenuItem(MenuItem removed) {
        MenuItem item = find_item(removed.getName());
        if (item == null) {
            System.out.println("ERROR: Cannot remove an item that is not in the menu.");
            return null;
        }
        else {
            menuItems.remove(item);
            return item;
        }
    }

    public MenuItem find_item(String name) {
        for (Map.Entry<MenuItem, Integer> entry : menuItems.entrySet()) {
            MenuItem item = entry.getKey();
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<MenuItem, Integer> entry : menuItems.entrySet()) {
            MenuItem key = entry.getKey();
            Integer value = entry.getValue();
            String temp = key.getName();
            result.append(temp).append("\n");
        }
        return result.toString();
    }
}