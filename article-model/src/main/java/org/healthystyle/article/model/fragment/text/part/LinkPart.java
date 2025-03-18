package org.healthystyle.article.model.fragment.text.part;

import org.healthystyle.article.model.fragment.text.Text;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "link_part")
public class LinkPart extends TextPart {
	@Column(nullable = false)
	private String source;

	public LinkPart() {
		super();
	}

	public LinkPart(Integer order, Text text, String value, String source) {
		super(order, text, value);
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
