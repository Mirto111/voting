package myProject.voting.util;

/**
 * Created by Secret_Hero on 22.03.2018.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException() {
    }
}
