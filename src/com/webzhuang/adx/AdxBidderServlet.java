package com.webzhuang.adx;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.portable.OutputStream;

/**
 * Servlet implementation class AdxBidderServlet
 */
@WebServlet("/AdxBidder")
public class AdxBidderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public AdxBidderServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String host = request.getRemoteHost();
		int port = request.getRemotePort();
		
		Cookie cookie = new Cookie("agid", "zelper");
		cookie.setMaxAge(3600);
		response.addHeader("Content-Encoding", "gzip");
		response.setContentType("text/html;charset=utf-8"); 
		response.addCookie(cookie);
		
		//PrintWriter out = new PrintWriter(new GZIPOutputStream(response.getOutputStream()));
//		response.getWriter().println("Hello World!");
//		response.getWriter().println(host);
//		response.getWriter().println(port);
		
		String content = "why? this is outputstream. printwriter.this.gzos.os.";
		//out.println(content);
		//out.close();
		content.getBytes();
		ServletOutputStream os = response.getOutputStream();
		GZIPOutputStream gzos = new GZIPOutputStream(os);
		gzos.write(content.getBytes(), 0, content.getBytes().length);
		//os.close();
		gzos.close();
		os.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
