package org.healthystyle.article.service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.Jackson2XmlMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class RabbitConfig {

	public static final String FILESYSTEM_DIRECT_EXCHANGE = "filesystem-direct-exchange";
	public static final String FILESYSTEM_UPLOAD_ROUTING_KEY = "file.upload";

	public static final String IMAGE_CREATED_ROUTING_KEY = "article.image.created";
	public static final String IMAGE_CREATED_QUEUE = "article-image-created-queue";
	public static final String FRAGMENT_IMAGE_CREATED_ROUTING_KEY = "article.fragment.image.created";
	public static final String FRAGMENT_IMAGE_CREATED_QUEUE = "article-fragment-image-created-queue";

	@Bean
	public Jackson2JsonMessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public DirectExchange fileSystemDirectExchange() {
		DirectExchange exchange = new DirectExchange(FILESYSTEM_DIRECT_EXCHANGE);
		exchange.setShouldDeclare(false);
		return exchange;
	}

	@Bean(name = IMAGE_CREATED_QUEUE)
	public Queue articleImageQueue() {
		return new Queue(IMAGE_CREATED_QUEUE);
	}

	@Bean(name = IMAGE_CREATED_ROUTING_KEY)
	public Binding imageBinding() {
		return BindingBuilder.bind(articleImageQueue()).to(fileSystemDirectExchange()).with(IMAGE_CREATED_ROUTING_KEY);
	}

	@Bean(name = FRAGMENT_IMAGE_CREATED_QUEUE)
	public Queue articleFragmentImageQueue() {
		return new Queue(FRAGMENT_IMAGE_CREATED_QUEUE);
	}

}
