package id.tkeyval;

public interface IoFile
{
  void write(byte[] buf);
  int read(long offset, int size, byte[] buf);
}
