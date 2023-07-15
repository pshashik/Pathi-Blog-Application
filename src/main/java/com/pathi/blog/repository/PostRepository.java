package com.pathi.blog.repository;

import com.pathi.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// No need to annotate with @Repository as JPA Repository interface has been implement with simpleJpaReposiory with @Repository and @Transactional(
//    readOnly = true
//)
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategoryId(Long id);

}
