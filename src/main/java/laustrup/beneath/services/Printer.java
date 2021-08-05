package laustrup.beneath.services;

public class Printer {

    private String errorLine = "############################################################################################################################################";

    public void writeErr(String message) {
        System.err.println("\n" + errorLine + "\n\n\t" + message + "\n\n" + errorLine + "\n");
    }

    public void writeExceptionErr(String message, java.lang.Exception exception) {
        System.err.println("\n" + errorLine + "\n\n\t" + message + "\n\t" + exception.getMessage() + "\n\t");
        exception.printStackTrace();
        System.err.println("\n\n\t" + errorLine + "\n");
    }

    public void writeMessage(String message) {
        System.out.println("\n" + message + "\n");
    }

}
