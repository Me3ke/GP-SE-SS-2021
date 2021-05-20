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

    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }


    public ConfirmationToken saveConfirmationToken(ConfirmationToken confToken) {

        return confirmationTokenRepository.save(confToken);
    }

    public void deleteConfirmationToken(Long id) {

        confirmationTokenRepository.deleteById(id);
    }

    /**
     * find the correct Confirmation token if it is there.
     * @param token searched token
     * @return the token
     */

    public Optional<ConfirmationToken> findConfirmationTokenByToken(String token) {
        Iterable<ConfirmationToken> tokens = confirmationTokenRepository.findAll();

        for (ConfirmationToken t : tokens) {
            if (t.getConfirmationToken().equals(token)) {
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }

    public boolean isExpired(ConfirmationToken token) {
        return (LocalDateTime.now().isAfter(token.getCreatedDate().plusDays(1)));
    }
}
