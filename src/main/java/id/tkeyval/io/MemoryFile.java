package id.tkeyval.io;

public class MemoryFile implements IoFile
{
  @Override
  public void write(byte[] buf)
  {
  }

  @Override
  public int read(long offset, int size, byte[] buf)
  {
    return 0;
  }
}
