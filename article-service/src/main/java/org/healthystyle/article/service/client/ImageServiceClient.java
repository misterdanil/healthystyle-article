package org.healthystyle.article.service.client;

public interface ImageServiceClient {
	boolean existsById(Long imageId);
	
	void deleteById(Long id);
}
