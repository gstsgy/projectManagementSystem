package com.guyue.pms.controller;

import com.guyue.pms.bean.db.Prodocument;
import com.guyue.pms.bean.view.ResponseBean;
import com.guyue.pms.bean.view.SearchFileView;
import com.guyue.pms.server.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("file")
public class FileController {
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private ResponseBean responseBean;
   /* @GetMapping("ftype")
    public ResponseBean getType(){
        return responseBean.getSuccess(fileUploadService.getType());
    }
*/
    @GetMapping("files")
    public ResponseBean getFiles(SearchFileView searchFileView){
        return responseBean.getSuccess(fileUploadService.query(searchFileView));
    }

    @DeleteMapping("/file")
    public ResponseBean deleteFile(@RequestBody List<Prodocument> list) {

        return responseBean.getSuccess(fileUploadService.delete(list));
    }
    @GetMapping("file")
    public void getFile(Prodocument prodocument, HttpServletRequest request, HttpServletResponse response) throws IOException {
        fileUploadService.downloadFile(prodocument,request,response);
    }

    @PostMapping(value = "/file")
    public ResponseBean addFiles(@RequestParam("file") MultipartFile file,String fabout,Long cid  ) {
            return responseBean.getSuccess(fileUploadService.addFile(file,0,fabout,cid));
    }

    @PutMapping("file")
    public ResponseBean updateFile(@RequestBody Prodocument prodocument) throws IOException {
        return responseBean.getSuccess( fileUploadService.updatefile(prodocument));
    }
}
