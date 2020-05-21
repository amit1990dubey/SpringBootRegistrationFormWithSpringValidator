package com.spring.ibm.DAO;


import com.spring.ibm.model.Country;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CountryDAOImpl {

    private static final Map<String, Country> COUNTRY_MAP = new HashMap<>();

    {
        initDATA();
    }

    public void initDATA()
    {
        Country vn =new Country("VN","Vietnam");
        Country en = new Country("EN","England");
        Country fr = new Country("FR","France");
        Country us = new Country("US","United State");
        Country rs = new Country("RS","Russia");


    }

    public Country findCountryByCode(String CountryCode)
    {

        return COUNTRY_MAP.get(CountryCode);

    }

    public List<Country>getCountries()
    {
        List<Country> list = new ArrayList<>();
        list.addAll(COUNTRY_MAP.values());
        return list;
    }
}
