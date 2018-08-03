package com.example.demo.service;

import com.example.demo.dao.CuxUsersMapper;
import com.example.demo.pojo.CuxUsers;
import com.example.demo.utils.JdbcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Zhao Guowei on 2018/7/22.
 */
@Service
public class LoginService {
    @Autowired
    private CuxUsersMapper cuxUsersMapper;
    JdbcUtil jdbcUtil= null;
    public Long Login(String username,String password) {
        jdbcUtil = new JdbcUtil();
        jdbcUtil.getConnection();
        //创建填充参数的list
        List<Object> paramList = new ArrayList<Object>();
        //填充参数
        paramList.add(username);
        paramList.add(password);
        String sql = "select * from cux_users where user_name = ? and password = ?";
        try {
            List<Map<String, Object>> mapList = jdbcUtil.findResult(sql, paramList);

            if (mapList.size() == 1){
                Map<String, Object> map = mapList.get(0);
                Long id = (Long)map.get("USER_ID");
                return id;
            }else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(this.getClass() + "执行查询操作抛出异常！");
            e.printStackTrace();
            return null;
        } finally {
            if (jdbcUtil != null) {
                jdbcUtil.releaseConn(); // 一定要释放资源
            }

        }

    }

/*    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        CuxUsers cuxUsers = cuxUsersMapper.selectByUsername(s);
        if(cuxUsers == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //用于添加用户的权限。只要把用户权限添加到authorities 就万事大吉。
        authorities.add(new SimpleGrantedAuthority("admin"));
        return new org.springframework.security.core.userdetails.User(cuxUsers.getUserName(),
                cuxUsers.getPassword(), authorities);
    }*/
}
