package exception;

public class RepFieldException extends Exception
{
    public RepFieldException()
    {
        super("[E] Check Rep Failed.");
        System.out.println("[E] Check Rep Failed.");
    }
    public RepFieldException(String message)
    {
        super("[E] Check Rep Failed. In Class "+message+".");
        System.out.println("[E] Check Rep Failed. In Class "+message+".");
    }
}
