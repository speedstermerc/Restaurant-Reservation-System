package src;

import java.util.ArrayList;
import java.util.List;

public class    MenuItem {
    private String name;
    private String[] ingredients;
    protected static final List<MenuItem> items = new ArrayList<>();

    /**
     * Use constructor when initiating the create_menu_item method.
     * @param name Name of the dish
     * @param ingredients List of ingredients given in the command.
     */
    public MenuItem(String name, String[] ingredients) {
        this.name = name;
        this.ingredients = ingredients;
        if (name == null) {
            System.out.println("Name of MenuItem cannot be null");
        } else {
            MenuItem.items.add(this);
            System.out.println(name + " created");
        }
    }

    public static void viewItems() {
        for (MenuItem item : items) {
            System.out.println(item);
        }
    }

    public void viewIngredients() {
        StringBuilder result = new StringBuilder("Ingredients: ");
        for (int i = 0; i < ingredients.length - 1; i++) {
            result.append(ingredients[i]).append(", ");
        }
        result.append(ingredients[ingredients.length - 1]).append(" ");
        System.out.println(result);
    }

    public MenuItem(String name, String ingredients) {
        this(name, ingredients.split(":"));
    }

    public String getName() {
        return name;
    }

    public static void calculateAveragePrice(String menuItemName){
        int total = 0;
        int count = 0;

        List<Restaurant> restaurants = Restaurant.getRestaurants();

        for (Restaurant r : restaurants) {
            Menu menu = r.getMenu();
            if (menu.find_item(menuItemName) != null) {
                total += menu.getPrice(menu.find_item(menuItemName));
                count++;
            }
        }
        if (find_item(menuItemName) == null) {
            System.out.println("ERROR: item doesn't exist");
            return;
        }
        
        if (total == 0){
            System.out.println("ERROR: item was never added to a restaurant");
            return;
        } else {
            double avg = total / (double)count;
            System.out.printf("Average price for %s: $%.2f\n",menuItemName, avg);
        }
    }

    public static void calculateItemOrdered(String menuItemName, Restaurant r){
        int timesOrdered = 0;

        MenuItem m = r.getMenu().find_item(menuItemName);
        if (m == null) {
            System.out.println("ERROR: No such menu item exists");
            return;
        } 
        List<Bill> bills = Bill.restaurantBills(r);
        for (Bill b : bills) {
            timesOrdered += b.getTimesOrdered(menuItemName);
        }
        System.out.println("The " + menuItemName + "was ordered " + timesOrdered + " times at the restaurant " + r.getName() + ".");
        
    }
    public static void calculateItemPopularity(String menuItemName) {
        
        int restaurantsThatCarry = 0;   // <- a
        int totalRestaurants = 0;     // <- b     a/b > 1/2 => 2a > b 
        for (Restaurant r : Restaurant.getRestaurants()) {
            if (r.getMenu().find_item(menuItemName) != null) {
                restaurantsThatCarry++;
            }
            totalRestaurants++;
        }

        if (find_item(menuItemName)==null){
            System.out.println("ERROR: item doesn't exist");
            return;
        }

        if (restaurantsThatCarry == 0) {
            System.out.println("ERROR: item was never added to a restaurant");
            return;
        }

        if (2 * restaurantsThatCarry > totalRestaurants) {
            System.out.printf("Popular: %s offered at %d out of %d restaurants (%d%%)\n",
            menuItemName, restaurantsThatCarry, totalRestaurants, 
            (int)Math.rint(100*(restaurantsThatCarry / (double)totalRestaurants)));
            return;
        } else {
            System.out.printf("Not popular: %s offered at %d out of %d restaurants (%d%%)\n",
            menuItemName, restaurantsThatCarry, totalRestaurants, 
            (int)Math.rint(100*(restaurantsThatCarry / (double)totalRestaurants)));
        }
    }

    public static MenuItem find_item(String name) {
        for (MenuItem item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }


    @Override
    public int hashCode() {
        int result=17;
        result=31*result+name.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MenuItem)) {
            return false;
        }
        MenuItem item = (MenuItem)obj;
        return this.getName().equals(item.getName());
    }

    @Override
    public String toString() {
        return name;
    }
}
