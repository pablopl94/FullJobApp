package com.fulljob.api.utils;

import com.jcraft.jsch.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SFTPUtils {

    private static final String HOST = "ftp.fulljob.paulopezdev.com";
    private static final String USERNAME = "curriculumsftp";
    private static final String PASSWORD = "tidus1994";  // Reemplaza por la contraseña real
    private static final int PORT = 22;

    private static final Logger logger = LoggerFactory.getLogger(SFTPUtils.class);

    public static String uploadToSFTP(MultipartFile curriculum, String nombreArchivo) {
        logger.info("Iniciando carga del archivo: {}", nombreArchivo);
        
        // Validamos que el archivo sea un PDF
        String tipoArchivo = curriculum.getContentType();
        if (!tipoArchivo.equals("application/pdf")) {
            logger.error("El archivo no es un PDF: {}", tipoArchivo);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo se permiten archivos PDF.");
        }

        try {
            // Creamos una nueva sesión SFTP
            logger.info("Conectando al servidor SFTP: {}", HOST);
            JSch jsch = new JSch();
            Session session = jsch.getSession(USERNAME, HOST, PORT);
            session.setPassword(PASSWORD);

            // Deshabilitamos la comprobación de clave del servidor
            session.setConfig("StrictHostKeyChecking", "no");
            logger.info("Conexión SFTP establecida con éxito.");
            session.connect();

            // Abrimos un canal SFTP
            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            logger.info("Canal SFTP abierto.");

            // Subimos el archivo al servidor SFTP
            String remoteFilePath = "curriculums/" + nombreArchivo;
            logger.info("Subiendo el archivo al servidor SFTP en la ruta: {}", remoteFilePath);
            InputStream inputStream = curriculum.getInputStream();
            channelSftp.put(inputStream, remoteFilePath); // Subimos el archivo

            // Cerramos el canal SFTP y la sesión
            channelSftp.exit();
            session.disconnect();
            logger.info("Archivo subido correctamente y sesión cerrada.");

            return remoteFilePath; // Devolvemos la ruta donde se guardó el archivo en el servidor SFTP

        } catch (JSchException | SftpException | IOException e) {
            logger.error("Error al subir el archivo: ", e);
            // Si ocurre algún error, lanzamos una excepción HTTP con el error
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al subir el archivo", e);
        }
    }
}
