package io.github.julianobrl.ctos.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Users")
public class Users {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private String fullname;
	
	private String username;
	private String password;
	private boolean admin = false;
	private Date createdOn;
	private boolean isOauthAccount = false;
	private boolean deleted = false;
	private LocalDateTime credentialsExpiryDate;
	private boolean isAccountExpired = false;
	private boolean isAccountLocked = false;
	private boolean isReadOnlyUser= false;
	
	@Override
	public String toString() {
		if(admin) {
			return "User [id=" + id + ", uname=" + username + ", Admin User]";
		} else {
			return "User [id=" + id + ", uname=" + username + "]";
		}
	}

}
