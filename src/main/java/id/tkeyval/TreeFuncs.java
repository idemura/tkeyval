package id.tkeyval;

import java.util.ArrayList;
import java.util.List;

import static id.tkeyval.Checks.check;

public final class TreeFuncs
{
  private TreeFuncs() {}

  public static ImmutableKey[] infixWalk(Tree tree)
  {
    var walk = new ArrayList<ImmutableKey>();
    infixWalkRec(tree.getRoot(), walk);
    return walk.toArray(new ImmutableKey[0]);
  }

  // Checks Binary Search Tree property using @check
  public static void checkBst(Tree tree)
  {
    checkBstRec(tree.getRoot(), null, null);
  }

  private static void infixWalkRec(Tree.Node node, List<ImmutableKey> output)
  {
    if (node != null) {
      output.add(node.getKey());
      infixWalkRec(node.getL(), output);
      infixWalkRec(node.getR(), output);
    }
  }

  private static void checkBstRec(Tree.Node node, ImmutableKey min, ImmutableKey max)
  {
    if (node != null) {
      check(min == null || min.compareTo(node.getKey()) < 0, "bst.min");
      check(max == null || max.compareTo(node.getKey()) > 0, "bst.max");

      checkBstRec(node.getL(), min, node.getKey());
      checkBstRec(node.getR(), node.getKey(), max);
    }
  }
}
