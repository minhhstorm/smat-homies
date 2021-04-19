package smat.meal.service;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import smat.meal.entity.UserEntity;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final Long id;
	private final String username;
	private final String email;

	@JsonIgnore
	private final String password;

	private final String address;
	private final Date birthday;
	private Instant created;
	private boolean enable;
	private String name;

	private final Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long id, String username, String email, String password, String address, Date birthday,
			String name, boolean enable, Instant created, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.address = address;
		this.birthday = birthday;
		this.enable = enable;
		this.name = name;
		this.created = created;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(UserEntity userEntity) {
		List<GrantedAuthority> authorities = userEntity.getRoles().stream()
				.map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName().name()))
				.collect(Collectors.toList());
		return new UserDetailsImpl(userEntity.getId(), userEntity.getUsername(), userEntity.getEmail(),
				userEntity.getPassword(), userEntity.getAddress(), userEntity.getBirthday(), userEntity.getName(),
				userEntity.isEnabled(), userEntity.getCreated(), authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
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

	public boolean equals(Object object) {
	    if (this == object)
	        return true;
	    if (object == null || getClass() != object.getClass())
            return false;
	    UserDetailsImpl user = (UserDetailsImpl) object;
	    return Objects.equals(id, user.id);
	}
}
