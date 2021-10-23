package exceptions;

public class NotEnoughProductForOrderException extends RuntimeException{
    public NotEnoughProductForOrderException(String message) {
        super(message);
    }
    public NotEnoughProductForOrderException(){
        super("The order has more products than the available quantity of it");
    }
}
