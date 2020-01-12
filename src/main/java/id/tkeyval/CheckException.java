package id.tkeyval;

public class CheckException extends RuntimeException
{
  public CheckException() {}

  public CheckException(String description)
  {
    super(description);
  }
}
