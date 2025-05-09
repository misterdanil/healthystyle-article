package org.healthystyle.article.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.healthystyle.article.model.Article;
import org.healthystyle.article.service.ArticleService;
import org.healthystyle.article.service.ViewService;
import org.healthystyle.article.service.dto.ArticleSaveRequest;
import org.healthystyle.article.service.dto.ArticleSort;
import org.healthystyle.article.service.dto.ImageSaveRequest;
import org.healthystyle.article.service.dto.PeriodSort;
import org.healthystyle.article.service.dto.fragment.FragmentImageSaveRequest;
import org.healthystyle.article.service.error.ArticleNotFoundException;
import org.healthystyle.article.service.error.CategoryNotFoundException;
import org.healthystyle.article.service.error.ImageNotFoundException;
import org.healthystyle.article.service.error.OrderExistException;
import org.healthystyle.article.service.error.PreviousOrderNotFoundException;
import org.healthystyle.article.service.error.fragment.FragmentExistException;
import org.healthystyle.article.service.error.fragment.FragmentNotFoundException;
import org.healthystyle.article.service.error.fragment.link.ArticleLinkExistException;
import org.healthystyle.article.service.error.fragment.roll.RollNotFoundException;
import org.healthystyle.article.service.error.fragment.text.TextNotFoundException;
import org.healthystyle.article.web.dto.mapper.ArticleMapper;
import org.healthystyle.util.error.ErrorResponse;
import org.healthystyle.util.error.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class ArticleController {
	@Autowired
	private ArticleService service;
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private ArticleMapper articleMapper;
	@Autowired
	private ViewService viewService;

	private static final Logger LOG = LoggerFactory.getLogger(ArticleController.class);

	@PostMapping(value = "/categories/{categoryId}/article")
	public ResponseEntity<?> addArticle(@PathVariable Long categoryId,
			@RequestParam("article") String stringSaveRequest, @RequestParam(required = false) MultipartFile imageFile,
			HttpServletRequest req) throws URISyntaxException {
		LOG.debug("Got request to save article: {}", stringSaveRequest);

		ArticleSaveRequest saveRequest;
		try {
			saveRequest = mapper.readValue(stringSaveRequest, ArticleSaveRequest.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Couldn't map article json to object", e);
		}

		ImageSaveRequest image = saveRequest.getImage();
		if (image != null) {
			image.setFile(imageFile);
		}

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
		Map<String, MultipartFile> multipartFiles = multipartRequest.getFileMap();

		saveRequest.getFragments().forEach(f -> f.getOrders().forEach(o -> {
			if (o instanceof FragmentImageSaveRequest) {
				LOG.debug("Recognized fragment image: {}. Setting file", o);
				((FragmentImageSaveRequest) o).getImage().setFile(multipartFiles.get(o.getId()));
			}
		}));

		Article article;
		try {
			article = service.save(saveRequest, categoryId);
		} catch (ValidationException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse("1001", e.getGlobalErrors(), e.getFieldErrors()));
		} catch (CategoryNotFoundException | FragmentNotFoundException | RollNotFoundException
				| PreviousOrderNotFoundException | ArticleNotFoundException | ImageNotFoundException
				| TextNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("1002", e.getGlobalErrors(), e.getFieldErrors()));
		} catch (FragmentExistException | OrderExistException | ArticleLinkExistException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ErrorResponse("1003", e.getGlobalErrors(), e.getFieldErrors()));
		}

		return ResponseEntity.created(new URI("/categories/" + categoryId + "/articles/" + article.getId())).build();
	}

	@GetMapping("/articles/{id}")
	public ResponseEntity<?> getArticleById(@PathVariable Long id) {
		LOG.debug("Got request to get article by id: {}", id);

		Article article;
		try {
			article = service.findById(id);
			viewService.checkAndSave(id);
		} catch (ValidationException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse("1000", e.getGlobalErrors(), e.getFieldErrors()));
		} catch (ArticleNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("1001", e.getGlobalErrors(), e.getFieldErrors()));
		}

		return ResponseEntity.ok(articleMapper.toDto(article));
	}

	@GetMapping(value = "/articles", params = "title")
	public ResponseEntity<?> getArticleById(@RequestParam String title, @RequestParam int page,
			@RequestParam int limit) {
		LOG.debug("Got request to get article by title: {}", title);

		Page<Article> articles;
		try {
			articles = service.findByTitle(title, page, limit);
		} catch (ValidationException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse("1000", e.getGlobalErrors(), e.getFieldErrors()));
		}

		return ResponseEntity.ok(articles.map(articleMapper::toDto));
	}

	@GetMapping(value = "/articles", params = { "title", "sort", "period" })
	public ResponseEntity<?> getFilteredArticles(@RequestParam(required = false) Long categoryId,
			@RequestParam String title, @RequestParam("sort") ArticleSort articleSort,
			@RequestParam("period") PeriodSort periodSort, @RequestParam int page, @RequestParam int limit) {
		LOG.debug("Got request to get article by title: {}", title);

		Page<Article> articles;
		try {
			if (articleSort.equals(ArticleSort.MOST_MARKED)) {
				articles = service.findMostMarked(title, categoryId, null, periodSort, page, limit);
			} else if (articleSort.equals(ArticleSort.MOST_WATCHED)) {
				articles = service.findMostWatched(title, categoryId, null, periodSort, page, limit);
			} else {
				return ResponseEntity.badRequest().build();
			}
		} catch (ValidationException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse("1000", e.getGlobalErrors(), e.getFieldErrors()));
		}

		return ResponseEntity.ok(articles.map(articleMapper::toDto));
	}
}
