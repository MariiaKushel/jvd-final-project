package test.javacourse.hotel.model.service;

import by.javacourse.hotel.entity.Image;
import by.javacourse.hotel.exception.DaoException;
import by.javacourse.hotel.exception.ServiceException;
import by.javacourse.hotel.model.dao.ImageDao;
import by.javacourse.hotel.model.service.impl.ImageServiceImpl;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.List;

public class ImageServiceImplTest {

    ImageServiceImpl imageService;
    ImageDao imageDaoMock;

    @BeforeMethod
    public void initialize() {
        imageService = new ImageServiceImpl();
        imageDaoMock = Mockito.mock(ImageDao.class);
        setMock(imageDaoMock);
    }

    @Test
    public void testCreateImage() throws DaoException, ServiceException {
        Mockito.when(imageDaoMock.create(Mockito.any()))
                .thenReturn(true);
        Assert.assertTrue(imageService.createImage(new byte[0], "12"));
        Mockito.verify(imageDaoMock, Mockito.times(1)).create(Mockito.any());
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testCreateImageException() throws DaoException, ServiceException {
        Mockito.when(imageDaoMock.create(Mockito.any()))
                .thenThrow(new DaoException());
        imageService.createImage(new byte[0], "12");
        Mockito.verify(imageDaoMock, Mockito.times(1)).create(Mockito.any());
    }

    @Test
    public void testFindImagesByRoomId() throws DaoException, ServiceException {
        Image image = Image.newBuilder()
                .setEntityId(1)
                .setRoomId(12)
                .setImageContent(new byte[0])
                .setPreview(false)
                .build();
        List<Image> imageList = List.of(image, image);
        Mockito.when(imageDaoMock.findImageByRoomId(Mockito.anyLong()))
                .thenReturn(imageList);
        List<Image> actual = imageService.findImagesByRoomId(12L);
        List<Image> expected = imageList;
        Mockito.verify(imageDaoMock, Mockito.times(1)).findImageByRoomId(Mockito.anyLong());
        Assert.assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindImagesByRoomIdException() throws DaoException, ServiceException {
        Mockito.when(imageDaoMock.findImageByRoomId(Mockito.anyLong()))
                .thenThrow(new DaoException());
        imageService.findImagesByRoomId(12L);
        Mockito.verify(imageDaoMock, Mockito.times(1)).findImageByRoomId(Mockito.anyLong());
    }

    @Test
    public void testChangePreview() throws DaoException, ServiceException {
        Mockito.when(imageDaoMock.changePreview(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(false);
        Assert.assertFalse(imageService.changePreview("12", "3"));
        Mockito.verify(imageDaoMock, Mockito.times(1))
                .changePreview(Mockito.anyLong(), Mockito.anyLong());
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testChangePreviewException() throws DaoException, ServiceException {
        Mockito.when(imageDaoMock.changePreview(Mockito.anyLong(), Mockito.anyLong()))
                .thenThrow(new DaoException());
        imageService.changePreview("12", "3");
        Mockito.verify(imageDaoMock, Mockito.times(1))
                .changePreview(Mockito.anyLong(), Mockito.anyLong());
    }

    @AfterMethod
    public void clean() {
        imageService = null;
    }

    private void setMock(ImageDao mock) {
        try {
            Field imageDao = ImageServiceImpl.class.getDeclaredField("imageDao");
            imageDao.setAccessible(true);
            imageDao.set(imageService, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}