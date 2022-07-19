package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    // 정적
    @GetMapping ("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello";
    }

    // mvc + 템플릿 엔진
    @GetMapping ("hello-mvc")
    public String helloMvc(@RequestParam(value = "name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    // Api - 문자인경우
    @GetMapping("hello-string")
    @ResponseBody // 응답 Body부에 내가 직접 넣어주겠다.
    public String helloString(@RequestParam("name") String name){
        return "hello " + name; // "hello spring"
        // 템플릿 엔진과의 차이 : view가 없음, 문자가 그대로 감 태그 없음
    }

    // api - 데이터인 경우
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
