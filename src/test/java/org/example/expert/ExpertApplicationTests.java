//package org.example.expert;
//
//import io.jsonwebtoken.Claims;
//import org.example.expert.config.JwtUtil;
//import org.example.expert.domain.auth.dto.request.SigninRequest;
//import org.example.expert.domain.auth.dto.request.SignupRequest;
//import org.example.expert.domain.auth.dto.response.SigninResponse;
//import org.example.expert.domain.auth.service.AuthService;
//import org.example.expert.domain.todo.repository.TodoRepository;
//import org.example.expert.domain.todo.service.TodoService;
//import org.example.expert.domain.user.entity.User;
//import org.example.expert.domain.user.repository.UserRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import java.time.LocalDate;
//import java.util.Random;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//class ExpertApplicationTests {
//
//    @Autowired
//    private AuthService authService;
//    @Autowired
//    private JwtUtil jwtUtil;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private TodoService todoService;
//
//    @Test
//    void contextLoads() {
//    }
//
//    @Test
//    @DisplayName("로그인후 토큰에서 닉네임 얻기")
//    void getNickName(){
//
//        userRepository.deleteAll();
//        String email = "test@test.com";
//        String password = "test";
//        String userRole = "user";
//        String nickName = "test";
//        SignupRequest signupRequest = new SignupRequest(email, password, userRole, nickName);
//        authService.signup(signupRequest);
//
//        SigninRequest request = new SigninRequest("test@test.com", "test");
//        SigninResponse response = authService.signin(request);
//        String token = response.getBearerToken();
//        if (token.startsWith("Bearer ")) {
//            token = token.substring(7); // "Bearer " 접두사 제거
//        }
//
//        assertThat((String)jwtUtil.extractClaims(token).get("nickName"))
//                .isEqualTo("test");
//
//        userRepository.deleteAll();
//
//    }
//
//    @Test
//    @DisplayName("투두리스트 여러 정렬조건만족시키는가 쿼리 확인")
//    void getTodos(){
//        String weather = null;
//        LocalDate startDate = LocalDate.parse("2025-04-01");
//        LocalDate endDate =null;
//        Pageable pageable = PageRequest.of(1, 10);
//        todoService.getTodos(pageable.getPageNumber(), pageable.getPageSize(), weather, startDate,endDate);
//    }
//
//    @Test
//    @DisplayName("대용량 데이터 다루기")
//    void searchNickName(){
//        long startTime = System.currentTimeMillis();
//        String nickname = "ShinyTiger355695136106";
//        userRepository.findUserByNickName(nickname);
//        long endTime = System.currentTimeMillis();
//        System.out.println("걸린 시간: " + (endTime - startTime) + "ms");
//    }
//
////    @Test
////    @DisplayName("유저닉네임랜덤 생성")
////    void generateUser(){
////        String[] ADJECTIVES = {
////                "Happy", "Brave", "Clever", "Fast", "Silent", "Shiny", "Lazy", "Wild", "Quick", "Calm"
////        };
////
////        String[] NOUNS = {
////                "Tiger", "Moon", "River", "Fox", "Bear", "Sky", "Lion", "Tree", "Star", "Penguin"
////        };
////        Random random = new Random();
////        int idx = 0;
////        while (idx < 1_000_000) {
////            String adjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];
////            String noun = NOUNS[random.nextInt(NOUNS.length)];
////            int number = random.nextInt(1_000_000); // 0 ~ 999999
////
////            String nickname = adjective + noun + String.format("%06d", number) + idx;
////            String email = nickname + "@test.com";
////            String password = "test";
////            String userRole = "user";
////
////            SignupRequest signupRequest = new SignupRequest(email, password, userRole, nickname);
////            authService.signup(signupRequest);
////            idx++;
////        }
////
////    }
//
//}
