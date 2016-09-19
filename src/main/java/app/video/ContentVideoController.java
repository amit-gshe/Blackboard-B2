package app.video;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blackboard.data.ValidationException;
import blackboard.data.content.Content;
import blackboard.data.content.ContentFile;
import blackboard.data.course.Course;
import blackboard.data.user.User;
import blackboard.persist.PersistenceException;
import blackboard.platform.spring.beans.annotations.ContextValue;
import blackboard.platform.spring.web.annotations.NoXSRF;

@Validated
@Controller
@RequestMapping("/item")
public class ContentVideoController {

  private static Logger logger = LoggerFactory.getLogger(ContentVideoController.class);

  @Autowired
  ContentItemService contentItemService;

  @RequestMapping(path = "/page/{action}", method = RequestMethod.GET)
  public String createContentPage(@PathVariable @NotBlank String action) {
    String view = "item/%s";
    return String.format(view, action);
  }

  @NoXSRF
  @RequestMapping(path = "/create", method = RequestMethod.POST)
  public String create(@ContextValue User currentUser, @ContextValue Content parentContent,
      @ContextValue Course course, @RequestParam @NotBlank String videoName,
      @RequestParam("videoFile") MultipartFile file, HttpServletRequest request) throws Exception {

    String redirectParams = "?course_id=%s&content_id=%s";
    try {
      redirectParams = String.format(redirectParams, course.getId().getExternalString(),
          parentContent.getId().getExternalString());

      Content content =
          contentItemService.saveContentItem(course.getId(), parentContent.getId(), videoName);

      File f = File.createTempFile("bblms", null);
      file.transferTo(f);
      ContentFile cf =
          contentItemService.addContentFile(course, content, f, file.getOriginalFilename());
      f.delete();

      contentItemService.setVideoAsContentBody(content, cf, request);

    } catch (ValidationException e) {
      logger.warn("Content validation failed!", e);
    } catch (PersistenceException e) {
      logger.warn("Content persistent failed!", e);
    } catch (Exception e) {
      logger.warn("Unexpected exception!", e);
      throw new RuntimeException();
    }
    return "redirect:../../blackboard/content/listContentEditable.jsp" + redirectParams;
  }

}
