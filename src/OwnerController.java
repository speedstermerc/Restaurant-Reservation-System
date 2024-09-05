package src;

public class OwnerController {
    private String[] tokens;
    public void commandHandle(String wholeInputLine) {
        System.out.println("Welcome to the customer interface.");
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
            if (tokens[0].equals("create_restaurant")) {

                int seating = Integer.parseInt(tokens[6]);
                if (seating <= 0) {
                    System.out.println("ERROR: Restaurant capacity must be positive");
                    System.out.println();
                    return;
                }
                String ownerID = tokens[7];

                Owner o = Owner.findOwner(ownerID);
                String unifiedLicense = tokens[8];
                if (o != null) {
                    Restaurant r = new Restaurant(
                            tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], seating,
                            o, unifiedLicense);
                } else {
                    System.out.println("ERROR: owner doesn't exist");
                }

            } else if (tokens[0].equals("customer_arrival")) {
                /* System.out.print("customer_identifier: " + tokens[1] + ", restaurant_identifier: " + tokens[2]);
                System.out.println(", reservation_date: " + tokens[3] + ", arrival_time: " + tokens[4] + ", reservation_time: " + tokens[5]); */

                Customer reservist = Customer.findCustomer(tokens[1]);
                Restaurant restaurant = Restaurant.findRestaurant(tokens[2]);

                if (reservist == null || restaurant == null) {
                    System.out.println("Either customer identifier or restaurant identifier is invalid");
                    System.out.println();
                    return;
                }
                reservist.handleCustomerArrival(tokens[3], tokens[4], tokens[5], restaurant);

            } else if (tokens[0].equals("create_owner")) {
                // create_owner, OWN001, Ricardo, Cardenas, Orlando, FL, 32801, 2022-05-30, Restaurant Brands
                Owner o = new Owner(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5],
                        tokens[6], tokens[7], tokens[8]);

            } else if (tokens[0].equals("add_menu_item")) {
                // add_menu_item, cheese pizza, REST001, 8
                if (Integer.parseInt(tokens[3]) < 0) {
                    System.out.println("Cost of item must be nonnegative");
                    System.out.println();
                    return;
                }
                Restaurant r = Restaurant.findRestaurant(tokens[2]);
                if (r == null) {
                    System.out.println("ERROR: Restaurant does not exist");
                } else {
                    r.getMenu().addMenuItem(r.getRestaurantID(), tokens[1], Integer.parseInt(tokens[3]));
                }
            } else if (tokens[0].equals("create_menu_item")) {
                // create_menu_item, cheese pizza, dough:mozzarella:tomato sauce
                new MenuItem(tokens[1], tokens[2]);

            } else if (tokens[0].equals("calculate_average_price")) {
                // calculate_average_price, menuItem
                MenuItem.calculateAveragePrice(tokens[1]);

            } else if (tokens[0].equals("calculate_item_popularity")) {
                // calculate_item_popularity, menuItem
                MenuItem.calculateItemPopularity(tokens[1]);

            } else if (tokens[0].equals("view_owners")) {
                String group = tokens[1];
                Owner.viewOwners(group);

            } else if (tokens[0].equals("view_all_restaurants")) {
                Restaurant.viewAllRestaurants();

            } else if (tokens[0].equals("view_restaurants_owned")) {
                //>> view_restaurants_owned, OWN001
                Owner o = Owner.findOwner(tokens[1]);
                if (o == null) {
                    System.out.println("ERROR: Owner does not exist.");
                } else {
                    o.viewOwnedRestaurants();
                }

            } else if (tokens[0].equals("view_all_customers")) {
                Customer.viewCustomers();

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
