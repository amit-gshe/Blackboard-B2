package app.service;

import java.util.List;

import app.model.Foo;

public interface FooService {

  public void save(Foo foo);

  public List<Foo> fetchAll();
  
}
