package exceptions.InvalidMovement;

public class InvalidMovement extends Exception{
    private static final long serialVersionUID = 1939313544201095060L;

    public InvalidMovement(){
        super();
    }

    public InvalidMovement(String message){
        super(message);
    }
}
