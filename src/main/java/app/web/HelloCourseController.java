package app.web;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import blackboard.data.course.Course;
import blackboard.data.course.CourseMembership;
import blackboard.data.user.User;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.PersistenceException;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.persist.user.UserDbLoader;
import blackboard.platform.spring.web.annotations.IdParam;

@Controller
public class HelloCourseController
{

  private static Logger logger = LoggerFactory.getLogger(HelloCourseController.class);
  
  // Annotates a variable so that the Spring container will 
  // attempt to wire the reference for you automatically.
  @Autowired
  private CourseMembershipDbLoader _membershipLoader;

  @Autowired
  private UserDbLoader _userLoader;

  @RequestMapping( "/course_users" )
  // @IdParam will take the string in the request parameter listed and use it as an Id object
  // to look up the object based on the type.   In this case, it will convert the string to 
  // an Id and look up the Course based on the Id.
  public ModelAndView listCourseUsers( @IdParam( "cid" ) Course course )
    throws KeyNotFoundException, PersistenceException
  {
    ModelAndView mv = new ModelAndView( "course_users" );
    mv.addObject( "course", course );

    // Load some data and put it in the model
    List<CourseMembership> members = _membershipLoader.loadByCourseId( course.getId() );
    List<User> users = new LinkedList<User>();
    for ( CourseMembership member : members )
    {
      users.add( _userLoader.loadById( member.getUserId() ) );
      logger.debug("CourseMembership: {}", member.getIntroduction());
    }
    mv.addObject( "users", users );
    
    return mv;
  }

}
