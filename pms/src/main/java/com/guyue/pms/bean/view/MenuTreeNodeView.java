package com.guyue.pms.bean.view;

import com.guyue.pms.bean.db.Pmenu;

import java.util.List;

/**
 * @Description 菜单树结构
 * @auther hujun
 * @create 2020-04-18 10:01
 */
public class MenuTreeNodeView {

    // 默认两级菜单   模块->页面
    // 主页 -模块-页面
    private Long menuId;   //菜单id
    private String menuAction;   //菜单路径
    private String menuLabel;   //菜单标题
    private String relativePath;   //菜单实体路径（动态路由）
    private String isLeaf;   //是否叶子菜单
    private String menuView;   //模块图标
    private String parentLabel;//父级标题
    private Long parentid;
    private Integer displayorder;
    private List<MenuTreeNodeView> children;

    public MenuTreeNodeView(Pmenu pmenu) {
        this.menuId = pmenu.getMenuid();
        this.menuAction = pmenu.getMenupath();
        this.menuLabel = pmenu.getMenuname();
        this.relativePath = pmenu.getMenuposi();
        this.isLeaf = pmenu.getLevel()==2?"1":"0";
        this.menuView=pmenu.getMenuico();
        this.parentid = pmenu.getParentid();
        this.displayorder = pmenu.getOrder();
        // this.parentLabel = parentLabel;

    }

    public Integer getMenuSeq() {
        return displayorder;
    }

    public void setMenuSeq(Integer displayorder) {
        this.displayorder = displayorder;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public MenuTreeNodeView() {
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuAction() {
        return menuAction;
    }

    public void setMenuAction(String menuAction) {
        this.menuAction = menuAction;
    }

    public String getMenuLabel() {
        return menuLabel;
    }

    public void setMenuLabel(String menuLabel) {
        this.menuLabel = menuLabel;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getMenuView() {
        return menuView;
    }

    public void setMenuView(String menuView) {
        this.menuView = menuView;
    }

    public String getParentLabel() {
        return parentLabel;
    }

    public void setParentLabel(String parentLabel) {
        this.parentLabel = parentLabel;
    }

    public List<MenuTreeNodeView> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTreeNodeView> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "MenuTreeNodeView{" +
                "menuId='" + menuId + '\'' +
                ", menuAction='" + menuAction + '\'' +
                ", menuLabel='" + menuLabel + '\'' +
                ", relativePath='" + relativePath + '\'' +
                ", isLeaf='" + isLeaf + '\'' +
                ", menuView='" + menuView + '\'' +
                ", parentLabel='" + parentLabel + '\'' +
                ", children=" + children +
                '}';
    }
}

