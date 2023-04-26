package hello.shop.web.form.member;

import hello.shop.entity.Address;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class MemberCreateForm {

    @NotBlank(message = "회원 이름은 필수입니다")
    private String name;

//    @NotBlank(message = "회원 배송 도시는 필수입니다")
    private String city;

//    @NotBlank(message = "회원 배송 도로는 필수입니다")
    private String street;

//    @NotBlank(message = "회원 배송 도로번호는 필수입니다")
    private String zipcode;

    @NotBlank(message = "뢰원 로그인 ID는 필수입니다")
    private String loginId;

    @NotBlank(message = "회원 로그인 비밀번호는 필수입니다")
    private String password;
}


