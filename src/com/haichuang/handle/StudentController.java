package com.haichuang.handle;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.haichuang.bean.FileInfo;
import com.haichuang.bean.Login;
import com.haichuang.service.FileInfoService;
import com.haichuang.service.ILoginService;
import com.haichuang.util.FileUtil;
import com.haichuang.util.QRCodeUtil;
import com.haichuang.util.apkUitls;

@Controller
@RequestMapping("/oneself")
public class StudentController {

	@Autowired
	@Qualifier("loginService")
	private ILoginService service;

	public void setService(ILoginService service) {
		this.service = service;
	}
	@Autowired
	@Qualifier("FileInfoService")
	private FileInfoService fileInfoService;

	public void setFileInfoService(FileInfoService fileInfoService) {
		this.fileInfoService = fileInfoService;
	}
	// 输入登录的信息
		@RequestMapping("/login.do")
		public ModelAndView dologin( Login stu) {
			System.out.println(stu);
			Login student = service.selectByUsername(stu);
			ModelAndView mv = new ModelAndView();
			mv.setView(new MappingJackson2JsonView());
			if (student == null) {
				String result = "账号密码错误";
				mv.addObject("result", result);
				return mv;
			}
			String result = "登陆成功！";
			mv.addObject("result", result);
			return mv;
		}

		// 输入注册的信息
		@RequestMapping("/register.do")
		public ModelAndView doRegister(Login stu) {
			System.out.print(stu);
			int student = service.insert(stu);
			ModelAndView mv = new ModelAndView();
			mv.setView(new MappingJackson2JsonView());
			if (student == 0) {
				String result = "注册失败";
				mv.addObject("result", result);
				return mv;
			}
			String result = "注册成功！";
			mv.addObject("result", result);
			return mv;
		}
	

	@RequestMapping("/files.do")
	public ModelAndView files(int page,int size) {

		ModelAndView mv = new ModelAndView();
		mv.setView(new MappingJackson2JsonView());
		List<FileInfo> files = fileInfoService.findFile(page,size);
		mv.addObject("files", files);

		return mv;
	}
	@RequestMapping("/file.do")
	public ModelAndView file() {

		ModelAndView mv = new ModelAndView();
		mv.setView(new MappingJackson2JsonView());
		List<FileInfo> files = fileInfoService.findFile();
		mv.addObject("files", files);

		return mv;
	}
	@RequestMapping("/fileother.do")
	public ModelAndView file(@RequestParam(value = "fileName", required = false) String fileName,
			@RequestParam(value = "filesname", required = false) String filesname,
			@RequestParam(value = "pkgname", required = false) String pkgname,
			@RequestParam(value = "versioncode", required = false) String versioncode,
			@RequestParam(value = "fileUrl", required = false) String fileUrl,
			@RequestParam(value = "versionname", required = false) String versionname) {

		ModelAndView mv = new ModelAndView();
		mv.setView(new MappingJackson2JsonView());
		List<FileInfo> files = fileInfoService.findFile(fileName, filesname, pkgname, versioncode, fileUrl, versionname);
		mv.addObject("files", files);

		return mv;
	}

	@RequestMapping("/upload.do")
	@ResponseBody
	public ModelAndView upload(@RequestParam("file") MultipartFile uploadFile, HttpServletRequest request) throws IOException {
		System.out.println("uploadFile = " + uploadFile);
		// 获得文件
		byte[] buf = uploadFile.getBytes();
		System.out.println("文件长度" + buf.length);
		// 文件名
		System.out.println("文件名getName = " + uploadFile.getName());
		System.out.println("文件名getOriginalFilename = " + uploadFile.getOriginalFilename());

		ModelAndView mv = new ModelAndView();
		mv.setView(new MappingJackson2JsonView());

		// 文件名 a.txt
		String originalFileName = uploadFile.getOriginalFilename();
		// 文件名 a.txt
		String textname = null;
		String name = originalFileName.substring(originalFileName.length()-3, originalFileName.length());
		String ap="apk";
		System.out.println(ap==name);
		if(ap.equals(name)) {
			textname="===";
		}
		if (textname == null) {
			String result = "上传的文件格式不正确!!!";
			mv.addObject("result", result);
			System.out.println("=======================");

			System.out.println(textname);
			return mv;
		}
		// 时间戳
		String time = FileUtil.createFileTimestamp();
		// 文件url /upload/1231231231231a.txt
		String fileUrl = "/apk/" + time + originalFileName;
		fileUrl = request.getServletContext().getRealPath(fileUrl);
		System.out.println("fileUrl = " + fileUrl);
		// 向url地址存储文件
		FileUtil.writeFileToUrl(uploadFile, fileUrl);
		File file= new File(uploadFile.getOriginalFilename());
        FileUtils.copyInputStreamToFile(uploadFile.getInputStream(),file);
        apkUitls files=new apkUitls();
        Map<String,Object> map=new HashMap<String,Object>();
        map=files.readAPK(file);
        Object filesname=map.get((String) "filename");
        Object pkgname=map.get( "pkgname");
        Object versioncode =map.get("versioncode");
        Object versionname=map.get("versionname");
		// 向数据库中保存文件信息
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileName(originalFileName);
		fileInfo.setFileUrl(fileUrl);
		fileInfo.setFilesname(filesname.toString());
		fileInfo.setPkgname( pkgname.toString());
		fileInfo.setVersioncode(versioncode.toString());
		fileInfo.setVersionname(versionname.toString());
       
        mv.addObject("map", map);
		
		int i=fileInfoService.addFileInfo(fileInfo);
		String user = "不成功！";
		if (i == 1) {
			user = "上传成功！";
		} 
		mv.addObject("user", user);
		return mv;
	}

	@RequestMapping("/download.do")
	public ResponseEntity<byte[]> download(Integer fileId) {
		
		try {
			FileInfo fileInfo = fileInfoService.findFileById(fileId);
			String fileUrl = fileInfo.getFileUrl();
			String fileName = fileInfo.getFileName();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDispositionFormData("attachment", fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

			ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(fileUrl)),
					headers, HttpStatus.CREATED);
			return entity;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping("/search.do")
	public ModelAndView search(Integer fileId) {
		ModelAndView mv = new ModelAndView();
		mv.setView(new MappingJackson2JsonView());
		FileInfo fileInfo = fileInfoService.findFileById(fileId);
	    mv.addObject("fileInfo", fileInfo);
		return mv;
	}
	
	@RequestMapping(value = "/createCommonQRCode")
    public void createCommonQRCode(HttpServletResponse response, Integer fileId) throws Exception {
        ServletOutputStream stream = null;
       
        try {
        	FileInfo fileInfo = fileInfoService.findFileById(fileId);
			String url = fileInfo.getFileUrl();
            stream = response.getOutputStream();
            //使用工具类生成二维码
            QRCodeUtil.encode(url, stream);
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }
}