package id.tkeyval;

import java.util.HashMap;
import java.util.Map;

public class LSM
{
  private Map<Key, byte[]> map;

  public LSM()
  {
    this.map = new HashMap<>();
  }

  void putValue(Key key, byte[] value)
  {
    map.put(key, value);
  }

  byte[] getValue(Key key)
  {
    return map.get(key);
  }
}
