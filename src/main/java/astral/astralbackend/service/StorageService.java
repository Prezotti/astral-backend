package astral.astralbackend.service;

import astral.astralbackend.entity.Produtor;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Value("${application.storage.local:true}")
    private boolean storageLocal;

    @Autowired(required = false)
    private AmazonS3 s3Client;

    public String uploadImagemProduto(MultipartFile file, Produtor produtor) {
        String fileName = "produtor_" + produtor.getId() + "/" + file.getOriginalFilename() + "_" + System.currentTimeMillis();

        if (storageLocal) {
            return uploadLocal(file, fileName);
        } else {
            return uploadS3(file, fileName);
        }
    }

    private String uploadLocal(MultipartFile file, String fileName) {
        try {
            Path uploadsDir = Paths.get("uploads").toAbsolutePath().normalize();
            Path filePath = uploadsDir.resolve(fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
            return "/uploads/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem localmente: " + e.getMessage());
        }
    }

    private String uploadS3(MultipartFile file, String fileName) {
        File fileObject = convertMultiPartFileToFile(file);
        try {
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObject));
            String fileUrl = s3Client.getUrl(bucketName, fileName).toString();
            return fileUrl;
        } finally {
            fileObject.delete();
        }
    }

    public String deleteImagemProduto(String urlArquivo) {
        if (storageLocal) {
            return deleteLocal(urlArquivo);
        } else {
            return deleteS3(urlArquivo);
        }
    }

    private String deleteLocal(String urlArquivo) {
        try {
            String path = urlArquivo.startsWith("/uploads/") ? urlArquivo.substring(1) : urlArquivo.replaceFirst(".*/uploads/", "uploads/");
            Path filePath = Paths.get(path).toAbsolutePath().normalize();
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
            return path;
        } catch (IOException e) {
            System.out.println("Erro ao deletar imagem local: " + e.getMessage());
            return null;
        }
    }

    private String deleteS3(String urlArquivo) {
        try {
            URL url = new URL(urlArquivo);
            String key = url.getPath().substring(1);
            s3Client.deleteObject(new DeleteObjectRequest(bucketName, key));
            return key;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            System.out.println("Erro convertendo o aquivo multipartFile para File: " + e);
        }
        return convertedFile;
    }

}
