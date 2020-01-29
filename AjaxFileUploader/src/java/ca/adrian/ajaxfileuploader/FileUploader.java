package ca.adrian.ajaxfileuploader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(value = "/fileUploader") 
public class FileUploader extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ajaxUpdateResult = "";
        try {
            DiskFileItemFactory dfif = new DiskFileItemFactory();
            ServletFileUpload sfu = new ServletFileUpload(dfif);
            //System.out.println(dfif.getDefaultCharset());
            dfif.setDefaultCharset("UTF-8");
            List<FileItem> items = sfu.parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    System.out.println(item.getFieldName() + " = " + item.getString());
                } else {
                    String fileName = item.getName();
                    InputStream content = item.getInputStream();
                    // Do whatever with the content InputStream.
                    System.out.println("Content Size="+item.getSize());
                    ajaxUpdateResult += "The file " + fileName + " has been successfully uploaded.\n\r";
                }
            }
        } catch (FileUploadException e) {
            throw new ServletException("Parsing file upload failed.", e);
        }
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(ajaxUpdateResult);
    }
}