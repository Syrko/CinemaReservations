package auxpackage;

import javax.servlet.http.*;

public class CookieManager {
	public static Cookie[] getCookies(HttpServletRequest req) {
		Cookie[] cookieArray;
		try {
			cookieArray = req.getCookies();
		}
		catch(Exception e){
			cookieArray = null;
		}
		return cookieArray;
	}
	public static void deleteCookie(Cookie cookie, HttpServletResponse res) {
		cookie.setMaxAge(0);
		res.addCookie(cookie);
		return;
	}
}
