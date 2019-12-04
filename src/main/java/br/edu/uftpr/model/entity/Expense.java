package br.edu.uftpr.model.entity;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.edu.uftpr.model.dto.ExpenseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "expenses")
@Getter
@Setter
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
	
	private Date created;
	private Date updated;
	
	public Expense(ExpenseDTO expense) {
		this.id = expense.getId();
		this.description = expense.getDescription();
		this.valuePay = expense.getValuePay();
		this.datePay = expense.getDatePay();
		this.paid = expense.getPaid();
	}
	
	public Expense(Long id, String description, Double valuePay, Date datePay, String paid, Date created,
			Date updated) {
		super();
		this.id = id;
		this.description = description;
		this.valuePay = valuePay;
		this.datePay = datePay;
		this.paid = paid;
		this.created = created;
		this.updated = updated;
	}

	public Expense() {
		this.id = null;
		this.description = "";
		this.valuePay = 0D;
		this.datePay = null;
		this.paid = "F";
	}
	
	public List<Expense> mapAll(List<ExpenseDTO> expenseDTOs) {
		List<Expense> expenses = new ArrayList<Expense>();
		for (ExpenseDTO expenseDTO : expenseDTOs) {
			expenses.add(new Expense(expenseDTO));
		}
		return expenses;
	}
	
	public void Expense(ExpenseDTO expense) {
		this.id = expense.getId();
		this.description = expense.getDescription();
		this.valuePay = expense.getValuePay();
		this.datePay = expense.getDatePay();
		this.paid = expense.getPaid();
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
