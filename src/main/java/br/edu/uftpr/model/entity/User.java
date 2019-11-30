package br.edu.uftpr.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.edu.uftpr.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
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

	public User(UserDTO dto) {
		this.id = dto.getId();
		this.name = dto.getName();
		this.username = dto.getUsername();
		this.password = dto.getPassword();
	}

	public User() {
		this.id = null;
		this.name = "";
		this.username = "";
		this.password = "";
	}

}
