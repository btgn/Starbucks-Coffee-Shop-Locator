package CSL;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/Homework3/CoffeeShopLocator")
public class CoffeeShopLocator extends HttpServlet {
	List<CoffeeShop> los = new ArrayList<CoffeeShop>();
	private static final long serialVersionUID = 1L;
       

	public CoffeeShopLocator() {
        super();
    }

	/*I've used all regexes from my past education system back in India I'm not sure if my then lecturer have taken them from Google*/

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String Loc = null;
		String Lng = null;
		String Lat = null;
		String City = null;
		String Town = null;
		String Address = null;
		String sa = null;
		String ZipCode = null;
		String[] code = null;
		int j = 0;
		String del = ",";
		String del1 = ": ";

		getServletContext().setAttribute("ListofShops", los);
		ServletContext context = this.getServletContext();
    	if (context.getAttribute("ListofShops") == null){
			context.setAttribute("ListofShops", new ArrayList<CoffeeShop>());
		}
    	ServletContext servletContext = config.getServletContext();
    	String strbcks = servletContext.getRealPath("/WEB-INF/starbucks.csv");
    	System.out.println(strbcks);
		BufferedReader br = null;
		String line;
		try {
			br = new BufferedReader(new FileReader(strbcks));
			while((line=br.readLine())!=null){
				String[] cfsp=line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				Lng = cfsp[j];
				Lat = cfsp[j+1];
				Town = cfsp[j+2];
				Address = cfsp[j+3];
				Loc = Lng + ", " + Lat;
				double earthRadius = 3959; //miles
				double lat = Double.parseDouble(Lat);
				double lng = Double.parseDouble(Lng);
				double dLat = Math.toRadians(lat);
			    double dLng = Math.toRadians(lng);
			    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
			               Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(lat)) *
			               Math.sin(dLng/2) * Math.sin(dLng/2);
			    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
			    float dist = (float) (earthRadius * c);
			    double srad = dist + (lat*lng);	
				
			    int n = 0, m = 0, t = 0;
				if(Address.contains(del)){
					n = Address.indexOf(del);
					sa = Address.substring(n+2);
					code = sa.split("(?<!\\G\\S+)\\s");
					if(code[j].contains(" ")){
						m = code[j].indexOf(" ");
						ZipCode = code[j].substring(m+1, code[j].length()-1);
					}
				}
				if(Town.contains(del1)){
					t = Town.indexOf(del1);
					City = Town.substring(t+2);
				}
				los.add(new CoffeeShop(Loc, City, Address, ZipCode, lat, lng, dist, (float) srad));
				
			}	
		}catch (FileNotFoundException fe) {
			fe.printStackTrace();
		}catch(IOException ie){
			ie.printStackTrace();
		}finally{
			if(br != null){
				try{
					br.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		
		
		
		
}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
//		out.println("<br><br><br>");
		out.println("<br><br><br><br><br><html><head><style>#wdth{width:150px; size:10} #wdth option{width:150px; size:10}</style><title>CoffeeShop</title><link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\"></head><body><center><form action=\"CoffeeShopLocator\" method=\"post\" >"
				+ "<link rel=\"stylesheet\" href=\"http://albertcervantes.com/cs320/cdn/homework2/styles/animate.css\">"
				+ "<br><div class=\"container\"><br><br><br><br><br><br><div class=\"row\"><div class=\"col-sm-offset-3 col-sm-6 newsletter-form animated bounceInDown\"><div class=\"well text-center\"><br><br><h2>Starbucks Shop Locator</h2>"
				+ "<br><select name=\"type\" id=\"wdth\"><option name=\"location\" value=\"location\" style=\"width:90%;\">Location</option><option name=\"city\" value=\"city\">City</option><option name=\"zip\" value=\"zip\">Zip Code</option></select> (Location Format: Longitude, Latitude) <br><br><br>"
				+ "   " + "<div class=\"form-group\"><div class=\"input-group\"><div class=\"input-group-addon\"><span class=\"glyphicon glyphicon-search\" aria-hidden=\"true\"></span></div><input class=\"form-control\" type=\"text\" name=\"query\" placeholder=\"Enter the Selected Value\"/></div></div>"
						+ "<br><br><button type=\"submit\" class=\"btn btn-primary\" value=\"Search\">Search</button></form></center></body></html>");
		out.println("<html class=\"\"> <head><meta charset=\"UTF-8\"><meta name=\"robots\" content=\"noindex\"><link rel=\"canonical\" href=\"http://codepen.io/mladen___/pen/emLBQe\" /> <style class=\"cp-pen-styles\">body { font-family: Arial, Helvetica, sans-serif; background-image: url(http://www.sweetcomments.net/glitterjoy/backgrounds/random/starbucks.jpg);overflow: hidden; } body #logo { position: absolute; top: 20%; left: 50%; width: 260px; height: 260px; margin: -130px 0 0 -130px; border-radius: 50%; background-color: #fff; -webkit-transform: scale(0); -moz-transform: scale(0); -ms-transform: scale(0); transform: scale(0); -webkit-animation: logo 5000ms forwards; -moz-animation: logo 5000ms forwards; -ms-animation: logo 5000ms forwards; animation: logo 5000ms forwards; } body #logo #green-circle-1 { position: absolute; width: 252px; height: 252px; margin: 4px 0 0 4px; border-radius: 50%; background-color: #006752; -webkit-transform: scale(0); -moz-transform: scale(0); -ms-transform: scale(0); transform: scale(0); -webkit-animation: greenCircleOne 5000ms forwards; -moz-animation: greenCircleOne 5000ms forwards; -ms-animation: greenCircleOne 5000ms forwards; animation: greenCircleOne 5000ms forwards; } body #logo #green-circle-1 #green-circle-2 { position: absolute; width: 238px; height: 238px; margin: 4px 0 0 4px; border-radius: 50%; background-color: #006752; border: 3px solid #fff; -webkit-transform: scale(0); -moz-transform: scale(0); -ms-transform: scale(0); transform: scale(0); -webkit-animation: whiteCircleOne 5000ms forwards; -moz-animation: whiteCircleOne 5000ms forwards; -ms-animation: whiteCircleOne 5000ms forwards; animation: whiteCircleOne 5000ms forwards; } body #logo #green-circle-1 #green-circle-2 p { margin: 0; padding: 0; color: #fff; -webkit-transform: scale(0); -moz-transform: scale(0); -ms-transform: scale(0); transform: scale(0); -webkit-animation-name: popUp; -webkit-animation-duration: 250ms; -webkit-animation-timing-function: ease; -webkit-animation-fill-mode: forwards; -moz-animation-name: popUp; -moz-animation-duration: 250ms; -moz-animation-timing-function: ease; -moz-animation-fill-mode: forwards; -ms-animation-name: popUp; -ms-animation-duration: 250ms; -ms-animation-timing-function: ease; -ms-animation-fill-mode: forwards; animation-name: popUp; animation-duration: 250ms; animation-timing-function: ease; animation-fill-mode: forwards; } body #logo #green-circle-1 #green-circle-2:before { position: absolute; top: 123px; right: 5px; content: \"\\2605\"; color: #fff; font-size: 30px; -webkit-transform: scale(0); -moz-transform: scale(0); -ms-transform: scale(0); transform: scale(0); -webkit-animation: popUp 250ms ease forwards 1400ms; -moz-animation: popUp 250ms ease forwards 1400ms; -ms-animation: popUp 250ms ease forwards 1400ms; animation: popUp 250ms ease forwards 1400ms; } body #logo #green-circle-1 #green-circle-2:after { position: absolute; top: 123px; left: 5px; content: \"\\2605\"; color: #fff; font-size: 30px; -webkit-transform: scale(0); -moz-transform: scale(0); -ms-transform: scale(0); transform: scale(0); -webkit-animation: popUp 250ms ease forwards 1100ms; -moz-animation: popUp 250ms ease forwards 1100ms; -ms-animation: popUp 250ms ease forwards 1100ms; animation: popUp 250ms ease forwards 1100ms; } body #logo #green-circle-1 #green-circle-2 .top { font-size: 40px; height: 119px; position: absolute; left: 50%; top: 0; width: 40px; margin-left: -20px; line-height: 45px; text-align: center; -webkit-transform-origin: bottom center; -moz-transform-origin: bottom center; -ms-transform-origin: bottom center; transform-origin: bottom center; font-weight: 900; } body #logo #green-circle-1 #green-circle-2 .top.char0 { -webkit-transform: rotate(-82deg); -moz-transform: rotate(-82deg); -ms-transform: rotate(-82deg); transform: rotate(-82deg); } body #logo #green-circle-1 #green-circle-2 .top.char0 p { -webkit-animation-delay: 1000ms; -moz-animation-delay: 1000ms; -ms-animation-delay: 1000ms; animation-delay: 1000ms; } body #logo #green-circle-1 #green-circle-2 .top.char1 { -webkit-transform: rotate(-63deg); -moz-transform: rotate(-63deg); -ms-transform: rotate(-63deg); transform: rotate(-63deg); } body #logo #green-circle-1 #green-circle-2 .top.char1 p { -webkit-animation-delay: 1050ms; -moz-animation-delay: 1050ms; -ms-animation-delay: 1050ms; animation-delay: 1050ms; } body #logo #green-circle-1 #green-circle-2 .top.char2 { -webkit-transform: rotate(-45deg); -moz-transform: rotate(-45deg); -ms-transform: rotate(-45deg); transform: rotate(-45deg); } body #logo #green-circle-1 #green-circle-2 .top.char2 p { -webkit-animation-delay: 1100ms; -moz-animation-delay: 1100ms; -ms-animation-delay: 1100ms; animation-delay: 1100ms; } body #logo #green-circle-1 #green-circle-2 .top.char3 { -webkit-transform: rotate(-23deg); -moz-transform: rotate(-23deg); -ms-transform: rotate(-23deg); transform: rotate(-23deg); } body #logo #green-circle-1 #green-circle-2 .top.char3 p { -webkit-animation-delay: 1150ms; -moz-animation-delay: 1150ms; -ms-animation-delay: 1150ms; animation-delay: 1150ms; } body #logo #green-circle-1 #green-circle-2 .top.char4 { -webkit-transform: rotate(-2deg); -moz-transform: rotate(-2deg); -ms-transform: rotate(-2deg); transform: rotate(-2deg); } body #logo #green-circle-1 #green-circle-2 .top.char4 p { -webkit-animation-delay: 1200ms; -moz-animation-delay: 1200ms; -ms-animation-delay: 1200ms; animation-delay: 1200ms; } body #logo #green-circle-1 #green-circle-2 .top.char5 { -webkit-transform: rotate(19deg); -moz-transform: rotate(19deg); -ms-transform: rotate(19deg); transform: rotate(19deg); } body #logo #green-circle-1 #green-circle-2 .top.char5 p { -webkit-animation-delay: 1250ms; -moz-animation-delay: 1250ms; -ms-animation-delay: 1250ms; animation-delay: 1250ms; } body #logo #green-circle-1 #green-circle-2 .top.char6 { -webkit-transform: rotate(41deg); -moz-transform: rotate(41deg); -ms-transform: rotate(41deg); transform: rotate(41deg); } body #logo #green-circle-1 #green-circle-2 .top.char6 p { -webkit-animation-delay: 1300ms; -moz-animation-delay: 1300ms; -ms-animation-delay: 1300ms; animation-delay: 1300ms; } body #logo #green-circle-1 #green-circle-2 .top.char7 { -webkit-transform: rotate(64deg); -moz-transform: rotate(64deg); -ms-transform: rotate(64deg); transform: rotate(64deg); } body #logo #green-circle-1 #green-circle-2 .top.char7 p { -webkit-animation-delay: 1350ms; -moz-animation-delay: 1350ms; -ms-animation-delay: 1350ms; animation-delay: 1350ms; } body #logo #green-circle-1 #green-circle-2 .top.char8 { -webkit-transform: rotate(85deg); -moz-transform: rotate(85deg); -ms-transform: rotate(85deg); transform: rotate(85deg); } body #logo #green-circle-1 #green-circle-2 .top.char8 p { -webkit-animation-delay: 1400ms; -moz-animation-delay: 1400ms; -ms-animation-delay: 1400ms; animation-delay: 1400ms; } body #logo #green-circle-1 #green-circle-2 .bottom { font-size: 40px; height: 119px; position: absolute; left: 50%; top: 50%; width: 40px; margin-left: -20px; line-height: 195px; text-align: center; transform-origin: top center; font-weight: 900; } body #logo #green-circle-1 #green-circle-2 .bottom.char0 { -webkit-transform: rotate(50deg); -moz-transform: rotate(50deg); -ms-transform: rotate(50deg); transform: rotate(50deg); } body #logo #green-circle-1 #green-circle-2 .bottom.char0 p { -webkit-animation-delay: 1150ms; -moz-animation-delay: 1150ms; -ms-animation-delay: 1150ms; animation-delay: 1150ms; } body #logo #green-circle-1 #green-circle-2 .bottom.char1 { -webkit-transform: rotate(30deg); -moz-transform: rotate(30deg); -ms-transform: rotate(30deg); transform: rotate(30deg); } body #logo #green-circle-1 #green-circle-2 .bottom.char1 p { -webkit-animation-delay: 1200ms; -moz-animation-delay: 1200ms; -ms-animation-delay: 1200ms; animation-delay: 1200ms; } body #logo #green-circle-1 #green-circle-2 .bottom.char2 { -webkit-transform: rotate(10deg); -moz-transform: rotate(10deg); -ms-transform: rotate(10deg); transform: rotate(10deg); } body #logo #green-circle-1 #green-circle-2 .bottom.char2 p { -webkit-animation-delay: 1250ms; -moz-animation-delay: 1250ms; -ms-animation-delay: 1250ms; animation-delay: 1250ms; } body #logo #green-circle-1 #green-circle-2 .bottom.char3 { -webkit-transform: rotate(-10deg); -moz-transform: rotate(-10deg); -ms-transform: rotate(-10deg); transform: rotate(-10deg); } body #logo #green-circle-1 #green-circle-2 .bottom.char3 p { -webkit-animation-delay: 1300ms; -moz-animation-delay: 1300ms; -ms-animation-delay: 1300ms; animation-delay: 1300ms; } body #logo #green-circle-1 #green-circle-2 .bottom.char4 { -webkit-transform: rotate(-31deg); -moz-transform: rotate(-31deg); -ms-transform: rotate(-31deg); transform: rotate(-31deg); } body #logo #green-circle-1 #green-circle-2 .bottom.char4 p { -webkit-animation-delay: 1350ms; -moz-animation-delay: 1350ms; -ms-animation-delay: 1350ms; animation-delay: 1350ms; } body #logo #green-circle-1 #green-circle-2 .bottom.char5 { -webkit-transform: rotate(-54deg); -moz-transform: rotate(-54deg); -ms-transform: rotate(-54deg); transform: rotate(-54deg); } body #logo #green-circle-1 #green-circle-2 .bottom.char5 p { -webkit-animation-delay: 1400ms; -moz-animation-delay: 1400ms; -ms-animation-delay: 1400ms; animation-delay: 1400ms; } body #logo #green-circle-1 #green-circle-2 #white-circle-1 { position: absolute; top: 50%; left: 50%; width: 134px; height: 134px; margin: -70px 0 0 -70px; border-radius: 50%; border: solid 3px #fff; background-color: #fff; overflow: hidden; } body #logo #green-circle-1 #green-circle-2 #white-circle-1 #box { position: absolute; top: 160px; left: 0; width: 400px; height: 400px; background-color: #000; background-image: url(\"http://snh.rs/sb.png\"); background-repeat: no-repeat; background-position: bottom right; -webkit-animation: blackBg 5000ms forwards; -moz-animation: blackBg 5000ms forwards; -ms-animation: blackBg 5000ms forwards; animation: blackBg 5000ms forwards; } body #logo #green-circle-1 #green-circle-2 #white-circle-1 #box .circle { float: left; width: 20px; height: 20px; margin: -10px 0 0 0; background-color: #fff; border-radius: 50%; } @-webkit-keyframes blackBg { from { top: 160px; left: 0; } 40% { top: 160px; left: 0; } 60% { top: -20px; left: -266px; } 80% { top: -260px; left: -266px; } to { top: -260px; left: -266px; } } @-webkit-keyframes popUp { from { -webkit-transform: scale(0); } 80% { -webkit-transform: scale(1.3); } to { -webkit-transform: scale(1); } } @-webkit-keyframes whiteCircleOne { from { -webkit-transform: scale(0); } 15% { -webkit-transform: scale(0); } 35% { -webkit-transform: scale(1); } to { -webkit-transform: scale(1); } } @-webkit-keyframes greenCircleOne { from { -webkit-transform: scale(0); } 8% { -webkit-transform: scale(0); } 15% { -webkit-transform: scale(1); } 50% { -webkit-transform: scale(1); } to { -webkit-transform: scale(1); } } @-webkit-keyframes logo { from { -webkit-transform: scale(0); } 15% { -webkit-transform: scale(1.5); } 50% { -webkit-transform: scale(1); } to { -webkit-transform: scale(1); } } @-moz-keyframes blackBg { from { top: 160px; left: 0; } 40% { top: 160px; left: 0; } 60% { top: -20px; left: -266px; } 80% { top: -260px; left: -266px; } to { top: -260px; left: -266px; } } @-moz-keyframes popUp { from { -moz-transform: scale(0); } 80% { -moz-transform: scale(1.3); } to { -moz-transform: scale(1); } } @-moz-keyframes whiteCircleOne { from { -moz-transform: scale(0); } 15% { -moz-transform: scale(0); } 35% { -moz-transform: scale(1); } to { -moz-transform: scale(1); } } @-moz-keyframes greenCircleOne { from { -moz-transform: scale(0); } 8% { -moz-transform: scale(0); } 15% { -moz-transform: scale(1); } 50% { -moz-transform: scale(1); } to { -moz-transform: scale(1); } } @-moz-keyframes logo { from { -moz-transform: scale(0); } 15% { -moz-transform: scale(1.5); } 50% { -moz-transform: scale(1); } to { -moz-transform: scale(1); } } @-ms-keyframes blackBg { from { top: 160px; left: 0; } 40% { top: 160px; left: 0; } 60% { top: -20px; left: -266px; } 80% { top: -260px; left: -266px; } to { top: -260px; left: -266px; } } @-ms-keyframes popUp { from { -ms-transform: scale(0); } 80% { -ms-transform: scale(1.3); } to { -ms-transform: scale(1); } } @-ms-keyframes whiteCircleOne { from { -ms-transform: scale(0); } 15% { -ms-transform: scale(0); } 35% { -ms-transform: scale(1); } to { -ms-transform: scale(1); } } @-ms-keyframes greenCircleOne { from { -ms-transform: scale(0); } 8% { -ms-transform: scale(0); } 15% { -ms-transform: scale(1); } 50% { -ms-transform: scale(1); } to { -ms-transform: scale(1); } } @-ms-keyframes logo { from { -ms-transform: scale(0); } 15% { -ms-transform: scale(1.5); } 50% { -ms-transform: scale(1); } to { -ms-transform: scale(1); } } @-moz-keyframes blackBg { from { top: 160px; left: 0; } 40% { top: 160px; left: 0; } 60% { top: -20px; left: -266px; } 80% { top: -260px; left: -266px; } to { top: -260px; left: -266px; } } @-webkit-keyframes blackBg { from { top: 160px; left: 0; } 40% { top: 160px; left: 0; } 60% { top: -20px; left: -266px; } 80% { top: -260px; left: -266px; } to { top: -260px; left: -266px; } } @-o-keyframes blackBg { from { top: 160px; left: 0; } 40% { top: 160px; left: 0; } 60% { top: -20px; left: -266px; } 80% { top: -260px; left: -266px; } to { top: -260px; left: -266px; } } @keyframes blackBg { from { top: 160px; left: 0; } 40% { top: 160px; left: 0; } 60% { top: -20px; left: -266px; } 80% { top: -260px; left: -266px; } to { top: -260px; left: -266px; } } @-moz-keyframes popUp { from { transform: scale(0); } 80% { transform: scale(1.3); } to { transform: scale(1); } } @-webkit-keyframes popUp { from { transform: scale(0); } 80% { transform: scale(1.3); } to { transform: scale(1); } } @-o-keyframes popUp { from { transform: scale(0); } 80% { transform: scale(1.3); } to { transform: scale(1); } } @keyframes popUp { from { transform: scale(0); } 80% { transform: scale(1.3); } to { transform: scale(1); } } @-moz-keyframes whiteCircleOne { from { transform: scale(0); } 15% { transform: scale(0); } 35% { transform: scale(1); } to { transform: scale(1); } } @-webkit-keyframes whiteCircleOne { from { transform: scale(0); } 15% { transform: scale(0); } 35% { transform: scale(1); } to { transform: scale(1); } } @-o-keyframes whiteCircleOne { from { transform: scale(0); } 15% { transform: scale(0); } 35% { transform: scale(1); } to { transform: scale(1); } } @keyframes whiteCircleOne { from { transform: scale(0); } 15% { transform: scale(0); } 35% { transform: scale(1); } to { transform: scale(1); } } @-moz-keyframes greenCircleOne { from { transform: scale(0); } 8% { transform: scale(0); } 15% { transform: scale(1); } 50% { transform: scale(1); } to { transform: scale(1); } } @-webkit-keyframes greenCircleOne { from { transform: scale(0); } 8% { transform: scale(0); } 15% { transform: scale(1); } 50% { transform: scale(1); } to { transform: scale(1); } } @-o-keyframes greenCircleOne { from { transform: scale(0); } 8% { transform: scale(0); } 15% { transform: scale(1); } 50% { transform: scale(1); } to { transform: scale(1); } } @keyframes greenCircleOne { from { transform: scale(0); } 8% { transform: scale(0); } 15% { transform: scale(1); } 50% { transform: scale(1); } to { transform: scale(1); } } @-moz-keyframes logo { from { transform: scale(0); } 15% { transform: scale(1.5); } 50% { transform: scale(1); } to { transform: scale(1); } } @-webkit-keyframes logo { from { transform: scale(0); } 15% { transform: scale(1.5); } 50% { transform: scale(1); } to { transform: scale(1); } } @-o-keyframes logo { from { transform: scale(0); } 15% { transform: scale(1.5); } 50% { transform: scale(1); } to { transform: scale(1); } } @keyframes logo { from { transform: scale(0); } 15% { transform: scale(1.5); } 50% { transform: scale(1); } to { transform: scale(1); } }</style></head><body> <div id=\"logo\"> <div id=\"green-circle-1\"> <div id=\"green-circle-2\"><span class=\"top char0\"> <p>S</p></span><span class=\"top char1\"> <p>T</p></span><span class=\"top char2\"> <p>A</p></span><span class=\"top char3\"> <p>R</p></span><span class=\"top char4\"> <p>B</p></span><span class=\"top char5\"> <p>U</p></span><span class=\"top char6\"> <p>C</p></span><span class=\"top char7\"> <p>K</p></span><span class=\"top char8\"> <p>S</p></span><span class=\"bottom char0\"> <p>C</p></span><span class=\"bottom char1\"> <p>O</p></span><span class=\"bottom char2\"> <p>F</p></span><span class=\"bottom char3\"> <p>F</p></span><span class=\"bottom char4\"> <p>E</p></span><span class=\"bottom char5\"> <p>E</p></span> <div id=\"white-circle-1\"> <div id=\"box\"> <div class=\"circle\"></div> <div class=\"circle\"></div> <div class=\"circle\"></div> <div class=\"circle\"></div> <div class=\"circle\"></div> <div class=\"circle\"></div> <div class=\"circle\"></div> <div class=\"circle\"></div> <div class=\"circle\"></div> <div class=\"circle\"></div> <div class=\"circle\"></div> <div class=\"circle\"></div> <div class=\"circle\"></div> <div class=\"circle\"></div> <div class=\"circle\"></div> <div class=\"circle\"></div> <div class=\"circle\"></div> <div class=\"circle\"></div> <div class=\"circle\"></div> </div> </div> </div> </div> </div> </body></html>");
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		out.println();
		String loc = request.getParameter("query");
		String city = request.getParameter("query");
		String zip = request.getParameter("query");
		out.println("<br><br><br><br><html><head><title>Shop Locator</title><link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\"></head>");
        String str = request.getParameter("type");
        out.println("<br><br><br><div class=\"container\"><div class=\"row\"><div class=\"col-sm-offset-3 col-sm-6 newsletter-form animated bounceInDown\"><div class=\"well text-center\"><table border = \"1\" align = \"center\" id=\"results\"><col width=\"130\"><tr><th>&nbsp;City</th><th>&nbsp;Address</th></tr>");
        
        if(str.matches("location") || str.matches("location1")){
        	
        	out.println("Starbucks Coffee Shops within 10 miles: <br><br>");
        	for(CoffeeShop csp : los){
					if(loc.matches(csp.getLoc()) || csp.getDist()<csp.getSrad()){
           			out.println("<tr><td>" + csp.getCity() + "</td><td>" + csp.getAddress().replace('"', ' ').replace(';', ',') + "</td></tr>");
           		}
    		}	
    	}
    		
    	else if(str.matches("city")){ 
    		for(CoffeeShop csp : los){
    			int temp=0;
    			temp = city.length();
    			if(city.equalsIgnoreCase(csp.getCity()) || city.equalsIgnoreCase(csp.getCity())){
    				out.println("<tr><td>&nbsp;" + csp.getCity() + "</td><td>&nbsp;" + csp.getAddress().replace('"', ' ').replace(';', ',') + "</td></tr>");
           		}
    	
    		}	
        }
    		
        else if(str.matches("zip")){
        	for(CoffeeShop csp : los){
        		if(zip.equals(csp.getZipcode())){
           			out.println("<tr><td>" + csp.getCity() + "</td><td>" + csp.getAddress().replace('"', ' ').replace(';', ',') + "</td></tr>");
           		}	
        	}
        }
        out.println("<style class=\\\"cp-pen-styles\\\">body { font-family: Arial, Helvetica, sans-serif; background-image: url(http://www.sweetcomments.net/glitterjoy/backgrounds/random/starbucks.jpg);overflow: hidden; } </style> ");
	}
}