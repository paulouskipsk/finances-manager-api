package br.edu.uftpr.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import br.edu.uftpr.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
	private Long id;
	
	@NotEmpty(message = "O nome não pode ser vazio")
    @Pattern(regexp = "^(\\s?[A-ZÀ-Ú][a-zà-ú]*)+(\\s[a-zà-ú]*)?(\\s[A-ZÀ-Ú][a-zà-ú]*)",
            message = "Insira o seu nome completo iniciando com letras maíusculas.")
	@Length(min = 3, max = 100, message = "A senha dever ter entre mínimo 3 e máximo 100 caracteres.")
	private String name;
	
	@NotEmpty(message = "O usuário não pode ser vazio")
    @Pattern(regexp = "^(\\s?[a-z0-9]*)",
            message = "Insira o seu usuário todo em minúsculo podendo utilizar números e letras sem acentuação.")
	@Length(min = 3, max = 30, message = "A senha dever ter entre mínimo 3 e máximo 30 caracteres.")
	private String username;
	
	@NotEmpty(message = "A Senha não pode ser vazia")
    @Pattern(regexp = "^(\\s?[a-z0-9]*)",
            message = "Insira sua senha toda em minúscula podendo utilizar números e letras sem acentuação.")
	@Length(min = 3, max = 100, message = "A senha dever ter no mínimo 3 e máximo 100 caracteres.")
	private String password;
	
	private Date created;
    private Date updated;
	
	public UserDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.created = user.getCreated();
		this.updated = user.getUpdated();
	}

	public UserDTO() {
		this.id = null;
		this.name = "";
		this.username = "";
		this.password = "";
	}
	
	public List<UserDTO> mapAll(List<User> users) {
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		for(User user : users ) {
			userDTOs.add(new UserDTO(user));
		}
		
		return userDTOs;
	}
}
