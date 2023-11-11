package com.crm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.crm.dao.UserRepository;
import com.crm.entities.CUser;

public class UserDetailsServicesImpl implements UserDetailsService{

	/*Step:2
	 * 1. Provide the Implementation of User Details {Interface} implement Class
	 * Name CustomUserDetails.[To Login with Database UserName or email, UserDetails
	 * interface having usefull Method which help to do so.] 
	 * 2. Provide the
	 * Implementation of UserDetailsService {Interface} Implement Class
	 * UserDetailsServiceImpl. [UserDetailsServiceImpl provide useful services to
	 * hold and get database user with DAO ] 
	 * 3. Write Security Configuration class
	 * with all Configuration, A simple class that extends
	 * WebSecurityConfigurationAdapter. [To initialised Security and provide the our
	 * User Name Password Login Authentication.]
	 */
	

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Fetching User From Database
//		CUser user = userRepository.getUserByUserName(username);
	CUser user =userRepository.getUserByUserName(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("Could not Found user !!");
		}
		CustomUserDetails customUserDetails=new CustomUserDetails(user);
		return customUserDetails;
	}

}
