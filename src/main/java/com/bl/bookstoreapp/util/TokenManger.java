package com.bl.bookstoreapp.util;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.bl.bookstoreapp.exception.CustomerExceptions;

@Component
public class TokenManger implements Serializable {
	@Value("${secret}")
	private String jwtSecret;

	public String createToken(String email) {
		try {
			// to set algorithm
			Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

			String token = JWT.create().withClaim("email", email).sign(algorithm);
			System.out.println("token encode " + token);
			return token;
		} catch (Exception exception) {
			throw new CustomerExceptions("not valid");

		}
	}

	public String decodeToken(String token) {
		String emailId;
		Verification verification = null;
		// for verification algorithm

		try {
			verification = JWT.require(Algorithm.HMAC256(jwtSecret));
			JWTVerifier jwtverifier = verification.build();
			// to decode token
			DecodedJWT decodedjwt = jwtverifier.verify(token);
			// retrive data
			Claim claim = decodedjwt.getClaim("email");
			emailId = claim.asString();
		} catch (Exception e) {

			throw new CustomerExceptions("Invalid token");
		} 
		System.out.println("email decoded " + emailId);
		return emailId;

	}

}
