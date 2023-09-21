package com.entity;

import com.utils.Annotation;
import com.utils.Constants;

import java.io.Serializable;

public class CpgCovArgs implements Serializable {
    @Annotation(Constants.BIGWIG_DESCRIPTION)
    public String bigwig = "";
    @Annotation(Constants.CPGPATH_DESCRIPTION)
    public String cpgPath = "";
    @Annotation(Constants.BEDPATH_DESCRIPTION)
    public String bedPath = "";
//    @Annotation(Constants.OPENCHROMATIN_DESCRIPTION)
//    public String openChromatin = "";
//    @Annotation(Constants.CHIPSEQBIGWIG_DESCRIPTION)
//    public String chipseqBigwig = "";
    @Annotation(Constants.TAG_DESCRIPTION)
    public String tag = "";
//    @Annotation(Constants.MISSINGDATAASZERO_DESCRIPTION)
//    public Boolean missingDataAsZero = false;
    @Annotation(Constants.RATIO)
    public Integer ratio ;
    public String getBigwig() {
        return bigwig;
    }

    public void setBigwig(String bigwig) {
        this.bigwig = bigwig;
    }

    public String getCpgPath() {
        return cpgPath;
    }

    public void setCpgPath(String cpgPath) {
        this.cpgPath = cpgPath;
    }

    public String getBedPath() {
        return bedPath;
    }

    public void setBedPath(String bedPath) {
        this.bedPath = bedPath;
    }

//    public String getOpenChromatin() {
//        return openChromatin;
//    }
//
//    public void setOpenChromatin(String openChromatin) {
//        this.openChromatin = openChromatin;
//    }
//
//    public String getChipseqBigwig() {
//        return chipseqBigwig;
//    }
//
//    public void setChipseqBigwig(String chipseqBigwig) {
//        this.chipseqBigwig = chipseqBigwig;
//    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

//    public Boolean getMissingDataAsZero() {
//        return missingDataAsZero;
//    }
//
//    public void setMissingDataAsZero(Boolean missingDataAsZero) {
//        this.missingDataAsZero = missingDataAsZero;
//    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }
}
