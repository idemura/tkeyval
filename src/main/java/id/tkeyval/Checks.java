package id.tkeyval;

public class Checks
{
  public static void check(boolean condition)
  {
    if (!condition) {
      throw new CheckException();
    }
  }
}
