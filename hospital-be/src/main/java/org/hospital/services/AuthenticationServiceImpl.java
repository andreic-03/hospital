package org.hospital.services;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hospital.api.model.AuthRequestModel;
import org.hospital.api.model.AuthResponseModel;
import org.hospital.errorhandling.AuthenticationException;
import org.hospital.errorhandling.Errors;
import org.hospital.errorhandling.UncheckedException;
import org.hospital.mappers.AuthMapper;
import org.hospital.mappers.TokenMapper;
import org.hospital.mappers.UserMapper;
import org.hospital.persistence.entity.UserEntity;
import org.hospital.persistence.repository.TokenRepository;
import org.hospital.persistence.repository.UserRepository;
import org.hospital.security.JwtTokenUtil;
import org.hospital.security.model.AppUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService, UserDetailsService {

	/** Logger available to subclasses. */
	protected final Log logger = LogFactory.getLog(getClass());
	private static final TokenMapper tokenMapper = TokenMapper.INSTANCE;
	private static final AuthMapper authMapper = AuthMapper.INSTANCE;

	@Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserRepository userRepository;
	@Autowired
	private TokenRepository tokenRepository;


	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> userEntity = userRepository.findByUsername(username);

		return userEntity
				.map(AppUserPrincipal::new)
				.orElseThrow(() -> new UsernameNotFoundException(username));
	}

	@Transactional
	@Override
    public AuthResponseModel authenticate(AuthRequestModel authRequestModel) {
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
