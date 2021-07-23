package laustrup.beneath.services;

public class Print {

    private String errorLine = "############################";

    public void writeErr(String message) {
        System.err.println(errorLine + "\n\n\t" + message + "\n\n" + errorLine);
    }

    public void writeExceptionErr(String message, java.lang.Exception exception) {
        System.err.println(errorLine + "\n\n\t" + message + "\n\t" + exception.getMessage() + "\n\t");
        exception.printStackTrace();
        System.err.println("\n\n\t" + errorLine);
    }

    public void writeMessage(String message) {
        System.out.println("\n" + message + "\n");
    }

}
