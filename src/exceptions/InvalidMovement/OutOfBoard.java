package exceptions.InvalidMovement;

public class OutOfBoard extends InvalidMovement {
    private static final long serialVersionUID = 3541422041167224412L;

    public OutOfBoard() {
        super();
    }

    public OutOfBoard(String message) {
        super(message);
    }
}
