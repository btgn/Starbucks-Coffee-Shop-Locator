package cslSQL;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/MVC/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Search() {
		super();
	}

	/*I've used all regexes from my past education system back in India I'm not sure if my then lecturer have taken them from Google*/

	public void init(ServletConfig config) throws ServletException {

		ServletContext context=config.getServletContext();
		String strbcks = context.getRealPath("/WEB-INF/starbucks.csv");

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(strbcks));
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}

		int i=0;
		String str,appnd="",appnd1 = "",appnd2 = "",str1 ="";
		String[] temp = null;
		String[] temp1 = null;
		int t =0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}

		String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs320stu33";
		String uname = "cs320stu33";
		String pass = "Fuu*5Ew.";

		Connection con = null;
		try {
			con = DriverManager.getConnection( url, uname, pass );
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		try {
			while((str=br.readLine())!=null){
				if(i==t){
					i++;
					continue;
				}
				if(i==t+1){
					str1=str1.concat(str);                                
					appnd=str1;                                  
					i++;
					continue;
				}
				else{
					if(i==t+2){
						appnd=appnd.concat(str);                            
						appnd1=appnd;                                 
						i++;
						continue;
					}
					else{
						if(i==t+3){
							appnd1=appnd1.concat(str).concat("\n");                           
							appnd2=appnd2.concat(appnd1);
							i=1;
							appnd=appnd1="";
							str1="";
							continue;
						}
					}
				}
			}
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}
		temp=appnd2.split("\n");
		for (int j = 0; j < temp.length; j++) {
			temp1=temp[j].split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
			int a=temp1[19].indexOf("(");
			int b=temp1[19].indexOf(")");
			int c1=temp1[19].indexOf(",");
			double lat=Double.parseDouble(temp1[19].substring(a+1,c1));
			double lon=Double.parseDouble(temp1[19].substring(c1+1,b));
			String zip=temp1[17];
			String city=temp1[15];
			String loc=temp1[11].substring(a+1,b);

			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement("insert into starbucks values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			}catch (SQLException sqle1) {
				sqle1.printStackTrace();
			}

			try {
				//loop runs for 23 times as we have 23 columns of data in the Database
				for(int s=0;s<23;s++){
					ps.setString(s+1,temp1[s] );
					ps.executeUpdate(); 
				}
			}catch (SQLException sqle2) {
				sqle2.printStackTrace();
			}

		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//work around for response.sendRedirect to achieve MVC Structure
		request.getRequestDispatcher("/WEB-INF/hw5/Search.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs=request.getSession();
		String csp=request.getParameter("type");
		String loc=request.getParameter("ev");
		String city=request.getParameter("ev");
		String zpcd=request.getParameter("ev");
		int n=loc.indexOf(",");
		
		//	Great Distance Circle Formula query has been taken from Online Sources
		if(csp.matches("location")){
			String lat = loc.substring(0, n);
			String lon = loc.substring(n+1,loc.length());
			String radius=request.getParameter("ev1");
			String pm="select city,location,zip (3959 * acos(cos(radians(latitude)) * cos(radians("+lat+")) * cos(radians(longitude) - radians("+lon+")) + sin(radians("+lat+")) * sin(radians(latitude)))) AS distance FROM starbucks HAVING distance < "+radius;
			hs.setAttribute("ev", pm);
			request.getRequestDispatcher("/WEB-INF/hw5/Search.jsp").forward(request, response);	
		}

		if(csp.matches("city")){
			city="'"+city+"%'";
			String str1="select city,location,zip from starbucks where city like"+city;
			hs.setAttribute("ev", str1);
			request.getRequestDispatcher("/WEB-INF/hw5/Search.jsp").forward(request, response);	
		}

		if(csp.matches("zip")){
			zpcd="'"+zpcd+"%'";
			String str2="select city,location,zip from starbucks where zip like"+zpcd;
			hs.setAttribute("ev", str2);
			request.getRequestDispatcher("/WEB-INF/hw5/Search.jsp").forward(request, response);	
		}
		
	}
}