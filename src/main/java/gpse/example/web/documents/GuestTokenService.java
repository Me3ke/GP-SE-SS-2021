package gpse.example.web.documents;

import org.springframework.stereotype.Service;

import java.util.Optional;

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
