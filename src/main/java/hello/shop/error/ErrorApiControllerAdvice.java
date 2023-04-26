package hello.shop.error;

import hello.shop.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ErrorApiControllerAdvice {

    // 결과: ErrorController와는다르게 각에러마다처리를다르게할필요가있기때문에 ExceptionHandlerExceptionResolver필요
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult IEto400(IllegalArgumentException e) {
        return new ErrorResult("400", e.getMessage());
    }

    // 결과: CustomException을 ExceptionHandlerExceptionResolver에등록할려면 다음과정이필요
    // 결과: 동적으로 상태코드를변경가능하게하는 역할의 함수
    @ExceptionHandler
    public ResponseEntity<ErrorResult> CEto400(CustomException e) {
        return new ResponseEntity<>(new ErrorResult("400", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult Eto500(Exception e) {
        return new ErrorResult("500", e.getMessage());
    }
}