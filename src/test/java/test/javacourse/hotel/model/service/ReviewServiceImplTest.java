package test.javacourse.hotel.model.service;

import by.javacourse.hotel.entity.Review;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.dao.ReviewDao;
import by.javacourse.hotel.model.service.impl.ReviewServiceImpl;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;

import static by.javacourse.hotel.controller.command.RequestAttribute.DATE_FROM_ATR;
import static by.javacourse.hotel.controller.command.RequestAttribute.DATE_TO_ATR;
import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class ReviewServiceImplTest {

    ReviewServiceImpl reviewService;
    ReviewDao reviewDaoMock;

    @BeforeMethod
    public void initialize() {
        reviewService = new ReviewServiceImpl();
        reviewDaoMock = Mockito.mock(ReviewDao.class);
        setMock(reviewDaoMock);
    }

    @Test
    public void testFindReviewsByRoomId() throws DaoException, ServiceException {
        List<Review> reviews = new ArrayList<>();
        Mockito.when(reviewDaoMock.findReviewByRoomId(Mockito.anyLong()))
                .thenReturn(reviews);
        List<Review> actual = reviewService.findReviewsByRoomId(1L);
        List<Review> expected = reviews;
        Mockito.verify(reviewDaoMock, Mockito.times(1)).findReviewByRoomId(Mockito.anyLong());
        Assert.assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindReviewsByRoomIdException() throws DaoException, ServiceException {
        Mockito.when(reviewDaoMock.findReviewByRoomId(Mockito.anyLong()))
                .thenThrow(new DaoException());
        reviewService.findReviewsByRoomId(1L);
        Mockito.verify(reviewDaoMock, Mockito.times(1)).findReviewByRoomId(Mockito.anyLong());
    }

    @Test
    public void testFindReviewsByDateRange() throws DaoException, ServiceException {
        List<Review> reviews = new ArrayList<>();
        Mockito.when(reviewDaoMock.findReviewByDateRange(Mockito.any(), Mockito.any()))
                .thenReturn(reviews);
        Map<String, String> reviewData = new HashMap<>();
        reviewData.put(DATE_FROM_ATR, "2020-01-01");
        reviewData.put(DATE_TO_ATR, "2020-01-05");
        List<Review> actual = reviewService.findReviewsByDateRange(reviewData);
        List<Review> expected = reviews;
        Mockito.verify(reviewDaoMock, Mockito.times(1))
                .findReviewByDateRange(Mockito.any(), Mockito.any());
        Assert.assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindReviewsByDateRangeException() throws DaoException, ServiceException {
        Mockito.when(reviewDaoMock.findReviewByDateRange(Mockito.any(), Mockito.any()))
                .thenThrow(new DaoException());
        Map<String, String> reviewData = new HashMap<>();
        reviewData.put(DATE_FROM_ATR, "2020-01-01");
        reviewData.put(DATE_TO_ATR, "2020-01-05");
        reviewService.findReviewsByDateRange(reviewData);
        Mockito.verify(reviewDaoMock, Mockito.times(1))
                .findReviewByDateRange(Mockito.any(), Mockito.any());
    }

    @Test
    public void testFindAllReviews() throws DaoException, ServiceException {
        List<Review> reviews = new ArrayList<>();
        Mockito.when(reviewDaoMock.findAll())
                .thenReturn(reviews);
        List<Review> actual = reviewService.findAllReviews();
        List<Review> expected = reviews;
        Mockito.verify(reviewDaoMock, Mockito.times(1)).findAll();
        Assert.assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindAllReviewsException() throws DaoException, ServiceException {
        Mockito.when(reviewDaoMock.findAll())
                .thenThrow(new DaoException());
        reviewService.findAllReviews();
        Mockito.verify(reviewDaoMock, Mockito.times(1)).findAll();
    }

    @Test
    public void testFindReviewsByUserId() throws DaoException, ServiceException {
        List<Review> reviews = new ArrayList<>();
        Mockito.when(reviewDaoMock.findReviewByUserId(Mockito.anyLong()))
                .thenReturn(reviews);
        List<Review> actual = reviewService.findReviewsByUserId(1L);
        List<Review> expected = reviews;
        Mockito.verify(reviewDaoMock, Mockito.times(1)).findReviewByUserId(Mockito.anyLong());
        Assert.assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindReviewsByUserIdException() throws DaoException, ServiceException {
        Mockito.when(reviewDaoMock.findReviewByUserId(Mockito.anyLong()))
                .thenThrow(new DaoException());
        reviewService.findReviewsByUserId(1L);
        Mockito.verify(reviewDaoMock, Mockito.times(1)).findReviewByUserId(Mockito.anyLong());
    }

    @Test
    public void testFindReviewsByUserIdFromDate() throws DaoException, ServiceException {
        List<Review> reviews = new ArrayList<>();
        Mockito.when(reviewDaoMock.findReviewByUserIdFromDate(Mockito.anyLong(), Mockito.any()))
                .thenReturn(reviews);
        List<Review> actual = reviewService.findReviewsByUserIdFromDate(1L, LocalDate.parse("2022-01-01"));
        List<Review> expected = reviews;
        Mockito.verify(reviewDaoMock, Mockito.times(1))
                .findReviewByUserIdFromDate(Mockito.anyLong(), Mockito.any());
        Assert.assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindReviewsByUserIdFromDateException() throws DaoException, ServiceException {
        Mockito.when(reviewDaoMock.findReviewByUserIdFromDate(Mockito.anyLong(), Mockito.any()))
                .thenThrow(new DaoException());
        reviewService.findReviewsByUserIdFromDate(1L, LocalDate.parse("2022-01-01"));
        Mockito.verify(reviewDaoMock, Mockito.times(1))
                .findReviewByUserIdFromDate(Mockito.anyLong(), Mockito.any());
    }

    @Test
    public void testCreateReview() throws DaoException, ServiceException {
        Mockito.when(reviewDaoMock.create(Mockito.any()))
                .thenReturn(true);
        Map<String, String> reviewData = new HashMap<>();
        reviewData.put(MARK_SES, "5");
        reviewData.put(CONTENT_SES, "azaza");
        reviewData.put(ORDER_ID_SES, "12");
        reviewData.put(DATE_SES, "2022-01-01");
        Assert.assertTrue(reviewService.createReview(reviewData, 1L));
        Mockito.verify(reviewDaoMock, Mockito.times(1)).create(Mockito.any());
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testCreateReviewException() throws DaoException, ServiceException {
        Mockito.when(reviewDaoMock.create(Mockito.any()))
                .thenThrow(new DaoException());
        Map<String, String> reviewData = new HashMap<>();
        reviewData.put(MARK_SES, "5");
        reviewData.put(CONTENT_SES, "azaza");
        reviewData.put(ORDER_ID_SES, "12");
        reviewData.put(DATE_SES, "2022-01-01");
        reviewService.createReview(reviewData, 1L);
        Mockito.verify(reviewDaoMock, Mockito.times(1)).create(Mockito.any());
    }

    @Test
    public void testFindReviewById() throws DaoException, ServiceException {
        Optional<Review> review = Optional.empty();
        Mockito.when(reviewDaoMock.findEntityById(Mockito.anyLong()))
                .thenReturn(review);
        Optional<Review> actual = reviewService.findReviewById("1");
        Optional<Review> expected = review;
        Mockito.verify(reviewDaoMock, Mockito.times(1))
                .findEntityById(Mockito.anyLong());
        Assert.assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindReviewByIdException() throws DaoException, ServiceException {
        Mockito.when(reviewDaoMock.findEntityById(Mockito.anyLong()))
                .thenThrow(new DaoException());
        reviewService.findReviewById("1");
        Mockito.verify(reviewDaoMock, Mockito.times(1))
                .findEntityById(Mockito.anyLong());
    }

    @Test
    public void testUpdateHidden() throws DaoException, ServiceException {
        Mockito.when(reviewDaoMock.update(Mockito.any()))
                .thenReturn(true);
        Assert.assertTrue(reviewService.updateHidden(1L, true));
        Mockito.verify(reviewDaoMock, Mockito.times(1)).update(Mockito.any());
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdateHiddenException() throws DaoException, ServiceException {
        Mockito.when(reviewDaoMock.update(Mockito.any()))
                .thenThrow(new DaoException());
        reviewService.updateHidden(1L, true);
        Mockito.verify(reviewDaoMock, Mockito.times(1)).update(Mockito.any());
    }

    @AfterMethod
    public void clean() {
        reviewService = null;
    }

    private void setMock(ReviewDao mock) {
        try {
            Field reviewDao = ReviewServiceImpl.class.getDeclaredField("reviewDao");
            reviewDao.setAccessible(true);
            reviewDao.set(reviewService, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}