package com.crm.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.crm.entities.CUser;

public class CustomUserDetails implements UserDetails {

	private CUser user;
		
	public CustomUserDetails(CUser user) {
		super();
		this.user=user;
	}

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getRole());
		return List.of(simpleGrantedAuthority);
	}

	@Override
	public String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
			return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	/*Step:1
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
	
}
