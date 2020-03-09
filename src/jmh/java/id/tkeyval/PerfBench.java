package id.tkeyval;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

public class PerfBench
{
  @Benchmark
  public void arrays(Blackhole bh)
  {
    bh.consume("123135345pofnuewrifune8runf892run18erun9p".hashCode());
  }
}

