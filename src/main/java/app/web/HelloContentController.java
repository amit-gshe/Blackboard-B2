package app.web;

import java.io.InputStream;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import blackboard.base.FormattedText;
import blackboard.data.ValidationException;
import blackboard.data.content.Content;
import blackboard.data.course.Course;
import blackboard.data.user.User;
import blackboard.persist.PersistenceException;
import blackboard.persist.content.ContentDbPersister;
import blackboard.platform.spring.beans.annotations.ContextValue;
import blackboard.platform.spring.web.annotations.NoXSRF;

@Controller
@RequestMapping("/item")
public class HelloContentController {

  private static Logger logger = LoggerFactory.getLogger(HelloContentController.class);

  @RequestMapping(path = "/createPage", method = RequestMethod.GET)
  public String createContentPage() {
    return "item/create";
  }

  @RequestMapping(path = "/editPage", method = RequestMethod.GET)
  public String editContentPage() {
    return "item/edit";
  }
  
  @RequestMapping(path= "/viewPage", method= RequestMethod.GET)
  public String viewContentPage(){
    return "item/view";
  }

  @Transactional
  @NoXSRF
  @RequestMapping(path = "/create", method = RequestMethod.POST)
  public String createContent(@ContextValue User currentUser, @ContextValue Content parentContent,
      @ContextValue Course course, @RequestParam String name, @RequestParam String body,
      HttpServletRequest request) throws Exception {
    String redirectParams = "?course_id=%s&content_id=%s";
    try {
      logger.debug("context user: {}", currentUser.getUserName());
      logger.debug("course: {}", course.getId().getExternalString());
      logger.debug("parent content: {}", parentContent.getId().getExternalString());
      redirectParams = String.format(redirectParams, course.getId().getExternalString(),parentContent.getId().getExternalString());
      // test
      InputStream is = getClass().getResourceAsStream("/template.html");
      body = StreamUtils.copyToString(is, Charset.defaultCharset());
      
      FormattedText fmt = FormattedText.toFormattedText(body);
      Content content = new Content();
      content.setCourseId(course.getId());
      content.setParentId(parentContent.getId());
      content.setIsAvailable(true);
      content.setIsTracked(true);
      content.setContentHandler("resource/x-cx-video");
      content.setTitle(name);
      content.setBody(fmt);
      content.setLaunchInNewWindow(true);
      content.setUrl("http://www.baidu.com");
      content.validate();
      ContentDbPersister.Default.getInstance().persist(content);
      
    } catch (ValidationException e) {
      logger.warn("Content validation failed: {}\n    cause:{}", e.getMessage(), e.getCause());
    } catch (PersistenceException e) {
      logger.warn("Content persistent failed: {}\n    cause:{}", e.getMessage(), e.getCause());
    } catch (Exception e) {
      logger.warn("internal error: {}\n    cause:{}", e.getMessage(), e.getCause());
      throw e;
    }
    return "redirect:../../blackboard/content/listContentEditable.jsp"+redirectParams;
  }

}
