package org.healthystyle.article.service.mark.impl;

import java.util.HashMap;

import org.healthystyle.article.model.mark.Mark;
import org.healthystyle.article.repository.mark.MarkRepository;
import org.healthystyle.article.service.dto.mark.MarkSaveRequest;
import org.healthystyle.article.service.error.mark.MarkNotFoundException;
import org.healthystyle.article.service.mark.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@Service
public class MarkServiceImpl implements MarkService {
	@Autowired
	private MarkRepository markRepository;

	@Override
	public Mark findByValue(Integer value) throws MarkNotFoundException {
		Mark mark = markRepository.findByValue(value);
		if (mark == null) {
			BindingResult result = new MapBindingResult(new HashMap<>(), "mark");
			result.reject("mark.find.value.not_found", "Не удалось найти оценку по данному значению");
			throw new MarkNotFoundException(value, result);
		}

		return mark;
	}

	@Override
	public Mark save(Integer value) {
		return markRepository.save(new Mark(value));
	}

}
