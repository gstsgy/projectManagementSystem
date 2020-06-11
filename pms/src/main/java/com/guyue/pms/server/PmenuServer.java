package com.guyue.pms.server;

import com.guyue.pms.bean.db.MenuRole;
import com.guyue.pms.bean.db.Pmenu;
import com.guyue.pms.bean.view.MenuTreeNodeView;
import com.guyue.pms.bean.view.TreeNodeView;
import com.guyue.pms.mapper.PmenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service

public class PmenuServer {
    @Autowired
    private PmenuMapper pmenuMapper;
    @Autowired
    private DbUtilServer dbUtilServer;

    public List<MenuTreeNodeView> getMenus(Long roleid){
        List<MenuTreeNodeView> MenuIdlist = new ArrayList();

        List<Pmenu> menus = pmenuMapper.queryByroleid(roleid);
        Map<Long, List<MenuTreeNodeView>> map= menus.stream().map(Pmenu::getParentid).distinct().
                map(pmenuMapper::queryByid).map(MenuTreeNodeView::new).sorted(Comparator.
                comparingInt(MenuTreeNodeView::getMenuSeq)).collect(Collectors.groupingBy(item -> item.getParentid()));

        MenuIdlist=map.keySet().stream().filter(Objects::nonNull).map(pmenuMapper::queryByid).filter(Objects::nonNull).
                sorted(Comparator.comparingInt(Pmenu::getOrder)).
                map(MenuTreeNodeView::new).
                map(item -> {
                    item.setChildren(map.get(item.getMenuId()).stream().map(it -> {
                        it.setParentLabel(item.getMenuLabel());
                        return it;
                    }).collect(Collectors.toList()));
                    return item;
                }).collect(Collectors.toList());
        return MenuIdlist;
    }

    public List<TreeNodeView> getMenus(){
       // List<MenuTreeNodeView> MenuIdlist = new ArrayList();

        List<TreeNodeView> tree =pmenuMapper.queryByParent(0l).stream().map(item->new TreeNodeView(item.getMenuname(),item.getMenuid())).collect(Collectors.toList());
        // 二级菜单
        tree.stream().forEach(item->item.setChildren(pmenuMapper.queryByParent(item.getValue()).stream().map(it->new TreeNodeView(1,item.getValue(),it.getMenuname(),it.getMenuid())).
                collect(Collectors.toList())));
        // 三级菜单 (使用并行流)
        tree.stream().forEach(item->item.getChildren().stream().forEach(it->it.setChildren(pmenuMapper.queryByParent(it.getValue()).stream()
                .map(i->new TreeNodeView(2,it.getValue(),i.getMenuname(),i.getMenuid())).collect(Collectors.toList()))));
//System.out.println(tree);
        return tree;



    }

    public Map getControls(Long roleid) {
        return  pmenuMapper.queryByroleid(roleid).stream().filter(Objects::nonNull).collect(Collectors.toMap(priControlModel ->
                ("c"+priControlModel.getMenuid()),priControlModel -> true));


    }
    @Transactional
    public boolean saveRole(Long roleid,List<Integer> menuid){
       pmenuMapper.delete(roleid) ;
        menuid.stream().forEach(it->{
            MenuRole menuRole = new MenuRole();
            menuRole.setMenuid(Long.valueOf(it.toString()));
            menuRole.setRoleid(roleid);
            dbUtilServer.insert(menuRole);
        });
        return true;
    }

}
