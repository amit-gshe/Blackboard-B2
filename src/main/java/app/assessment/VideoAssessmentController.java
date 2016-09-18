package app.assessment;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import blackboard.data.ValidationException;
import blackboard.data.content.Content;
import blackboard.data.course.Course;
import blackboard.data.gradebook.Lineitem;
import blackboard.data.user.User;
import blackboard.persist.PersistenceException;
import blackboard.persist.gradebook.LineitemDbPersister;
import blackboard.platform.spring.beans.annotations.ContextValue;
import blackboard.platform.spring.web.annotations.NoXSRF;

@Validated
@Controller
@RequestMapping("/assessment")
public class VideoAssessmentController {

  private static Logger logger = LoggerFactory.getLogger(VideoAssessmentController.class);
  
  @RequestMapping(path = "/page/{action}", method = RequestMethod.GET)
  public String createPage(@PathVariable @NotBlank String action) {
    String view = "assessment/%s";
    return String.format(view, action);
  }

  @NoXSRF
  @RequestMapping(path = "/create", method = RequestMethod.POST)
  @Transactional
  public String create(@ContextValue User currentUser, @ContextValue Content parentContent,
      @ContextValue Course course, @RequestParam String name, HttpServletRequest request) {
    String redirectParams = "?course_id=%s&content_id=%s";
    try {
      redirectParams = String.format(redirectParams, course.getId().getExternalString(),
          parentContent.getId().getExternalString());
      Lineitem item = new Lineitem();
      item.setCourseId(course.getId());
      item.setName(name);
      item.setPointsPossible(100);
      item.setType("Test assessment");
      item.validate();
      LineitemDbPersister.Default.getInstance().persist(item);
    } catch (PersistenceException e) {
      logger.error("Persist failed!",e);
    } catch (ValidationException e) {
      logger.error("Validate failed!",e);
    } catch(Exception e){
      logger.error("Unexpected exception!",e);
    }
    return "redirect:../../blackboard/content/listContentEditable.jsp" + redirectParams;
  }
}
