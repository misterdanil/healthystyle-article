package org.healthystyle.article.service.client.generator;

public interface CorrelationIdGenerator<T> {
	String generate(T identifier);
}
