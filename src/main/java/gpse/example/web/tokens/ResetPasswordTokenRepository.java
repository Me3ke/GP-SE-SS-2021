package gpse.example.web.tokens;

import org.springframework.data.repository.CrudRepository;

public interface ResetPasswordTokenRepository extends CrudRepository<ResetPasswordToken, Long> {
}
