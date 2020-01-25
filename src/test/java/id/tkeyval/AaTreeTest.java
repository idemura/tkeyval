package id.tkeyval;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AaTreeTest
{
  @Test
  public void basic()
  {
    var t = new AaTree();
    assertEquals(0, t.size());
    var k0 = ImmutableKey.ofString("nm");
    t.put(k0, "v0");
    t.validate();
    assertEquals(1, t.size());
    assertEquals("v0", t.get(k0));
    assertArrayEquals(TreeFuncs.infixWalk(t), keysOf("nm"));
    TreeFuncs.checkBst(t);

    var k1 = ImmutableKey.ofString("kp");
    t.put(k1, "v1");
    t.validate();
    assertEquals(2, t.size());
    assertEquals("v0", t.get(k0));
    assertEquals("v1", t.get(k1));
    assertArrayEquals(TreeFuncs.infixWalk(t), keysOf("kp", "nm"));
    TreeFuncs.checkBst(t);

    var k2 = ImmutableKey.ofString("ka");
    t.put(k2, "v2");
    t.validate();
    assertEquals(3, t.size());
    assertEquals("v0", t.get(k0));
    assertEquals("v1", t.get(k1));
    assertEquals("v2", t.get(k2));
    assertArrayEquals(TreeFuncs.infixWalk(t), keysOf("kp", "ka", "nm"));
    TreeFuncs.checkBst(t);

    {
      var r = t.getRoot();
      assertEquals(1, r.getLevel());
      assertEquals(ImmutableKey.ofString("kp"), r.getKey());
      assertEquals(0, r.getL().getLevel());
      assertEquals(ImmutableKey.ofString("ka"), r.getL().getKey());
      assertEquals(0, r.getR().getLevel());
      assertEquals(ImmutableKey.ofString("nm"), r.getR().getKey());
    }
  }

  private static ImmutableKey[] keysOf(String... keyStrings)
  {
    var keys = new ImmutableKey[keyStrings.length];
    for (int i = 0; i < keys.length; i++) {
      keys[i] = ImmutableKey.ofString(keyStrings[i]);
    }
    return keys;
  }
}
