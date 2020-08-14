package com.haichuang.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.haichuang.bean.FileInfo;
import com.haichuang.bean.Pager;

public interface FileInfoService {

	int addFileInfo(FileInfo fileInfo);

	List<FileInfo> findFile();

	FileInfo findFileById(Integer fileId);

	List<FileInfo> findFile(String fileName, String filesname, String pkgname,String versioncode, String fileUrl,String versionname);
	
	List<FileInfo> findFile(int page, int size);
}