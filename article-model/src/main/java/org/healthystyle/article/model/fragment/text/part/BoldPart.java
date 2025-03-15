package org.healthystyle.article.model.fragment.text.part;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bold_part")
@DiscriminatorValue("bold")
public class BoldPart extends TextPart {

}
