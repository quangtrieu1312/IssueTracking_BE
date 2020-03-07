package com.trieutruong.webpage.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.trieutruong.webpage.constant.DomainName;

@Document(collection = DomainName.USER)
public class User implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1220437047046216999L;

	private String userId;

	private String email;
	private String username;
	private String password;
	private boolean enabled;

	private List<String> roles;

	public User(String userId, String email, String username, String password, List<String> roles) {
		this.userId = userId;
		this.email = email;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String user_account_id) {
		this.userId = user_account_id;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public void addRole(String role) {
		roles.add(role);
	}

	public void addRoles(List<String> roles) {
		for (String it : roles)
			this.addRole(it);
	}

	public String getRolesInOneStringWithComma() {
		String rolesInOneString = new String();
		rolesInOneString = roles.get(0);
		for (int i = 1; i < roles.size(); i++)
			rolesInOneString += ", " + roles.get(i);
		return rolesInOneString;
	}

	public String getRolesInOneStringWithSpace() {
		String rolesInOneString = new String();
		rolesInOneString = roles.get(0);
		for (int i = 1; i < roles.size(); i++)
			rolesInOneString += " " + roles.get(i);
		return rolesInOneString;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.commaSeparatedStringToAuthorityList(this.getRolesInOneStringWithComma());
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
