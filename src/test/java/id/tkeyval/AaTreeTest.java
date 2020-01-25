package id.tkeyval;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AaTreeTest
{
  @Test
  public void testPutGetReplaceSize()
  {
    var aaTree = new AaTree();
    var t = new AutoValidateTree(aaTree);
    assertEquals(0, t.size());
    t.put(ImmutableKey.ofString("80"), "v0");
    assertEquals(1, t.size());
    t.put(ImmutableKey.ofString("50"), "v1");
    assertEquals(2, t.size());
    t.put(ImmutableKey.ofString("40"), "v2");
    assertEquals(3, t.size());

    assertEquals("v0", t.get(ImmutableKey.ofString("80")));
    assertEquals("v1", t.get(ImmutableKey.ofString("50")));
    assertEquals("v2", t.get(ImmutableKey.ofString("40")));

    // Replace with new values
    t.put(ImmutableKey.ofString("80"), "z0");
    assertEquals(3, t.size());
    t.put(ImmutableKey.ofString("50"), "z1");
    assertEquals(3, t.size());
    t.put(ImmutableKey.ofString("40"), "z2");
    assertEquals(3, t.size());

    assertEquals("z0", t.get(ImmutableKey.ofString("80")));
    assertEquals("z1", t.get(ImmutableKey.ofString("50")));
    assertEquals("z2", t.get(ImmutableKey.ofString("40")));
  }

  @Test
  public void testPut()
  {
    var aaTree = new AaTree();
    var t = new AutoValidateTree(aaTree);
    t.put(ImmutableKey.ofString("80"), "v0");
    wrap(aaTree.root()).isKey("80").isLevel(0);

    // Skew
    t.put(ImmutableKey.ofString("50"), "v1");
    wrap(aaTree.root()).isKey("50").isLevel(0)
        .withR((r) -> wrap(r).isKey("80").isLevel(0));

    // Noop
    t.put(ImmutableKey.ofString("40"), "v2");
    wrap(aaTree.root()).isKey("50").isLevel(1)
        .withL((l) -> wrap(l).isKey("40").isLevel(0))
        .withR((r) -> wrap(r).isKey("80").isLevel(0));

    // Skew+Split
    t.put(ImmutableKey.ofString("30"), "v3");
    wrap(aaTree.root()).isKey("50").isLevel(1)
        .withL((l) -> wrap(l).isKey("30").isLevel(0)
            .withR((lr) -> wrap(lr).isKey("40").isLevel(0)))
        .withR((r) -> wrap(r).isKey("80").isLevel(0));

    t.put(ImmutableKey.ofString("20"), "v4");
    wrap(aaTree.root()).isKey("30").isLevel(1)
        .withL((l) -> wrap(l).isKey("20").isLevel(0))
        .withR((r) -> wrap(r).isKey("50").isLevel(1)
            .withL((rl) -> wrap(rl).isKey("40").isLevel(0))
            .withR((rr) -> wrap(rr).isKey("80").isLevel(0)));

    t.put(ImmutableKey.ofString("60"), "v5");
    t.put(ImmutableKey.ofString("70"), "v6");
    wrap(aaTree.root()).isKey("50").isLevel(2)
        .withL((l) -> wrap(l).isKey("30").isLevel(1)
            .withL((ll) -> wrap(ll).isKey("20").isLevel(0))
            .withR((lr) -> wrap(lr).isKey("40").isLevel(0)))
        .withR((r) -> wrap(r).isKey("70").isLevel(1)
            .withL((rl) -> wrap(rl).isKey("60").isLevel(0))
            .withR((rr) -> wrap(rr).isKey("80").isLevel(0)));
  }

  @Test
  public void testGet()
  {
    var aaTree = new AaTree();
    var t = new AutoValidateTree(aaTree);
    t.put(ImmutableKey.ofString("80"), "v0");
    t.put(ImmutableKey.ofString("50"), "v1");
    t.put(ImmutableKey.ofString("40"), "v2");
    t.put(ImmutableKey.ofString("30"), "v3");
    t.put(ImmutableKey.ofString("20"), "v4");
    t.put(ImmutableKey.ofString("60"), "v5");
    t.put(ImmutableKey.ofString("70"), "v6");

    assertEquals("v0", t.get(ImmutableKey.ofString("80")));
    assertEquals("v1", t.get(ImmutableKey.ofString("50")));
    assertEquals("v2", t.get(ImmutableKey.ofString("40")));
    assertEquals("v3", t.get(ImmutableKey.ofString("30")));
    assertEquals("v4", t.get(ImmutableKey.ofString("20")));
    assertEquals("v5", t.get(ImmutableKey.ofString("60")));
    assertEquals("v6", t.get(ImmutableKey.ofString("70")));
  }

  private static final class AaNodeWrapper
  {
    private final AaTree.AaNode node;

    AaNodeWrapper(AaTree.AaNode node)
    {
      this.node = node;
    }

    AaNodeWrapper isKey(String keyString)
    {
      assertEquals(ImmutableKey.ofString(keyString), node.getKey());
      return this;
    }

    AaNodeWrapper isLevel(int level)
    {
      assertEquals(level, node.getLevel());
      return this;
    }

    AaNodeWrapper withL(Consumer<AaTree.AaNode> consumer)
    {
      consumer.accept(node.getL());
      return this;
    }

    AaNodeWrapper withR(Consumer<AaTree.AaNode> consumer)
    {
      consumer.accept(node.getR());
      return this;
    }
  }

  private AaNodeWrapper wrap(AaTree.AaNode node)
  {
    return new AaNodeWrapper(node);
  }
}
