package ai.ssy.service.impl;


import ai.ssy.mapper.ProvinceMapper;
import ai.ssy.model.Province;
import ai.ssy.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceMapper provinceMapper;

    @Override
    public void save(Province province) {
        provinceMapper.save(province);
    }
}
