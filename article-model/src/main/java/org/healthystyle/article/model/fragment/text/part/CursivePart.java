package org.healthystyle.article.model.fragment.text.part;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cursive_part")
@DiscriminatorValue("cursive")
public class CursivePart extends TextPart {

}
