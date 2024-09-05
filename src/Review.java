package src;
import java.util.*;
public class Review {
    int score;
    String tags;
    Restaurant restaurant;

    public Review(int score, String tags, Restaurant restaurant) {
        if (!(1 <= score && score <=100)) {System.out.println("Score must be between 1 and 100 inclusive"); return;}
        this.score = score;
        restaurant.addReview(this);
        restaurant.updateAvgRating();
        StringBuilder str = new StringBuilder();
        String[] parts = tags.split(":");
        for (String part : parts) {
            str.append(", ").append(part);
        }
        str.deleteCharAt(0);
        this.tags = String.valueOf(str);
        this.restaurant = restaurant;
    }

    public int getScore() {
        return score;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
    public String getTags() {
        return tags;
    }



    public static void addReview(Restaurant requestedRestaurant, int score, String tags) {

        new Review(score, tags, requestedRestaurant);
        StringBuilder tagString = new StringBuilder();
        tagString.append("Tags:");
        for (Review revs : requestedRestaurant.getReviews()) {
            tagString.append(",").append(revs);
        }
        tagString.deleteCharAt(5);
        tagString.append(" ");
        System.out.printf("%s (%s) rating for this reservation: %d\n",requestedRestaurant.getRestaurantID(), requestedRestaurant.getName(), score);
        System.out.printf("%s (%s) average rating: %d\n", requestedRestaurant.getRestaurantID(), requestedRestaurant.getName(), requestedRestaurant.getAvgRating());
        System.out.println(tagString);
    }


    public String toString() {
        return tags;
    }
}