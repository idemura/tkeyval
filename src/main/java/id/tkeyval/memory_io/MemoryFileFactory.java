package id.tkeyval.memory_io;

import id.tkeyval.IoFile;
import id.tkeyval.IoFileFactory;

public class MemoryFileFactory implements IoFileFactory
{
  @Override
  public IoFile open(String name)
  {
    return new MemoryFile();
  }
}
