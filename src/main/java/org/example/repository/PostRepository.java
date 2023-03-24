package org.example.repository;

import org.example.exception.NotFoundException;
import org.example.model.Post;



import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

// Stub

public class PostRepository {
    CopyOnWriteArraySet<Long> removed = new CopyOnWriteArraySet<>();
    AtomicLong generateIndex = new AtomicLong(1);
    ConcurrentHashMap<Long, Post> listPost = new ConcurrentHashMap<>();

    public List<Post> all() {
        return new ArrayList<>(listPost.values()).stream()
                .filter(o -> !removed.contains(o.getId()))
                .collect(Collectors.toList());
    }
    public Optional<Post> getById(long id) {
        if(removed.contains(id)) throw new NotFoundException();
        return Optional.of(listPost.get(id));
    }
    public Post save(Post post) {
        if(removed.contains(post.getId())){
            throw new NotFoundException();
        }
        if(post.getId()==0){
            post.setId(generateIndex.getAndIncrement());
        }
        listPost.put(post.getId(), post);
        return post;
    }
    public void removeById(long id) {
        removed.add(id);
    }
}
