package com.fulljob.api.utils;

import com.jcraft.jsch.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.io.InputStream;

public class SFTPUtils {

    private static final String HOST = "ftp.fulljob.paulopezdev.com";
    private static final String USERNAME = "curriculumsftp";
    private static final String PASSWORD = "contraseña_sftp";  // Reemplaza por la contraseña real
    private static final int PORT = 22;

    public static String uploadToSFTP(MultipartFile curriculum, String nombreArchivo) {
        // Validamos que el archivo sea un PDF
        String tipoArchivo = curriculum.getContentType();
        if (!tipoArchivo.equals("application/pdf")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo se permiten archivos PDF.");
        }

        String host = "ftp.fulljob.paulopezdev.com";
        String username = "curriculumsftp";
        String password = ""; // Reemplazar por la contraseña real

        try {
            // Creamos una nueva sesión SFTP
            JSch jsch = new JSch();
            Session session = jsch.getSession(username, host, 22);
            session.setPassword(password);

            // Deshabilitamos la comprobación de clave del servidor
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            // Abrimos un canal SFTP
            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            // Subimos el archivo al servidor SFTP
            String remoteFilePath = "/curriculums/" + nombreArchivo;
            InputStream inputStream = curriculum.getInputStream();
            channelSftp.put(inputStream, remoteFilePath); // Subimos el archivo

            // Cerramos el canal SFTP y la sesión
            channelSftp.exit();
            session.disconnect();

            return remoteFilePath; // Devolvemos la ruta donde se guardó el archivo en el servidor SFTP
        } catch (JSchException | SftpException | IOException e) {
            // Si ocurre algún error, lanzamos una excepción HTTP con el error
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al subir el archivo", e);
        }
    
}
}
