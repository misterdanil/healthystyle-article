package org.healthystyle.article.service.client.impl;

import org.healthystyle.article.service.client.FileClient;
import org.healthystyle.article.service.config.RabbitConfig;
import org.healthystyle.article.service.dto.FileSaveRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileClientImpl implements FileClient<Void> {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public static final Logger LOG = LoggerFactory.getLogger(FileClientImpl.class);

	@Override
	public Void save(FileSaveRequest saveRequest) {
		rabbitTemplate.convertAndSend(RabbitConfig.FILESYSTEM_DIRECT_EXCHANGE, "file.upload", saveRequest, msg -> {
			msg.getMessageProperties().setCorrelationId(saveRequest.getCorrelationId());
			msg.getMessageProperties().setReplyTo(saveRequest.getReplyTo());
			return msg;
		});

		return null;
	}

}
