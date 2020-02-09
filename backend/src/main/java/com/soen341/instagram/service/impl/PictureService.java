package com.soen341.instagram.service.impl;

import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.dao.impl.PictureRepository;
import com.soen341.instagram.exception.picture.FileNotFoundException;
import com.soen341.instagram.exception.picture.NotAPictureException;
import com.soen341.instagram.exception.picture.PictureNotFoundException;
import com.soen341.instagram.exception.picture.UnknownIOException;
import com.soen341.instagram.model.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;

@Service("pictureService")
public class PictureService {

    private final static int MAX_RETRIES = 1000;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PictureRepository pictureRepository;

    @Transactional
    public void uploadPicture(String caption, MultipartFile picture) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = ((UserDetails)authentication.getPrincipal()).getUsername();

            Picture newPicture = new Picture();
            newPicture.setAccount(accountRepository.findByUsername(username));
            newPicture.setCaption(caption);
            System.out.println(picture.getBytes().length);
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(picture.getBytes()));

            if (bufferedImage == null) {
                throw new NotAPictureException("Please upload a valid picture file.");
            }

            File pictureFile;
            Date currentDate;

            // find a name for the file that is available
            int retries = 0;
            while (true) {
                if (retries >= MAX_RETRIES) {
                    throw new UnknownIOException("Could not upload picture");
                }
                currentDate = new Date();
                String currentDateString = String.valueOf(currentDate.getTime());
                pictureFile = new File("./pictures/" + username + "/" + currentDateString + ".jpg");
                pictureFile.getParentFile().mkdirs(); // create parent directories if they don't exist
                if(pictureFile.createNewFile()) {
                    // if we successfully create the file (i.e. file did not exist previously), then exit loop
                    break;
                }
                retries++;
            }
            newPicture.setFilePath(pictureFile.getPath());
            newPicture.setCreated(currentDate);

            // compress image
            BufferedImage compressedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            // change invisible pixels to white pixels (png to img)
            compressedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

            ImageIO.write(compressedImage, "jpg", pictureFile);
            pictureRepository.save(newPicture);
            System.out.println(pictureFile.length());
        } catch (IOException e) {
            throw new UnknownIOException("An unknown error occurred.", e);
        }
    }

    public byte[] loadPicture(long id) {
        Optional<Picture> optionalPic = pictureRepository.findById(id);
        if (!optionalPic.isPresent()) {
            throw new PictureNotFoundException("The specified picture does not exist.");
        }
        try {
            Picture pic = optionalPic.get();
            Path picPath = Paths.get(pic.getFilePath());
            if (!Files.exists(picPath)) {
                throw new FileNotFoundException("The image file could not be found.");
            }
            byte[] pictureBytes = Files.readAllBytes(Paths.get(pic.getFilePath()));
            return pictureBytes;
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnknownIOException("An unknown error occurred.", e);
        }
    }
}
