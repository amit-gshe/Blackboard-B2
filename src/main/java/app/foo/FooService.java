package app.foo;

import java.util.List;

public interface FooService {

  public void save(Foo foo);

  public List<Foo> fetchAll();
  
}
