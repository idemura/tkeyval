package id.tkeyval;

public final class Checks
{
  private Checks() {}

  public static void check(boolean condition)
  {
    if (!condition) {
      throw new CheckException();
    }
  }

  public static void check(boolean condition, String description)
  {
    if (!condition) {
      throw new CheckException(description);
    }
  }
}
