package br.edu.uftpr.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import br.edu.uftpr.model.dto.UserDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@Data
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usr_id")
	private Long id;
	@Column(name = "usr_name", nullable = false)
	private String name;
	@Column(name = "usr_username", nullable = false, unique = true, updatable = false)
	private String username;
	@Column(name = "usr_password", nullable = false)
	private String password;
	
	private Date created;
    private Date updated;

	public User(UserDTO dto) {
		this.id = dto.getId();
		this.name = dto.getName();
		this.username = dto.getUsername();
		this.password = dto.getPassword();
	}
	
	public User(Long id, String name, String username, String password) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
	}

	public User() {
		this.id = null;
		this.name = "";
		this.username = "";
		this.password = "";
	}
		
	public void update(UserDTO dto) {
        this.name = dto.getName();
        this.username = dto.getUsername();
        this.password = dto.getPassword();
    }
	
	public List<User> mapAll(List<UserDTO> userDTOs) {
		List<User> users = new ArrayList<User>();
		for(UserDTO userDTO : userDTOs ) {
			users.add(new User(userDTO));
		}
		return users;
	}

    @PreUpdate
    public void onUpdate() {
        this.updated = new Date();
    }

    @PrePersist
    public void onSave() {
        final Date now = new Date();
        this.created = now;
        this.updated = now;
    }
}
