package com.taotao.service.lmpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.pojo.PictureResult;
import com.taotao.common.utils.FastDFSClient;

@Service
public class PictureServiceImpl implements com.taotao.service.PictureService {

	@Value("${IMAGE_SERVER_BASE_URL}")
	private String IMAGE_SERVER_BASE_URL;
	
	@Override
	public PictureResult uploadPic(MultipartFile picFile) {
		PictureResult result = new PictureResult();
		
		if(picFile == null) {
			result.setError(1);
			result.setMessage("图片为空！");
		}
		try {
			//获取图片扩展名
			String filename = picFile.getOriginalFilename();
			String extName = filename.substring(filename.lastIndexOf(".")+1);
			
			FastDFSClient dfsClient = new FastDFSClient("classpath:properties/fdfs_client.conf");
			String url = IMAGE_SERVER_BASE_URL + dfsClient.uploadFile(picFile.getBytes(), extName);
			result.setError(0);
			result.setUrl(url);
		} catch (Exception e) {
			result.setError(1);
			result.setMessage("图片上传失败！");
			e.printStackTrace();
		}
		return result;
	}

}
