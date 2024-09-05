package src;

public class RegulatoryController {
    private String[] tokens;
    public void commandHandle(String wholeInputLine) {
        System.out.println("Welcome to the regulatory interface.");
        final String DELIMITER = ",";
        try {
            wholeInputLine = wholeInputLine.trim();

            if (wholeInputLine.isEmpty() || wholeInputLine.startsWith("//")) {
                return;
            }
            tokens = wholeInputLine.split(DELIMITER);
            for (int i = 0; i < tokens.length; i++) {
                // get rid of whitespace
                tokens[i] = tokens[i].trim();
            }

            if (wholeInputLine.isEmpty() || tokens[0].indexOf("//") == 0) {
                return;

            }
            System.out.println(">> " + wholeInputLine.trim());

            if (tokens[0].equals("view_owners")) {
                String group = tokens[1];
                Owner.viewOwners(group);
            } else if (tokens[0].equals("view_all_restaurants")) {
                Restaurant.viewAllRestaurants();
            } else if (tokens[0].equals("view_all_customers")) {
                Customer.viewCustomers();
            } else if (tokens[0].equals("view_all_menu_items")) {
                MenuItem.viewItems();
            } else if (tokens[0].equals("view_ingredients")) {
                //>> view_ingredients, cheese pizza
                String itemName = tokens[1];
                MenuItem item = MenuItem.find_item(itemName);
                if (item == null) {
                    System.out.println("ERROR: Item does not exist.");
                } else {
                    item.viewIngredients();
                }
            } else if (tokens[0].equals("view_menu_items")) {
                //view_menu_items, REST001
                Restaurant r = Restaurant.findRestaurant(tokens[1]);
                if (r == null) {
                    System.out.println("ERROR: restaurant does not exist.");
                } else {
                    System.out.println(r.getMenu().toString());
                }
            } else {
                if (tokens[0].equals("quit()")) {
                    System.out.println("Changing user...");
                } else {
                    System.out.println("command " + tokens[0] + " NOT acknowledged");
                }
            }
        } catch (Exception e) {
            System.out.println("error during command loop >> execution");
            // e.printStackTrace();
            System.out.println();
        }
        System.out.println();
    }

    public boolean exitLoop() {
        String tokens[] = this.tokens;
        if (tokens[0].equals("quit()")) {
            return true;
        } else {
            return false;
        }
    }
}
