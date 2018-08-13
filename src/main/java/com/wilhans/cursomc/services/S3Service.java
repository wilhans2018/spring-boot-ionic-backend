package com.wilhans.cursomc.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class S3Service {

	private Logger LOG = LoggerFactory.getLogger(S3Service.class);
	@Autowired
	private AmazonS3 amazonS3;

	@Value("${s3.bucket}")
	private String bucketName;

	public URI uploadFile(MultipartFile multiPartfile) {
		try {
			String fileName = multiPartfile.getOriginalFilename();
			InputStream is = multiPartfile.getInputStream();
			String contentType = multiPartfile.getContentType();
			return uploadFile(is, fileName, contentType);
		} catch (IOException e) {
			throw new RuntimeException("Erro de IO:" + e.getMessage());			
		}

	}

	public URI uploadFile(InputStream is, String fileName, String contentType) {
		try {

			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(contentType);
			LOG.info("iniciando upload");
			amazonS3.putObject(bucketName, fileName, is, meta);
			LOG.info("finalizando upload");
			return amazonS3.getUrl(bucketName, fileName).toURI();

		} catch (URISyntaxException e) {
			throw new RuntimeException("Erro ao converter URL para URI");

		}

	}

}
