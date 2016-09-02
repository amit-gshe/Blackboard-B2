package app.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import app.model.Foo;
import app.service.FooService;

@Controller
public class HelloFooController {

  @Autowired
  FooService fooService;

  @SuppressWarnings({"rawtypes"})
  @RequestMapping("/fooController")
  public ModelAndView helloFoo(@RequestParam(value = "n", required = false) String name,
      @RequestParam(value = "v", required = false) String value) throws Exception {
    if (name != null && value != null) {
      Foo f = new Foo();
      f.setName(name);
      f.setValue(value);
      fooService.save(f);
    }

    List l = fooService.fetchAll();

    ModelAndView mv = new ModelAndView("foo");
    mv.addObject("fooList", l);
    return mv;
  }

}
