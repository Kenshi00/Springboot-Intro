package hello.hellospring2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    // localhost:8080/hello를 하면 GetMapping method 실행
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data","hello!!");
        // templates의 hello.html의 data에 hello!! 넘겨줌
        return "hello"; // templates 폴더의 hello.html에 가서 렌더링해라!
        // viewResolver가 resources:templates/+{ViewName}+.html 스프링 부트 템플릿엔진 기본 viewName 매핑
    }
}
