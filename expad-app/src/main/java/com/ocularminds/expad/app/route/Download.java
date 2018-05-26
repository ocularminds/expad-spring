
package com.ocularminds.expad.app.route;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Download extends HttpServlet {
    private ServletContext context;
    HttpSession session;

    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String process = request.getParameter("PR");
        this.session = request.getSession(true);
        ArrayList list = new ArrayList();
        if (process != null && process.equalsIgnoreCase("ATM")) {
            list = (ArrayList)this.session.getAttribute("DownloadData");
            //this.processDebitCardDownload(request, response, list);
        } else {
            String foracid = request.getParameter("AC");
        }
    }

    private void processDebitCardDownload(HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        String userid = (String)this.session.getAttribute("CurrentUser");
        response.getWriter().write(this.getHTMLReport());
    }

    private String getHTMLReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN'");
        sb.append(" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><br>");
        sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        sb.append("<head>");
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;");
        sb.append("charset=\"iso-8859-1\" />");
        sb.append("<title>eXPad : Debit Card Download Report</title>");
        sb.append("<style type=\"text/css\"><!--.style2 { font-size: 18px;font-weight: bold;");
        sb.append("}--></style></head><body align='center'><table width=\"45%\">");
        sb.append("  <tr bgcolor=\"#CCCCCC\">");
        sb.append("<td colspan=\"2\"><span class=\"style2\">Debit Card File Down Report.");
        sb.append("</span></td>");
        sb.append("</tr> <tr> <td><strong>File Successful Downloaded.</strong></td>");
        sb.append("<td>&nbsp;</td></tr><tr><td>&nbsp;</td><td>&nbsp;</td></tr>");
        sb.append("<tr><td>Do you want to view the file?");
        sb.append("<form id=\"form1\" name=\"form1\" method=\"post\" action=\"\">");
        sb.append("<input name=\"radiobutton\" type=\"radio\" value=\"radiobutton\" ");
        sb.append("checked=\"checked\" disabled/>");
        sb.append("Yes.<input name=\"radiobutton\" type=\"radio\" value=\"radiobutton\" />");
        sb.append("No.<input type=\"button\" name=\"Button\" value=\"Close\" ");
        sb.append("onClick=\"window.location='screen.jsp'\"/>");
        sb.append("</form></td><td>&nbsp;</td></tr><tr><td>&nbsp;</td>");
        sb.append("<td>&nbsp;</td></tr></table></body></html>");
        return sb.toString();
    }
}

