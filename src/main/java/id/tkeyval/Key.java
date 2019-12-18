package id.tkeyval;

import java.util.Arrays;

public class Key
{
  private final byte[] key;

  private Key(byte[] key)
  {
    this.key = key;
  }

  public static Key of(byte[] key)
  {
    return new Key(key);
  }

  @Override
  public int hashCode()
  {
    return Arrays.hashCode(key);
  }

  @Override
  public boolean equals(Object other)
  {
    if (other instanceof Key) {
      return Arrays.equals(key, ((Key) other).key);
    }
    return false;
  }

  @Override
  public String toString()
  {
    var sb = new StringBuilder();
    int i = 0;
    for (; i + 3 <= key.length; i += 3) {
      sb.append(BASE_64.charAt(key[i] & 63));
      sb.append(BASE_64.charAt((key[i] >>> 6) | ((key[i + 1] & 15) << 2)));
      sb.append(BASE_64.charAt((key[i + 1] >>> 4) | (key[i + 2] & 3) << 4));
      sb.append(BASE_64.charAt((key[i + 2] >>> 2)));
    }
    switch (key.length - i) {
    case 0:
      break;
    case 1:

    case 2:
    }
    return sb.toString();
  }

  private static final String BASE_64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
}
