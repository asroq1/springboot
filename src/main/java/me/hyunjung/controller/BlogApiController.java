package me.hyunjung.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.hyunjung.domain.Article;
import me.hyunjung.dto.AddArticleRequest;
import me.hyunjung.dto.ArticleResponse;
import me.hyunjung.dto.UpdateArticleRequest;
import me.hyunjung.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BlogApiController {
  private final BlogService blogService;

  @PostMapping("/api/articles")
  public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest req){
    Article saveArticle = blogService.save(req);
    return ResponseEntity.status(HttpStatus.CREATED).body(saveArticle);
  }

  @GetMapping("/api/articles")
  public ResponseEntity<List<ArticleResponse>> findAllArticles(){
    List<ArticleResponse> articles = blogService.findAll()
    .stream()
        .map(ArticleResponse::new)
        .toList();

    return ResponseEntity.ok()
        .body((articles));
  }
  @GetMapping("/api/articles/{id}")
  public ResponseEntity<ArticleResponse> findArticleById(@PathVariable Long id){
      Article article = blogService.findById(id);

      return ResponseEntity.ok()
        .body(new ArticleResponse(article));
  }

  @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody UpdateArticleRequest req){
    Article updateArticle = blogService.update(id, req);
    return ResponseEntity.ok().body(updateArticle);
  }

  @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id){
      blogService.delete(id);
      return ResponseEntity.ok().build();
  }
}
