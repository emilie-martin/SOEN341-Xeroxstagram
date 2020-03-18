package com.soen341.instagram.service.impl;

import com.soen341.instagram.dao.impl.PictureRepository;
import com.soen341.instagram.dto.picture.PictureDTO;
import com.soen341.instagram.exception.picture.InvalidIdException;
import com.soen341.instagram.exception.picture.NotAPictureException;
import com.soen341.instagram.exception.picture.PictureNotFoundException;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Picture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PictureServiceTest {
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

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);

        doReturn("test account").when(account).getUsername();

        pic = new Picture();
        pic.setId(1);
        pic.setAccount(account);

        doReturn(Optional.of(pic)).when(pictureRepository).findById(1L);

        picDTO = new PictureDTO();
        picDTO.setId(1L);

        doReturn(picDTO).when(modelMapper).map(pic, PictureDTO.class);
    }

    @Test
    public void testUploadPicture() {
        final String caption = "caption";
        final MockMultipartFile file = getTestFile("src/test/resources/1pixel.jpg");
        Picture uploadedPicture = pictureService.uploadPicture(caption, file, account);
        assertThat(uploadedPicture.getAccount()).isEqualTo(account);
        assertThat(uploadedPicture.getCaption()).isEqualTo(caption);
    }

    @Test
    public void testUploadPictureNotValidFile() {
        final String caption = "caption";
        final MockMultipartFile file = getTestFile("src/test/resources/invalidPicture.txt");
        assertThatCode(() -> pictureService.uploadPicture(caption, file, account))
                .isInstanceOf(NotAPictureException.class);
    }

    @Test
    public void testGetPictureDTOFromId() {
        PictureDTO pic = pictureService.getPictureDTOFromId("1");
        assertThat(pic.getAccount()).isEqualTo("test account");
        assertThat(pic.getId()).isEqualTo(1L);
    }

    @Test
    public void testGetPictureDTOFromId_NotFound() {
        assertThatCode(() -> pictureService.getPictureDTOFromId("2"))
                .isInstanceOf(PictureNotFoundException.class);
    }

    @Test
    public void testGetPictureDTOFromId_InvalidId() {
        assertThatCode(() -> pictureService.getPictureDTOFromId("invalid"))
                .isInstanceOf(InvalidIdException.class);
    }

    @Test
    public void testGetAccountPictures() {
        doReturn(Collections.singletonList(pic)).when(pictureRepository).findByAccount(account);
        List<Long> pictures = pictureService.getAccountPictures(account);
        assertThat(pictures.size()).isEqualTo(1);
        assertThat(pictures.get(0)).isEqualTo(1L);
    }

    @Test
    public void testToPictureDTO() {
        assertThat(pictureService.toPictureDTO(pic)).isEqualTo(picDTO);
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
