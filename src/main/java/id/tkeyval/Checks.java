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
}
