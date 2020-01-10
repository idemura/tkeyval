package id.tkeyval;

public interface Tree
{
  int size();
  void put(ImmutableKey key, byte[] value);
  byte[] get(ImmutableKey key);
}
