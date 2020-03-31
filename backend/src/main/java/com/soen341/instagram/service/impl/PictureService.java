package com.soen341.instagram.service.impl;

// Spring Boot
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

// Project
import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.dao.impl.PictureRepository;
import com.soen341.instagram.dto.picture.PictureDTO;
import com.soen341.instagram.exception.like.MultipleLikeException;
import com.soen341.instagram.exception.like.NoLikeException;
import com.soen341.instagram.exception.picture.*;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Picture;
import com.soen341.instagram.utils.UserAccessor;

// Other libraries
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

@Service("pictureService")
public class PictureService
{
	private final static int MAX_RETRIES = 1000;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private PictureRepository pictureRepository;
	@Autowired
	private ModelMapper modelMapper;

	public Picture uploadPicture(String caption, MultipartFile picture, Account user)
	{
		Picture newPicture = new Picture();
		newPicture.setAccount(user);
		newPicture.setCaption(caption);
		newPicture.setCreated(new Date());

		File pictureFile;
		try 
		{
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(picture.getBytes()));

			if (image == null)
			{
				throw new NotAPictureException("Please upload a valid picture file.");
			}

			pictureFile = createNewFileWithUniqueName("./pictures/" + user.getUsername());
			BufferedImage compressedImage = compressImage(image);

			ImageIO.write(compressedImage, "jpg", pictureFile);
		}
		catch (IOException e)
		{
			throw new UnknownIOException("An unknown error occurred while trying to upload the picture.", e);
		}

		newPicture.setFilePath(pictureFile.getPath());
		pictureRepository.save(newPicture);
		
		return newPicture;
	}

	public PictureDTO getPictureDTOFromId(String id)
	{
		Picture pic = getPictureFromId(id);
		
		return toPictureDTO(pic);
	}

	public byte[] loadPicture(String id)
	{
		Picture pic = getPictureFromId(id);
		Path picPath = Paths.get(pic.getFilePath());
		
		if (!Files.exists(picPath))
		{
			throw new FileNotFoundException("The image file could not be found.");
		}
		
		try
		{
			byte[] pictureBytes = Files.readAllBytes(Paths.get(pic.getFilePath()));
			
			return pictureBytes;
		}
		catch (IOException e)
		{
			throw new UnknownIOException("An unknown error occurred while trying to access the picture.", e);
		}
	}

	public List<Long> getAccountPictures(Account account)
	{
		return pictureRepository.findByAccount(account).stream().map(pic -> pic.getId()).collect(Collectors.toList());
	}

	public PictureDTO toPictureDTO(Picture pic)
	{
		PictureDTO picDTO = modelMapper.map(pic, PictureDTO.class);
		picDTO.setAccount(pic.getAccount().getUsername());
		picDTO.setLikeCount(pic.getLikeCount());
		
		return picDTO;
	}

	private Picture getPictureFromId(String id)
	{
		long pictureId;
		
		try
		{
			pictureId = Long.valueOf(id);
		}
		catch (NumberFormatException e)
		{
			throw new InvalidIdException("Invalid picture ID.");
		}
		
		Optional<Picture> optionalPic = pictureRepository.findById(pictureId);
		
		if (!optionalPic.isPresent())
		{
			throw new PictureNotFoundException("The specified picture does not exist.");
		}
		
		return optionalPic.get();
	}

	private File createNewFileWithUniqueName(String directory) throws IOException
	{
		File pictureFile;
		int retries = 0;
		while (true)
		{
			if (retries >= MAX_RETRIES)
			{
				throw new UnknownIOException("Failed to upload picture.");
			}
			
			String currentDateString = String.valueOf(new Date().getTime());
			pictureFile = new File(directory + "/" + currentDateString + ".jpg");
			pictureFile.getParentFile().mkdirs(); // create parent directories if they don't exist
			
			if (pictureFile.createNewFile()) { // if file didn't exist previous and creation successful, exit loop
				return pictureFile;
			}
			
			retries++;
		}
	}

	private BufferedImage compressImage(BufferedImage image)
	{
		BufferedImage compressedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		// Change invisible pixels to white pixels (png to img)
		compressedImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
		
		return compressedImage;
	}

	public int likePicture(final String pictureId)
	{
		final Picture picture = getPictureFromId(pictureId);
		final Set<Account> likedBy = picture.getLikedBy();
		
		if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken))
		{
			final boolean liked = likedBy.add(UserAccessor.getCurrentAccount(accountRepository));
			
			if (!liked)
			{
				throw new MultipleLikeException("You can only like this picture once.");
			}
			
			pictureRepository.save(picture);
		}
		return picture.getLikeCount();
	}

	public int unlikePicture(final String pictureId)
	{
		final Picture picture = getPictureFromId(pictureId);
		final Set<Account> likedBy = picture.getLikedBy();
		final boolean unliked = likedBy.remove(UserAccessor.getCurrentAccount(accountRepository));
		
		if (!unliked)
		{
			throw new NoLikeException("You have not liked this picture yet.");
		}
		
		pictureRepository.save(picture);
		
		return picture.getLikeCount();
	}

	public boolean getLikeStatus(String pictureId)
	{
		if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken))
		{
			final Picture picture = getPictureFromId(pictureId);
			final Set<Account> likedBy = picture.getLikedBy();
			
			return likedBy.contains(UserAccessor.getCurrentAccount(accountRepository));
		}
		else
		{
			return false;
		}
	}
}
