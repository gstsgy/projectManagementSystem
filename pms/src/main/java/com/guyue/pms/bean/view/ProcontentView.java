package com.guyue.pms.bean.view;

import com.guyue.pms.bean.db.Procontent;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProcontentView extends Procontent {
    private List<Map> subtypeenum;

    public List<Map> getSubtypeenum() {
        return subtypeenum;
    }

    public void setSubtypeenum(List<Map> subtypeenum) {
        this.subtypeenum = subtypeenum;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Procontent that = (Procontent) o;
        return Objects.equals(this.getInfoid(), that.getInfoid()) &&
                Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getInfoid());
    }


}
