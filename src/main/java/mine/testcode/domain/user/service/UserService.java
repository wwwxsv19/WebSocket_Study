package mine.testcode.domain.user.service;

import lombok.RequiredArgsConstructor;
import mine.testcode.domain.user.User;
import mine.testcode.domain.user.presentation.CreateDto;
import mine.testcode.domain.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public void saveUser(CreateDto request) {
        User user = User.builder()
                .userName(request.getUserName())
                .userEmail(request.getUserEmail())
                .userPassword(bCryptPasswordEncoder.encode(request.getUserPassword()))
                .userRole("USER")
                .build();

        userRepository.save(user);
    }
}