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
  void remove(ImmutableKey key);
  Object get(ImmutableKey key);
  Node root();
  void validate();
}
