package id.tkeyval;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AaTreeTest
{
  @Test
  public void basic()
  {
    var t = new AaTree();
    assertEquals(0, t.size());
    var k0 = ImmutableKey.ofString("nm");
    var v0 = "10";
    t.put(k0, v0);
    t.validate();
    assertEquals(1, t.size());
    assertEquals(v0, t.get(k0));

    var k1 = ImmutableKey.ofString("kp");
    var v1 = new byte[]{1, 0};
    t.put(k1, v1);
    t.validate();
    assertEquals(v0, t.get(k0));
    assertEquals(v1, t.get(k1));

    var k2 = ImmutableKey.ofString("ka");
    var v2 = new byte[]{1, 5};
    t.put(k2, v2);
    t.validate();
    assertEquals(v0, t.get(k0));
    assertEquals(v1, t.get(k1));
    assertEquals(v2, t.get(k2));
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
}
