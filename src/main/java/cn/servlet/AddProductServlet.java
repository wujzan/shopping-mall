//�n�ФT508170624�d�Ӧw
package cn.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import cn.connection.DbCon;
import cn.dao.ProductDao;
import cn.model.Product;

/**
 * Servlet implementation class AddProductServlet
 */
@WebServlet("/add-product")
public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		try(PrintWriter out = response.getWriter()){
			
			//1�B�إ�SmartUpload����
	        SmartUpload smartUpload = new SmartUpload();	        
	        //2�B��l�ƫإߪ�SmartUpload����
	        smartUpload.initialize(getServletConfig(), request, response);	        
	        //3�B�i���ɮת��W��
            smartUpload.upload();
	        
            
//			String name = request.getParameter("product-name");
//			String category = request.getParameter("product-category");
//			double price = Double.parseDouble(request.getParameter("product-price"));
	        
            //�Q��smartUpload �ѨMenctype="multipart/form-data �ǭ�Ū�����D
	        String name = smartUpload.getRequest().getParameter("product-name");
			String category = smartUpload.getRequest().getParameter("product-category");
			int price = Integer.parseInt(smartUpload.getRequest().getParameter("product-price"));			
	        
			try {
				ProductDao pdao = new ProductDao(DbCon.getConnection());
				boolean nameExists = pdao.productNameCheck(name); //�ˬd�ӫ~�W�٦��L����
				
				if(nameExists) { //�ӫ~�W�٤w�g�s�b
					out.print("�ӫ~�W�٤w�g�s�b!");
				}else {
					Product newProduct = new Product();

					newProduct.setName(name);
					newProduct.setCategory(category);
					newProduct.setPrice(price);
					newProduct.setImage(name+".jpg");
										
					boolean result = pdao.addNewProduct(newProduct);
					if(result) out.print("�ӫ~�w���\�s�W!");

					
					
		            //4�B��W�Ǫ��ɮ��x�s����A���W��������Ƨ��A�ڳo�̫إߪ��Oupload��Ƨ�
		            String fileSavePath = request.getServletContext().getRealPath("product-image");    //  �o��upload��Ƨ������|
		            	//System.out.println(fileSavePath);
		            Files files = smartUpload.getFiles();                                  //�o��Ҧ��W�Ǫ��ɮ�
		            for (int i = 0; i < files.getCount(); i++) {                           //�M���Ҧ��W�Ǫ��C�@���ɮ�
		                File curFile = files.getFile(i);                                   //�o���e�W�Ǫ��ɮ�

//		                //�x�s���ɦW
//		                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		                String filename = simpleDateFormat.format(new Date());
		                String filename = name;
		                
		                String fileExt = curFile.getFileExt();                                  //��o��e�ɮת��r���W
		                String lastFilePath = fileSavePath + "/" + filename + "." + fileExt;    //��e�ɮת��x�s���|

		                curFile.saveAs(lastFilePath);
		             }					
					
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		} catch (SmartUploadException e1) {
			e1.printStackTrace();
		}
	}

}
