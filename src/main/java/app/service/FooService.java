package app.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.model.Foo;

@Service
@Transactional
public class FooService {

  @Autowired
  private SessionFactory sessionFactory;

  public void save(Foo foo) {
    sessionFactory.getCurrentSession().save(foo);
  }

  @SuppressWarnings("unchecked")
  public List<Foo> fetchAll() {
    return sessionFactory.getCurrentSession().createQuery("from Foo").getResultList();
  }
}
