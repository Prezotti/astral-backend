package astral.astralbackend.service;

import astral.astralbackend.entity.Produtor;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class StorageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    public String uploadImagemProduto(MultipartFile file, Produtor produtor){
        File fileObject = convertMultiPartFileToFile(file);
        String fileName = "produtor_" + produtor.getId() + "/" +file.getOriginalFilename()+"_"+System.currentTimeMillis();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObject));
        fileObject.delete();
        String fileUrl = s3Client.getUrl(bucketName, fileName).toString();
        return fileUrl;
    }

    private File convertMultiPartFileToFile(MultipartFile file){
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)){
            fos.write(file.getBytes());
        }catch (IOException e){
            System.out.println("Erro convertendo o aquivo multipartFile para File: " + e);
        }
        return convertedFile;
    }

}
