package com.elasticbox.jenkins.model.box;

import com.elasticbox.jenkins.model.AbstractModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by serna on 11/27/15.
 */
public class AbstractBox  extends AbstractModel {

    private String name;
    private String owner;
    private BoxType type;

    public AbstractBox(String id, String name, BoxType boxType) {
        super(id);
        this.name = name;
        this.type = boxType;
    }

    public String getName() {
        return name;
    }

    public BoxType getType() {
        return type;
    }

}