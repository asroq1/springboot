package me.hyunjung.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.hyunjung.domain.Article;
import me.hyunjung.dto.AddArticleRequest;
import me.hyunjung.repository.BlogRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BlogService {
  private final BlogRepository blogRepository;

  public Article save(AddArticleRequest req){
    return  blogRepository.save(req.toEntity());
  }

  public List<Article> findAll() {
    return blogRepository.findAll();
  }

}
