package com.soen341.instagram.service.impl;

import com.soen341.instagram.dao.impl.PictureRepository;
import com.soen341.instagram.dto.picture.PictureDTO;
import com.soen341.instagram.exception.picture.InvalidIdException;
import com.soen341.instagram.exception.picture.NotAPictureException;
import com.soen341.instagram.exception.picture.PictureNotFoundException;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Picture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PictureServiceTest
{
    @InjectMocks
    private PictureService pictureService;

    @Mock
    private PictureRepository pictureRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private Account account;

    private Picture pic;
    private PictureDTO picDTO;

    @Before
    public void setup()
    {
        //MockitoAnnotations.initMocks(this);

        Mockito.when(account.getUsername()).thenReturn("test account");

        pic = new Picture();
        pic.setId(1);
        pic.setAccount(account);

        Mockito.when(pictureRepository.findById(1L)).thenReturn(Optional.of(pic));

        picDTO = new PictureDTO();
        picDTO.setId(1L);

        Mockito.when(modelMapper.map(pic, PictureDTO.class)).thenReturn(picDTO);
    }

    @Test
    public void testUploadPicture()
    {
        final String caption = "caption";
        final MockMultipartFile file = getTestFile("src/test/resources/1pixel.jpg");
        Picture uploadedPicture = pictureService.uploadPicture(caption, file, account);
        assertEquals(account, uploadedPicture.getAccount());
        assertEquals(caption, uploadedPicture.getCaption());
    }

    @Test(expected = NotAPictureException.class)
    public void testUploadPictureNotValidFile()
    {
        final String caption = "caption";
        final MockMultipartFile file = getTestFile("src/test/resources/invalidPicture.txt");
        pictureService.uploadPicture(caption, file, account);
    }

    @Test
    public void testGetPictureDTOFromId()
    {
        PictureDTO pic = pictureService.getPictureDTOFromId("1");
        assertEquals("test account", pic.getAccount());
        assertEquals(1L, pic.getId());
    }

    @Test(expected = PictureNotFoundException.class)
    public void testGetPictureDTOFromId_NotFound()
    {
        pictureService.getPictureDTOFromId("2");
    }

    @Test(expected = InvalidIdException.class)
    public void testGetPictureDTOFromId_InvalidId()
    {
        pictureService.getPictureDTOFromId("invalid");
    }

    @Test
    public void testGetAccountPictures()
    {
        Mockito.when(pictureRepository.findByAccount(account)).thenReturn(Collections.singletonList(pic));
        List<Long> pictures = pictureService.getAccountPictures(account);
        assertEquals(1, pictures.size());
        assertEquals(1L, (long)pictures.get(0));
    }

    @Test
    public void testToPictureDTO()
    {
        assertEquals(picDTO, pictureService.toPictureDTO(pic));
    }

    private MockMultipartFile getTestFile(String filePath) {
        final File file = new File(filePath);
        MockMultipartFile mockMultipartFile = null;
        try {
            final FileInputStream input = new FileInputStream(file);
            mockMultipartFile = new MockMultipartFile(file.getName(), input);
        } catch (final Exception e) {
        }
        return mockMultipartFile;
    }
}
