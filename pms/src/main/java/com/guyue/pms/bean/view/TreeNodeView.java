package com.guyue.pms.bean.view;

import java.util.List;
import java.util.function.Function;

/**
 * @Description 权限树结构
 * @auther hujun
 * @create 2020-04-14 13:33
 */
public class TreeNodeView {
    private int level;   // 权限级别 0 1 2
    private Long parentVal; // 父级权限
    private String title; //   标题
    private Long value; //   标题
    private Boolean expand; //      是否展开直子节点
    // private Boolean disabled; //  禁掉响应
    private Boolean disableCheckbox; // 	禁掉 checkbox
    private Boolean selected; //  是否选中子节点
    private Boolean checked; //  是否勾选(如果勾选，子节点也会全部勾选)
    private List<TreeNodeView> children; //      子节点属性数组
   // private Function render; //  自定义当前节点渲染内容，见示例



    public TreeNodeView() {
    }

    public TreeNodeView(String title,Long value) {
        this.title = title;
        this.value = value;
    }
    public TreeNodeView(int level,Long parentVal,String title,Long value) {
        this.level = level;
        this.parentVal = parentVal;
        this.title = title;
        this.value = value;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Long getParentVal() {
        return parentVal;
    }

    public void setParentVal(Long parentVal) {
        this.parentVal = parentVal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Boolean getExpand() {
        return expand;
    }

    public void setExpand(Boolean expand) {
        this.expand = expand;
    }

   /* public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }*/

    public Boolean getDisableCheckbox() {
        return disableCheckbox;
    }

    public void setDisableCheckbox(Boolean disableCheckbox) {
        this.disableCheckbox = disableCheckbox;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public List<TreeNodeView> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNodeView> children) {
        this.children = children;
    }



    @Override
    public String toString() {
        return "TreeNodeView{" +
                "title='" + title + '\'' +
                ", value='" + value + '\'' +
                ", expand=" + expand +
                //    ", disabled=" + disabled +
                ", disableCheckbox=" + disableCheckbox +
                ", selected=" + selected +
                ", checked=" + checked +
                ", children=" + children +
              //  ", render=" + render +
                '}';
    }
}

