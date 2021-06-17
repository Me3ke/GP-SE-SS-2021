package gpse.example.web.tokens;

import org.springframework.data.repository.CrudRepository;

/**
 * Repository for ResetPasswordToken.
 */
public interface ResetPasswordTokenRepository extends CrudRepository<ResetPasswordToken, Long> {
}
