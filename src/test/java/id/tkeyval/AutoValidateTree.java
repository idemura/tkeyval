package id.tkeyval;

public class AutoValidateTree implements Tree
{
  private final Tree tree;

  public AutoValidateTree(Tree tree)
  {
    this.tree = tree;
  }

  @Override
  public int size()
  {
    return tree.size();
  }

  @Override
  public void put(ImmutableKey key, Object value)
  {
    tree.put(key, value);
    tree.validate();
  }

  @Override
  public Object get(ImmutableKey key)
  {
    return tree.get(key);
  }

  @Override
  public Node root()
  {
    return tree.root();
  }

  @Override
  public void validate()
  {
    tree.validate();
  }
}
