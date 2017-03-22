package support.header;

import enums.CacheControl;
import enums.ContentType;
import enums.Header;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

@SuppressWarnings({"WeakerAccess", "unused", "SameParameterValue"})
public abstract class HttpHeader {
	public static final String HTTP_VERSION = "HTTP/1.1";

	protected Map<Header, String> headers;

	@Override
	public String toString() {
		try {
			return generateHeaderString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public void setHeader(Header header, String value) {
		this.headers.put(header, value);
	}

	public void setContentType(ContentType type) throws Exception {
		this.headers.put(Header.CONTENT_TYPE, getContentTypeString(type));
	}

	public ContentType getContentType() throws Exception {
		String contentType = this.headers.get(Header.CONTENT_TYPE);

		if (contentType == null) {
			return null;
		}

		return fromContentTypeString(contentType);
	}

	public void setContentLength(Long size) {
		this.headers.put(Header.CONTENT_LENGTH, size.toString());
	}

	public long getContentLength() {
		String size = this.headers.get(Header.CONTENT_LENGTH);

		if (size == null) {
			return 0;
		}

		return Long.parseLong(size);
	}

	public void setAccessControlAllowOrigin(URI uri) throws Exception {
		if (uri.getHost() == null) {
			throw new Exception("Invalid URI provided.");
		}

		this.headers.put(Header.ACCESS_CONTROL_ALLOW_ORIGIN, uri.getHost());
	}

	public void setAccessControlAllowOriginAll() throws Exception {
		this.headers.put(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
	}

	public void setLastModified(Date date) {
		setDateHeader(Header.LAST_MODIFIED, date);
	}

	public void setEntityTag(String entityTag) {
		setEntityTag(entityTag, false);
	}

	public void setEntityTag(String entityTag, boolean isWeak) {
		entityTag = "\"" + entityTag + "\"";

		if (isWeak) {
			entityTag = "W/" + entityTag;
		}

		this.headers.put(Header.ETAG, entityTag);
	}

	public void setCacheControl(CacheControl cacheControl) {
		setCacheControl(cacheControl, 0);
	}

	public void setCacheControl(CacheControl cacheControl, Integer seconds) {
		String value = "";

		switch (cacheControl) {
			case MUST_REVALIDATE:
				value = "must-revalidate";
				break;
			case NO_CACHE:
				value = "no-cache";
				break;
			case NO_STORE:
				value = "no-store";
				break;
			case NO_TRANSFORM:
				value = "no-transform";
				break;
			case PUBLIC:
				value = "public";
				break;
			case PRIVATE:
				value = "private";
				break;
			case PROXY_REVALIDATE:
				value = "proxy-revalidate";
				break;
			case MAX_AGE:
				value = "max-age=" + seconds;
				break;
			case S_MAXAGE:
				value = "s-maxage=" + seconds;
				break;
		}

		this.headers.put(Header.CACHE_CONTROL, value);
	}

	protected void setDateHeader(Header header, Date date) {
		DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy KK:mm:ss z");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));

		this.headers.put(header, df.format(date));
	}

	protected String generateHeaderString() throws Exception {
		String headerString = "";

		for (Map.Entry<Header, String> headerStringEntry : this.headers.entrySet()) {
			headerString += getHeaderName(headerStringEntry.getKey()) + ": " + headerStringEntry.getValue() + "\r\n";
		}

		return headerString + "\r\n";
	}

	protected String getHeaderName(Header header) throws Exception {
		switch (header) {
			case ACCESS_CONTROL_ALLOW_ORIGIN:
				return "Access-Control-Allow-Origin";
			case ACCEPT_PATCH:
				return "Accept-Patch";
			case ACCEPT_RANGES:
				return "Accept-Ranges";
			case AGE:
				return "Age";
			case ALLOW:
				return "Allow";
			case ALT_SVC:
				return "Alt-Svc";
			case CACHE_CONTROL:
				return "Cache-Control";
			case CONNECTION:
				return "Connection";
			case CONTENT_DISPOSITION:
				return "Content-Disposition";
			case CONTENT_ENCODING:
				return "Content-Encoding";
			case CONTENT_LANGUAGE:
				return "Content-Language";
			case CONTENT_LENGTH:
				return "Content-Length";
			case CONTENT_LOCATION:
				return "Content-Location";
			case CONTENT_MD5:
				return "Content-MD5";
			case CONTENT_RANGE:
				return "Content-Range";
			case CONTENT_TYPE:
				return "Content-Type";
			case DATE:
				return "Date";
			case ETAG:
				return "ETag";
			case EXPIRES:
				return "Expires";
			case LAST_MODIFIED:
				return "Last-Modified";
			case LINK:
				return "Link";
			case LOCATION:
				return "Location";
			case PRAGMA:
				return "Pragma";
			case PROXY_AUTHENTICATE:
				return "Proxy-Authenticate";
			case PUBLIC_KEY_PINS:
				return "Public-Key-Pins";
			case REFRESH:
				return "Refresh";
			case RETRY_AFTER:
				return "Retry-After";
			case SERVER:
				return "Server";
			case SET_COOKIE:
				return "Set-Cookie";
			case STATUS:
				return "Status";
			case STRICT_TRANSPORT_SECURITY:
				return "Strict-Transport-Security";
			case TRAILER:
				return "Trailer";
			case TRANSFER_ENCODING:
				return "Transfer-Encoding";
			case UPGRADE:
				return "Upgrade";
			case VIA:
				return "Via";
			case WARNING:
				return "Warning";
			case WWW_AUTHENTICATE:
				return "WWW-Authenticate";
			case X_FRAME_OPTIONS:
				return "X-Frame-Options";
		}

		throw new Exception("Invalid header.");
	}

	protected static Header fromHeaderName(String name) throws Exception {
		switch (name) {
			case "Access-Control-Allow-Origin":
				return Header.ACCESS_CONTROL_ALLOW_ORIGIN;
			case "Accept-Patch":
				return Header.ACCEPT_PATCH;
			case "Accept-Ranges":
				return Header.ACCEPT_RANGES;
			case "Age":
				return Header.AGE;
			case "Allow":
				return Header.ALLOW;
			case "Alt-Svc":
				return Header.ALT_SVC;
			case "Cache-Control":
				return Header.CACHE_CONTROL;
			case "Connection":
				return Header.CONNECTION;
			case "Content-Disposition":
				return Header.CONTENT_DISPOSITION;
			case "Content-Encoding":
				return Header.CONTENT_ENCODING;
			case "Content-Language":
				return Header.CONTENT_LANGUAGE;
			case "Content-Length":
				return Header.CONTENT_LENGTH;
			case "Content-Location":
				return Header.CONTENT_LOCATION;
			case "Content-MD5":
				return Header.CONTENT_MD5;
			case "Content-Range":
				return Header.CONTENT_RANGE;
			case "Content-Type":
				return Header.CONTENT_TYPE;
			case "Date":
				return Header.DATE;
			case "ETag":
				return Header.ETAG;
			case "Expires":
				return Header.EXPIRES;
			case "Last-Modified":
				return Header.LAST_MODIFIED;
			case "Link":
				return Header.LINK;
			case "Location":
				return Header.LOCATION;
			case "Pragma":
				return Header.PRAGMA;
			case "Proxy-Authenticate":
				return Header.PROXY_AUTHENTICATE;
			case "Public-Key-Pins":
				return Header.PUBLIC_KEY_PINS;
			case "Refresh":
				return Header.REFRESH;
			case "Retry-After":
				return Header.RETRY_AFTER;
			case "Server":
				return Header.SERVER;
			case "Set-Cookie":
				return Header.SET_COOKIE;
			case "Status":
				return Header.STATUS;
			case "Strict-Transport-Security":
				return Header.STRICT_TRANSPORT_SECURITY;
			case "Trailer":
				return Header.TRAILER;
			case "Transfer-Encoding":
				return Header.TRANSFER_ENCODING;
			case "Upgrade":
				return Header.UPGRADE;
			case "Via":
				return Header.VIA;
			case "Warning":
				return Header.WARNING;
			case "WWW-Authenticate":
				return Header.WWW_AUTHENTICATE;
			case "X-Frame-Options":
				return Header.X_FRAME_OPTIONS;
		}

		throw new Exception("Invalid header name.");
	}

	protected String getContentTypeString(ContentType contentType) throws Exception {
		switch (contentType) {
			case TEXT_PLAIN:
				return "text/plain";
			case TEXT_HTML:
				return "text/html";
			case TEXT_CSS:
				return "text/css";
			case TEXT_JAVASCRIPT:
				return "text/javascript";
			case IMAGE_GIF:
				return "image/gif";
			case IMAGE_PNG:
				return "image/png";
			case IMAGE_JPEG:
				return "image/jpeg";
			case IMAGE_BMP:
				return "image/bmp";
			case IMAGE_WEBP:
				return "image/webp";
			case AUDIO_MIDI:
				return "audio/midi";
			case AUDIO_MPEG:
				return "audio/mpeg";
			case AUDIO_WEBM:
				return "audio/webm";
			case AUDIO_OGG:
				return "audio/ogg";
			case AUDIO_WAV:
				return "audio/wav";
			case VIDEO_WEBM:
				return "video/webm";
			case VIDEO_OGG:
				return "video/ogg";
			case APPLICATION_OCTET_STREAM:
				return "application/octet-stream";
			case APPLICATION_PKCS12:
				return "application/pkcs12";
			case APPLICATION_VND_MSPOWERPOINT:
				return "application/vnd.mspowerpoint";
			case APPLICATION_XHTML_XML:
				return "application/xhtml+xml";
			case APPLICATION_XML:
				return "application/xml";
			case APPLICATION_PDF:
				return "application/pdf";
		}

		throw new Exception("Invalid content type.");
	}

	protected ContentType fromContentTypeString(String contentType) throws Exception {
		switch (contentType) {
			case "text/plain":
				return ContentType.TEXT_PLAIN;
			case "text/html":
				return ContentType.TEXT_HTML;
			case "text/css":
				return ContentType.TEXT_CSS;
			case "text/javascript":
				return ContentType.TEXT_JAVASCRIPT;
			case "image/gif":
				return ContentType.IMAGE_GIF;
			case "image/png":
				return ContentType.IMAGE_PNG;
			case "image/jpeg":
				return ContentType.IMAGE_JPEG;
			case "image/bmp":
				return ContentType.IMAGE_BMP;
			case "image/webp":
				return ContentType.IMAGE_WEBP;
			case "audio/midi":
				return ContentType.AUDIO_MIDI;
			case "audio/mpeg":
				return ContentType.AUDIO_MPEG;
			case "audio/webm":
				return ContentType.AUDIO_WEBM;
			case "audio/ogg":
				return ContentType.AUDIO_OGG;
			case "audio/wav":
				return ContentType.AUDIO_WAV;
			case "video/webm":
				return ContentType.VIDEO_WEBM;
			case "video/ogg":
				return ContentType.VIDEO_OGG;
			case "application/octet-stream":
				return ContentType.APPLICATION_OCTET_STREAM;
			case "application/pkcs12":
				return ContentType.APPLICATION_PKCS12;
			case "application/vnd.mspowerpoint":
				return ContentType.APPLICATION_VND_MSPOWERPOINT;
			case "application/xhtml+xml":
				return ContentType.APPLICATION_XHTML_XML;
			case "application/xml":
				return ContentType.APPLICATION_XML;
			case "application/pdf":
				return ContentType.APPLICATION_PDF;
		}

		throw new Exception("Invalid content type.");
	}

	public String getHeaderValue(Header header) {
		return this.headers.get(header);
	}
}
