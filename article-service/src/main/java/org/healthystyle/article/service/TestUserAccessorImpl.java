package org.healthystyle.article.service;

import java.util.Collections;

import org.healthystyle.util.user.User;
import org.springframework.stereotype.Component;

@Component
public class TestUserAccessorImpl implements UserAccessor {

	@Override
	public User getUser() {
		return new User(1L, Collections.EMPTY_LIST);
	}

}
