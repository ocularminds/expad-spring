


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreditCardProcess extends HttpServlet {
    private ServletContext context;
    HttpSession session;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userid = request.getParameter("userid");
        String password = request.getParameter("password");
        this.session = request.getSession(true);
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        HtmlUtility htmlUtil = new HtmlUtility("finacle");
        String customerId = request.getParameter("customerid");
        String customerName = request.getParameter("customername");
        String preferedName = request.getParameter("prefname");
        String preferedID = request.getParameter("prefid");
        String sol = request.getParameter("SOL");
        String phone = request.getParameter("mobileno");
        String eMail = request.getParameter("email");
        String collectingSol = request.getParameter("colbranch");
        String userId = (String)this.session.getAttribute("CurrentUser");
        String reqID = request.getParameter("requestid");
        String[] linkedAccounts = request.getParameterValues("acc");
        String accept = request.getParameter("accept");
        String[] services = request.getParameterValues("services");
        CardService csb = (CardService)ServiceLocator.locate(CardService.class);
        for (int x = 0; x < services.length; ++x) {
        }
        out.print("<script>");
        out.print("alert('Request Sucessfully Posted!');");
        out.print("window.location='screen.jsp?np=debitCardRequest';");
        out.print("</script>");
    }
}

