package com.soen341.instagram.service.impl;

// Testing
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

// Project
import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.dao.impl.PictureRepository;
import com.soen341.instagram.dto.picture.PictureDTO;
import com.soen341.instagram.exception.like.MultipleLikeException;
import com.soen341.instagram.exception.like.NoLikeException;
import com.soen341.instagram.exception.picture.InvalidIdException;
import com.soen341.instagram.exception.picture.NotAPictureException;
import com.soen341.instagram.exception.picture.PictureNotFoundException;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Picture;
import com.soen341.instagram.utils.UserAccessor;

// Other libraries
import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PictureService.class, UserAccessor.class})
public class PictureServiceTest
{
	@InjectMocks
	private PictureService pictureService;
	@Mock
	private PictureRepository pictureRepository;
	@Mock
	private AccountRepository accountRepository;
	@Mock
	private ModelMapper modelMapper;
	@Mock
	private Account account;
	@Rule
	private TemporaryFolder tempFolder = new TemporaryFolder();
	private Picture pic;
	private PictureDTO picDTO;

	@Before
	public void setup()
	{
		Mockito.when(account.getUsername()).thenReturn("test account");

		PowerMockito.mockStatic(UserAccessor.class);
		PowerMockito.when(UserAccessor.getCurrentAccount(accountRepository)).thenReturn(account);

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
		mockFileCreation();

		final Picture uploadedPicture = pictureService.uploadPicture(caption, file, account);
		assertEquals(account, uploadedPicture.getAccount());
		assertEquals(caption, uploadedPicture.getCaption());
	}

	@Test(expected = NotAPictureException.class)
	public void testUploadPictureNotValidFile()
	{
		final String caption = "caption";
		final MockMultipartFile file = getTestFile("src/test/resources/invalidPicture.txt");
		mockFileCreation();
		pictureService.uploadPicture(caption, file, account);
	}

	@Test
	public void testGetPictureDTOFromId()
	{
		final PictureDTO pic = pictureService.getPictureDTOFromId("1");
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
		final List<Long> pictures = pictureService.getAccountPictures(account);
		assertEquals(1, pictures.size());
		assertEquals(1L, (long)pictures.get(0));
	}

	@Test
	public void testToPictureDTO()
	{
		assertEquals(picDTO, pictureService.toPictureDTO(pic));
	}

	private MockMultipartFile getTestFile(String filePath)
	{
		final File file = new File(filePath);
		MockMultipartFile mockMultipartFile = null;
		try
		{
			final FileInputStream input = new FileInputStream(file);
			mockMultipartFile = new MockMultipartFile(file.getName(), input);
		}
		catch (final Exception e)
		{
			System.out.println(e.getMessage());
		}

		return mockMultipartFile;
	}

	@Test
	public void likePictureSuccessfully()
	{
		assertEquals(pictureService.likePicture(String.valueOf(pic.getId())), 1);
	}

	@Test(expected = MultipleLikeException.class)
	public void likePictureMultipleTimes_ExpectMultipleLikeException()
	{
		pictureService.likePicture(String.valueOf(pic.getId()));
		pictureService.likePicture(String.valueOf(pic.getId()));
	}

	@Test
	public void unlikePictureSuccessfully()
	{
		// Mock having liked the picture previously
		final Set<Account> likedBy = pic.getLikedBy();
		likedBy.add(account);

		assertEquals(pic.getLikedBy().size(), 1);
		assertEquals(pictureService.unlikePicture(String.valueOf(pic.getId())), 0);
	}

	@Test(expected = NoLikeException.class)
	public void unlikeNotLikedPicture_ExpectNoLikeException()
	{
		assertEquals(pictureService.unlikePicture(String.valueOf(pic.getId())), 0);
	}

	@Test
	public void getLikeStatus_ExpectTrue()
	{
		// Mock having liked the picture previously
		final Set<Account> likedBy = pic.getLikedBy();
		likedBy.add(account);

		assertEquals(pictureService.getLikeStatus(String.valueOf(pic.getId())), true);
	}

	@Test
	public void getLikeStatus_ExpectFalse()
	{
		assertEquals(pictureService.getLikeStatus(String.valueOf(pic.getId())), false);
	}

	private void mockFileCreation()
	{
		try
		{
			File tempFile = new File(tempFolder.getRoot().getPath() + "/pic.jpg");
			PowerMockito.whenNew(File.class).withAnyArguments().thenReturn(tempFile);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
