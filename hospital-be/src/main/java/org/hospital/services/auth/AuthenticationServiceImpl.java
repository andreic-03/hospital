package org.hospital.services.auth;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hospital.api.model.AuthRequestModel;
import org.hospital.api.model.AuthResponseModel;
import org.hospital.errorhandling.AuthenticationException;
import org.hospital.errorhandling.Errors;
import org.hospital.mappers.AuthMapper;
import org.hospital.mappers.TokenMapper;
import org.hospital.persistence.repository.TokenRepository;
import org.hospital.security.JwtTokenUtil;
import org.hospital.security.model.AppUserPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	/** Logger available to subclasses. */
	protected final Log logger = LogFactory.getLog(getClass());
	private final TokenMapper tokenMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
	private final TokenRepository tokenRepository;
	private final AuthMapper authMapper;

	@Transactional
	@Override
    public AuthResponseModel login(AuthRequestModel authRequestModel) {
		final Authentication authentication;

		try {
			authentication =
					authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(authRequestModel.getUsername(), authRequestModel.getPassword())
					);
		} catch (BadCredentialsException | LockedException ex) {
			logger.error(ex.getMessage());
			throw new AuthenticationException(Errors.Functional.UNAUTHENTICATED);
		}

		final var user = (AppUserPrincipal) authentication.getPrincipal();

        final var accessToken = jwtTokenUtil.generateToken(user);

		tokenRepository.save(tokenMapper.toTokenEntity(accessToken, user.userEntity()));
		return authMapper.toAuthResponseModel(user.getUsername(), accessToken);
    }

	@Transactional
	@Override
	public void logout(String jwtToken) {
		tokenRepository.deleteByToken(jwtToken);
	}

}
