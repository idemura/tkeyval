package id.tkeyval;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import static id.tkeyval.Checks.check;

public class ImmutableKey implements Comparable<ImmutableKey>
{
  private final byte[] key;
  private final int keyHashCode;

  // Constructor is private because we don't make a copy of the key here to
  // ensure immutability. Factory method @of does.
  private ImmutableKey(byte[] key)
  {
    check(key.length < 256);
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

  @Override
  public int compareTo(ImmutableKey other)
  {
    return Arrays.compare(key, other.key);
  }
}
