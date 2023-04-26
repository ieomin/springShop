package hello.shop.web.form.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class MemberLoginForm {
    @NotBlank(message = "회원 로그인 ID는 필수입니다")
    private String loginId;
    @NotBlank(message = "회원 로그인 비밀번호는 필수입니다")
    private String password;
}
