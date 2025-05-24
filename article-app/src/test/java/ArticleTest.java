import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.healthystyle.article.app.Main;
import org.healthystyle.article.model.Article;
import org.healthystyle.article.model.ArticleSource;
import org.healthystyle.article.model.Category;
import org.healthystyle.article.model.Image;
import org.healthystyle.article.model.Source;
import org.healthystyle.article.model.fragment.ArticleLink;
import org.healthystyle.article.model.fragment.Fragment;
import org.healthystyle.article.model.fragment.FragmentImage;
import org.healthystyle.article.model.fragment.Quote;
import org.healthystyle.article.model.fragment.Roll;
import org.healthystyle.article.model.fragment.RollElement;
import org.healthystyle.article.model.fragment.text.Text;
import org.healthystyle.article.model.fragment.text.part.BoldPart;
import org.healthystyle.article.model.fragment.text.part.CursivePart;
import org.healthystyle.article.model.fragment.text.part.LinkPart;
import org.healthystyle.article.model.fragment.text.part.TextPart;
import org.healthystyle.article.repository.ArticleRepository;
import org.healthystyle.article.repository.ArticleSourceRepository;
import org.healthystyle.article.repository.CategoryRepository;
import org.healthystyle.article.repository.ImageRepository;
import org.healthystyle.article.repository.SourceRepository;
import org.healthystyle.article.repository.fragment.ArticleLinkRepository;
import org.healthystyle.article.repository.fragment.FragmentImageRepository;
import org.healthystyle.article.repository.fragment.FragmentRepository;
import org.healthystyle.article.repository.fragment.OrderRepository;
import org.healthystyle.article.repository.fragment.QuoteRepository;
import org.healthystyle.article.repository.fragment.RollElementRepository;
import org.healthystyle.article.repository.fragment.RollRepository;
import org.healthystyle.article.repository.fragment.TextRepository;
import org.healthystyle.article.repository.fragment.text.LinkPartRepository;
import org.healthystyle.article.repository.fragment.text.TextPartRepository;
import org.healthystyle.article.service.ArticleService;
import org.healthystyle.article.service.CategoryService;
import org.healthystyle.article.service.UserAccessor;
import org.healthystyle.article.service.dto.ArticleSaveRequest;
import org.healthystyle.article.service.dto.ArticleSourceSaveRequest;
import org.healthystyle.article.service.dto.CategorySaveRequest;
import org.healthystyle.article.service.dto.ImageSaveRequest;
import org.healthystyle.article.service.dto.SourceSaveRequest;
import org.healthystyle.article.service.dto.fragment.ArticleLinkSaveRequest;
import org.healthystyle.article.service.dto.fragment.FragmentImageSaveRequest;
import org.healthystyle.article.service.dto.fragment.FragmentSaveRequest;
import org.healthystyle.article.service.dto.fragment.OrderSaveRequest;
import org.healthystyle.article.service.dto.fragment.QuoteSaveRequest;
import org.healthystyle.article.service.dto.fragment.RollElementSaveRequest;
import org.healthystyle.article.service.dto.fragment.RollSaveRequest;
import org.healthystyle.article.service.dto.fragment.TextSaveRequest;
import org.healthystyle.article.service.dto.fragment.text.BoldPartSaveRequest;
import org.healthystyle.article.service.dto.fragment.text.CursivePartSaveRequest;
import org.healthystyle.article.service.dto.fragment.text.LinkPartSaveRequest;
import org.healthystyle.article.service.dto.fragment.text.TextPartSaveRequest;
import org.healthystyle.article.service.error.ArticleNotFoundException;
import org.healthystyle.article.service.error.CategoryExistException;
import org.healthystyle.article.service.error.CategoryNotFoundException;
import org.healthystyle.article.service.error.ImageNotFoundException;
import org.healthystyle.article.service.error.OrderExistException;
import org.healthystyle.article.service.error.PreviousOrderNotFoundException;
import org.healthystyle.article.service.error.fragment.FragmentExistException;
import org.healthystyle.article.service.error.fragment.FragmentNotFoundException;
import org.healthystyle.article.service.error.fragment.link.ArticleLinkExistException;
import org.healthystyle.article.service.error.fragment.roll.RollNotFoundException;
import org.healthystyle.article.service.error.fragment.text.TextNotFoundException;
import org.healthystyle.util.error.ValidationException;
import org.healthystyle.util.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(classes = Main.class)
public class ArticleTest {
	@Autowired
	private ArticleService articleService;
	@MockitoBean
	private ArticleRepository articleRepository;

	@MockitoBean
	private CategoryRepository categoryRepository;

	@MockitoBean
	private FragmentRepository fragmentRepository;
	@MockitoBean
	private ImageRepository imageRepository;
	@MockitoBean
	private QuoteRepository quoteRepository;
	@MockitoBean
	private FragmentImageRepository fragmentImageRepository;
	@MockitoBean
	private RollRepository rollRepository;
	@MockitoBean
	private RollElementRepository rollElementRepository;
	@MockitoBean
	private TextRepository textRepository;
	@MockitoBean
	private TextPartRepository textPartRepository;
	@MockitoBean
	private LinkPartRepository linkPartRepository;
	@MockitoBean
	private ArticleSourceRepository articleSourceRepository;
	@MockitoBean
	private SourceRepository sourceRepository;
	@MockitoBean
	private ArticleLinkRepository articleLinkRepository;

	@Autowired
	private CategoryService categoryService;

	private Article article;
	private ArticleSaveRequest articleSaveRequest;
	private ArticleSource articleSource;
	private ArticleSourceSaveRequest articleSourceSaveRequest;

	private Category category1;
	private CategorySaveRequest categorySaveRequest;

	private Image image;
	private Source mainImageSource;
	private ImageSaveRequest imageSaveRequest;
	private SourceSaveRequest mainImageSourceSaveRequest;

	private Fragment fragment;
	private FragmentSaveRequest fragmentSaveRequest;

	private ArticleLink articleLink;
	private ArticleLinkSaveRequest articleLinkSaveRequest;

	private FragmentImage fragmentImage;
	private FragmentImageSaveRequest fragmentImageSaveRequest;
	private Image image2;
	private ImageSaveRequest imageSaveRequest2;
	private Source source2;
	private SourceSaveRequest sourceSaveRequest2;

	private Quote quote;
	private QuoteSaveRequest quoteSaveRequest;

	private Roll roll;
	private RollSaveRequest rollSaveRequest;
	private RollElement rollElement;
	private RollElementSaveRequest rollElementSaveRequest;

	private Text text;
	private TextSaveRequest textSaveRequest;

	private CursivePart cursivePart;
	private CursivePartSaveRequest cursivePartSaveRequest;
	private BoldPart boldPart;
	private BoldPartSaveRequest boldPartSaveRequest;
	private TextPart textPart;
	private TextPartSaveRequest textPartSaveRequest;
	private LinkPart linkPart;
	private LinkPartSaveRequest linkPartSaveRequest;

	@MockitoBean
	private OrderRepository orderRepository;

	@MockitoBean
	private UserAccessor accessor;

	private User user;

	private Article article2;

	@BeforeEach
	public void setup() {
		category1 = new Category("Спорт", 1);
		category1.setId(1L);

		mainImageSource = new Source("Источник главного изображения", "https://google.com");
		mainImageSourceSaveRequest = new SourceSaveRequest();
		mainImageSourceSaveRequest.setDescription(mainImageSource.getDescription());
		mainImageSourceSaveRequest.setLink(mainImageSource.getLink());

		image = new Image();
		image.setId(1L);
		image.setImageId(1L);
		image.setSource(mainImageSource);
		imageSaveRequest = new ImageSaveRequest();
		imageSaveRequest.setRoot("article");
		imageSaveRequest.setRelativePath("/test/image.png");
		imageSaveRequest.setSource(mainImageSourceSaveRequest);
		imageSaveRequest.setFile(new MockMultipartFile("image.png", "dawadadw".getBytes()));

		article = new Article("Тестовое название", 1L, category1);
		article.setId(1L);
		article.setImage(image);
		articleSaveRequest = new ArticleSaveRequest();
		articleSaveRequest.setTitle(article.getTitle());
		articleSaveRequest.setImage(imageSaveRequest);

		article2 = new Article("Тестовая ссылка на статью", 2L, category1);
		article2.setId(2L);

		fragment = new Fragment(1, article, "Тестовый фрагмент");
		fragment.setId(1L);

		articleLink = new ArticleLink(article2, fragment, 1);
		articleLinkSaveRequest = new ArticleLinkSaveRequest();
		articleLinkSaveRequest.setArticleId(article2.getId());
		articleLinkSaveRequest.setOrder(articleLink.getOrder());
		articleLinkSaveRequest.setId("111");

		source2 = new Source("Источник изображения фрагмента", "https://yandex.ru");
		sourceSaveRequest2 = new SourceSaveRequest();
		sourceSaveRequest2.setDescription(source2.getDescription());
		sourceSaveRequest2.setLink(source2.getLink());

		image2 = new Image();
		image2.setId(2L);
		image2.setImageId(1L);
		image2.setSource(mainImageSource);
		imageSaveRequest2 = new ImageSaveRequest();
		imageSaveRequest2.setRoot("article");
		imageSaveRequest2.setRelativePath("/test/fragment_image.png");
		imageSaveRequest2.setSource(sourceSaveRequest2);
		imageSaveRequest2.setFile(new MockMultipartFile("fragment_image.png", "dawdad".getBytes()));

		fragmentImage = new FragmentImage(image2, fragment, 2);
		fragmentImage.setId(2L);
		fragmentImageSaveRequest = new FragmentImageSaveRequest();
		fragmentImageSaveRequest.setOrder(fragmentImage.getOrder());
		fragmentImageSaveRequest.setImage(imageSaveRequest2);
		fragmentImageSaveRequest.setId("222");

		quote = new Quote(fragment, 3, "Автор", "Цитата");
		quote.setId(3L);
		quoteSaveRequest = new QuoteSaveRequest();
		quoteSaveRequest.setId("333");
		quoteSaveRequest.setName(quote.getName());
		quoteSaveRequest.setDescription(quote.getText());
		quoteSaveRequest.setOrder(quote.getOrder());

		roll = new Roll(fragment, 4);
		roll.setId(4L);
		rollSaveRequest = new RollSaveRequest();
		rollSaveRequest.setId("444");
		rollSaveRequest.setOrder(roll.getOrder());
		rollElement = new RollElement(roll, "Some text", 1);
		rollElementSaveRequest = new RollElementSaveRequest();
		rollElementSaveRequest.setOrder(rollElement.getOrder());
		rollElementSaveRequest.setText(rollElement.getText());
		List<RollElementSaveRequest> rollElementSaveRequests = new ArrayList<>();
		rollSaveRequest.setRollElements(rollElementSaveRequests);
		roll.addRollElement(rollElement);

		text = new Text(fragment, 5);
		text.setId(5L);
		textSaveRequest = new TextSaveRequest();
		textSaveRequest.setId("555");
		textSaveRequest.setOrder(text.getOrder());

		textPart = new TextPart(1, text, "Обычный текст");
		textPartSaveRequest = new TextPartSaveRequest();
		textPartSaveRequest.setOrder(textPart.getOrder());
		textPartSaveRequest.setText(textPart.getValue());

		cursivePart = new CursivePart(2, text, "Курсивный текст");
		cursivePartSaveRequest = new CursivePartSaveRequest();
		cursivePartSaveRequest.setOrder(cursivePart.getOrder());
		cursivePartSaveRequest.setText(cursivePart.getValue());

		boldPart = new BoldPart(3, text, "Жирный текст");
		boldPartSaveRequest = new BoldPartSaveRequest();
		boldPartSaveRequest.setOrder(boldPart.getOrder());
		boldPartSaveRequest.setText(boldPart.getValue());

		linkPart = new LinkPart(4, text, "Ссылка в тексте", "https://google.com");
		linkPartSaveRequest = new LinkPartSaveRequest();
		linkPartSaveRequest.setOrder(linkPart.getOrder());
		linkPartSaveRequest.setText(linkPart.getValue());

		text.addTextPart(textPart);
		text.addTextPart(cursivePart);
		text.addTextPart(boldPart);
		text.addTextPart(linkPart);

		List<TextPartSaveRequest> textPartSaveRequests = new ArrayList<>();
		textPartSaveRequests.add(textPartSaveRequest);
		textPartSaveRequests.add(cursivePartSaveRequest);
		textPartSaveRequests.add(boldPartSaveRequest);
		textPartSaveRequests.add(linkPartSaveRequest);

		textSaveRequest.setParts(textPartSaveRequests);

		List<String> roles = new ArrayList<>();
		roles.add("USER");
		user = new User(1L, roles);

		List<OrderSaveRequest> orderSaveRequests = new ArrayList<>();
		orderSaveRequests.add(articleLinkSaveRequest);
		orderSaveRequests.add(fragmentImageSaveRequest);
		orderSaveRequests.add(quoteSaveRequest);
		orderSaveRequests.add(rollSaveRequest);
		orderSaveRequests.add(textSaveRequest);
		fragmentSaveRequest = new FragmentSaveRequest();
		fragmentSaveRequest.setOrder(fragment.getOrder());
		fragmentSaveRequest.setTitle(fragment.getTitle());
		fragmentSaveRequest.setOrders(orderSaveRequests);

		fragment.addOrder(articleLink);
		fragment.addOrder(fragmentImage);
		fragment.addOrder(quote);
		fragment.addOrder(roll);
		fragment.addOrder(text);

		article.setImage(image);

		article.addFragment(fragment);
		article.addSource(articleSource);

		article.addFragment(fragment);

		List<FragmentSaveRequest> fragmentSaveRequests = new ArrayList<>();
		fragmentSaveRequests.add(fragmentSaveRequest);

		articleSaveRequest.setFragments(fragmentSaveRequests);
	}

	@Test
	public void createArticleTest() throws ValidationException, ImageNotFoundException, ArticleNotFoundException,
			OrderExistException, PreviousOrderNotFoundException, FragmentExistException, ArticleLinkExistException,
			RollNotFoundException, FragmentNotFoundException, CategoryNotFoundException, TextNotFoundException {
		when(accessor.getUser()).thenReturn(user);

		when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
		when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
		when(articleRepository.findById(2L)).thenReturn(Optional.of(article2));
		when(fragmentRepository.findById(fragment.getId())).thenReturn(Optional.of(fragment));
		when(textRepository.findById(text.getId())).thenReturn(Optional.of(text));
		when(rollRepository.findById(roll.getId())).thenReturn(Optional.of(roll));
		when(orderRepository.existsByFragmentAndOrder(fragment.getId(), 2)).thenReturn(true);
		when(orderRepository.existsByFragmentAndOrder(fragment.getId(), 3)).thenReturn(true);
		when(orderRepository.existsByFragmentAndOrder(fragment.getId(), 4)).thenReturn(true);

		when(articleRepository.save(any(Article.class))).thenReturn(article);

		when(articleRepository.save(any(Article.class))).thenReturn(article);
		when(imageRepository.save(any(Image.class))).thenReturn(image);
		when(sourceRepository.save(any(Source.class))).thenReturn(mainImageSource);
		when(fragmentRepository.save(any(Fragment.class))).thenReturn(fragment);
		when(articleLinkRepository.save(any(ArticleLink.class))).thenReturn(articleLink);
		when(quoteRepository.save(any(Quote.class))).thenReturn(quote);
		when(rollRepository.save(any(Roll.class))).thenReturn(roll);
		when(rollElementRepository.save(any(RollElement.class))).thenReturn(rollElement);
		when(textRepository.save(any(Text.class))).thenReturn(text);
		when(textPartRepository.save(any(CursivePart.class))).thenReturn(cursivePart);
		when(textPartRepository.save(any(TextPart.class))).thenReturn(textPart);
		when(textPartRepository.save(any(BoldPart.class))).thenReturn(boldPart);
		when(linkPartRepository.save(any(LinkPart.class))).thenReturn(linkPart);
		articleService.save(articleSaveRequest, 1L);
		verify(articleRepository, times(1)).save(any(Article.class));

	}

	@Test
	public void createArticlePreviousOrderNotFoundTest()
			throws ValidationException, ImageNotFoundException, ArticleNotFoundException, OrderExistException,
			PreviousOrderNotFoundException, FragmentExistException, ArticleLinkExistException, RollNotFoundException,
			FragmentNotFoundException, CategoryNotFoundException, TextNotFoundException {
		when(accessor.getUser()).thenReturn(user);

		textSaveRequest.setOrder(textSaveRequest.getOrder() + 1);

		when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
		when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
		when(articleRepository.findById(2L)).thenReturn(Optional.of(article2));
		when(fragmentRepository.findById(fragment.getId())).thenReturn(Optional.of(fragment));
		when(textRepository.findById(text.getId())).thenReturn(Optional.of(text));
		when(rollRepository.findById(roll.getId())).thenReturn(Optional.of(roll));
		when(orderRepository.existsByFragmentAndOrder(fragment.getId(), 2)).thenReturn(true);
		when(orderRepository.existsByFragmentAndOrder(fragment.getId(), 3)).thenReturn(true);
		when(orderRepository.existsByFragmentAndOrder(fragment.getId(), 4)).thenReturn(true);

		when(articleRepository.save(any(Article.class))).thenReturn(article);

		when(articleRepository.save(any(Article.class))).thenReturn(article);
		when(imageRepository.save(any(Image.class))).thenReturn(image);
		when(sourceRepository.save(any(Source.class))).thenReturn(mainImageSource);
		when(fragmentRepository.save(any(Fragment.class))).thenReturn(fragment);
		when(articleLinkRepository.save(any(ArticleLink.class))).thenReturn(articleLink);
		when(quoteRepository.save(any(Quote.class))).thenReturn(quote);
		when(rollRepository.save(any(Roll.class))).thenReturn(roll);
		when(rollElementRepository.save(any(RollElement.class))).thenReturn(rollElement);
		when(textRepository.save(any(Text.class))).thenReturn(text);
		when(textPartRepository.save(any(CursivePart.class))).thenReturn(cursivePart);
		when(textPartRepository.save(any(TextPart.class))).thenReturn(textPart);
		when(textPartRepository.save(any(BoldPart.class))).thenReturn(boldPart);
		when(linkPartRepository.save(any(LinkPart.class))).thenReturn(linkPart);
		assertThrows(PreviousOrderNotFoundException.class, () -> {
			articleService.save(articleSaveRequest, 1L);
		});
	}

	@Test
	public void createArticleSameFragmentPartOrderTest()
			throws ValidationException, ImageNotFoundException, ArticleNotFoundException, OrderExistException,
			PreviousOrderNotFoundException, FragmentExistException, ArticleLinkExistException, RollNotFoundException,
			FragmentNotFoundException, CategoryNotFoundException, TextNotFoundException {
		when(accessor.getUser()).thenReturn(user);

		textSaveRequest.setOrder(rollSaveRequest.getOrder());

		when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
		when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
		when(articleRepository.findById(2L)).thenReturn(Optional.of(article2));
		when(fragmentRepository.findById(fragment.getId())).thenReturn(Optional.of(fragment));
		when(textRepository.findById(text.getId())).thenReturn(Optional.of(text));
		when(rollRepository.findById(roll.getId())).thenReturn(Optional.of(roll));
		when(orderRepository.existsByFragmentAndOrder(fragment.getId(), 2)).thenReturn(true);
		when(orderRepository.existsByFragmentAndOrder(fragment.getId(), 3)).thenReturn(true);
		when(orderRepository.existsByFragmentAndOrder(fragment.getId(), 4)).thenReturn(true);

		when(articleRepository.save(any(Article.class))).thenReturn(article);

		when(articleRepository.save(any(Article.class))).thenReturn(article);
		when(imageRepository.save(any(Image.class))).thenReturn(image);
		when(sourceRepository.save(any(Source.class))).thenReturn(mainImageSource);
		when(fragmentRepository.save(any(Fragment.class))).thenReturn(fragment);
		when(articleLinkRepository.save(any(ArticleLink.class))).thenReturn(articleLink);
		when(quoteRepository.save(any(Quote.class))).thenReturn(quote);
		when(rollRepository.save(any(Roll.class))).thenReturn(roll);
		when(rollElementRepository.save(any(RollElement.class))).thenReturn(rollElement);
		when(textRepository.save(any(Text.class))).thenReturn(text);
		when(textPartRepository.save(any(CursivePart.class))).thenReturn(cursivePart);
		when(textPartRepository.save(any(TextPart.class))).thenReturn(textPart);
		when(textPartRepository.save(any(BoldPart.class))).thenReturn(boldPart);
		when(linkPartRepository.save(any(LinkPart.class))).thenReturn(linkPart);

		assertThrows(OrderExistException.class, () -> {
			articleService.save(articleSaveRequest, 1L);
		});
	}

	@Test
	public void createArticleFragmentValidationTest()
			throws ValidationException, ImageNotFoundException, ArticleNotFoundException, OrderExistException,
			PreviousOrderNotFoundException, FragmentExistException, ArticleLinkExistException, RollNotFoundException,
			FragmentNotFoundException, CategoryNotFoundException, TextNotFoundException {
		fragmentSaveRequest.setOrder(-5);

		when(accessor.getUser()).thenReturn(user);

		when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
		when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
		when(articleRepository.findById(2L)).thenReturn(Optional.of(article2));
		when(fragmentRepository.findById(fragment.getId())).thenReturn(Optional.of(fragment));
		when(textRepository.findById(text.getId())).thenReturn(Optional.of(text));
		when(rollRepository.findById(roll.getId())).thenReturn(Optional.of(roll));
		when(orderRepository.existsByFragmentAndOrder(fragment.getId(), 2)).thenReturn(true);
		when(orderRepository.existsByFragmentAndOrder(fragment.getId(), 3)).thenReturn(true);
		when(orderRepository.existsByFragmentAndOrder(fragment.getId(), 4)).thenReturn(true);

		when(articleRepository.save(any(Article.class))).thenReturn(article);

		when(articleRepository.save(any(Article.class))).thenReturn(article);
		when(imageRepository.save(any(Image.class))).thenReturn(image);
		when(sourceRepository.save(any(Source.class))).thenReturn(mainImageSource);
		when(fragmentRepository.save(any(Fragment.class))).thenReturn(fragment);
		when(articleLinkRepository.save(any(ArticleLink.class))).thenReturn(articleLink);
		when(quoteRepository.save(any(Quote.class))).thenReturn(quote);
		when(rollRepository.save(any(Roll.class))).thenReturn(roll);
		when(rollElementRepository.save(any(RollElement.class))).thenReturn(rollElement);
		when(textRepository.save(any(Text.class))).thenReturn(text);
		when(textPartRepository.save(any(CursivePart.class))).thenReturn(cursivePart);
		when(textPartRepository.save(any(TextPart.class))).thenReturn(textPart);
		when(textPartRepository.save(any(BoldPart.class))).thenReturn(boldPart);
		when(linkPartRepository.save(any(LinkPart.class))).thenReturn(linkPart);

		assertThrows(FragmentExistException.class, () -> {
			articleService.save(articleSaveRequest, 1L);
		});
	}

	@Test
	public void createCategoryTest() throws ValidationException, CategoryExistException, CategoryNotFoundException,
			OrderExistException, PreviousOrderNotFoundException {
		when(accessor.getUser()).thenReturn(user);

		when(categoryRepository.save(any(Category.class))).thenReturn(category1);
		when(categoryRepository.existsByTitle(any(String.class))).thenReturn(false);

		categoryService.save(categorySaveRequest);
		verify(articleRepository, times(1)).save(any(Article.class));
	}

	@Test
	public void createCategoryExistTest() throws ValidationException, CategoryExistException, CategoryNotFoundException,
			OrderExistException, PreviousOrderNotFoundException {
		when(accessor.getUser()).thenReturn(user);

		when(categoryRepository.save(any(Category.class))).thenReturn(category1);
		when(categoryRepository.existsByTitle(any(String.class))).thenReturn(true);

		assertThrows(CategoryExistException.class, () -> {
			categoryService.save(categorySaveRequest);
		});
	}
	
	@Test
	public void createCategoryWithParentTest() throws ValidationException, CategoryExistException, CategoryNotFoundException,
			OrderExistException, PreviousOrderNotFoundException {
		Category parentCategory = new Category("Parent category", null);
		parentCategory.setId(2L);
		
		category1.setParentCategory(parentCategory);
		when(accessor.getUser()).thenReturn(user);

		when(categoryRepository.save(any(Category.class))).thenReturn(category1);
		when(categoryRepository.existsByTitle(any(String.class))).thenReturn(false);
		when(categoryRepository.existsByParentAndOrder(any(Long.class), any(Integer.class))).thenReturn(false);
		when(categoryRepository.existsByOrder(any(Integer.class))).thenReturn(false);


		categoryService.save(categorySaveRequest);
		verify(articleRepository, times(1)).save(any(Article.class));
	}
	
	@Test
	public void createCategoryWithParentExistOrderTest() throws ValidationException, CategoryExistException, CategoryNotFoundException,
			OrderExistException, PreviousOrderNotFoundException {
		Category parentCategory = new Category("Parent category", null);
		parentCategory.setId(2L);
		
		category1.setParentCategory(parentCategory);
		when(accessor.getUser()).thenReturn(user);

		when(categoryRepository.save(any(Category.class))).thenReturn(category1);
		when(categoryRepository.existsByTitle(any(String.class))).thenReturn(false);
		when(categoryRepository.existsByParentAndOrder(any(Long.class), any(Integer.class))).thenReturn(true);
		when(categoryRepository.existsByOrder(any(Integer.class))).thenReturn(true);


		categoryService.save(categorySaveRequest);
		verify(articleRepository, times(1)).save(any(Article.class));
	}
}
