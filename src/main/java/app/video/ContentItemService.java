package app.video;

import java.io.File;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import blackboard.data.ValidationException;
import blackboard.data.content.Content;
import blackboard.data.content.ContentFile;
import blackboard.data.course.Course;
import blackboard.persist.Id;
import blackboard.persist.PersistenceException;
import blackboard.platform.filesystem.FileSystemException;

public interface ContentItemService {

  public Content saveContentItem(Id courseId, Id parentContentId, String videoName) throws ValidationException, PersistenceException;

  public ContentFile addContentFile(Course course, Content content, File tempFile, String saveName) throws PersistenceException, ValidationException, FileSystemException;

  public void setVideoAsContentBody(Content content, ContentFile contentFile, HttpServletRequest request) throws Exception;

  public List<Content> loadCourseContents(Id courseId) throws PersistenceException;

  public Collection<ContentFile> loadContentFiles(Id id) throws PersistenceException;
}
