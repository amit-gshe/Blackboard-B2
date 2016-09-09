package app.web;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ViewResolver;

import blackboard.base.FormattedText;
import blackboard.data.ValidationException;
import blackboard.data.content.Content;
import blackboard.data.content.ContentFile;
import blackboard.data.course.Course;
import blackboard.data.user.User;
import blackboard.persist.PersistenceException;
import blackboard.persist.content.ContentDbPersister;
import blackboard.persist.content.ContentFileDbPersister;
import blackboard.platform.contentsystem.manager.DocumentManager;
import blackboard.platform.contentsystem.service.ContentSystemServiceExFactory;
import blackboard.platform.coursecontent.CourseContentManagerFactory;
import blackboard.platform.spring.beans.annotations.ContextValue;
import blackboard.platform.spring.web.annotations.NoXSRF;

@Validated
@Controller
@RequestMapping("/item")
public class HelloContentController {

  private static Logger logger = LoggerFactory.getLogger(HelloContentController.class);

  @Autowired
  ViewResolver viewResovler;
  
  @RequestMapping(path = "/page/{action}", method = RequestMethod.GET)
  public String createContentPage(@PathVariable @NotBlank String action) {
    String view = "item/%s";
    return String.format(view, action);
  }

  @NoXSRF
  @RequestMapping(path = "/create", method = RequestMethod.POST)
  @Transactional
  public String create(@ContextValue User currentUser, @ContextValue Content parentContent,
      @ContextValue Course course, @RequestParam @NotBlank String videoName,
      @RequestParam("videoFile") MultipartFile file, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws Exception {
    String redirectParams = "?course_id=%s&content_id=%s";
    try {
      logger.debug("context user: {}", currentUser.getUserName());
      logger.debug("course: {}", course.getId().getExternalString());
      logger.debug("parent content: {}", parentContent.getId().getExternalString());
      redirectParams = String.format(redirectParams, course.getId().getExternalString(),parentContent.getId().getExternalString());
      
      // handle the file uploading
      File f = File.createTempFile("temp", "tmp");
      file.transferTo(f);
      
      long fileSize = f.length();
      String fileName = file.getOriginalFilename();
      logger.debug("uploaded file: {}, size: {}",fileName,fileSize);
      
      Content content = new Content();
      content.setCourseId(course.getId());
      content.setParentId(parentContent.getId());
      content.setIsAvailable(true);
      content.setIsTracked(true);
      content.setContentHandler("resource/x-cx-video");
      content.setTitle(videoName);
      content.setLaunchInNewWindow(true);
      content.setUrl("http://www.baidu.com");
      content.validate();
      ContentDbPersister.Default.getInstance().persist(content);
      
      // Save the file
      String csLocation = ContentSystemServiceExFactory.getInstance().getDocumentManagerEx().getHomeDirectory(course);
      ContentFile cf = CourseContentManagerFactory.getInstance().addAttachedLocalFileAsContentFile(content, f, csLocation, fileName, fileName,ContentFile.Action.EMBED, DocumentManager.DuplicateFileHandling.Rename);
      ContentFileDbPersister.Default.getInstance().persist(cf);
      
      String url = ContentSystemServiceExFactory.getInstance().getDocumentManagerEx().getRelativeWebDAVURI(cf.getName());
      logger.debug("content url: {}",url);
      
      // test
      model.put("url", url);
      MockHttpServletResponse mockResp = new MockHttpServletResponse();
      viewResovler.resolveViewName("item/body", Locale.getDefault()).render(model, request, mockResp);
      String body = mockResp.getContentAsString();
      logger.debug("generated html: {}",body);    
      FormattedText fmt = FormattedText.toFormattedText(body);
      content.setBody(fmt);
      content.setUrl(url);
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
