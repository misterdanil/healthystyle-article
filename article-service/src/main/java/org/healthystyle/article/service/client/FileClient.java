package org.healthystyle.article.service.client;

import org.healthystyle.article.service.dto.FileSaveRequest;

public interface FileClient<T> {
	T save(FileSaveRequest saveRequest);
}
