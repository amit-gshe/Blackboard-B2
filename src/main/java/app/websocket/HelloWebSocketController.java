package app.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWebSocketController {

  @RequestMapping("/websocket")
  public String ws(){
    return "ws";
  }
}
