package kr.ne.abc.template.common.wysiwyg;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

@RestController
public class SummernoteController {
	
	/** resourceLoader **/
	@Autowired
	private ResourceLoader resourceLoader;
	
	/**
	 * uploadSummernoteImageFile
	 * @param locale
	 * @param articleDTO
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/summernote/uploadSummernoteImageFile")
	public String uploadSummernoteImageFile(
			Locale locale
			, @RequestParam(value="file_url", required=true) String file_url
			, @RequestParam(value="file", required=true) MultipartFile multipartFile) throws IOException {
		
		JsonObject jsonObject = new JsonObject();
		
		String contextRoot, fileRoot;
		
//		String fileUrl = "/upload/article/"+ articleDTO.getArticle_category_name() + "/" + articleDTO.getArticle_code() + "/editor/";
		
		String fileUrl = file_url;
		contextRoot = resourceLoader.getResource("classpath:/static").getURI().getPath();
		fileRoot = contextRoot + fileUrl;
		
		String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
		String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명
		File targetFile = new File(fileRoot + savedFileName);	
		
		try {
			InputStream fileStream = multipartFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
			jsonObject.addProperty("url", fileUrl+savedFileName);
			jsonObject.addProperty("responseCode", "success");

		} catch (IOException e) {
			
			FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
			jsonObject.addProperty("responseCode", "error");
			//e.printStackTrace();
			//System.out.println(e.getMessage().toString());
		}
		
		String a = jsonObject.toString();

		return a;
	}

	/**
	 * deleteSummernoteImageFile
	 * @param locale
	 * @param articleDTO
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/summernote/deleteSummernoteImageFile")
	public String deleteSummernoteImageFile(
			Locale locale
			, @RequestParam(value="file_url", required=true) String file_url
			, @RequestParam(value="delete_filename", required=true) String delete_filename) throws IOException {
		
		String contextRoot, fileRoot;
		
//		String fileUrl = "/upload/article/"+ articleDTO.getArticle_category_name() + "/" + articleDTO.getArticle_code() + "/editor/";
		String fileUrl = file_url;
		contextRoot = resourceLoader.getResource("classpath:/static").getURI().getPath();
		fileRoot = contextRoot + fileUrl;
		
		//기존파일 지우기
		if(delete_filename != null) {
				File deleteFile = new File(fileRoot + delete_filename);
				if(deleteFile.exists()) {
					if(deleteFile.delete()) {
						//System.out.println("파일삭제성공");
					}else {
						//System.out.println("파일삭제실패");
					}
				}else {
					//System.out.println("해당파일이 존재하지 않습니다.");
				}
		} else {
			//System.out.println("값이 없음");
		}

		return "성공";
	}
	
}
