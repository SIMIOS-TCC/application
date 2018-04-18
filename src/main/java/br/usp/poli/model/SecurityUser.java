package br.usp.poli.model;

import java.util.Collection;
 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
 
public class SecurityUser extends User {
 
	private static final long serialVersionUID = 1L;
 
	public SecurityUser(UserModel userModel,  Collection<? extends GrantedAuthority> authorities) {		
		super(userModel.getLogin(), userModel.getPassword(), userModel.isActive(), true, true, true, authorities);
	}	
}