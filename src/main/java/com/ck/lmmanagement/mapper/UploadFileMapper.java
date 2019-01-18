package com.ck.lmmanagement.mapper;

import com.ck.lmmanagement.domain.UploadFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 01378803
 * @date 2019/1/4 11:09
 * Description  :
 */
@Mapper
@Repository
public interface UploadFileMapper extends BaseMapper<UploadFile> {
    /**
     * 根据refId失效附件
     * @param refId
     * @return
     */
    Integer updateToDisableByRefId(Long refId);
}
