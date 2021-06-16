package gpse.example.web.documents;

import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Class that wires the database to the objekts it represents.
 */
@Service
public class GuestTokenService {

    private final GuestTokenRepository guestTokenRepository;

    public GuestTokenService(GuestTokenRepository repo) {
        this.guestTokenRepository = repo;
    }

    public GuestToken saveGuestToken(final GuestToken guestToken) {
        return guestTokenRepository.save(guestToken);
    }

    public void deleteGuestToken(final long id) {
        guestTokenRepository.deleteById(id);
    }


    /**
     * finding an GuestToken entity by a specified String token.
     * @param token the String token that is needed
     * @return returns an optional GuestToken which can be empty or contains the GuestToken
     */
    public Optional<GuestToken> findGuestTokenByToken(String token) {
        final Iterable<GuestToken> guestTokens = guestTokenRepository.findAll();

        for (final GuestToken gToken : guestTokens) {
            if (gToken.getToken().equals(token)) {
                return Optional.of(gToken);
            }
        }
        return Optional.empty();
    }
}
