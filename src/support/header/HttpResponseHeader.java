package support.header;

import enums.Header;

import java.util.LinkedHashMap;

@SuppressWarnings({"WeakerAccess", "unused", "SameParameterValue"})
public class HttpResponseHeader extends HttpHeader {
	public enum Status {
		CONTINUE,                           // "100" "Continue"
		SWITCHING_PROTOCOLS,                // "101" "Switching Protocols"
		OK,                                 // "200" "OK"
		CREATED,                            // "201" "Created"
		ACCEPTED,                           // "202" "Accepted"
		NON_AUTHORITATIVE_INFORMATION,      // "203" "Non-Authoritative Information"
		NO_CONTENT,                         // "204" "No Content"
		RESET_CONTENT,                      // "205" "Reset Content"
		PARTIAL_CONTENT,                    // "206" "Partial Content"
		MULTIPLE_CHOICES,                   // "300" "Multiple Choices"
		MOVED_PERMANENTLY,                  // "301" "Moved Permanently"
		FOUND,                              // "302" "Found"
		SEE_OTHER,                          // "303" "See Other"
		NOT_MODIFIED,                       // "304" "Not Modified"
		USE_PROXY,                          // "305" "Use Proxy"
		TEMPORARY_REDIRECT,                 // "307" "Temporary Redirect"
		BAD_REQUEST,                        // "400" "Bad Request"
		UNAUTHORIZED,                       // "401" "Unauthorized"
		PAYMENT_REQUIRED,                   // "402" "Payment Required"
		FORBIDDEN,                          // "403" "Forbidden"
		NOT_FOUND,                          // "404" "Not Found"
		METHOD_NOT_ALLOWED,                 // "405" "Method Not Allowed"
		NOT_ACCEPTABLE,                     // "406" "Not Acceptable"
		PROXY_AUTHENTICATION_REQUIRED,      // "407" "Proxy Authentication Required"
		REQUEST_TIME_OUT,                   // "408" "Request Time-out"
		CONFLICT,                           // "409" "Conflict"
		GONE,                               // "410" "Gone"
		LENGTH_REQUIRED,                    // "411" "Length Required"
		PRECONDITION_FAILED,                // "412" "Precondition Failed"
		REQUEST_ENTITY_TOO_LARGE,           // "413" "Request Entity Too Large"
		REQUEST_URI_TOO_LARGE,              // "414" "Request-URI Too Large"
		UNSUPPORTED_MEDIA_TYPE,             // "415" "Unsupported Media Type"
		REQUESTED_RANGE_NOT_SATISFIABLE,    // "416" "Requested range not satisfiable"
		EXPECTATION_FAILED,                 // "417" "Expectation Failed"
		INTERNAL_SERVER_ERROR,              // "500" "Internal Server Error"
		NOT_IMPLEMENTED,                    // "501" "Not Implemented"
		BAD_GATEWAY,                        // "502" "Bad Gateway"
		SERVICE_UNAVAILABLE,                // "503" "Service Unavailable"
		GATEWAY_TIME_OUT,                   // "504" "Gateway Time-out"
		HTTP_VERSION_NOT_SUPPORTED,         // "505" "HTTP Version not supported"
	}

	private Status status;

	public HttpResponseHeader() {
		this(Status.OK);
	}

	public HttpResponseHeader(Status status) {
		this.status = status;

		this.headers = new LinkedHashMap<>();
	}

	@Override
	public String toString() {
		try {
			return getStatusLine() + super.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private String getStatusLine() throws Exception {
		return HTTP_VERSION + " " + getStatusID() + " " + getReasonPhrase() + "\r\n";
	}

	private int getStatusID() throws Exception {
		switch (this.status) {
			case CONTINUE:
				return 100;
			case SWITCHING_PROTOCOLS:
				return 101;
			case OK:
				return 200;
			case CREATED:
				return 201;
			case ACCEPTED:
				return 202;
			case NON_AUTHORITATIVE_INFORMATION:
				return 203;
			case NO_CONTENT:
				return 204;
			case RESET_CONTENT:
				return 205;
			case PARTIAL_CONTENT:
				return 206;
			case MULTIPLE_CHOICES:
				return 300;
			case MOVED_PERMANENTLY:
				return 301;
			case FOUND:
				return 302;
			case SEE_OTHER:
				return 303;
			case NOT_MODIFIED:
				return 304;
			case USE_PROXY:
				return 305;
			case TEMPORARY_REDIRECT:
				return 307;
			case BAD_REQUEST:
				return 400;
			case UNAUTHORIZED:
				return 401;
			case PAYMENT_REQUIRED:
				return 402;
			case FORBIDDEN:
				return 403;
			case NOT_FOUND:
				return 404;
			case METHOD_NOT_ALLOWED:
				return 405;
			case NOT_ACCEPTABLE:
				return 406;
			case PROXY_AUTHENTICATION_REQUIRED:
				return 407;
			case REQUEST_TIME_OUT:
				return 408;
			case CONFLICT:
				return 409;
			case GONE:
				return 410;
			case LENGTH_REQUIRED:
				return 411;
			case PRECONDITION_FAILED:
				return 412;
			case REQUEST_ENTITY_TOO_LARGE:
				return 413;
			case REQUEST_URI_TOO_LARGE:
				return 414;
			case UNSUPPORTED_MEDIA_TYPE:
				return 415;
			case REQUESTED_RANGE_NOT_SATISFIABLE:
				return 416;
			case EXPECTATION_FAILED:
				return 417;
			case INTERNAL_SERVER_ERROR:
				return 500;
			case NOT_IMPLEMENTED:
				return 501;
			case BAD_GATEWAY:
				return 502;
			case SERVICE_UNAVAILABLE:
				return 503;
			case GATEWAY_TIME_OUT:
				return 504;
			case HTTP_VERSION_NOT_SUPPORTED:
				return 505;
		}

		throw new Exception("No status code set.");
	}

	private String getReasonPhrase() throws Exception {
		switch (status) {
			case CONTINUE:
				return "Continue";
			case SWITCHING_PROTOCOLS:
				return "Switching Protocols";
			case OK:
				return "OK";
			case CREATED:
				return "Created";
			case ACCEPTED:
				return "Accepted";
			case NON_AUTHORITATIVE_INFORMATION:
				return "Non-Authoritative Information";
			case NO_CONTENT:
				return "No Content";
			case RESET_CONTENT:
				return "Reset Content";
			case PARTIAL_CONTENT:
				return "Partial Content";
			case MULTIPLE_CHOICES:
				return "Multiple Choices";
			case MOVED_PERMANENTLY:
				return "Moved Permanently";
			case FOUND:
				return "Found";
			case SEE_OTHER:
				return "See Other";
			case NOT_MODIFIED:
				return "Not Modified";
			case USE_PROXY:
				return "Use Proxy";
			case TEMPORARY_REDIRECT:
				return "Temporary Redirect";
			case BAD_REQUEST:
				return "Bad Request";
			case UNAUTHORIZED:
				return "Unauthorized";
			case PAYMENT_REQUIRED:
				return "Payment Required";
			case FORBIDDEN:
				return "Forbidden";
			case NOT_FOUND:
				return "Not Found";
			case METHOD_NOT_ALLOWED:
				return "Method Not Allowed";
			case NOT_ACCEPTABLE:
				return "Not Acceptable";
			case PROXY_AUTHENTICATION_REQUIRED:
				return "Proxy Authentication Required";
			case REQUEST_TIME_OUT:
				return "Request Time-out";
			case CONFLICT:
				return "Conflict";
			case GONE:
				return "Gone";
			case LENGTH_REQUIRED:
				return "Length Required";
			case PRECONDITION_FAILED:
				return "Precondition Failed";
			case REQUEST_ENTITY_TOO_LARGE:
				return "Request Entity Too Large";
			case REQUEST_URI_TOO_LARGE:
				return "Request-URI Too Large";
			case UNSUPPORTED_MEDIA_TYPE:
				return "Unsupported Media Type";
			case REQUESTED_RANGE_NOT_SATISFIABLE:
				return "Requested range not satisfiable";
			case EXPECTATION_FAILED:
				return "Expectation Failed";
			case INTERNAL_SERVER_ERROR:
				return "Internal Server Error";
			case NOT_IMPLEMENTED:
				return "Not Implemented";
			case BAD_GATEWAY:
				return "Bad Gateway";
			case SERVICE_UNAVAILABLE:
				return "Service Unavailable";
			case GATEWAY_TIME_OUT:
				return "Gateway Time-out";
			case HTTP_VERSION_NOT_SUPPORTED:
				return "HTTP Version not supported";
		}

		throw new Exception("No status code set.");
	}

	private static Status fromStatusID(int id) throws Exception {
		switch (id) {
			case 100:
				return Status.CONTINUE;
			case 101:
				return Status.SWITCHING_PROTOCOLS;
			case 200:
				return Status.OK;
			case 201:
				return Status.CREATED;
			case 202:
				return Status.ACCEPTED;
			case 203:
				return Status.NON_AUTHORITATIVE_INFORMATION;
			case 204:
				return Status.NO_CONTENT;
			case 205:
				return Status.RESET_CONTENT;
			case 206:
				return Status.PARTIAL_CONTENT;
			case 300:
				return Status.MULTIPLE_CHOICES;
			case 301:
				return Status.MOVED_PERMANENTLY;
			case 302:
				return Status.FOUND;
			case 303:
				return Status.SEE_OTHER;
			case 304:
				return Status.NOT_MODIFIED;
			case 305:
				return Status.USE_PROXY;
			case 307:
				return Status.TEMPORARY_REDIRECT;
			case 400:
				return Status.BAD_REQUEST;
			case 401:
				return Status.UNAUTHORIZED;
			case 402:
				return Status.PAYMENT_REQUIRED;
			case 403:
				return Status.FORBIDDEN;
			case 404:
				return Status.NOT_FOUND;
			case 405:
				return Status.METHOD_NOT_ALLOWED;
			case 406:
				return Status.NOT_ACCEPTABLE;
			case 407:
				return Status.PROXY_AUTHENTICATION_REQUIRED;
			case 408:
				return Status.REQUEST_TIME_OUT;
			case 409:
				return Status.CONFLICT;
			case 410:
				return Status.GONE;
			case 411:
				return Status.LENGTH_REQUIRED;
			case 412:
				return Status.PRECONDITION_FAILED;
			case 413:
				return Status.REQUEST_ENTITY_TOO_LARGE;
			case 414:
				return Status.REQUEST_URI_TOO_LARGE;
			case 415:
				return Status.UNSUPPORTED_MEDIA_TYPE;
			case 416:
				return Status.REQUESTED_RANGE_NOT_SATISFIABLE;
			case 417:
				return Status.EXPECTATION_FAILED;
			case 500:
				return Status.INTERNAL_SERVER_ERROR;
			case 501:
				return Status.NOT_IMPLEMENTED;
			case 502:
				return Status.BAD_GATEWAY;
			case 503:
				return Status.SERVICE_UNAVAILABLE;
			case 504:
				return Status.GATEWAY_TIME_OUT;
			case 505:
				return Status.HTTP_VERSION_NOT_SUPPORTED;
		}

		throw new Exception("No status code set.");
	}

	public static HttpResponseHeader fromString(String response) throws Exception {
		String[] rows = response.split("\\r\\n");

		int code = Integer.parseInt(rows[0].split(" ", 3)[1]);

		Status status = fromStatusID(code);

		HttpResponseHeader httpResponseHeader = new HttpResponseHeader(status);

		for (int i = 1; i < rows.length; i++) {
			String[] parts = rows[i].split(": ", 2);

			Header header = fromHeaderName(parts[0]);

			httpResponseHeader.headers.put(header, parts[1]);
		}

		return httpResponseHeader;
	}
}
