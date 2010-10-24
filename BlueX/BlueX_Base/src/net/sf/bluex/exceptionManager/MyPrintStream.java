/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.exceptionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Date;

/**
 *
 * @author Blue
 */
class MyPrintStream extends PrintStream{
    private ExceptionInitializer ei;
    private String errorFile;

    public MyPrintStream(ExceptionInitializer ei, String errorFile) throws FileNotFoundException {
        super(new FileOutputStream(errorFile, true));
        this.errorFile=errorFile;
        this.ei=ei;
    }

    @Override
    public void println(String x) {
        x=x.replace("<", "&lt;");
        x=x.replace(">", "&gt;");
        super.println("<stack-trace>"+x+"</stack-trace>");
    }

    @Override
    public void print(String s) {
        if(!s.startsWith("Exception"))
            super.print(s);
    }

    @Override
    public void println(Object x) {
        File file=new File(errorFile);
        if(file.length()==0){
            PrintWriter pw=null;
            try{
                pw=new PrintWriter(file);
                pw.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<log>\n<exception>\n<time>dummy</time>\n<detail>");
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
            finally{
                pw.close();
            }
        }

        x=x.toString().replace("<", "&lt;");
        x=x.toString().replace(">", "&gt;");
        super.print("</detail>");
        super.println("</exception>");
        super.println("<exception>");
        super.println("<time>"+new Date().toString()+"</time>");
        super.print("<message>");
        super.print(x.toString());
        super.println("</message>");
        super.println("<detail>");

        if(ei!=null)
            ei.exceptionOccurred(x.toString());
    }
}
