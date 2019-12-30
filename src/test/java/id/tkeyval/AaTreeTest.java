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
    var v0 = new byte[]{1, 2};
    t.putKey(k0, v0);
    t.validate();
    assertEquals(1, t.size());
    assertArrayEquals(v0, t.getKey(k0));

    var k1 = ImmutableKey.ofString("kp");
    var v1 = new byte[]{1, 0};
    t.putKey(k1, v1);
    t.validate();
    assertArrayEquals(v0, t.getKey(k0));
    assertArrayEquals(v1, t.getKey(k1));

    var k2 = ImmutableKey.ofString("ka");
    var v2 = new byte[]{1, 5};
    t.putKey(k2, v2);
    t.validate();
    assertArrayEquals(v0, t.getKey(k0));
    assertArrayEquals(v1, t.getKey(k1));
    assertArrayEquals(v2, t.getKey(k2));
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
