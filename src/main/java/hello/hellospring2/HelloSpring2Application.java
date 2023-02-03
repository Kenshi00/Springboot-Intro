package hello.hellospring2;
// 아무때나 @Component를 붙여도 되는 것이 아님.
// 위의 hello.hellospring2의 하위항목들을 다 뒤져서 @Component가 붙어 있으면
// 스프링 빈에 등록해 주는 것
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloSpring2Application {

	public static void main(String[] args) {
		SpringApplication.run(HelloSpring2Application.class, args);
	}

}
