package id.tkeyval;

public interface Tree
{
  interface Node
  {
    ImmutableKey getKey();
    Node getL();
    Node getR();
  }

  int size();
  void put(ImmutableKey key, Object value);
  Object get(ImmutableKey key);
  Node getRoot();
}
