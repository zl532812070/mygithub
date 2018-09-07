package com.kindeditor.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class UploadJson
 */
public class UploadJson extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 文件保存目录路径
	private String savePath;
	// 定义允许上传的文件扩展名
	private HashMap<String, String> extMap;
	// 最大文件大小
	private long maxSize = 1000000;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadJson() {
		super();
		// TODO Auto-generated constructor stub
		System.out.println("UploadJson.UploadJson()");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Content-type", "text/html;charset=UTF-8");  
		PrintWriter out = response.getWriter();
		// 文件保存目录URL
		String saveUrl  = request.getContextPath() + "/attached/";
		String savePathClone = savePath;
		if (!ServletFileUpload.isMultipartContent(request)) {
			out.write(getError("请选择文件。"));
			return;
		}
		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if (!extMap.containsKey(dirName)) {
			out.write(getError("目录名不正确。"));
			return;
		}
		// 创建文件夹
		savePathClone =savePath + "/" + dirName + "/";
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePathClone);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePathClone += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePathClone);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List items = null;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Iterator itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			String fileName = item.getName();
			long fileSize = item.getSize();
			if (!item.isFormField()) {
				// 检查文件大小
				if (item.getSize() > maxSize) {
					out.write(getError("上传文件大小超过限制。"));
					return;
				}
				// 检查扩展名
				String fileExt = fileName.substring(
						fileName.lastIndexOf(".") + 1).toLowerCase();
				if (!Arrays.<String> asList(extMap.get(dirName).split(","))
						.contains(fileExt)) {
					out.write(getError("上传文件扩展名是不允许的扩展名。\n只允许"
							+ extMap.get(dirName) + "格式。"));
					return;
				}

				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String newFileName = df.format(new Date()) + "_"
						+ new Random().nextInt(1000) + "." + fileExt;
				try {
					File uploadedFile = new File(savePathClone, newFileName);
					item.write(uploadedFile);
				} catch (Exception e) {
					out.write(getError("上传文件失败。"));
					return;
				}

				JSONObject obj = new JSONObject();
				obj.put("error", 0);
				obj.put("url", saveUrl + newFileName);
				out.write(obj.toJSONString());
			}
		}
	}

	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toJSONString();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("UploadJson.init()");
		super.init(config);
		savePath = config.getServletContext().getRealPath("/") + "attached";

		extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

	}

}
