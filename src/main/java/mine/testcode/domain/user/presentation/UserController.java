package mine.testcode.domain.user.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.testcode.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = "application/json; charset=UTF-8")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/userCreate")
    public ResponseEntity<?> createUser(@RequestBody CreateDto request) {
        log.info("사용자 생성 요청");

        userService.saveUser(request);

        return ResponseEntity.ok().body("생성 완료!");
    }
}
