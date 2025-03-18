package org.healthystyle.article.model.fragment.text.part;

import org.healthystyle.article.model.fragment.text.Text;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cursive_part")
public class CursivePart extends TextPart {

	public CursivePart() {
		super();
	}

	public CursivePart(Integer order, Text text, String value) {
		super(order, text, value);
	}

}
