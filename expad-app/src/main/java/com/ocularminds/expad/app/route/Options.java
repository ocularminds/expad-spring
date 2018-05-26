
package com.ocularminds.expad.app.route;

import com.ocularminds.expad.model.Function;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Options extends HttpServlet {
    
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        StringBuilder sb = new StringBuilder();
        response.setContentType("text/csv");
        ArrayList functions = (ArrayList)session.getAttribute("FunctionInSession");
        for (int x = 0; x < functions.size(); ++x) {
            Function f = (Function)functions.get(x);
            sb.append(f.getDescription() + "," + f.getUrl() + "," + f.getType() + "\n\r");
        }
        response.getWriter().write(sb.toString());
    }
}

