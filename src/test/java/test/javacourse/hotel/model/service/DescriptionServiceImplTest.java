package test.javacourse.hotel.model.service;

import by.javacourse.hotel.entity.Description;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.dao.DescriptionDao;
import by.javacourse.hotel.model.service.impl.DescriptionServiceImpl;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.javacourse.hotel.controller.command.SessionAttribute.*;

public class DescriptionServiceImplTest {
    
    private DescriptionServiceImpl descriptionService;
    private DescriptionDao descriptionDaoMock;

    @BeforeMethod
    public void initialize() throws DaoException {
        descriptionService = new DescriptionServiceImpl();
        descriptionDaoMock = Mockito.mock(DescriptionDao.class);
        setMock(descriptionDaoMock);
    }

    @Test
    public void testFindDescriptionByRoomIdEmpty() throws ServiceException, DaoException {
        long roomId = 99999L;
        Mockito.when(descriptionDaoMock.findEntityById(roomId))
                .thenReturn(Optional.empty());
        Optional<Description> actual = descriptionService.findDescriptionByRoomId(roomId);
        Optional<Description> expected = Optional.empty();
        Mockito.verify(descriptionDaoMock, Mockito.times(1)).findEntityById(roomId);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testFindDescriptionByRoomIdNotEmpty() throws ServiceException, DaoException {
        long roomId = 1;
        Optional<Description> notEmptyRoom = Optional.of(Description.newBuilder()
                .setEntityId(roomId)
                .build());
        Mockito.when(descriptionDaoMock.findEntityById(roomId))
                .thenReturn(notEmptyRoom);
        Optional<Description> actual = descriptionService.findDescriptionByRoomId(roomId);
        Optional<Description> expected = Optional.of(Description.newBuilder().setEntityId(roomId).build());
        Mockito.verify(descriptionDaoMock, Mockito.times(1)).findEntityById(roomId);
        Assert.assertEquals(actual, expected);
    }

    @Test (expectedExceptions = ServiceException.class)
    public void testFindDescriptionByRoomIdException() throws ServiceException, DaoException {
        Mockito.when(descriptionDaoMock.findEntityById(-1L))
                .thenThrow(new DaoException());
        descriptionService.findDescriptionByRoomId(-1L);
        Mockito.verify(descriptionDaoMock, Mockito.times(1)).findEntityById(-1L);
    }

    @Test
    public void testUpdateDescriptionPositive() throws DaoException, ServiceException {
        Mockito.when(descriptionDaoMock.update(Mockito.any()))
                .thenReturn(true);
        Map<String , String> descriptionData = new HashMap<>();
        descriptionData.put(DESCRIPTION_ID_SES, "42");
        descriptionData.put(DESCRIPTION_RU_SES, "уруру");
        descriptionData.put(DESCRIPTION_EN_SES, "azaza");
        Assert.assertTrue(descriptionService.updateDescription(descriptionData));
        Mockito.verify(descriptionDaoMock, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    public void testUpdateDescriptionNegative() throws DaoException, ServiceException {
        Mockito.when(descriptionDaoMock.create(Mockito.any()))
                .thenReturn(true);
        Map<String , String> descriptionData = new HashMap<>();
        descriptionData.put(DESCRIPTION_ID_SES, null);
        descriptionData.put(DESCRIPTION_RU_SES, "уруру");
        descriptionData.put(DESCRIPTION_EN_SES, "azaza");
        descriptionData.put(ROOM_ID_SES, "42");
        Assert.assertTrue(descriptionService.updateDescription(descriptionData));
        Mockito.verify(descriptionDaoMock, Mockito.times(1)).create(Mockito.any());
    }

    @AfterMethod
    public void clean() {
        descriptionService = null;
    }

    private void setMock(DescriptionDao mock) {
        try {
            Field descriptionDao = DescriptionServiceImpl.class.getDeclaredField("descriptionDao");
            descriptionDao.setAccessible(true);
            descriptionDao.set(descriptionService, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}