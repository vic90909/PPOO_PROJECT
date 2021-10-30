package exceptions;

public class DateOutOfBoundsException extends RuntimeException{
    public DateOutOfBoundsException(String message) {
        super(message);
    }
    public DateOutOfBoundsException(){
        super("The input date is out of bonds!\n Please consider that the month is between 1 and 12 and the Day is between 1 and 31");
    }
}
