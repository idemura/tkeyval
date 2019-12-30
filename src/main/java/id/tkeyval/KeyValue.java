package id.tkeyval;

import java.util.Map;
import java.util.TreeMap;

import static id.tkeyval.Checks.check;

public class KeyValue
{
  private Map<ImmutableKey, byte[]> map;

  public KeyValue()
  {
    this.map = new TreeMap<>();
  }

  void putKey(ImmutableKey key, byte[] value)
  {
    check(value != null);
    map.put(key, value);
  }

  byte[] getKey(ImmutableKey key)
  {
    return map.get(key);
  }

  void removeKey(ImmutableKey key)
  {
    // Put a tombstone
    map.put(key, null);
  }
}
