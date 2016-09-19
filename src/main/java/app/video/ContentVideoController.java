package app.video;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import app.data.RestObject;
import app.exception.BBNotFoundException;
import blackboard.data.ValidationException;
import blackboard.data.content.Content;
import blackboard.data.content.ContentFile;
import blackboard.data.course.Course;
import blackboard.data.user.User;
import blackboard.persist.PersistenceException;
import blackboard.platform.contentsystem.service.ContentSystemServiceExFactory;
import blackboard.platform.spring.beans.annotations.ContextValue;
import blackboard.platform.spring.web.annotations.IdParam;
import blackboard.platform.spring.web.annotations.NoXSRF;

@Validated
@Controller
public class ContentVideoController {

  private static Logger logger = LoggerFactory.getLogger(ContentVideoController.class);

  @Autowired
  ContentItemService contentItemService;

  @RequestMapping(path = "/item/page/{action}", method = RequestMethod.GET)
  public String createContentPage(@PathVariable @NotBlank String action) {
    String view = "item/%s";
    return String.format(view, action);
  }

  @NoXSRF
  @RequestMapping(path = "/item/create", method = RequestMethod.POST)
  public String create(@ContextValue User currentUser, @ContextValue Content parentContent,
      @ContextValue Course course, @RequestParam @NotBlank String videoName,
      @RequestParam("videoFile") MultipartFile file, HttpServletRequest request) throws Exception {

    String redirectParams = "?course_id=%s&content_id=%s";
    try {
      redirectParams = String.format(redirectParams, course.getId().getExternalString(),
          parentContent.getId().getExternalString());

      // 保存item
      Content content =
          contentItemService.saveContentItem(course.getId(), parentContent.getId(), videoName);

      // 保存上传的视频
      File f = File.createTempFile("bblms", null);
      file.transferTo(f);
      ContentFile cf =
          contentItemService.addContentFile(course, content, f, file.getOriginalFilename());
      f.delete();

      // 将视频与item结合
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

  @RequestMapping(path = "/videos", method = RequestMethod.GET)
  public String loadAllVideos(@ContextValue Course course, Model model) {

    try {
      List<Content> contentList = contentItemService.loadCourseContents(course.getId());
      List<ContentFile> contentFileList = new ArrayList<>();

      for (Content content : contentList) {
        contentFileList.addAll(contentItemService.loadContentFiles(content.getId()));
      }

      logger.debug("获取content的数量");

      model.addAttribute("contents", contentList);
      model.addAttribute("contentFiles", contentFileList);

    } catch (PersistenceException e) {
      logger.error("PersistenceException", e);
    } catch (Exception e) {
      logger.error("unexpected exception", e);
    }
    return "management/content_list";
  }

  @RequestMapping(path = "/course/videos", method = RequestMethod.GET)
  @ResponseBody
  public RestObject<List<VideoDTO>> loadAllVideos(@IdParam("cid") Course course) {

    List<VideoDTO> list = new ArrayList<>();
    try {
      if (course == null) {
        throw new BBNotFoundException("课程不存在,请检查cid");
      }
      List<Content> contentList = contentItemService.loadCourseContents(course.getId());
      for (Content content : contentList) {
        for (ContentFile cf : contentItemService.loadContentFiles(content.getId())) {
          String url = ContentSystemServiceExFactory.getInstance().getDocumentManagerEx()
              .getRelativeWebDAVURI(cf.getName());
          VideoDTO dto = new VideoDTO(course.getId().getExternalString(),
              content.getId().getExternalString(), cf.getId().getExternalString(), url);
          list.add(dto);
        }
      }

      return new RestObject<List<VideoDTO>>(true, list);

    } catch (PersistenceException e) {
      logger.error("PersistenceException", e);
      return new RestObject<List<VideoDTO>>(false, "PersistenceException");

    } catch (BBNotFoundException e) {
      return new RestObject<List<VideoDTO>>(false, e.getMessage());

    } catch (Exception e) {
      logger.error("unexpected exception", e);
      return new RestObject<List<VideoDTO>>(false, "UnexpectedException");

    }
  }

}
