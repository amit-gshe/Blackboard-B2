package app.web;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import app.model.Foo;

@Controller
public class HelloFooController {

	@Autowired
	private SessionFactory _sessionFactory;

	@SuppressWarnings({ "rawtypes" })
	@Transactional
	@RequestMapping("/fooController")
	public ModelAndView helloFoo(@RequestParam(value = "n", required = false) String name,
			@RequestParam(value = "v", required = false) String value) throws Exception {
		if (name != null && value != null) {
			Foo f = new Foo();
			f.setName(name);
			f.setValue(value);
			_sessionFactory.getCurrentSession().save(f);
		}

		// now load the Foo's
		Session s = _sessionFactory.getCurrentSession();
		List l = s.createQuery("from Foo").getResultList();

		// pass the list back to the JSP view
		ModelAndView mv = new ModelAndView("foo");
		mv.addObject("fooList", l);
		return mv;
	}

}
