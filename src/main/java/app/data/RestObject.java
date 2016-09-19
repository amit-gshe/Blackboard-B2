package app.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RestObject<T> {

  private T data;
  private boolean success = true;
  private String info;
  
  public RestObject( boolean success,T data) {
    this.success = success;
    this.data = data;
  }

  public RestObject(boolean success, String info) {
    this.success = success;
    this.info = info;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }
  
  
}
