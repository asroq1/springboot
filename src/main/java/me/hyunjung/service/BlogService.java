package me.hyunjung.service;

import java.util.List;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.hyunjung.domain.Article;
import me.hyunjung.dto.AddArticleRequest;
import me.hyunjung.dto.UpdateArticleRequest;
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

  public Article findById(Long id){
    return blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No article found with id: " + id));
  }

  @Transactional
  public Article update(long id, UpdateArticleRequest req) {
    Article article = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(" not found : " + id));

    article.update(req.getTitle(),  req.getContent());

    return article;
  }

  @Transactional
    public  void delete(long id) {
       blogRepository.deleteById(id);
    }
}
