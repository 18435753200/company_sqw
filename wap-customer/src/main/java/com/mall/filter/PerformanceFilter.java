/**
 * 
 */
package com.mall.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jianping.gao
 *
 */
public class PerformanceFilter implements Filter {

	/**
	 * slf4j log4j
	 */
	private static Logger LOGGER = LoggerFactory
			.getLogger(PerformanceFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		chain.doFilter(request, response);
		stopWatch.stop();

		if (req.getRequestURI().startsWith("/commons/")) {
			return;
		}

		StringBuffer sb = new StringBuffer();
		Enumeration<?> e = request.getParameterNames();
		while (e.hasMoreElements()) {
			if (sb.length() > 0) {
				sb.append(",");
			}
			String name = (String) e.nextElement();
			String[] values = request.getParameterValues(name);
			sb.append("{").append(name).append("=");
			for (int i = 0; i < values.length; ++i) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append(values[i]);
			}
			sb.append("}");
		}

		StringBuffer server = new StringBuffer();
		String remoteAddr = req.getRemoteAddr();
		server.append("{REMOTE_ADDR=").append(remoteAddr).append("}");
		e = req.getHeaderNames();
		while (e.hasMoreElements()) {
			server.append(",");
			String name = (String) e.nextElement();
			String value = req.getHeader(name);
			server.append("{").append(name).append("=").append(value)
					.append("}");
		}

		LOGGER.info("{millsec=" + stopWatch.getTime() + "} "
				+ req.getRequestURI() + " {" + sb.toString() + "}, {"
				+ server.toString() + "}");
	}
}
