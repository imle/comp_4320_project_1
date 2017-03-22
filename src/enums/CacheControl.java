package enums;

public enum CacheControl {
	MUST_REVALIDATE,    // must-revalidate
	NO_CACHE,           // no-cache
	NO_STORE,           // no-store
	NO_TRANSFORM,       // no-transform
	PUBLIC,             // public
	PRIVATE,            // private
	PROXY_REVALIDATE,   // proxy-revalidate
	MAX_AGE,            // max-age=<seconds>
	S_MAXAGE,           // s-maxage=<seconds>
}
