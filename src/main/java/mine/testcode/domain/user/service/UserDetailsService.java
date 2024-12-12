package mine.testcode.domain.user.service;

import lombok.RequiredArgsConstructor;
import mine.testcode.domain.user.CustomUserDetails;
import mine.testcode.domain.user.User;
import mine.testcode.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(userEmail);

        if (user == null) throw new UsernameNotFoundException("NOT FOUND");

        return new CustomUserDetails(user);
    }
}
