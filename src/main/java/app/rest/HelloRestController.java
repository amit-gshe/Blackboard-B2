package app.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class HelloRestController {
  
  @RequestMapping("/rest")
  public List<Map<String,Object>> helloRest(@NotBlank String key, @NotBlank String value){
    List<Map<String,Object>> list = new ArrayList<>();
    Map<String, Object> map = new HashMap<>();
    map.put(key, value);
    list.add(map);
    return list;
  }
}
