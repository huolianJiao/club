package com.glue.club.web.dao;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.glue.club.web.model.User;

/**
 * @author jiao
 */
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 根据accountId查询用户，防止重复插入
     * @param accountId
     * @return
     */
    User findByAccountId(@Param("accountId")String accountId);

    User findByToken(@Param("token")String token);

}
