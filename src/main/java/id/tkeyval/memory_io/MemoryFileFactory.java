package id.tkeyval.memory_io;

import id.tkeyval.IoFile;
import id.tkeyval.IoFileFactory;

public class MemoryFileFactory implements IoFileFactory
{
  @Override
  public IoFile create(String name)
  {
    return new MemoryFile();
  }

  @Override
  public IoFile open(String name)
  {
    return new MemoryFile();
  }

  @Override
  public void move(String name, String to)
  {
  }

  @Override
  public void remove(String name)
  {
  }
}
