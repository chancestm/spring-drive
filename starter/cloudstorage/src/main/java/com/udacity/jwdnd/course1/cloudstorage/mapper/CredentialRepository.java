package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialRepository {

    @Insert("INSERT INTO CREDENTIALS(url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    int insert(Credential credential);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, password=#{password} WHERE credentialid=#{credentialid}")
    int update(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE  credentialid=#{credentialid} AND userid=#{userid}")
    Credential findById(int credentialid, int userid);

    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userid}")
    List<Credential> findAll(int userid);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    int delete(int credentialid);
}
