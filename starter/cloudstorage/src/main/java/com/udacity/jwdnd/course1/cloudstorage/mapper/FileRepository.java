package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileRepository {

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    File findByName(String filename);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File findById(int fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> findAll(int userid);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteFile(int fileId);
    @Insert("INSERT INTO FILES(filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    int insert(File file);
}
