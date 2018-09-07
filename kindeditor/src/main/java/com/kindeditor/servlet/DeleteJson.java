package com.kindeditor.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteJson
 */
public class DeleteJson extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String tomcatPath;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteJson() {
		super();
		// TODO Auto-generated constructor stub
		System.out.println("DeleteJson.DeleteJson()");
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
		String CrrentDelUrl = request.getParameter("参数项1");
		String CurrentIsDir = request.getParameter("参数项2");

		File deleteFile = new File(tomcatPath + CrrentDelUrl);

		System.out.println(CurrentIsDir);
		if ("D".equals(CurrentIsDir)) {// 目录
			deleteDir(deleteFile);
		} else {// 文件
			deleteFile.delete();
		}
		out.write("succeed");
	}

	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
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
		System.out.println("DeleteJson.init()");
		super.init(config);
		String projectPath = config.getServletContext().getRealPath("");
		tomcatPath = projectPath.substring(0, projectPath.lastIndexOf("\\"));
	}

}
