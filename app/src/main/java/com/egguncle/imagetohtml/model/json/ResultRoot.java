package com.egguncle.imagetohtml.model.json;


import java.io.Serializable;
import java.util.List;

/**
 * Created by egguncle on 17-5-24.
 */

public class ResultRoot implements Serializable{

    private boolean error;

    private List<ResultHtmlImage> results;

    public void setError(boolean error){
        this.error = error;
    }
    public boolean getError(){
        return this.error;
    }
    public void setResults(List<ResultHtmlImage> results){
        this.results = results;
    }
    public List<ResultHtmlImage> getResults(){
        return this.results;
    }
}