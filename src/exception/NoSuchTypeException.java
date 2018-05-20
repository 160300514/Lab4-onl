package exception;

public class NoSuchTypeException extends Exception
{
    public NoSuchTypeException()
    {
        super("[E] Element cannot be added: unknown type.");
        System.out.println("[E] Element cannot be added: unknown type.");
    }
    public NoSuchTypeException(String message)
    {
        super(message);
        System.out.println(message);
    }
}
