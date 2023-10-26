package com.summitworks;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository ur;
	
	static ExecutorService exeServ = Executors.newSingleThreadExecutor();
	
	public User checkCredentials(String email, String Password) {
		User user = ur.getUserByLogin(email, Password);
		if(user != null) {
			String token = createToken();
			ur.setUserToken(email, token);
			user.setAccessToken(token);
			exeServ.submit(()->{
				try {
					TimeUnit.MINUTES.sleep(60);
					ur.setUserToken(email, null);
					
				}catch(InterruptedException e) {
				e.printStackTrace();
				}
			});
		}
		return user;
		
	}
	
	public String createToken() {
		int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 20;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	    
	    return generatedString;
	}
	
	public User checkToken(String token) {
		return ur.getUserByToken(token);
	}
	
	public void insert(User user) {
		ur.save(user);
	}
}
