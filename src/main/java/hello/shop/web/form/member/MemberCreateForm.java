package hello.shop.web.form.member;

import hello.shop.entity.Address;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class MemberCreateForm {
    @NotBlank(message = "회원 로그인ID는 필수입니다")
    private String loginId;
    @NotBlank(message = "회원 비밀번호는 필수입니다")
    private String password;
    @NotBlank(message = "회원 이름은 필수입니다")
    private String name;
    @NotBlank(message = "회원 배송지는 필수입니다")
    private String cityStreetZipcode;
}


