package id.tkeyval;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class ImmutableKey
{
  private final byte[] key;
  private final int keyHashCode;

  // Constructor is private because we don't make a copy of the key here to
  // ensure immutability. Factory method @of does.
  private ImmutableKey(byte[] key)
  {
    this.key = key;
    this.keyHashCode = Arrays.hashCode(key);
  }

  public static ImmutableKey of(byte[] key)
  {
    return new ImmutableKey(Arrays.copyOf(key, key.length));
  }

  public static ImmutableKey ofString(String key)
  {
    return new ImmutableKey(key.getBytes(StandardCharsets.UTF_8));
  }

  @Override
  public int hashCode()
  {
    return keyHashCode;
  }

  @Override
  public boolean equals(Object other)
  {
    if (other instanceof ImmutableKey) {
      return Arrays.equals(key, ((ImmutableKey) other).key);
    }
    return false;
  }

  @Override
  public String toString()
  {
    return "Key(" + Base64.getEncoder().encodeToString(key) + ")";
  }
}
