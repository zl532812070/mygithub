package com.excel.service;
import java.io.BufferedWriter;  
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.OutputStreamWriter;  
import java.io.Writer;  
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
  
import freemarker.template.Configuration;  
import freemarker.template.Template;  
import freemarker.template.TemplateException;  
  
public class WordUtils {  
      
    private Configuration configuration = null;  
      
    public WordUtils(){  
        configuration = new Configuration();  
        configuration.setDefaultEncoding("UTF-8");  
    }  
      
    public static void main(String[] args) {  
        WordUtils test = new WordUtils();  
        test.createWord();  
    }  
    
    public void createWord(){  
        Map<String,Object> dataMap=new HashMap<String,Object>();  
        getData(dataMap);  
        configuration.setClassForTemplateLoading(this.getClass(), "/template");  //FTL文件所存在的位置  
        Template t=null;  
        try {  
        	//注：word转xml时，不要选2003xml
            t = configuration.getTemplate("test2.ftl","UTF-8"); //文件名  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        File outFile = new File("D:/"+Math.random()*10000+".doc");  //导出文档的存放位置
        Writer out = null;  
        try {  
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));  
        } catch (Exception e1) {  
            e1.printStackTrace();  
        }  
           
        try {  
            t.process(dataMap, out);  
        } catch (TemplateException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    private void getData(Map<String, Object> dataMap) {  
        dataMap.put("factoryNull", "121");  
        dataMap.put("iNull", "1");
        dataMap.put("modelPara","222");
        dataMap.put("factorySame","55");
        dataMap.put("AllNum",66);
               
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();  
        for (int i = 0; i < 10; i++) {  
            Map<String,Object> map = new HashMap<String,Object>();  
            map.put("number", i);  
            map.put("content", "内容"+i);  
            list.add(map);  
        }  
        dataMap.put("list", list);  
    }  
}  
