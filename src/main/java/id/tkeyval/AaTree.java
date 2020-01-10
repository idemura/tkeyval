package id.tkeyval;

import static id.tkeyval.Checks.check;

public class AaTree implements Tree
{
  static class Node
  {
    private ImmutableKey key;
    private byte[] value;
    private Node l;
    private Node r;
    private int level;

    private Node(ImmutableKey key, byte[] value)
    {
      this.key = key;
      this.value = value;
    }

    ImmutableKey getKey()
    {
      return key;
    }

    int getLevel()
    {
      return level;
    }

    Node getL()
    {
      return l;
    }

    Node getR()
    {
      return r;
    }
  }

  private Node root;
  private int size = 0;

  public AaTree() {}

  @Override
  public int size()
  {
    return size;
  }

  @Override
  public void put(ImmutableKey key, byte[] value)
  {
    root = putRec(root, new Node(key, value));
  }

  @Override
  public byte[] get(ImmutableKey key)
  {
    var p = root;
    while (p != null) {
      var cmp = key.compareTo(p.key);
      if (cmp == 0) {
        return p.value;
      }
      p = cmp < 0? p.l: p.r;
    }
    return null;
  }

  void validate()
  {
    validateRec(root);
  }

  Node getRoot()
  {
    return root;
  }

  private static Node skew(Node n)
  {
    if (n.l != null && n.l.level == n.level) {
      var l = n.l;
      n.l = l.r;
      l.r = n;
      return l;
    }
    return n;
  }

  private static Node split(Node n)
  {
    if (n.r != null && n.r.r != null && n.level == n.r.r.level) {
      var r = n.r;
      n.r = r.l;
      r.l = n;
      r.level++;
      return r;
    }
    return n;
  }

  private Node putRec(Node node, Node newNode)
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
      node.l = putRec(node.l, newNode);
    } else {
      node.r = putRec(node.r, newNode);
    }
    return split(skew(node));
  }

  private static void validateRec(Node node)
  {
    if (node == null) {
      return;
    }
    validateRec(node.l);
    validateRec(node.r);
    var l = node.l;
    if (l != null) {
      check(l.level == node.level || l.level == node.level - 1);
      if (l.l != null) {
        check(node.level > l.l.level);
      }
    }
    var r = node.r;
    if (r != null) {
      check(r.level == node.level || r.level == node.level - 1);
      if (r.r != null && r.r.r != null) {
        check(node.level > r.r.r.level);
      }
    }
  }
}
