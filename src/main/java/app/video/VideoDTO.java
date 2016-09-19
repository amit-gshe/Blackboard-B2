package app.video;

public class VideoDTO {

  private String courseId;
  private String contentId;
  private String contentFileId;
  private String src;
  
  public VideoDTO(String courseId, String contentId, String contentFileId, String src) {
    this.courseId = courseId;
    this.contentId = contentId;
    this.contentFileId = contentFileId;
    this.src = src;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getContentId() {
    return contentId;
  }

  public void setContentId(String contentId) {
    this.contentId = contentId;
  }

  public String getContentFileId() {
    return contentFileId;
  }

  public void setContentFileId(String contentFileId) {
    this.contentFileId = contentFileId;
  }

  public String getSrc() {
    return src;
  }

  public void setSrc(String src) {
    this.src = src;
  }

  @Override
  public String toString() {
    return "VideoDTO [courseId=" + courseId + ", contentId=" + contentId + ", contentFileId="
        + contentFileId + ", src=" + src + "]";
  }

}
