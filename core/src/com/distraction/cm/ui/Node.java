package com.distraction.cm.ui;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    public Node<T> prev;
    public List<Node<T>> next;
    public T data;
    public Node(T data){
        this.data = data;
        next = new ArrayList<Node<T>>();
    }
}
