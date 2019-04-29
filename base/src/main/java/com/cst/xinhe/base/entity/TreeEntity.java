/**
 * Copyright &copy; 2015-2020 <a href="http://www.our.com/">SunWebStar</a> All rights reserved.
 */
package com.cst.xinhe.base.entity;


import java.util.List;

/**
 * @param <T>
 */
public abstract class TreeEntity<T> extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private T parent;    // 父级，递归到最顶层节点,其中不会找每个节点的子节点

    private boolean hasChildren;//是否有子节点
    private List<T> children;//所有子节点，注意子节点向下只找一层

    public T getParent() {
        return parent;
    }

    public void setParent(T parent) {
        this.parent = parent;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
