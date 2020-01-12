package id.tkeyval;

public interface Tree
{
  int size();
  void put(ImmutableKey key, Object value);
  Object get(ImmutableKey key);
}
