package mine.testcode.domain.user;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Table(name = "user_tbl")
@Data
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;

    private String userEmail;

    private String userPassword;

    private String userRole;
}
