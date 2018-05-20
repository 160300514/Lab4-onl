package exception;

public class MessageFieldIncorrectException extends Exception
{
    public MessageFieldIncorrectException()
    {

        super("[E] Some Element in the input line has some unsolvable error.");
        System.out.println("[E] Some Element in the input line has some unsolvable error.");
    }
    public MessageFieldIncorrectException(String message)
    {
        super(message);
        System.out.println(message);
    }
}
