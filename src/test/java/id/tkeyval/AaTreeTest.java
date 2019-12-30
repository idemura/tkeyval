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
    var k0 = ImmutableKey.ofString("abc");
    var v0 = new byte[]{1, 2, 3};
    t.putKey(k0, v0);
    t.validate();
    assertEquals(1, t.size());
    assertArrayEquals(v0, t.getKey(k0));
  }
}
