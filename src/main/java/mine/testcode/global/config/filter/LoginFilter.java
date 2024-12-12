package mine.testcode.global.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.testcode.domain.user.CustomUserDetails;
import mine.testcode.global.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Map<String, String> loginRequest;

        try {
            loginRequest = objectMapper.readValue(request.getInputStream(), Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String userEmail = loginRequest.get("userEmail");
        String userPassword = loginRequest.get("userPassword");

        if (userEmail == null || userPassword == null) {
            throw new RuntimeException();
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userEmail, userPassword);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        log.info("LoginFilter successfulAuthentication");

        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();

        // 반환할 사용자 정보 추출 후 정리
        String userName = customUserDetails.getUsername();
        String userEmail = customUserDetails.getUserEmail();
        String userRole = customUserDetails.getUserRole();

        String token = jwtUtil.createJwtAuthorizationToken(userEmail, userRole);

        // 응답 헤더에 WJT 토큰 추가
        response.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(objectMapper.writeValueAsString("Login Unsuccessful"));
    }
}
