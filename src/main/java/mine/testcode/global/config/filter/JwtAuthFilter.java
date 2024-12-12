package mine.testcode.global.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.testcode.domain.user.CustomUserDetails;
import mine.testcode.domain.user.User;
import mine.testcode.global.utils.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("JwtAuthFilter Started");

        String header = request.getHeader("Authorization");

        // 예외 상황의 경우, 검사 없이 지나간다
        if (header == null || !header.startsWith("Bearer ")) {
            log.warn("Token is null or doesn't start with Bearer");
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7); // "Bearer " 이후의 토큰 부분만 가져옵니다.
        log.info("Token : {}", token);

        // 토큰이 만료되었을 경우, 검사 없이 지나간다
        if (jwtUtil.isExpired(token)) {
            log.warn("Expired token");
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰에서 사용자 정보 추출
        String userEmail = jwtUtil.getUserEmail(token);
        String userRole = jwtUtil.getUserRole(token);

        // 사용자 정보로 User 객체 생성
        User user = User.builder()
                .userEmail(userEmail)
                .userPassword("")
                .userRole(userRole)
                .build();

        // UserDetails 객체 생성
        CustomUserDetails userDetails = new CustomUserDetails(user);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // SecurityContextHolder 에 인증 정보 설정
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);

    }
}
