package org.example.expert;

import io.jsonwebtoken.Claims;
import org.example.expert.config.JwtUtil;
import org.example.expert.domain.auth.dto.request.SigninRequest;
import org.example.expert.domain.auth.dto.request.SignupRequest;
import org.example.expert.domain.auth.dto.response.SigninResponse;
import org.example.expert.domain.auth.service.AuthService;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ExpertApplicationTests {

    @Autowired
    private AuthService authService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("로그인후 토큰에서 닉네임 얻기")
    void getNickName(){

        userRepository.deleteAll();
        String email = "test@test.com";
        String password = "test";
        String userRole = "user";
        String nickName = "test";
        SignupRequest signupRequest = new SignupRequest(email, password, userRole, nickName);
        authService.signup(signupRequest);

        SigninRequest request = new SigninRequest("test@test.com", "test");
        SigninResponse response = authService.signin(request);
        String token = response.getBearerToken();
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 접두사 제거
        }

        assertThat((String)jwtUtil.extractClaims(token).get("nickName"))
                .isEqualTo("test");

        userRepository.deleteAll();

    }

}
