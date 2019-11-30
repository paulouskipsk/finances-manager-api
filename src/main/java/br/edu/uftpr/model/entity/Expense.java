package br.edu.uftpr.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.edu.uftpr.model.dto.ExpenseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@AllArgsConstructor
@Data
@ToString
public class Expense {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "exp_id")
	private Long id;
	@Column(name = "exp_description", nullable = false)
	private String description;
	@Column(name = "exp_value_pay", nullable = false)
	private Double valuePay;
	@Column(name = "exp_date_pay", nullable = false)
	@Temporal(value = TemporalType.DATE)
	private Date datePay;
	@Column(name = "exp_paid", nullable = false)
	private String paid;
	
	public Expense(ExpenseDTO expense) {
		this.id = expense.getId();
		this.description = expense.getDescription();
		this.valuePay = expense.getValuePay();
		this.datePay = expense.getDatePay();
		this.paid = expense.getPaid();
	}

	public Expense() {
		this.id = null;
		this.description = "";
		this.valuePay = 0D;
		this.datePay = null;
		this.paid = "F";
	}

}
