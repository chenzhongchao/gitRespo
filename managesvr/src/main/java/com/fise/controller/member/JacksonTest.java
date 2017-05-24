package com.fise.controller.member;

import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class JacksonTest {
    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        String json = "{\"user_name\":\"bflee\",\"id_number\":\"123456\"}";
//        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
//        O o = (O) mapper.readValue(json, O.class);
//        System.out.println(o.getIdNumber());
    		int[] a = new int[5];
    		boolean[] b = new boolean[5];
    		System.out.println("a[4]=" + a[4]);
    		System.out.println("b[5]=" + b[5]);
        }
    }
    
	class O implements Serializable{
        private static final long serialVersionUID = -3004824622398622080L;
        private String userName ;
        private String idNumber ;
        public String getUserName() {
            return userName;
        }
        public void setUserName(String userName) {
            this.userName = userName;
        }
        public String getIdNumber() {
            return idNumber;
        }
        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
     }

     
}