package id.tkeyval;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ImmutableKeyTest
{
  @Test
  void basic()
  {
    var k1 = ImmutableKey.ofString("abc");
    var k2 = ImmutableKey.ofString("abc");
    assertEquals(k1, k2);
    assertEquals("Key(YWJj)", k1.toString());
    assertEquals("Key(YWJj)", k2.toString());
  }

  @Test
  void immutable()
  {
    var bytes = new byte[]{1, 2, 3};
    var k = ImmutableKey.of(bytes);
    assertEquals("Key(AQID)", k.toString());
    bytes[0] = 0;
    bytes[1] = 0;
    bytes[2] = 0;
    assertEquals("Key(AQID)", k.toString());
  }

  @Test
  void compare()
  {
    var k1 = ImmutableKey.ofString("abc");
    var k2 = ImmutableKey.ofString("aba");
    assertTrue(k1.compareTo(k2) > 0);
  }
}
