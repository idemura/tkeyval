package id.tkeyval;

import java.util.HashMap;
import java.util.Map;

public class LSM
{
  private Map<ImmutableKey, byte[]> map;

  public LSM()
  {
    this.map = new HashMap<>();
  }

  void putValue(ImmutableKey key, byte[] value)
  {
    map.put(key, value);
  }

  byte[] getValue(ImmutableKey key)
  {
    return map.get(key);
  }
}
