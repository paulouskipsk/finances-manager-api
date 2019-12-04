package br.edu.uftpr.model.dto;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import br.edu.uftpr.model.entity.Expense;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class ExpenseDTO {
	private Long id;

	@NotEmpty(message = "A descrição não pode ser vazia")
	@Pattern(regexp = "^(\\s?[A-ZÀ-Ú][a-zà-ú][0-9]*)+(\\s[a-zà-ú][0-9]*)+", message = "Insira a descrição iniciando com letra maíuscula.")
	@Length(min = 3, max = 100, message = "A descrição dever ter no mínimo 3 e máximo 100 caracteres.")
	private String description;

	@NotEmpty(message = "O Valor a pagar deve ser informado")
	@Min(value = (long) 0.1, message = "Valor a pagar deve ser informado")
	private Double valuePay;

	@NotEmpty(message = "A Data não pode ser vazia")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date datePay;

	@NotEmpty(message = "O Status e pagamento não pode ser vazio")
	@Pattern(regexp = "^([TF])$", message = "O Status e pagamento deve ser 'T' ou 'F' com letra maíuscula.")
	private String paid;

	private Date created;
	private Date updated;

	public ExpenseDTO(Expense expense) {
		this.id = expense.getId();
		this.description = expense.getDescription();
		this.valuePay = expense.getValuePay();
		this.datePay = expense.getDatePay();
		this.paid = expense.getPaid();
		this.created = expense.getCreated();
		this.updated = expense.getUpdated();
	}

	public ExpenseDTO(Long id, String description, Double valuePay, Date datePay, String paid) {
		this.id = id;
		this.description = description;
		this.valuePay = valuePay;
		this.datePay = datePay;
		this.paid = paid;
	}

	public ExpenseDTO() {
		this.id = null;
		this.description = "";
		this.valuePay = 0D;
		this.datePay = null;
		this.paid = "F";
	}

}
