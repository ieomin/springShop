package hello.shop.web.form.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Getter @Setter
public class MemberLoginForm {
    @NotBlank(message = "회원 로그인ID는 필수입니다")
    private String loginId;
    @NotBlank(message = "회원 비밀번호는 필수입니다")
    private String password;
}
