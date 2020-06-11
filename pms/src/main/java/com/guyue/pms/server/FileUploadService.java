package com.guyue.pms.server;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guyue.pms.bean.db.Prodocument;
import com.guyue.pms.bean.view.SearchFileView;
import com.guyue.pms.mapper.ProinfoMapper;
import com.guyue.pms.util.DateUtil;
import com.guyue.pms.util.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
@Service
public class FileUploadService {
    @Value("${ftype}")
    private String ftype;
    @Value("${filePath}")
    private String filePath;
    @Autowired
    private ProinfoMapper proinfoMapper;
    @Autowired
    private DbUtilServer dbUtilServer;
    @Autowired
    private IDUtil idUtil;

    public List<Map> getType(){
        String[] strarr = ftype.split(",");
        AtomicInteger index=new AtomicInteger(0);
       return Arrays.stream(strarr).map(item->{
            Map map = new HashMap();
            map.put("code",index.addAndGet(1));
            map.put("name",item);
            return map;
        }).collect(Collectors.toList());
    }

    public PageInfo<Prodocument> query(SearchFileView searchFileView){
        PageHelper.startPage(searchFileView.getPageNum(), searchFileView.getPageSize());

            List<Prodocument> list = proinfoMapper.queryfile(searchFileView);
            //System.out.println(list.size());

            PageInfo<Prodocument> pageModel = new PageInfo<>(list);
            return pageModel;
    }

    public boolean addFile(MultipartFile file, Integer ftype,String fabout,Long cid){
        boolean bool = false;
        if (!file.isEmpty()) {
            Prodocument prodocument = new Prodocument();
            prodocument.setFtype(ftype);
            prodocument.setFabout(fabout);
            prodocument.setCid(cid);
            String fileName = file.getOriginalFilename();
            prodocument.setMname(fileName);
            prodocument.setMid(idUtil.nextId());
            prodocument.setFsize(getFileSize(file.getSize()));
          prodocument.setUodatetime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));

            java.io.File dest = new java.io.File(filePath + prodocument.getMid());
            try {
                file.transferTo(dest);
                int num= dbUtilServer.insert(prodocument);
                bool = num>0;
            } catch (IOException e) {
                e.printStackTrace();
                bool = false;
            }
        }
        return bool;
    }

    /**
     * @desc 文件下载
     * @param prodocument 文件id号
     * @param request
     * @param response
     */
    public void downloadFile(Prodocument prodocument, HttpServletRequest request, HttpServletResponse response) throws IOException {

                InputStream inputStream = new FileInputStream(new java.io.File(filePath + prodocument.getMid()));
                OutputStream outputStream = response.getOutputStream();
                response.setContentType("application/x-download");
                response.addHeader("Content-Disposition", "attachment;fileName=" + prodocument.getMname()); // 设置文件名
                // 把输入流copy到输出流
                IOUtils.copy(inputStream, outputStream);
                outputStream.flush();
    }

    public boolean delete(List<Prodocument> list) {
       list.forEach(item->{
           java.io.File file=new java.io.File(filePath + item.getMid());
           if(file.exists()&&file.isFile())
               file.delete();
           proinfoMapper.deletefile(item.getMid());
       });

        return 1>0;
    }

    public boolean updatefile(Prodocument prodocument){
        return proinfoMapper.updatefile(prodocument)>0;
    }



    /**
     * @desc 计算文件大小
     * @param num 文件大小，单位B
     * @return
     */
    private String getFileSize(long num) {
        String res = "";
        if(num<1024) {
            res = num + "B";
        }
        else if(num<1024*1024) {
            res =Math.round(num /1024)  + "K";
        }
        else if(num<1024*1024*1024) {
            res =Math.round(num /1024/1024)  + "M";
        }
        else {
            res =Math.round(num /1024/1024/1024)  + "G";
        }
        return res;
    }
}
