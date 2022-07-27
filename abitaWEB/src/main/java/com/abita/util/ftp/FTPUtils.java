/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.util.ftp;

import com.abita.util.exceptions.UtilException;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Proxy;
import com.jcraft.jsch.ProxyHTTP;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;

/**
 * Classe utilitaire pour la connexion et l'upload de fichier sur un FTP
 * @author
 *
 */
public final class FTPUtils {

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(FTPUtils.class);

  /**
   * Constructeur privé
   */
  private FTPUtils() {
  }

  /**
   * Permet de se connecter à un FTP
   * @param host nom de l’hôte
   * @param port port de l’hôte
   * @param user nom d’utilisateur
   * @param pwd mot de passe
   * @param proxyAddress Adresse du proxy
   * @param proxyPort Port du proxy
   * @return encapsulation des fonctionnalités de manipulation du FTP
   * @throws UtilException une UtilException
   */
  public static Channel connection(String host, String port, String user, String pwd, String proxyAddress, String proxyPort) throws UtilException {
    try {
      JSch jsch = new JSch();
      LOGGER.info("DEBUT : Création de la session");
      Session session = jsch.getSession(user, host);
      session.setPassword(pwd);
      LOGGER.info("FIN : Création de la session");

      LOGGER.info("DEBUT : Mise en place du port");
      if (port != null && Pattern.matches("-?[0-9]+", port)) {
        session.setPort(Integer.parseInt(port));
      }
      LOGGER.info("FIN : Mise en place du port");

      LOGGER.info("DEBUT : Mise en place du proxy");
      if (proxyAddress != null && proxyPort != null && Pattern.matches("-?[0-9]+", proxyPort)) {
        Proxy proxy = new ProxyHTTP(proxyAddress, Integer.parseInt(proxyPort));
        session.setProxy(proxy);
      }
      LOGGER.info("FIN : Mise en place du proxy");

      LOGGER.info("DEBUT : Connexion de la session");
      session.setConfig("StrictHostKeyChecking", "no");
      session.connect();
      LOGGER.info("FIN : Connexion de la session");

      LOGGER.info("DEBUT : Ouverture du flux");
      Channel channel = session.openChannel("sftp");
      LOGGER.info("FIN : Ouverture du flux");
      LOGGER.info("DEBUT : Connexion au flux");
      channel.connect();
      LOGGER.info("FIN : Connexion au flux");

      return channel;
    } catch (JSchException e) {
      LOGGER.error("Erreur survenue lors de la connexion au ftp", e);
      throw new UtilException(e);
    }
  }

  /**
   * Permet d’envoyer un fichier sur le FTP
   * @param channel encapsulation des informations du FTP
   * @param localFile fichier à envoyer
   * @param newFileName nom du fichier final
   * @param hostDir chemin du dossier où placer le fichier
   */
  public static void uploadFile(Channel channel, File localFile, String newFileName, String hostDir) {
    String finalHostDir = hostDir;
    if (hostDir == null) {
      finalHostDir = "/";
    }
    try {
      LOGGER.info("DEBUT : Creation du fichier");
      FileInputStream input = new FileInputStream(localFile);
      ChannelSftp channelSftp = (ChannelSftp) channel;
      LOGGER.info("FIN : Creation du fichier");
      LOGGER.info("DEBUT : Envoi du fichier au sftp");
      channelSftp.put(input, finalHostDir + newFileName);
      LOGGER.info("FIN : Envoi du fichier au sftp");
    } catch (FileNotFoundException e) {
      LOGGER.error("Erreur survenue lors de la création du fichier", e);
    } catch (SftpException e) {
      LOGGER.error("Erreur survenue lors de l'envoi du fichier", e);
    }
  }

  /**
   * Permet de se déconnecter du FTP
   * @param channel encapsulation des informations du FTP
   */
  public static void disconnect(Channel channel) {
    try {
      if (channel.isConnected()) {
        channel.disconnect();
        channel.getSession().disconnect();
      }
    } catch (JSchException e) {
      LOGGER.error("Erreur survenue lors de la déconnexion", e);
    }
  }

}
