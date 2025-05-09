package org.healthystyle.article.web;

import org.healthystyle.article.model.Image;
import org.healthystyle.article.service.ImageService;
import org.healthystyle.article.service.config.RabbitConfig;
import org.healthystyle.article.service.dto.ImageUpdateRequest;
import org.healthystyle.article.service.error.ImageNotFoundException;
import org.healthystyle.util.error.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {
	@Autowired
	private ImageService imageService;

	private static final Logger LOG = LoggerFactory.getLogger(ImageController.class);

	@RabbitListener(queues = RabbitConfig.IMAGE_CREATED_QUEUE)
	public void getFileResult(Long id, MessageProperties props) {
		LOG.debug("Got file saving result: {}", id);

		Long imageId = Long.valueOf(props.getCorrelationId());
		ImageUpdateRequest updateRequest = new ImageUpdateRequest();
		updateRequest.setImageId(id);

		try {
			imageService.update(updateRequest, imageId);
		} catch (ValidationException e) {
			LOG.warn("Couldn't update image. It's invalid", e);
		} catch (ImageNotFoundException e) {
			LOG.warn("Got image file saving result with correlation id which not exists: " + imageId, e);
		}
	}
}
