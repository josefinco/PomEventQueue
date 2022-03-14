package br.com.ti365.POMEventQueue.logger;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.avaya.pim.eventsdk.logger.ILogger;

public class LoggerImpl implements ILogger
{
	private static final String POM_EVENT__SDKL_LOG4J_PROPERTIES_LOCATION = ".\\conf\\EventSDKLOG4J.properties";

	private static final String POM_EVENT_SDK_LOGGER_NAME = "POM_EVENT_SDK";

	private String POM_EVENT_SDK_DIR = ".\\conf";

	private Logger logger = null;

	private static ILogger instTracer = null;

	static String FQCN = LoggerImpl.class.getName();


	public static ILogger getTracer()
	{
		if (instTracer == null)
		{
			try
			{
				instTracer = new LoggerImpl();
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instTracer;
	}


	private LoggerImpl() throws Exception
	{
		if (logger == null)
		{
			RefreshLogger();
		}
	}


	public void fine(String Msg)
	{
		Msg = getClassInfo() + Msg;
		logger.log(FQCN, LogLevel.FINE, Msg, null);
	}


	public void finer(String Msg)
	{
		Msg = getClassInfo() + Msg;
		logger.log(FQCN, LogLevel.FINER, Msg, null);
	}


	public void finest(String Msg)
	{
		Msg = getClassInfo() + Msg;
		logger.log(FQCN, LogLevel.FINEST, Msg, null);
	}


	public void error(String Msg, Exception ex)
	{
		if (ex != null)
		{
			Msg = Msg + getPrintStackTrace(ex);
		}
		Msg = getClassInfo() + Msg;
		logger.log(FQCN, LogLevel.ERROR, Msg, null);
	}


	private void RefreshLogger()
	{
		try
		{
			logger = null;
			// org.apache.log4j.BasicConfigurator.configure();
			logger = Logger.getLogger(POM_EVENT_SDK_LOGGER_NAME);
			if (logger == null)
			{
				System.out.println("Not possible to refresh logger");
				return;
			}
			logger.setLevel(Level.ERROR);
			String applicationHome = System.getProperty(POM_EVENT_SDK_DIR);
			System.out.println("ApplicationHome : " + applicationHome);
			if (applicationHome == null)
			{
				System.out.println("trying to find properties file in current directory as POM_EVENT_SDK_DIR is not set");
				applicationHome = System.getProperty("user.dir");
			}
			// System.out.println("ApplicationHome = " + ApplicationHome);
			String propFileName = applicationHome + File.separator + POM_EVENT__SDKL_LOG4J_PROPERTIES_LOCATION;
			System.out.println(propFileName);
			propFileName = propFileName.replace("\\", "/");
			propFileName = propFileName.replace("//", "/");
			propFileName = propFileName.replace("//", "/");// required twice
			PropertyConfigurator.configureAndWatch(propFileName);
		} catch (Exception e1)
		{
			e1.printStackTrace();
		}
	}


	private String getClassInfo()
	{
		Throwable t = new Throwable();
		java.lang.StackTraceElement[] ste = t.getStackTrace();
		StackTraceElement stackElement = null;
		String fqcn = null;
		for (int i = 1; i < ste.length; i++)
		{
			stackElement = ste[i];
			fqcn = stackElement.getClassName();
			// in case of agent manager check for tracer API since all agent APIs invoke
			// AgtMgrTracer.log API
			if (fqcn != null && fqcn.contains("ILoggerImpl"))
				continue;
			// we found actual class, break from here
			break;
		}
		// if no class information can be gathered then return blank
		if (stackElement == null || fqcn == null || fqcn.equals(""))
			return "";
		String className = fqcn.substring(fqcn.lastIndexOf(".") + 1);
		StringBuilder classlineBuilder = new StringBuilder();
		classlineBuilder.append(className).append(".").append(stackElement.getMethodName()).append(":")
		                .append(stackElement.getLineNumber()).append(" - ");
		return classlineBuilder.toString();
	}


	public static String getPrintStackTrace(Throwable e)
	{
		StringWriter sw = null;
		PrintWriter pw = null;
		String msg = "";
		try
		{
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			// e.printStackTrace();
			msg = "\n-----------------------------------------------------\n" + sw.toString()
			      + "\n-----------------------------------------------------\n";
			pw.flush();
			sw.flush();
		} catch (Exception e2)
		{
			e2.printStackTrace();
		} finally
		{
			try
			{
				if (null != sw)
				{
					sw.close();
				}
				if (null != pw)
				{
					pw.close();
				}
			} catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
		return msg;
	}
}
