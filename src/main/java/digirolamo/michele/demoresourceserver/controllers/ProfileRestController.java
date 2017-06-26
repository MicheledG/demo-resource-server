package digirolamo.michele.demoresourceserver.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
 * Simple controller that returns back information about the authenticated user
 */
@RestController
public class ProfileRestController {
	
	
	/*
	 * Only requests with access token obtained with "password" grant
	 * can access this API endpoint.
	 * Indeed in the @PreAuthorize annotation the presence of an authenticated user is checked with the access token
	 * NOTE: there is no need to use API /profiles/<user-id> because the userid is linked with the access token
	 */
	@PreAuthorize("#oauth2.isUser()") 
	@RequestMapping(value="/myProfile", method=RequestMethod.GET)
	public ResponseEntity<Map<String, String>> getProfileInfo(){
		// retrieve the username of the authenticated user from the security context
		OAuth2Authentication oauth2 = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
		Authentication userAuthentication = oauth2.getUserAuthentication();
		String username = userAuthentication.getName();
		
		//insert the username in the response
		Map<String, String> responseMap = new HashMap<>();	
		responseMap.put("username", username);
		
		return new ResponseEntity<Map<String,String>>(responseMap, HttpStatus.OK);
	}
	
}
