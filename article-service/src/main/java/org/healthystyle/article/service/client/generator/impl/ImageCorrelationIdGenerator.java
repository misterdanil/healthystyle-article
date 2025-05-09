package org.healthystyle.article.service.client.generator.impl;

import org.healthystyle.article.model.Image;
import org.healthystyle.article.service.client.generator.CorrelationIdGenerator;
import org.springframework.stereotype.Component;

@Component
public class ImageCorrelationIdGenerator implements CorrelationIdGenerator<Image> {

	@Override
	public String generate(Image image) {
		return image.getId().toString();
	}

}
