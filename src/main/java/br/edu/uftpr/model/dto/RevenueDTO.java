package br.edu.uftpr.model.dto;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import br.edu.uftpr.model.entity.Revenue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@Data
@ToString
public class RevenueDTO {
	private Long id;

	@NotEmpty(message = "A descrição não pode ser vazia")
	@Pattern(regexp = "^(\\s?[A-ZÀ-Ú][a-zà-ú][0-9]*)+(\\s[a-zà-ú][0-9]*)+", message = "Insira a descrição iniciando com letra maíuscula.")
	@Length(min = 3, max = 100, message = "A descrição dever ter no mínimo 3 e máximo 100 caracteres.")
	private String description;

	@NotEmpty(message = "O Valor a receber deve ser informado")
	@Min(value = (long) 0.1, message = "Valor deve ser informado")
	private Double valueReceiveble;

	@NotEmpty(message = "A Data não pode ser vazia")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dateReceiveble;

	@NotEmpty(message = "O Status e pagamento não pode ser vazio")
	@Pattern(regexp = "^([TF])$", message = "O Status e pagamento deve ser 'T' ou 'F' com letra maíuscula.")
	private String receivebled;

	public RevenueDTO(Revenue revenue) {
		this.id = revenue.getId();
		this.description = revenue.getDescription();
		this.valueReceiveble = revenue.getValueReceiveble();
		this.dateReceiveble = revenue.getDateReceiveble();
		this.receivebled = revenue.getReceivebled();
	}

	public RevenueDTO() {
		this.id = null;
		this.description = "";
		this.valueReceiveble = 0D;
		this.dateReceiveble = null;
		this.receivebled = "F";
	}
}
