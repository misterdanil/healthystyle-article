package org.healthystyle.article.service.mark;

import org.healthystyle.article.model.mark.Mark;
import org.healthystyle.article.service.dto.mark.MarkSaveRequest;
import org.healthystyle.article.service.error.mark.MarkNotFoundException;

public interface MarkService {
	Mark findByValue(Integer value) throws MarkNotFoundException;
	
	Mark save(MarkSaveRequest mark);

}
