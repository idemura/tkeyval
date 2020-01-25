package id.tkeyval;

import static id.tkeyval.Checks.check;

public final class AaTree implements Tree
{
  static final class AaNode implements Node
  {
    private ImmutableKey key;
    private Object value;
    private AaNode L;
    private AaNode R;
    private int level;

    AaNode(ImmutableKey key, Object value)
    {
      this.key = key;
      this.value = value;
    }

    @Override
    public ImmutableKey getKey()
    {
      return key;
    }

    @Override
    public AaNode getL()
    {
      return L;
    }

    @Override
    public AaNode getR()
    {
      return R;
    }

    @Override
    public String toString()
    {
      return "AaNode(" + key + " level=" + level + ")";
    }

    int getLevel()
    {
      return level;
    }
  }

  private AaNode root;
  private int size = 0;

  public AaTree() {}

  @Override
  public int size()
  {
    return size;
  }

  @Override
  public void put(ImmutableKey key, Object value)
  {
    root = putRec(root, new AaNode(key, value));
  }

  @Override
  public Object get(ImmutableKey key)
  {
    var p = root;
    while (p != null) {
      var cmp = key.compareTo(p.key);
      if (cmp == 0) {
        return p.value;
      }
      p = cmp < 0? p.L : p.R;
    }
    return null;
  }

  @Override
  public AaNode root()
  {
    return root;
  }

  @Override
  public void validate()
  {
    TreeFuncs.checkBinarySearchTree(this);
    validateRec(root);
  }

  private static AaNode skew(AaNode n)
  {
    if (n.L != null && n.L.level == n.level) {
      var l = n.L;
      n.L = l.R;
      l.R = n;
      return l;
    }
    return n;
  }

  private static AaNode split(AaNode n)
  {
    if (n.R != null && n.R.R != null && n.level == n.R.R.level) {
      var r = n.R;
      n.R = r.L;
      r.L = n;
      r.level++;
      return r;
    }
    return n;
  }

  private AaNode putRec(AaNode node, AaNode newNode)
  {
    if (node == null) {
      size++;
      return newNode;
    }
    var cmp = newNode.key.compareTo(node.key);
    if (cmp == 0) {
      node.value = newNode.value;
      return node;
    }
    if (cmp < 0) {
      node.L = putRec(node.L, newNode);
    } else {
      node.R = putRec(node.R, newNode);
    }
    return split(skew(node));
  }

  private static void validateRec(AaNode node)
  {
    if (node == null) {
      return;
    }
    validateRec(node.L);
    validateRec(node.R);
    var deepL = getLevelMinusOne(node, true);
    check(deepL == node.L);
    var deepR = getLevelMinusOne(node, false);
    check(deepR == node.R || deepR == node.R.R);
    if (node.L == null && node.R == null) {
      check(node.level == 0);
    }
  }

  private static AaNode getLevelMinusOne(AaNode start, boolean takeLeft)
  {
    var node = start;
    while (node != null && node.level == start.level) {
      node = takeLeft? node.L : node.R;
    }
    check(node == null || node.level == start.level - 1, "level condition");
    return node;
  }
}
