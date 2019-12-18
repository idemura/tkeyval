package id.tkeyval;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KeyTest
{
  @Test
  void equality()
  {
    var k1 = Key.of("abc".getBytes());
    var k2 = Key.of("abc".getBytes());
    assertEquals(k1, k2);
//    assertEquals(2, -1>>>1);
    assertEquals(2, -1>>1);
    assertEquals(1, 3&1);
  }
}
