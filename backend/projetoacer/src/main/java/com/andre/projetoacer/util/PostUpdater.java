package com.andre.projetoacer.util;

import com.andre.projetoacer.domain.Institution;
import com.andre.projetoacer.domain.User;
import com.andre.projetoacer.services.InstitutionService;
import com.andre.projetoacer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.andre.projetoacer.domain.GenericUser;
import com.andre.projetoacer.domain.Post;


@Component
public class PostUpdater {

    @Autowired
    private UserService userService;

    @Autowired
    private InstitutionService institutionService;

	public void updateListPosts(GenericUser user, Post post) {
        user.getPosts().add(post);

        if(user instanceof User){
            userService.updateListPosts((User) user);
        }
        else{
            institutionService.updateListPosts((Institution) user);
        }
    }
}
