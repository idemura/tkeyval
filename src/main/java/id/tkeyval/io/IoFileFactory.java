package id.tkeyval.io;

// Files in our system are immutable. Because of this, we can create it and
// write, or open for reading.
public interface IoFileFactory
{
  IoFile create(String name);
  IoFile open(String name);
  void move(String name, String to);
  void remove(String name);
}
