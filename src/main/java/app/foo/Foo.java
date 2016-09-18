package app.foo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "cx_springdemo_foo")
public class Foo {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="cx_springdemo_foo_seq")
  @SequenceGenerator(name = "cx_springdemo_foo_seq", sequenceName = "cx_springdemo_foo_seq")
  @Column(name = "foo_id")
  private Long id;
  
  @Column(name = "name")
  private String name;
  
  @Column(name = "value")
  private String value;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
