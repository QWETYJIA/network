package com.haichuang.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haichuang.bean.FileInfo;
import com.haichuang.bean.Pager;
import com.haichuang.dao.FileInfoMapper;
import com.haichuang.service.FileInfoService;

@Service("FileInfoService")
public class FileInfoServiceImpl implements FileInfoService {
	@Autowired
	private FileInfoMapper dao;
	@Override
	public int addFileInfo(FileInfo fileInfo) {
		int i=0;
		i=dao.addFileInfo(fileInfo);
		return i;
	}

	
	public List<FileInfo> findFile(int page,int size) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("page", (page-1)*size);

		params.put("size", size);

		Pager<FileInfo> pager = new Pager<FileInfo>();

		List<FileInfo> list = dao.findByPager(params);
		for (FileInfo discipline : list) {
			System.out.println(discipline);
		}

		pager.setRows(list);

		pager.setTotal(dao.count());

		return  list;
		
	}
	
	@Override
	public FileInfo findFileById(Integer fileId) {
		return dao.findFileById(fileId);
	}


	public void setDao(FileInfoMapper dao) {
		this.dao = dao;
	}

	@Override
	public List<FileInfo> findFile() {
		return dao.findFiles();
	}


	


	@Override
	public List<FileInfo> findFile(String fileName, String filesname, String pkgname, String versioncode,
			String fileUrl, String versionname) {
		return dao.findFile(fileName, filesname, pkgname, versioncode, fileUrl, versionname);
	}


	

}
