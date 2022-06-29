//軟創三508170624吳倬安
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
			
			//1、建立SmartUpload物件
	        SmartUpload smartUpload = new SmartUpload();	        
	        //2、初始化建立的SmartUpload物件
	        smartUpload.initialize(getServletConfig(), request, response);	        
	        //3、進行檔案的上傳
            smartUpload.upload();
	        
            
//			String name = request.getParameter("product-name");
//			String category = request.getParameter("product-category");
//			double price = Double.parseDouble(request.getParameter("product-price"));
	        
            //利用smartUpload 解決enctype="multipart/form-data 傳值讀取問題
	        String name = smartUpload.getRequest().getParameter("product-name");
			String category = smartUpload.getRequest().getParameter("product-category");
			int price = Integer.parseInt(smartUpload.getRequest().getParameter("product-price"));			
	        
			try {
				ProductDao pdao = new ProductDao(DbCon.getConnection());
				boolean nameExists = pdao.productNameCheck(name); //檢查商品名稱有無重複
				
				if(nameExists) { //商品名稱已經存在
					out.print("商品名稱已經存在!");
				}else {
					Product newProduct = new Product();

					newProduct.setName(name);
					newProduct.setCategory(category);
					newProduct.setPrice(price);
					newProduct.setImage(name+".jpg");
										
					boolean result = pdao.addNewProduct(newProduct);
					if(result) out.print("商品已成功新增!");

					
					
		            //4、把上傳的檔案儲存到伺服器上相應的資料夾，我這裡建立的是upload資料夾
		            String fileSavePath = request.getServletContext().getRealPath("product-image");    //  得到upload資料夾的路徑
		            	//System.out.println(fileSavePath);
		            Files files = smartUpload.getFiles();                                  //得到所有上傳的檔案
		            for (int i = 0; i < files.getCount(); i++) {                           //遍歷所有上傳的每一個檔案
		                File curFile = files.getFile(i);                                   //得到當前上傳的檔案

//		                //儲存的檔名
//		                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		                String filename = simpleDateFormat.format(new Date());
		                String filename = name;
		                
		                String fileExt = curFile.getFileExt();                                  //獲得當前檔案的字尾名
		                String lastFilePath = fileSavePath + "/" + filename + "." + fileExt;    //當前檔案的儲存路徑

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
