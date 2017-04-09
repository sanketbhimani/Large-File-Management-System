package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class temp_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("    <head>\r\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("        <script type=\"text/javascript\" src=\"jit.js\"></script>\r\n");
      out.write("        <script src=\"jquery.min.js\"></script>\r\n");
      out.write("    </head>\r\n");
      out.write("    <style>\r\n");
      out.write("        body{\r\n");
      out.write("            color: white !important;\r\n");
      out.write("            background-color: black !important;\r\n");
      out.write("        }\r\n");
      out.write("        #sidepanel{\r\n");
      out.write("            width: 15%;\r\n");
      out.write("            display: block;\r\n");
      out.write("            float:right;\r\n");
      out.write("        }\r\n");
      out.write("        #loader {\r\n");
      out.write("            position: absolute;\r\n");
      out.write("            top:50%;\r\n");
      out.write("            left:50%;\r\n");
      out.write("            -webkit-animation: spin 2s linear infinite;\r\n");
      out.write("            animation: spin 2s linear infinite;\r\n");
      out.write("            visibility: hidden;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        @-webkit-keyframes spin {\r\n");
      out.write("            0% { -webkit-transform: rotate(0deg); }\r\n");
      out.write("            100% { -webkit-transform: rotate(360deg); }\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        @keyframes spin {\r\n");
      out.write("            0% { transform: rotate(0deg); }\r\n");
      out.write("            100% { transform: rotate(360deg); }\r\n");
      out.write("        }\r\n");
      out.write("        #log {\r\n");
      out.write("            width: 1000px;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        #infovis {\r\n");
      out.write("            width: 85%;\r\n");
      out.write("            float:left;\r\n");
      out.write("            display: block;\r\n");
      out.write("            height: 100%;\r\n");
      out.write("            overflow: hidden;\r\n");
      out.write("            background-color: black;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        .jitFgHelp {\r\n");
      out.write("            display: none;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        /*TOOLTIPS*/\r\n");
      out.write("        .tip {\r\n");
      out.write("            color: #111;\r\n");
      out.write("            width: 300px;\r\n");
      out.write("            background-color: white;\r\n");
      out.write("            border:1px solid #ccc;\r\n");
      out.write("            -moz-box-shadow:#555 2px 2px 8px;\r\n");
      out.write("            -webkit-box-shadow:#555 2px 2px 8px;\r\n");
      out.write("            -o-box-shadow:#555 2px 2px 8px;\r\n");
      out.write("            box-shadow:#555 2px 2px 8px;\r\n");
      out.write("            opacity:0.9;\r\n");
      out.write("            filter:alpha(opacity=90);\r\n");
      out.write("            font-size:10px;\r\n");
      out.write("            font-family:Verdana, Geneva, Arial, Helvetica, sans-serif;\r\n");
      out.write("            padding:7px;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("    </style>\r\n");
      out.write("    <body onload=\"init()\">\r\n");
      out.write("        <script src=\"example1.js\"></script>\r\n");
      out.write("    <center>\r\n");
      out.write("        <h1>Dictionary</h1>\r\n");
      out.write("        <input type=\"text\" id=\"query\" name=\"query\" placeholder=\"Word\"/>\r\n");
      out.write("        <input type=\"button\" name=\"searchButton\" value=\"Search\" onClick=\"search();\"/>\r\n");
      out.write("        <hr>\r\n");
      out.write("    </center>\r\n");
      out.write("    <div id=\"main\">\r\n");
      out.write("    <div id=\"log\"></div>\r\n");
      out.write("\r\n");
      out.write("    <span id=\"infovis\"></span>\r\n");
      out.write("    <span id=\"defpanel\"></span>\r\n");
      out.write("    </div>\r\n");
      out.write("    <br><br><br>\r\n");
      out.write("    <br><br><br>\r\n");
      out.write("    <div style=\"position:absolute;\" id=\"sidepanel\">\r\n");
      out.write("    </div>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
