package hello.hellospring2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
// localhost가 실행되면 컨테이너 안의 컨트롤러부터 찾아본다.
// 그래서 첫 화면 들어갔을 때 처음에 설정한 index.html static파일 대신
// home이 먼저 뜨는것. (우선순위가 있다.)
@Controller
public class HomeController {
    // localhost:8080 들어오면 바로 이거 호출
    @GetMapping("/")
    public String home(){
        return "home";
    }
}
