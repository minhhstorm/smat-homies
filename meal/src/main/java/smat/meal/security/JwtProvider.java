package smat.meal.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import smat.meal.exception.SmatException;
import smat.meal.service.UserDetailsImpl;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {
	private KeyStore keyStore;
	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	@PostConstruct
	public void init() {
		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
			keyStore.load(resourceAsStream, "secret".toCharArray());
		} catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
			throw new SmatException("Exception occurred while loading keystore");
		}

	}

	public String generateToken(Authentication authentication) {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//		org.springframework.security.core.userdetails.User principal = (User) authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.signWith(getPrivateKey())
				.compact();
	}

	private PrivateKey getPrivateKey() {
		try {
			return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
		} catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
			throw new SmatException("Exception occured while retrieving public key from keystore");
		}
	}

	public String getUserNameFromJwtToken(String token) {
		return  Jwts.parser().setSigningKey(getPrivateKey()).parseClaimsJwt(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String token) {
		try {
			Jwts.parser().setSigningKey(getPrivateKey()).parseClaimsJwt(token);
			return true;
		} catch (SignatureException | ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException exception) {
			logger.error(exception.getMessage());
		}
		return false;
	}
}
