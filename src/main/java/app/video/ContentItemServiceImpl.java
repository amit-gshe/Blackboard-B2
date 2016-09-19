package app.video;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ViewResolver;

import blackboard.base.FormattedText;
import blackboard.data.ValidationException;
import blackboard.data.content.Content;
import blackboard.data.content.ContentFile;
import blackboard.data.course.Course;
import blackboard.persist.Id;
import blackboard.persist.PersistenceException;
import blackboard.persist.content.ContentDbPersister;
import blackboard.persist.content.ContentFileDbPersister;
import blackboard.platform.contentsystem.manager.DocumentManager;
import blackboard.platform.contentsystem.service.ContentSystemServiceExFactory;
import blackboard.platform.coursecontent.CourseContentManagerFactory;
import blackboard.platform.filesystem.FileSystemException;

@Service("contentItemService")
@Transactional
public class ContentItemServiceImpl implements ContentItemService {

  private static Logger logger = LoggerFactory.getLogger(ContentItemServiceImpl.class);
  private ViewResolver viewResovler;

  @Autowired
  public ContentItemServiceImpl(ViewResolver viewResovler) {
    this.viewResovler = viewResovler;
  }

  @Override
  public Content saveContentItem(Id courseId, Id parentContentId, String videoName) throws ValidationException, PersistenceException {
    Content content = new Content();
    content.setCourseId(courseId);
    content.setParentId(parentContentId);
    content.setIsAvailable(true);
    content.setIsTracked(true);
    content.setContentHandler("resource/x-cx-video");
    content.setTitle(videoName);
    content.setLaunchInNewWindow(true);
    content.validate();
    ContentDbPersister.Default.getInstance().persist(content);
    return content;
  }

  @Override
  public ContentFile addContentFile(Course course, Content content, File tempFile,
      String saveName) throws PersistenceException, ValidationException, FileSystemException {
    String csLocation = ContentSystemServiceExFactory.getInstance().getDocumentManagerEx()
        .getHomeDirectory(course);
    ContentFile cf = CourseContentManagerFactory.getInstance().addAttachedLocalFileAsContentFile(
        content, tempFile, csLocation, saveName, saveName,
        ContentFile.Action.EMBED, DocumentManager.DuplicateFileHandling.Rename);
    ContentFileDbPersister.Default.getInstance().persist(cf);
    return cf;
  }

  @Override
  public void setVideoAsContentBody(Content content, ContentFile contentFile, HttpServletRequest request) throws Exception {
    String url = ContentSystemServiceExFactory.getInstance().getDocumentManagerEx()
        .getRelativeWebDAVURI(contentFile.getName());
    MockHttpServletResponse mockResp = new MockHttpServletResponse();
    Map<String, Object> model = new HashMap<>();
    model.put("url", url);
    viewResovler.resolveViewName("item/body", Locale.getDefault()).render(model, request,
        mockResp);
    String body = mockResp.getContentAsString();
    logger.debug("generated html: {}", body);
    FormattedText fmt = FormattedText.toFormattedText(body);
    content.setBody(fmt);
    content.setUrl(url);
    ContentDbPersister.Default.getInstance().persist(content);
    
  }

}
