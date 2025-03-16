package org.healthystyle.article.model.fragment.text.part;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "link_part")
public class LinkPart {
	@Column(nullable = false)
	private String source;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
