package gpse.example.web.tokens;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service for resetPasswordTokens.
 */
@Service
public class ResetPasswordTokenService {

    private final ResetPasswordTokenRepository repo;

    public ResetPasswordTokenService(final ResetPasswordTokenRepository repo) {
        this.repo = repo;
    }

    public ResetPasswordToken saveResetPasswordToken(final ResetPasswordToken rpt) {
        return repo.save(rpt);
    }

    public void deleteResetPasswordToken(final long id) {
        repo.deleteById(id);
    }


    /**
     * Method to find one token not by an String token not by id.
     * @param givenToken the String token
     * @return the Optional Token
     */
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
