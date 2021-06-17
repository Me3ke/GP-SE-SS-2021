package gpse.example.web.tokens;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ResetPasswordTokenService {

    private final ResetPasswordTokenRepository repo;

    public ResetPasswordTokenService(ResetPasswordTokenRepository repo) {
        this.repo = repo;
    }

    public ResetPasswordToken saveResetPasswordToken(ResetPasswordToken rpt) {
        return repo.save(rpt);
    }

    public void deleteResetPasswordToken (final long id) {
        repo.deleteById(id);
    }

    public Optional<ResetPasswordToken> findResetPasswordTokenByToken(final String givenToken) {
        final Iterable<ResetPasswordToken> tokens = repo.findAll();

        for (final ResetPasswordToken token : tokens) {
            if (token.getToken().equals(givenToken)) {
                return Optional.of(token);
            }
        }
        return Optional.empty();
    }


    public boolean isExpired(final ResetPasswordToken token) {
        return LocalDateTime.now().isAfter(token.getCreatedDate().plusDays(1));
    }
}
