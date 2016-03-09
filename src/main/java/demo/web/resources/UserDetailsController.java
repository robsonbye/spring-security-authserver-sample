package demo.web.resources;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by robson.andrade on 08/03/2016.
 * curl localhost:8080/me -H "Authorization: Bearer 7b4c5e9b-d548-48e2-bbd3-30a3c19f04d7"
 */
@RestController
public class UserDetailsController {

    @RequestMapping("/me")
    public Principal authenticatedUser(Principal principal){
        return principal;
    }


    /**
     * curl localhost:8080/private -H "Authorization: Bearer 736f8974-eb5e-4a90-946c-69f371149827"
     * @return
     */
    @RequestMapping("/private")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public Collection<String> restrictAccess(){
        return Arrays.asList("Hi,everybody!!".split(","));
    }

    /**
     * curl localhost:8080/private-user -H "Authorization: Bearer 736f8974-eb5e-4a90-946c-69f371149827"
     * @return
     */
    @RequestMapping("/private-user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Collection<String> restrictAccessUser(){
        return Arrays.asList("Hi,everybody!!".split(","));
    }
}
