package com.travian.task.queue.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "PREFERENCE")
@Data
public class PreferenceEntity implements Serializable{

	@EmbeddedId
    private PreferenceKey primKey;
	@Column(name="VALUE")
	private String value;
}
