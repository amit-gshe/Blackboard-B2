package app.web;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import blackboard.data.content.Content;
import blackboard.data.course.Course;
import blackboard.data.user.User;
import blackboard.platform.spring.beans.annotations.ContextValue;
import blackboard.platform.spring.web.annotations.NoXSRF;

@Validated
@Controller
@RequestMapping("/assignment")
public class HelloAssignment {

  @RequestMapping(path = "/page/{action}", method = RequestMethod.GET)
  public String createPage(@PathVariable @NotBlank String action) {
    String view = "assignment/%s";
    return String.format(view, action);
  }

  @NoXSRF
  @RequestMapping(path="/create", method=RequestMethod.POST)
  @Transactional
  public String create(@ContextValue User currentUser, @ContextValue Content parentContent,
      @ContextValue Course course, @RequestParam String name, @RequestParam String body,
      HttpServletRequest request) {
    String redirectParams = "?course_id=%s&content_id=%s";
    redirectParams = String.format(redirectParams, course.getId().getExternalString(),
        parentContent.getId().getExternalString());
    return "redirect:../../blackboard/content/listContentEditable.jsp" + redirectParams;
  }
}
