package com.soen341.instagram.service.impl;

import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.dao.impl.PictureRepository;
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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;

@Service("pictureService")
public class PictureService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PictureRepository pictureRepository;

    @Transactional
    public void uploadPicture(String caption, MultipartFile picture) {
        if (picture.isEmpty()) {
            // error
        }

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = ((UserDetails)authentication.getPrincipal()).getUsername();

            Picture newPicture = new Picture();
            newPicture.setAccount(accountRepository.findByUsername(username));
            newPicture.setCaption(caption);

            File pictureFile;
            Date currentDate;
            // find a name for the file that is available
            while (true) {
                currentDate = new Date();
                String currentDateString = String.valueOf(currentDate.getTime());
                pictureFile = new File("./pictures/" + username + "/" + currentDateString + ".jpg");
                pictureFile.getParentFile().mkdirs(); // create parent directories if they don't exist
                if(pictureFile.createNewFile()) {
                    // if we successfully create the file (i.e. file did not exist previously)
                    break;
                }
            }
            newPicture.setFilePath(pictureFile.getPath());
            newPicture.setCreated(currentDate);

            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(picture.getBytes()));
            ImageIO.write(bufferedImage, "jpg", pictureFile);
            pictureRepository.save(newPicture);
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
            File picFile = new File(pic.getFilePath());
            if (!picFile.exists()) {
                throw new FileNotFoundException("The image could not be found.");
            }
            byte[] pictureBytes = Files.readAllBytes(Paths.get(pic.getFilePath()));
            return pictureBytes;
        } catch (IOException e) {
            throw new UnknownIOException("An unknown error occurred.", e);
        }
    }
}
