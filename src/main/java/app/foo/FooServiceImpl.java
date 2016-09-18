package app.foo;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("fooService")
@Transactional
public class FooServiceImpl implements FooService {

  private SessionFactory sessionFactory;

  @Autowired
  public FooServiceImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public void save(Foo foo) {
    sessionFactory.getCurrentSession().save(foo);
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<Foo> fetchAll() {
    return sessionFactory.getCurrentSession().createQuery("from Foo").getResultList();
  }
}
