package colruyt.pcrs.security;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.crypto.KeyGenerator;
import javax.ejb.Stateless;

import colruyt.pcrsejb.bo.user.UserBo;
import colruyt.pcrsejb.converter.user.UserConverter;
import io.jsonwebtoken.Jwts;


@Stateless
public class TokenFacade {

	private UserConverter userconvert = new UserConverter();
	private SecureRandom random = new SecureRandom();
	private TokenDlService dl = new TokenDlService();
	
	
    private KeyGenerator keyGenerator;
	
	
	public Token issueToken(UserBo u) {
		
		Token to =  new Token(this.generateToken(u.getEmail()), LocalDate.now(), userconvert.convertToEntity(u));
		this.dl.save(to);
		
		return to;
		
		
	}
    
    @PostConstruct
    public void init() {
    	
    	try {
			keyGenerator = KeyGenerator.getInstance("DES");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    }
	
	
	
	
	private String generateToken(String user) {
		
		  Key key = keyGenerator.generateKey();
		  String jwtToken = Jwts.builder()
	                .setSubject(user)
	               
	                .setIssuedAt(new Date())
	                .setExpiration(new Date((LocalDateTime.now().plusMinutes(60).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())))
	                .signWith(key)
	                .compact();
	        return jwtToken;
	   
	}
	
	public UserBo getUserByToken(String token) {
			throw new IllegalAccessError();
	}
	
	
}
