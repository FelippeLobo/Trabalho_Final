package com.mycompany.trabalho;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 *
 * @author felip
 */
public class Trabalho {

    public static void main(String[] args) {
        try {
            // Caminho para diretorio de checagem e do banco de imagens editadas
            
            String editImagesDirectoriPath = "C:\\Users\\felip\\OneDrive\\Área de Trabalho\\Banco de Imagens";
            String imagesToCheckDirectoryPath = "C:\\Users\\felip\\OneDrive\\Área de Trabalho\\Input";
            // Criar o banco de dados simulado para armazenar imagens editadas (caminho, hash, assinatura)
            HashMap<String, String[]> storedImagesDb = new HashMap<>();

            // Carregar e processar as imagens do banco de imagens
            String[] editedImages = getAllFilesInDirectory(editImagesDirectoriPath);

            ImageHandler imageHandler = new ImageHandler();
            HashGenerator hashGenerator = new HashGenerator();
            DigitalSignature digitalSignature = new DigitalSignature();

            for (String imagePath : editedImages) {
                // Carregar a imagem
                BufferedImage image = imageHandler.loadImage(imagePath);

                // Converter imagem para bytes
                File imageFile = new File(imagePath);
                byte[] imageBytes = Files.readAllBytes(imageFile.toPath());

                // Gerar o hash da imagem
                String hash = hashGenerator.generateHash(imageBytes);
                System.out.println("Hash gerado para " + imageFile.getName() + ": " + hash);

                // Gerar a assinatura digital do hash
                String signature = digitalSignature.signHash(hash);
                System.out.println("Assinatura digital gerada para " + imageFile.getName() + ": " + signature);

                // Armazenar no "banco de dados" simulado
                storedImagesDb.put(imageFile.getName(), new String[]{hash, signature});
                System.out.println("_______________________________________________________________");
            }

            // Conjunto de imagens para verificar se fazem parte do banco de imagens
            String[] imagesToCheck = getAllFilesInDirectory(imagesToCheckDirectoryPath);

            System.out.println("=====================VERIFICACAO===============================");
            for (String imagePath : imagesToCheck) {
                // Carregar a imagem para verificação
                BufferedImage image = imageHandler.loadImage(imagePath);
                File imageFile = new File(imagePath);
                byte[] imageBytes = Files.readAllBytes(imageFile.toPath());

                // Gerar o hash da imagem verificada
                String imageHash = hashGenerator.generateHash(imageBytes);

                // Verificar se a imagem está armazenada no banco de dados
                boolean found = false;
                for (String storedImagePath : storedImagesDb.keySet()) {
                    String[] storedData = storedImagesDb.get(storedImagePath);
                    String storedHash = storedData[0];
                    String storedSignature = storedData[1];
                    
                    // Verifica o hash e a assinatura digital
                    if (imageHash.equals(storedHash) && digitalSignature.verifySignature(imageHash, storedSignature)) {
                        System.out.println("Hash gerado para " + imageFile.getName() + ": " + imageHash);
                        System.out.println("A imagem '" + imageFile.getName() + "' e identica a '" + storedImagePath + "' e foi validada.");
                        System.out.println("_______________________________________________________________");
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("Hash gerado para " + imageFile.getName() + ": " + imageHash);
                    System.out.println("A imagem '" + imageFile.getName() + "' nao foi encontrada no banco de dados.");
                    System.out.println("_______________________________________________________________");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] getAllFilesInDirectory(String directoryPath) {
        File directory = new File(directoryPath);

        if (directory.isDirectory()) {

            File[] files = directory.listFiles();

            if (files != null) {

                String[] filePaths = new String[files.length];

                for (int i = 0; i < files.length; i++) {
                    filePaths[i] = files[i].getAbsolutePath();
                }

                return filePaths;
            }
        }

        return new String[0];
    }

}
