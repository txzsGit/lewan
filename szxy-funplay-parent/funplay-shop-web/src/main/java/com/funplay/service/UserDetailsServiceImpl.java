package com.funplay.service;

import com.funplay.pojo.Seller;
import com.funplay.sellergoods.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证类
 */
public class UserDetailsServiceImpl implements UserDetailsService{
    private SellerService sellerService;

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> grantedAuthorityList=new ArrayList();
        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_SELLER"));
        Seller seller = sellerService.findOne(username);
        if(seller!=null) {
            if (seller.getStatus().equals("1")) {
                return new User(username, seller.getPassword(), grantedAuthorityList);
            }else{
                return null;
            }
        }else {
            return null;
        }
    }
}
