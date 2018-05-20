package exception;

public class InnerClassException extends Exception
{
    public InnerClassException()
    {
        super("[E] Debug Error: Inner Class Config Error");
        System.out.println("[E] Debug Error: Inner Class Config Error, Please Connect Administrator.");
    }
    public InnerClassException(String addMessage)
    {
        super("[E] Debug Error: Inner Class Config Error on: "+addMessage);
        System.out.println("[E] Debug Error: Inner Class Config Error on: "+addMessage+" , Please Connect Administrator.");
    }
}
