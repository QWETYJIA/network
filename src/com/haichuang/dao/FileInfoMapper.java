package com.haichuang.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.haichuang.bean.FileInfo;

public interface FileInfoMapper {

	int addFileInfo(FileInfo fileInfo);

	public List<FileInfo> findByPager(Map<String, Object> params);

	public List<FileInfo> findFiles();
	public long count();
	List<FileInfo> findFile(@Param(value = "fileName") String fileName,
			@Param(value = "filesname") String filesname,
			@Param(value = "pkgname") String pkgname,
			@Param(value = "versioncode") String versioncode,
			@Param(value = "fileUrl") String fileUrl,
			@Param(value = "versionname") String versionname);
	FileInfo findFileById(Integer fileId);
}