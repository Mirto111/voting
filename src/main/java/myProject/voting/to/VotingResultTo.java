package myProject.voting.to;

public class VotingResultTo {

    private String restaurantName;

    private int count;

    public VotingResultTo(String restaurantName, int count) {
        this.restaurantName = restaurantName;
        this.count = count;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public int getCount() {
        return count;
    }
}
