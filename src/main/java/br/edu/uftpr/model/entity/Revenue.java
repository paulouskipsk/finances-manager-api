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

import br.edu.uftpr.model.dto.RevenueDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "revenues")
@Getter
@Setter
@Data
@ToString
public class Revenue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rev_id", nullable = false)
	private Long id;
	@Column(name = "rev_description", nullable = false)
	private String description;
	@Column(name = "rev_value_receiveble", nullable = false)
	private Double valueReceiveble;
	@Column(name = "rev_date_receiveble", nullable = false)
	@Temporal(value = TemporalType.DATE)
	private Date dateReceiveble;
	@Column(name = "rev_receivebled", nullable = false)
	private String receivebled;

	private Date created;
	private Date updated;

	public Revenue(RevenueDTO revenue) {
		this.id = revenue.getId();
		this.description = revenue.getDescription();
		this.valueReceiveble = revenue.getValueReceiveble();
		this.dateReceiveble = revenue.getDateReceiveble();
		this.receivebled = revenue.getReceivebled();
		this.created = revenue.getCreated();
		this.updated = revenue.getUpdated();
	}

	public Revenue(Long id, String description, Double valueReceiveble, Date dateReceiveble, String receivebled) {
		this.id = id;
		this.description = description;
		this.valueReceiveble = valueReceiveble;
		this.dateReceiveble = dateReceiveble;
		this.receivebled = receivebled;
	}

	public Revenue() {
		this.id = null;
		this.description = "";
		this.valueReceiveble = 0D;
		this.dateReceiveble = null;
		this.receivebled = "F";
	}

	public List<Revenue> mapAll(List<RevenueDTO> revenueDTOs) {
		List<Revenue> revenues = new ArrayList<Revenue>();
		for (RevenueDTO revenueDTO : revenueDTOs) {
			revenues.add(new Revenue(revenueDTO));
		}
		return revenues;
	}
	
	public void update(RevenueDTO revenue) {
		this.id = revenue.getId();
		this.description = revenue.getDescription();
		this.valueReceiveble = revenue.getValueReceiveble();
		this.dateReceiveble = revenue.getDateReceiveble();
		this.receivebled = revenue.getReceivebled();
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
