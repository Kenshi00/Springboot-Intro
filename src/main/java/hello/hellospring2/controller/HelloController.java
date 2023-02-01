package hello.hellospring2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    // MVC 방식 (템플릿 엔진 사용해서 html로 렌더링)
    // RequestParam은 파라미터를 받아서 전달해 주는 것이다.
    // ex) localhost:8080/hello-mvc?name=spring (HTTP식 값 전달) -> hello spring
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model){
        model.addAttribute("name",name);
        return "hello-template";
        // templates 폴더의 hello-template.html에 전달
    }

    // API 방식
    // HTTP의 body에다가 내가 직접 data를 넣어서 주는 것
    // 템플릿 엔진 필요 x, html 렌더링 x, 데이터를 그대로 넘겨줌
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return "hello " + name; // view 같은거 안거치고 "hello spring" 가 그대로 내려감
    }

    // json 방식 { key : value}
    // localhost:8080/hello-api -> 톰캣이 hello-api 확인
    // 스프링 컨테이너에서 GetMapping("hello-api") 발견
    // @ResponseBody 있어서 viewResolver 대신 HttpMessageConverter 동작,
    // 원래는 HTTP body에 던지는데
    // return hello와 같이 객체인 경우는 JsonConverter, 문자인 경우 StringConverter

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        // Ctrl+shift+enter (); 빠르게 완성
        Hello hello = new Hello();
        hello.setName(name);
        return hello; // 객체를 넘김
        // localhost:8080/hello-api?name=spring
        // { "name" : "spring" } 과 같은 json 등장
    }

    static class Hello{
        // Getter Setter alt+insert 단축키

        // 자바빈 표준 방식, property 접근 방식 ...
        // name 변수가 private이라 외부에서 접근을 못함.
        // 그래서 public인 getName, setName으로 접근
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
