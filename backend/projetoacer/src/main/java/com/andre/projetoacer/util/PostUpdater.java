package com.andre.projetoacer.util;

import org.springframework.stereotype.Component;

import com.andre.projetoacer.domain.GenericUser;
import com.andre.projetoacer.domain.Post;

@Component
public class PostUpdater {
	public void updateListPosts(GenericUser user, Post post) {
		user.getPosts().add(post);
	}
}
