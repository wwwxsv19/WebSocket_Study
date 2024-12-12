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
        log.info("JwtAuthFilter 시작");

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            log.warn("존재하지 않거나 잘못된 토큰입니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        if (jwtUtil.isExpired(token)) {
            log.warn("유효하지 않은 토큰입니다.");
            filterChain.doFilter(request, response);
            return;
        }

        log.info("토큰 : {}", token);

        String userEmail = jwtUtil.getUserEmail(token);
        String userRole = jwtUtil.getUserRole(token);

        User user = User.builder()
                .userEmail(userEmail)
                .userPassword("")
                .userRole(userRole)
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(user);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);

        log.info("JwtAuthFilter 통과");
        filterChain.doFilter(request, response);
    }
}
