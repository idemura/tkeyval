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
  private int size;

  public AaTree() {}

  @Override
  public int size()
  {
    return size;
  }

  @Override
  public void put(ImmutableKey key, Object value)
  {
    root = putRec(root, key, value);
  }

  @Override
  public void remove(ImmutableKey key)
  {
    root = removeRec(root, key);
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

  private static AaNode rotateL(AaNode n)
  {
    var l = n.L;
    n.L = l.R;
    l.R = n;
    return l;
  }

  private static AaNode rotateR(AaNode n)
  {
    var r = n.R;
    n.R = r.L;
    r.L = n;
    return r;
  }

  // @putRec never returns null
  private AaNode putRec(AaNode node, ImmutableKey key, Object value)
  {
    if (node == null) {
      size++;
      return new AaNode(key, value);
    }
    var cmp = key.compareTo(node.key);
    if (cmp == 0) {
      node.value = value;
      return node;
    }
    if (cmp < 0) {
      node.L = putRec(node.L, key, value);
      // Skew: @node.L is not null here
      if (node.level == node.L.level) {
        node = rotateL(node);
      }
    } else {
      node.R = putRec(node.R, key, value);
    }
    // Split
    if (node.R != null && node.R.R != null && node.level == node.R.R.level) {
      node = rotateR(node);
      node.level++;
    }
    return node;
  }

  private AaNode removeRec(AaNode node, ImmutableKey key)
  {
    if (node == null) {
      return null;
    }
    var cmp = key.compareTo(node.key);
    if (cmp == 0) {
      size--;
      if (node.L == null) {
        return node.R;
      }
      if (node.R == null) {
        return node.L;
      }
      // Find successor and move it into this node
      var s = node.R;
      while (s.L != null) {
        s = s.L;
      }
      key = s.key;
      node.key = s.key;
      node.value = s.value;
    }

    if (cmp < 0) {
      node.L = removeRec(node.L, key);
      if (node.L != null && node.level > node.L.level + 1) {
        // This is more complicated case. We should consider 3 cases:
        //    L            L         L
        //   / \          / \       / \
        // L-2 L-1      L-2  L    L-2 L-1
        //       \            \         \
        //       L-1          L-1       L-2
        if (node.R != null) {
          if (node.level == node.R.level) {
            // Case 2
            node.level--;
            node = rotateR(node);
          }
          else {
            // Case 1 and 3
            node.level--;
            if (node.R.R != null && node.level == node.R.R.level) {
              // Case 1
              node = rotateR(node);
              node.level++;
            }
          }
        }
      }
    }
    else {
      node.R = removeRec(node.R, key);
      if (node.R != null && node.level > node.R.level + 1) {
        node.level--;
        if (node.L != null) {
          node = rotateL(node);

        }
      }
    }
    if (node.L == null && node.R == null) {
      node.level = 0;
    }
    return node;
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
