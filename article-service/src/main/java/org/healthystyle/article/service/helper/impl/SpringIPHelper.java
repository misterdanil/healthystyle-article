package org.healthystyle.article.service.helper.impl;

import org.healthystyle.article.service.helper.IPHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class SpringIPHelper implements IPHelper {

	@Override
	public String getIP() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		return request.getRemoteAddr();
	}

}
