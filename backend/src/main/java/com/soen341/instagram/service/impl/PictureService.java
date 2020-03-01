package com.soen341.instagram.service.impl;

import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.dao.impl.PictureRepository;
import com.soen341.instagram.dto.picture.PictureDTO;
import com.soen341.instagram.exception.like.MultipleLikeException;
import com.soen341.instagram.exception.like.NoLikeException;
import com.soen341.instagram.exception.picture.*;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Picture;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

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
public class PictureService {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	@Qualifier("UserDetailsService")
	private UserDetailsService userDetailsService;
	
    private final static int MAX_RETRIES = 1000;

    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private ModelMapper modelMapper;


    public Picture uploadPicture(String caption, MultipartFile picture, Account user) {
        Picture newPicture = new Picture();
        newPicture.setAccount(user);
        newPicture.setCaption(caption);
        newPicture.setCreated(new Date());

        File pictureFile;
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(picture.getBytes()));

            if (image == null) {
                throw new NotAPictureException("Please upload a valid picture file.");
            }

            // create a file for the picture with a unique file name
            pictureFile = createNewFileWithUniqueName("./pictures/" + user.getUsername());
            BufferedImage compressedImage = compressImage(image);

            // save compressed image to file
            ImageIO.write(compressedImage, "jpg", pictureFile);
        } catch (IOException e) {
            throw new UnknownIOException("An unknown error occurred while trying to upload the picture.", e);
        }

        newPicture.setFilePath(pictureFile.getPath());
        pictureRepository.save(newPicture);
        return newPicture;
    }

    public PictureDTO getPictureDTOFromId(String id) {
        Picture pic = getPictureFromId(id);
        return toPictureDTO(pic);
    }

    public byte[] loadPicture(String id) {
        Picture pic = getPictureFromId(id);
        Path picPath = Paths.get(pic.getFilePath());
        if (!Files.exists(picPath)) {
            throw new FileNotFoundException("The image file could not be found.");
        }
        try {
            byte[] pictureBytes = Files.readAllBytes(Paths.get(pic.getFilePath()));
            return pictureBytes;
        } catch (IOException e) {
            throw new UnknownIOException("An unknown error occurred while trying to access the picture.", e);
        }
    }

    public List<Long> getAccountPictures(Account account) {
        return pictureRepository.findByAccount(account).stream().map(pic -> pic.getId()).collect(Collectors.toList());
    }

    public PictureDTO toPictureDTO(Picture pic) {
        PictureDTO picDTO = modelMapper.map(pic, PictureDTO.class);
        picDTO.setAccount(pic.getAccount().getUsername());
        return picDTO;
    }

    private Picture getPictureFromId(String id) {
        long pictureId;
        try {
            pictureId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new InvalidIdException("Invalid picture ID.");
        }
        Optional<Picture> optionalPic = pictureRepository.findById(pictureId);
        if (!optionalPic.isPresent()) {
            throw new PictureNotFoundException("The specified picture does not exist.");
        }
        return optionalPic.get();
    }
    
    private File createNewFileWithUniqueName(String directory) throws IOException {
        File pictureFile;
        int retries = 0;
        while (true) {
            if (retries >= MAX_RETRIES) {
                throw new UnknownIOException("Failed to upload picture.");
            }
            String currentDateString = String.valueOf(new Date().getTime());
            pictureFile = new File(directory + "/" + currentDateString + ".jpg");
            pictureFile.getParentFile().mkdirs(); // create parent directories if they don't exist
            if (pictureFile.createNewFile()) {
                // if we successfully create the file (i.e. file did not exist previously), then exit loop
                return pictureFile;
            }
            retries++;
        }
    }

    private BufferedImage compressImage(BufferedImage image) {
        BufferedImage compressedImage = new BufferedImage(image.getWidth(),
                image.getHeight(), BufferedImage.TYPE_INT_RGB);
        // change invisible pixels to white pixels (png to img)
        compressedImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
        return compressedImage;
    }
    
    // like service
	public int likePicture(@PathVariable final long pictureId) {
		final Picture picture = getPictureFromId(Long.toString(pictureId));
		final Set<Account> likedBy = picture.getLikedBy();
		final boolean liked = likedBy.add(getCurrentUser());
		
		if (!liked) {
			throw new MultipleLikeException("You can only like this picture once.");
		}
		
		pictureRepository.save(picture);
		
		return picture.getLikeCount();
		
	}
	
	public int unlikePicture(@PathVariable final long pictureId) {
		final Picture picture = getPictureFromId(Long.toString(pictureId));
		final Set<Account> likedBy = picture.getLikedBy();
		final boolean unliked = likedBy.remove(getCurrentUser());
		
		if (!unliked) {
			throw new NoLikeException("You have not liked this picture yet.");
		}
		
		pictureRepository.save(picture);
		
		return picture.getLikeCount();
		
	}
	
	private Account getCurrentUser()
	{
		
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails)
		{
			String username = userDetailsService.loadUserByUsername(((UserDetails) principal).getUsername())
					.getUsername();
			return accountRepository.findByUsername(username);
		}
		else
		{
			throw new IllegalStateException("No user authenticated");
		}
	}
	
}
