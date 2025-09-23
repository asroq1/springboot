package me.hyunjung.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.hyunjung.domain.Article;
import me.hyunjung.dto.AddArticleRequest;
import me.hyunjung.dto.ArticleResponse;
import me.hyunjung.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BlogApiController {
  private final BlogService blogService;

  @PostMapping("/api/articles")
  public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest req){
    Article saveArticle = blogService.save(req);
    return ResponseEntity.status(HttpStatus.CREATED).body(saveArticle);
  }

  @GetMapping("api/articles")
  public ResponseEntity<List<ArticleResponse>> findAllArticles(){
    List<ArticleResponse> articles = blogService.findAll()
    .stream()
        .map(ArticleResponse::new)
        .toList();

    return ResponseEntity.ok()
        .body((articles));
  }
}
