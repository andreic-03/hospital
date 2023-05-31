package org.patients.services;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.jsonwebtoken.JwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.patients.errorhandling.AuthenticationException;
import org.patients.errorhandling.Errors;
import org.patients.errorhandling.UncheckedException;
import org.patients.models.Credential;
import org.patients.models.dto.CredentialDTO;
import org.patients.repositories.CredentialRepository;
import org.patients.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService, UserDetailsService {

	/** Logger available to subclasses. */
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CredentialRepository credentialRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credential user = credentialRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(user.getUsername(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("user")));
    }

    public String authenticate(CredentialDTO credentialDTO) {
        authenticate(credentialDTO.getUsername(), credentialDTO.getPassword());

        final UserDetails userDetails = loadUserByUsername(credentialDTO.getUsername());

        return jwtTokenUtil.generateToken(userDetails);
    }

    @Override
    public Authentication authenticate(String token) {
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			logger.warn("Authentication was already performed through other means. Ignoring token.");
			return SecurityContextHolder.getContext().getAuthentication();
		}

		try {
			if (jwtTokenUtil.isTokenExpired(token)) {
				throw new AuthenticationException(Errors.Functional.TOKEN_EXPIRED);
			}
		} catch (JwtException jwtException) {
			Map<String, Object> context = new HashMap<>();
			context.put("detailedMessage", jwtException.getMessage());
			throw new AuthenticationException(Errors.Functional.TOKEN_INVALID, context);
		}

		UserDetails userDetails = loadUserByUsername(jwtTokenUtil.getUsernameFromToken(token));

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails.getUsername(), null, userDetails.getAuthorities());

		// After setting the Authentication in the context, we specify
		// that the current user is authenticated. So it passes the Spring Security Configurations successfully.
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

		return usernamePasswordAuthenticationToken;
    }

    @SuppressWarnings("unchecked")
	private void authenticate(String username, String password) {
    	Map<String, Object> context = new HashMap<>();
    	if (Strings.isNullOrEmpty(username)) {
    		context.put("paramNames", Lists.newArrayList("username"));
    	}
    	if (Strings.isNullOrEmpty(password)) {
    		if (context.get("paramNames") == null) {
        		context.put("paramNames", Lists.newArrayList("password"));
    		} else {
    			((List<Object>) context.get("paramNames")).add("password");
    		}
    	}
    	if (!context.isEmpty()) {
			// invalid data was provided to the application, so we can't recover from this - unchecked exception
    		throw new UncheckedException(Errors.Functional.MISSING_INPUT, context);
    	}

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException | LockedException | BadCredentialsException e) {
			// invalid data was provided to the application, so we can't recover from this - unchecked exception
            throw new AuthenticationException(Errors.Functional.WRONG_CREDENTIALS, null, e);
        }
    }

}
