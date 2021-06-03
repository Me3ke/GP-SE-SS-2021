package gpse.example.domain.users;


import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


/**
 * standard confirmation token service class.
 */
@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationTokenService(final ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }


    public ConfirmationToken saveConfirmationToken(final ConfirmationToken confToken) {

        return confirmationTokenRepository.save(confToken);
    }

    public void deleteConfirmationToken(final Long id) {

        confirmationTokenRepository.deleteById(id);
    }

    /**
     * find the correct Confirmation token if it is there.
     * @param givenToken searched token
     * @return the token
     */

    public Optional<ConfirmationToken> findConfirmationTokenByToken(final String givenToken) {
        final Iterable<ConfirmationToken> tokens = confirmationTokenRepository.findAll();

        for (final ConfirmationToken token : tokens) {
            if (token.getToken().equals(givenToken)) {
                return Optional.of(token);
            }
        }
        return Optional.empty();
    }

    public boolean isExpired(final ConfirmationToken token) {
        return LocalDateTime.now().isAfter(token.getCreatedDate().plusDays(1));
    }
}
