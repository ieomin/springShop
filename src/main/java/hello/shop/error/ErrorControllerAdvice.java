package hello.shop.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ErrorControllerAdvice {

//    @GetMapping("/400")
//    public void occur400(HttpServletResponse res) throws IOException {
//        res.sendError(400, "400에러 메시지");
//    }
//
//    @GetMapping("/500")
//    public void occur500(HttpServletResponse res) throws IOException {
//        res.sendError(500, "500에러 메시지");
//    }
//
//    @GetMapping("/RE")
//    public void occurRE(){
//        throw new RuntimeException("RE로인한 500에러 메시지");
//    }

    // 팁: get이외의다른요청도 처리가능
    @RequestMapping("/error-page/400")
    public String to400(){
        return "error-page/400";
    }

    @RequestMapping("/error-page/404")
    public String to404(){
        return "error-page/404";
    }

    // 팁: 구체적이진 않긴 해서 ErrorApiController에 밀림
    @RequestMapping("/error-page/500")
    public String Eto500(){
        return "error-page/500";
    }
}
