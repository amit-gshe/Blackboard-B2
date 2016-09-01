package app.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import app.model.Foo;

@Service
public class FooService {

  private SessionFactory sessionFactory;

  public FooService(SessionFactory _sessionFactory) {
    this.sessionFactory = _sessionFactory;
  }

  public void save(Foo foo) {
    Session s = sessionFactory.openSession();
    Transaction tx = s.beginTransaction();
    try {
      s.save(foo);
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      s.close();
    }
  }

  @SuppressWarnings("unchecked")
  public List<Foo> fetchAll() {
    Session s = sessionFactory.openSession();
    try {
      return s.createQuery("from Foo").getResultList();
    } finally {
      s.close();
    }
  }
}
