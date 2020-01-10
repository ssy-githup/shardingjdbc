package ai.ssy.mapper;

import ai.ssy.model.Province;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProvinceMapper {
    public int save(Province province);
}
