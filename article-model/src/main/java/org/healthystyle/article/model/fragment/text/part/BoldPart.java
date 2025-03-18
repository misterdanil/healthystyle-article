package org.healthystyle.article.model.fragment.text.part;

import org.healthystyle.article.model.fragment.text.Text;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bold_part")
public class BoldPart extends TextPart {

	public BoldPart() {
		super();
	}

	public BoldPart(Integer order, Text text, String value) {
		super(order, text, value);
	}

}
