package com.guyue.pms.bean.view;

import com.guyue.pms.bean.db.Procontent;
import com.guyue.pms.bean.db.Proinfo;

import java.util.List;
import java.util.Map;

public class ProinfoView extends Proinfo {
    private List<ProcontentView> children;

    public List<ProcontentView> getChildren() {
        return children;
    }

    public void setChildren(List<ProcontentView> children) {
        this.children = children;
    }


}
