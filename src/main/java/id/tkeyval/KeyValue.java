package id.tkeyval;

import static id.tkeyval.Checks.check;

public class KeyValue
{
  private Tree map;

  public KeyValue()
  {
    this.map = new AaTree();
  }

  public void put(ImmutableKey key, Object value)
  {
    check(value != null);
    map.put(key, value);
  }

  public void remove(ImmutableKey key)
  {
    // Put a tombstone
    map.put(key, null);
  }

  public Object get(ImmutableKey key)
  {
    return map.get(key);
  }
}
